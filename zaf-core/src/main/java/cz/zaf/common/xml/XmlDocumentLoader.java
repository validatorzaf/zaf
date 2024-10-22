package cz.zaf.common.xml;

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
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller.Listener;

/**
 * Generic XML document loader
 */
public class XmlDocumentLoader<T> {
	
	static private final Logger log = LoggerFactory.getLogger(XmlDocumentLoader.class);	
	
	static private XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
	
	private Exception parserError;
	
	private Document document;

	final private Path sourceFile;
	
	JAXBContext jaxbContext;
	
	/**
	 * Parsed object
	 */
	private T rootObj;
	private Class<T> rootObjClass;

	private Map<Object, Location> objToLocationMap; 
	
	public XmlDocumentLoader(final Path sourceFile, final JAXBContext jaxbContext, 
			final Class<T> rootObjClass) {
		this.sourceFile = sourceFile;
		this.jaxbContext = jaxbContext;
		this.rootObjClass = rootObjClass;
	}
	
	public Exception getParserError() {
		return parserError;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public Path getSourceFile() {
		return sourceFile;
	}

	public Document load() {
    	log.debug("Parsing file: {}", getSourceFile());
    	
    	// reset previous document (if any)
    	document = null;
    	
        try (InputStream is = Files.newInputStream(getSourceFile())) {
            PositionalXMLReader2 xmlReader = new PositionalXMLReader2();
            document = xmlReader.readXML(is);
        } catch (SAXException e) {
            parserError = e;
            log.error("Parsing failed, file: {}", getSourceFile(), e);
        } catch (IOException e) {        	
        	parserError = e;
            log.error("Reading failed, file: {}", getSourceFile(), e);
        }
        return document;
	}

	public Element getRootElement() {
		if(document == null) { 
			return null;
		}
		return document.getDocumentElement();
	}

	public void loadJaxb()  throws JAXBException, XMLStreamException, IOException {
		Objects.requireNonNull(document);
		
		final Map<Object, Location> jaxbToLocationMap = new HashMap<>();
		
		var unmarshaller = jaxbContext.createUnmarshaller();
		
		// Create XMLEvent stream which can be monitored during unmarshalling
		try(InputStream fis = Files.newInputStream(getSourceFile())) {
			XMLStreamReader xsr = xmlInputFactory.createXMLStreamReader(fis);
			
			unmarshaller.setListener(new Listener() {
				public void beforeUnmarshal(Object target, Object parent) {
					Location location = xsr.getLocation();
					if(location!=null) {
						jaxbToLocationMap.put(target, location);
					}
					
				}
			});
			
			JAXBElement<T> unmObject = unmarshaller.unmarshal(xsr, rootObjClass );
			this.rootObj = unmObject.getValue();
			this.objToLocationMap = jaxbToLocationMap;
		}
	}

	public T getRootObj() {
		return rootObj;
	}


	public Location getXmlLocation(Object object) {
		Objects.requireNonNull(this.objToLocationMap);
		return objToLocationMap.get(object);
	}
}
