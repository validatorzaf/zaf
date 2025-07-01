package cz.zaf.eadvalidator.ap2023;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;
import cz.zaf.common.validation.ValidationInput;
import cz.zaf.common.xml.PositionalXMLReader2;
import cz.zaf.schema.ead3.Ead;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller.Listener;

public class EadLoader implements Closeable, ValidationInput {
	
	private static Logger log = LoggerFactory.getLogger(EadLoader.class);

    static private JAXBContext ead3Context;
    {
    	try {
    		ead3Context = JAXBContext.newInstance(Ead.class);
    	} catch(JAXBException e) {
    		throw new RuntimeException("Failed to initialize JAXB", e);
    	}
    }
    static XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

    private final Path filePath;
    private Exception parserError;

    /**
     * Validation result
     */
    private ValidationResult validationResult;

    private Document document;

    /**
     * Loaded EAD Object using JAXB
     */
	private Ead ead;

	/**
	 * Map unmarshalled object to the original location
	 */
	private Map<Object, Location> eadToLocationMap;

    public EadLoader(final Path filePath) {
        this(filePath, new ValidationResultImpl(null, filePath.getFileName().toString()));
    }

    public EadLoader(Path filePath, ValidationResult validationResult) {
        Objects.requireNonNull(filePath);

        this.filePath = filePath;
        this.validationResult = validationResult;
	}

	public Path getFilePath() {
        return filePath;
    }

    public Exception getParserError() {
        return parserError;
    }

    public Document getDocument() {
        return document;
    }

    /**
     * Load EAD from file
     * 
     * Return loaded document or null if failed
     */
    public Document load() {
    	log.debug("Parsing EAD, file: {}", filePath);
    	
    	// reset previous document (if any)
    	document = null;
    	
        try (InputStream is = Files.newInputStream(filePath)) {
            PositionalXMLReader2 xmlReader = new PositionalXMLReader2();
            document = xmlReader.readXML(is);
        } catch (SAXException e) {
            parserError = e;
            log.error("Parsing failed, file: {}", filePath, e);
        } catch (IOException e) {        	
            parserError = e;
            log.error("Reading failed, file: {}", filePath, e);
        }
        return document;
    }

    @Override
    public void close() throws IOException {
        document = null;
        parserError = null;
    }

    public ValidationResult getResult() {
        return validationResult;
    }

    /**
     * Load object using JAXB
     * @throws JAXBException 
     * @throws XMLStreamException 
     * @throws IOException 
     */
	public void loadJaxb() throws JAXBException, XMLStreamException, IOException {
		Objects.requireNonNull(document);
		
		final Map<Object, Location> jaxbToLocationMap = new HashMap<>();
		
		var unmarshaller = ead3Context.createUnmarshaller();
		
		// Create XMLEvent stream which can be monitored during unmarshalling
		try(InputStream fis = Files.newInputStream(filePath)) {
			XMLStreamReader xsr = xmlInputFactory.createXMLStreamReader(fis);
			
			unmarshaller.setListener(new Listener() {
				public void beforeUnmarshal(Object target, Object parent) {
					Location location = xsr.getLocation();
					if(location!=null) {
						jaxbToLocationMap.put(target, location);
					}
					
				}
			});
			
			JAXBElement<Ead> eadObject = unmarshaller.unmarshal(xsr, Ead.class);
			this.ead = eadObject.getValue();
			this.eadToLocationMap = jaxbToLocationMap;
		}				
	}
	
	public Ead getEad() {
		return ead;
	}
	
	public Location getEadLocation(Object obj) {
		Objects.requireNonNull(this.eadToLocationMap);
		return eadToLocationMap.get(obj);
	}

}
