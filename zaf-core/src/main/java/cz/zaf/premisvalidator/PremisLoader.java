package cz.zaf.premisvalidator;

import java.nio.file.Path;
import java.util.Objects;

import javax.xml.stream.Location;

import cz.zaf.common.xml.XmlDocumentLoader;
import cz.zaf.schema.premis3.PremisComplexType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public class PremisLoader extends XmlDocumentLoader<PremisComplexType> {

	static private JAXBContext premisJaxbContext;
    static {
    	try {
    		premisJaxbContext = JAXBContext.newInstance(PremisComplexType.class);
    	} catch(JAXBException e) {
    		throw new RuntimeException("Failed to initialize JAXB", e);
    	}
    }
	
	public PremisLoader(final Path sourceFile) {
		super(sourceFile, PremisLoader.premisJaxbContext, PremisComplexType.class);
	}
}
