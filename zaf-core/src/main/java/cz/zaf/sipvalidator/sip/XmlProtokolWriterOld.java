package cz.zaf.sipvalidator.sip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.XMLConstants;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.ctc.wstx.api.WstxInputProperties;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import cz.zaf.schema.validacesip.ObjectFactory;
import cz.zaf.schema.validacesip.TEntity;
import cz.zaf.schema.validacesip.TIdentifikator;
import cz.zaf.schema.validacesip.TKontrola;
import cz.zaf.schema.validacesip.TPravidlo;
import cz.zaf.schema.validacesip.TSip;
import cz.zaf.schema.validacesip.TTypEntity;
import cz.zaf.schema.validacesip.TVysledekKontroly;
import cz.zaf.sipvalidator.nsesss2017.EntityId;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;

public class XmlProtokolWriterOld implements ProtokolWriter
{
    
    public static String XML_PREFIX = "kontrolasip";
    public static String SCHEMA_URL = "http://www.ahmp.cz/schema/validacesip/v1";
    
    private static Logger logger = LoggerFactory.getLogger(XmlProtokolWriterOld.class);
    
    final static ObjectFactory objectFactory = new ObjectFactory();

    private OutputStream outputStream;
    boolean closeOutputStream = false;
    private Path outputPath;
    Marshaller marshaller;

    private XMLStreamWriter xmlStreamWriter;

    private IndentingXMLStreamWriter indentingStreamWriter;
    
    static JAXBContext jaxbContext;
    static Schema schema;
    
    {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (InputStream is = XmlProtokolWriterOld.class.getClassLoader()
                .getResourceAsStream("schema/validaceSIP.xsd")) {
            schema = sf.newSchema(new StreamSource(is));
        } catch (IOException | SAXException e) {
            throw new RuntimeException("Failed to load internal XSD", e);
        }
        
        try {
            jaxbContext = JAXBContext.newInstance(TSip.class);
        } catch (JAXBException e) {
            logger.error("Failed to initialize JAXBContext", e);
            throw new IllegalStateException("Failed to initialize JAXBContext", e);
        }
    }

    public XmlProtokolWriterOld(final String outputPath,
                             final String kontrolaId,
                             final ProfilValidace profilValidace) throws IOException, JAXBException, XMLStreamException,
            DatatypeConfigurationException {
        if (StringUtils.isNoneBlank(outputPath)) {
            this.outputPath = Paths.get(outputPath);
            if (Files.isDirectory(this.outputPath)) {
                // create file name
                this.outputPath = this.outputPath.resolve("protokol.xml");
            }
            outputStream = Files.newOutputStream(this.outputPath);
            closeOutputStream = true;
        } else {
            outputStream = System.out;
        }

        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty(WstxInputProperties.P_RETURN_NULL_FOR_DEFAULT_NAMESPACE, Boolean.TRUE);

        xmlStreamWriter = outputFactory.createXMLStreamWriter(outputStream);
        indentingStreamWriter = new IndentingXMLStreamWriter(xmlStreamWriter);
        indentingStreamWriter.setIndentStep("    ");
        
        indentingStreamWriter.writeStartDocument();
        indentingStreamWriter.writeStartElement("validaceSIP");
        indentingStreamWriter.setDefaultNamespace(SCHEMA_URL);
        indentingStreamWriter.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
         indentingStreamWriter.writeAttribute("xmlns", SCHEMA_URL);
        indentingStreamWriter.writeAttribute("xmlns", null,
                                             "xsi", "http://www.w3.org/2001/XMLSchema-instance");
        indentingStreamWriter.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", 
                                             "http://www.ahmp.cz/schema/validacesip/v1 http://www.ahmp.cz/schema/validacesip/v1/validaceSIP.xsd");

        // write attributes to root element
        indentingStreamWriter.writeAttribute("validaceID",
                                             kontrolaId == null ? UUID.randomUUID().toString() : kontrolaId);
        
        pripravAppInfo();
        pripravCas();
        
        String pouzitiKontroly = profilValidace.getNazev();
        /*
        if(!pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_S_KOMPONENTAMI)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.PŘEJÍMKA)
                ) {
            pouzitiKontroly = TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA.value();
        }*/
    
