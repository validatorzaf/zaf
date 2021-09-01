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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.ctc.wstx.api.WstxInputProperties;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import cz.zaf.schema.kontrolasip.ObjectFactory;
import cz.zaf.schema.kontrolasip.TPouzitiKontroly;
import cz.zaf.schema.kontrolasip.TPravidlo;
import cz.zaf.schema.kontrolasip.TSip;
import cz.zaf.schema.kontrolasip.TTyp;
import cz.zaf.schema.kontrolasip.TVysledekKontroly;
import cz.zaf.schema.kontrolasip.TVysledekPravidla;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;

public class XmlProtokolWriter implements ProtokolWriter,
    AutoCloseable {
    
    public static String XML_PREFIX = "kontrolasip";
    public static String SCHEMA_URL = "http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v2";
    
    private static Logger logger = LoggerFactory.getLogger(XmlProtokolWriter.class);
    
    final static ObjectFactory objectFactory = new ObjectFactory();

    private OutputStream outputStream;
    boolean closeOutputStream = false;
    Marshaller marshaller;

    private XMLStreamWriter xmlStreamWriter;

    private IndentingXMLStreamWriter indentingStreamWriter;
    
    static JAXBContext jaxbContext;
    static Schema schema;
    
    {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try (InputStream is = XmlProtokolWriter.class.getClassLoader().getResourceAsStream("schema/kontrolasip.xsd")) {
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

    public XmlProtokolWriter(String outputPath, String kontrolaId, 
                             ProfilValidace profilValidace) throws IOException, JAXBException, XMLStreamException, DatatypeConfigurationException {
        if (StringUtils.isNoneBlank(outputPath)) {
            Path path = Paths.get(outputPath);
            if (Files.isDirectory(path)) {
                // create file name
                path = path.resolve("protokol.xml");
            }
            outputStream = Files.newOutputStream(path);
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
        indentingStreamWriter.writeStartElement("kontrolaSIP");
        indentingStreamWriter.setDefaultNamespace(SCHEMA_URL);
        indentingStreamWriter.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
         indentingStreamWriter.writeAttribute("xmlns", SCHEMA_URL);
        indentingStreamWriter.writeAttribute("xmlns", null,
                                             "xsi", "http://www.w3.org/2001/XMLSchema-instance");
        indentingStreamWriter.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", 
                                             "http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v2 http://digitalniarchiv.ahmp.cz/schema/kontrolasip/v2/kontrolasip.xsd");

        // write attributes to root element
        indentingStreamWriter.writeAttribute("kontrolaID", kontrolaId==null?UUID.randomUUID().toString():kontrolaId);
        
        pripravAppInfo();
        pripravCas();
        
        String pouzitiKontroly = profilValidace.getNazev();
        if(!pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_S_KOMPONENTAMI)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.PŘEJÍMKA)
                ) {
            pouzitiKontroly = TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA.value();
        }
    
        indentingStreamWriter.writeAttribute("pouzitiKontroly", pouzitiKontroly);
    }
    
    private void pripravCas() throws DatatypeConfigurationException, XMLStreamException {
        Instant now = Instant.now();
        String dateTimeString = now.toString();
        XMLGregorianCalendar datumKontroly = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        
        indentingStreamWriter.writeAttribute("datumKontroly", datumKontroly.toXMLFormat());
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
            sipNode.setSipID(metsObjId);
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
            TTyp typKontrolyNode = convert(vysl);
            sip.getTyp().add(typKontrolyNode);
        }        
        return sip;
    }
    
    private static TTyp convert(VysledekKontroly vysl) {
        TTyp typ = objectFactory.createTTyp();
        typ.setNazev(vysl.getKontrola_nazev());
        typ.setStav(convert(vysl.getStavKontroly()));

        // prevod pravidel
        for (VysledekPravidla pravidlo : vysl.getPravidla()) {
            TPravidlo pravNode = convert(pravidlo);
            typ.getPravidlo().add(pravNode);
        }
        return typ;
    }
    
    /**
     * Konverze jednotliveho pravidla
     * 
     * @param pravidlo
     *            vysledek pravidla
     * @return Prevedene pravidlo
     */
    private static TPravidlo convert(VysledekPravidla pravidlo) {
        TPravidlo pravNode = objectFactory.createTPravidlo();
        pravNode.setId(pravidlo.getId());
        pravNode.setText(pravidlo.getTextPravidla());
        pravNode.setZdroj(pravidlo.getZdroj());
        if (!pravidlo.getStav()) {
            pravNode.setStav(TVysledekPravidla.CHYBA);
            pravNode.setPopisChyby(pravidlo.getPopisChybyObecny());
            pravNode.setVypisChyby(pravidlo.getVypis_chyby());
            pravNode.setMistoChyby(pravidlo.getMisto_chyby());
        } else {
            pravNode.setStav(TVysledekPravidla.OK);
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
    }

}
