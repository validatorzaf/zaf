
package cz.zaf.common.result;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.xml.XMLConstants;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.ctc.wstx.api.WstxInputProperties;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

import cz.zaf.common.ZafInfo;
import cz.zaf.schema.validation_v2.ObjectFactory;
import cz.zaf.schema.validation_v2.TCheck;
import cz.zaf.schema.validation_v2.TCheckResult;
import cz.zaf.schema.validation_v2.TPackage;
import cz.zaf.schema.validation_v2.TRule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class XmlProtokolWriterV2 implements ProtokolWriter
 {
    
    public static final String SCHEMA_URL = "https://stands.nacr.cz/validation_zaf/v2";
    
    private static Logger logger = LoggerFactory.getLogger(XmlProtokolWriterV2.class);
    
    public static final ObjectFactory objectFactory = new ObjectFactory();

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
        try (InputStream is = XmlProtokolWriterV2.class.getClassLoader()
                .getResourceAsStream("schema/validace_v1.xsd")) {
            schema = sf.newSchema(new StreamSource(is));
        } catch (IOException | SAXException e) {
            throw new RuntimeException("Failed to load internal XSD", e);
        }
        
        try {
            jaxbContext = JAXBContext.newInstance(TPackage.class);
        } catch (JAXBException e) {
            logger.error("Failed to initialize JAXBContext", e);
            throw new IllegalStateException("Failed to initialize JAXBContext", e);
        }
    }
    
    private final ValidationProfileInfo profileInfo;
    
    public XmlProtokolWriterV2(final String outputPath,
                             final String kontrolaId,
                             final ValidationProfileInfo profileInfo) throws IOException, JAXBException,
            XMLStreamException,
            DatatypeConfigurationException {
        this.profileInfo = profileInfo;

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
        indentingStreamWriter.writeStartElement("validace");
        indentingStreamWriter.setDefaultNamespace(SCHEMA_URL);
        indentingStreamWriter.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
         indentingStreamWriter.writeAttribute("xmlns", SCHEMA_URL);
        indentingStreamWriter.writeAttribute("xmlns", null,
                                             "xsi", "http://www.w3.org/2001/XMLSchema-instance");
        indentingStreamWriter.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", 
                                             "https://stands.nacr.cz/validace_zaf/v1 https://stands.nacr.cz/validace_zaf/v1/validace_v1.xsd");

        // write attributes to root element
        indentingStreamWriter.writeAttribute("identifikatorValidace",
                                             kontrolaId == null ? UUID.randomUUID().toString() : kontrolaId);
    
        pripravAppInfo();
        pripravCas();
        
        /*
        if(!pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_S_KOMPONENTAMI)&&
                !pouzitiKontroly.equals(TPouzitiKontroly.PŘEJÍMKA)
                ) {
            pouzitiKontroly = TPouzitiKontroly.SKARTAČNÍ_ŘÍZENÍ_JEN_METADATA.value();
        }*/
    
    }
    
    private void pripravCas() throws DatatypeConfigurationException, XMLStreamException {
        Instant now = Instant.now();
        String dateTimeString = now.toString();
        XMLGregorianCalendar datumKontroly = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        
        indentingStreamWriter.writeAttribute("datumValidace", datumKontroly.toXMLFormat());
    }

    private void pripravAppInfo() throws IOException, XMLStreamException {
        // read default version from app
        /*final Properties properties = new Properties();
        try (InputStream r = getClass().getClassLoader().getResourceAsStream("zaf-core.properties")) {
            properties.load(r);
        }
        String verzeApp = properties.getProperty("version");
        String artifactId = properties.getProperty("artifactId");
        */

        indentingStreamWriter.writeAttribute("nazevAplikace", ZafInfo.getAppName());
        indentingStreamWriter.writeAttribute("verzeAplikace", ZafInfo.getAppVersion());
        indentingStreamWriter.writeAttribute("typValidace", profileInfo.getValidationType());
        indentingStreamWriter.writeAttribute("profilValidace", profileInfo.getProfileName());
        indentingStreamWriter.writeAttribute("verzePravidel", profileInfo.getRuleVersion());        
    }
    

    @Override
    public void writeVysledek(ValidationResult sipInfo) throws JAXBException {
        
        var balicek = convert(sipInfo);
        
        var jaxbElement = new JAXBElement<>(new QName(SCHEMA_URL, "package"), TPackage.class, balicek);
        marshaller.marshal(jaxbElement, indentingStreamWriter);

    }
    
    private static void writeBalicekInfo(TPackage balicekNode,
                                         ValidationResult sipInfo) {
        String metsObjId = sipInfo.getValidatedObjectId();
        if (StringUtils.isNotEmpty(metsObjId)) {
            balicekNode.setIdentifier(metsObjId);
        }

        balicekNode.setFileName(sipInfo.getValidatedObjectName());

    }

    private TPackage convert(ValidationResult sipInfo) {
        var balicek = objectFactory.createTPackage();
        writeBalicekInfo(balicek, sipInfo);
        
        List<ValidationLayerResult> kontroly = sipInfo.getValidationLayerResults();
        for (ValidationLayerResult vysl : kontroly) {
            var kontrolaXml = convert(vysl);
            balicek.getCheck().add(kontrolaXml);
        }        
        return balicek;
    }
    
    private static TCheck convert(ValidationLayerResult vysl) {
        var kontrolaXml = objectFactory.createTCheck();
        kontrolaXml.setName(vysl.getValidationName());
        kontrolaXml.setStatus(convert(vysl.getValidationStatus()));
        kontrolaXml.setEmbeddedFile(vysl.getInnerFileName());

        // prevod pravidel
        for (RuleValidationError pravidlo : vysl.getPravidla()) {
            var pravNode = convert(pravidlo);
            kontrolaXml.getRule().add(pravNode);
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
    private static TRule convert(RuleValidationError pravidlo) {
        var pravNode = objectFactory.createTRule();
        pravNode.setCode(pravidlo.getId());
        pravNode.setText(pravidlo.getTextPravidla());
        pravNode.setSource(pravidlo.getZdroj());
        pravNode.setErrorDescription(pravidlo.getPopisChybyObecny());
        pravNode.setErrorReport(pravidlo.getVypisChyby());
        pravNode.setErrorLocation(pravidlo.getMistoChyby());
        pravNode.setErrorCode(pravidlo.getKodChyby().getErrorCode());

        pravidlo.writeDetail(pravNode);
        return pravNode;
    }

    private static TCheckResult convert(ValidationStatus stavKontroly) {
        switch (stavKontroly) {
        case ERROR:
            return TCheckResult.ERROR;
        case NOT_EXCECUTED:
            return TCheckResult.NOT_EXECUTED;
        case OK:
            return TCheckResult.OK;
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
