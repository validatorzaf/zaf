package cz.zaf.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.stream.XMLInputFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import jakarta.xml.bind.JAXBContext;

/**
 * Generic XML document loader
 */
public class XmlDocumentLoader {
	
	static private final Logger log = LoggerFactory.getLogger(XmlDocumentLoader.class);
	
	static private JAXBContext jaxbContext;
	
	static private XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
	
	private Exception parserError;
	
	private Document document;

	final private Path sourceFile;
	
	public XmlDocumentLoader(final Path sourceFile) {
		this.sourceFile = sourceFile;
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
}