        indentingStreamWriter.writeAttribute("druhValidace", pouzitiKontroly);
    }
    
    private void pripravCas() throws DatatypeConfigurationException, XMLStreamException {
        Instant now = Instant.now();
        String dateTimeString = now.toString();
        XMLGregorianCalendar datumKontroly = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        
        indentingStreamWriter.writeAttribute("datumValidace", datumKontroly.toXMLFormat());
    }

    private void pripravAppInfo() throws IOException, XMLStreamException {
        // read default version from app
        final Properties properties = new Properties();
        try (InputStream r = getClass().getClassLoader().getResourceAsStream("zaf-core.properties")) {
            properties.load(r);
        }
        String verzeApp = properties.getProperty("version");
        String artifactId = properties.getProperty("artifactId");

        indentingStreamWriter.writeAttribute("nazevAplikace", artifactId);
        indentingStreamWriter.writeAttribute("verzeAplikace", verzeApp);
        indentingStreamWriter.writeAttribute("verzePravidel", NsessV3.ZAF_RULE_VERSION);
    }
    

    @Override
    public void writeVysledek(SipInfo sipInfo) throws JAXBException {
        
        TSip sip = convert(sipInfo);
        
        JAXBElement<TSip> jaxbElement = new JAXBElement<>(new QName(SCHEMA_URL, "sip"), TSip.class, sip);
        marshaller.marshal(jaxbElement, indentingStreamWriter);

    }
    
    private static void writeSipInfo(TSip sipNode, SipInfo sipInfo) {
        String metsObjId = sipInfo.getMetsObjId();
        if (StringUtils.isNotEmpty(metsObjId)) {
            sipNode.setOBJID(metsObjId);
        }

        String g = sipInfo.getNameZip();
        if (g == null) {
            g = sipInfo.getName();
        }
        sipNode.setNazevSouboru(g);

    }

    private TSip convert(SipInfo sipInfo) {
        TSip sip = objectFactory.createTSip();
        writeSipInfo(sip, sipInfo);
        
        List<VysledekKontroly> kontroly = sipInfo.getSeznamKontrol();
        for (VysledekKontroly vysl : kontroly) {
            TKontrola kontrolaXml = convert(vysl);
            sip.getKontrola().add(kontrolaXml);
        }        
        return sip;
    }
    
    private static TKontrola convert(VysledekKontroly vysl) {
        TKontrola kontrolaXml = objectFactory.createTKontrola();
        kontrolaXml.setNazev(vysl.getKontrola_nazev());
        kontrolaXml.setStav(convert(vysl.getStavKontroly()));

        // prevod pravidel
        for (ChybaPravidla pravidlo : vysl.getPravidla()) {
            TPravidlo pravNode = convert(pravidlo);
            kontrolaXml.getPravidlo().add(pravNode);
        }
        return kontrolaXml;
    }
    
    /**
     * Konverze jednotliveho chybujiciho pravidla
     * 
     * @param pravidlo
     *            vysledek pravidla
     * @return Prevedene pravidlo
     */
    private static TPravidlo convert(ChybaPravidla pravidlo) {
        TPravidlo pravNode = objectFactory.createTPravidlo();
        pravNode.setKod(pravidlo.getId());
        pravNode.setZneni(pravidlo.getTextPravidla());
        pravNode.setZdroj(pravidlo.getZdroj());
        pravNode.setPopisChyby(pravidlo.getPopisChybyObecny());
        pravNode.setVypisChyby(pravidlo.getVypisChyby());
        pravNode.setMistoChyby(pravidlo.getMistoChyby());
        pravNode.setKodChyby(pravidlo.getKodChyby().getErrorCode());

        List<EntityId> entityIds = pravidlo.getEntityIds();
        if (CollectionUtils.isNotEmpty(entityIds)) {
            TEntity entityNode = objectFactory.createTEntity();
            List<TIdentifikator> idents = entityNode.getIdentifikator();
            for (EntityId entityId : entityIds) {
                TIdentifikator ident = objectFactory.createTIdentifikator();
                ident.setZdroj(entityId.getZdrojIdent().getZdroj());
                ident.setValue(entityId.getZdrojIdent().getIdentifikator());
                TTypEntity typEntity = null;
                switch (entityId.getDruhEntity()) {
                case SPISOVY_PLAN:
                    typEntity = TTypEntity.SPISOVÝ_PLÁN;
                    break;
                case VECNA_SKUPINA:
                    typEntity = TTypEntity.VĚCNÁ_SKUPINA;
                    break;
                case DIL:
                    typEntity = TTypEntity.DÍL;
                    break;
                case SPIS:
                    typEntity = TTypEntity.SPIS;
                    break;
                case DOKUMENT:
                    typEntity = TTypEntity.DOKUMENT;
                    break;
                case SOUCAST:
                    typEntity = TTypEntity.SOUČÁST;
                    break;
                case TYPOVY_SPIS:
                case KOMPONENTA:
                case SKARTACNI_RIZENI:
                default:
                    continue;
                }
                if (typEntity == null) {
                    continue;
                }
                ident.setTyp(typEntity);

                idents.add(ident);
            }
            if (idents.size() > 0) {
                pravNode.setEntity(entityNode);
            }
        }
        return pravNode;
    }

    private static TVysledekKontroly convert(StavKontroly stavKontroly) {
        switch (stavKontroly) {
        case CHYBA:
            return TVysledekKontroly.CHYBA;
        case NESPUSTENA:
            return TVysledekKontroly.NESPUSTENA;
        case OK:
            return TVysledekKontroly.OK;
        }
        throw new IllegalStateException("Failed to convert :" + stavKontroly);
    }

    @Override
    public void close() throws Exception {
        indentingStreamWriter.writeEndElement();
        indentingStreamWriter.writeEndDocument();
        xmlStreamWriter.close();
        
        if(closeOutputStream) {
            outputStream.close();
        }

        // TODO: Nutno dokoncit
        //  - pridat reseni pro zapis vysledku na standardni vystup
        // 
        // verify output
        if (this.outputPath != null) {
            try (InputStream in = Files.newInputStream(outputPath)) {
                Validator validator = schema.newValidator();
                validator.validate(new StreamSource(in));
            }
        }
    }

}
