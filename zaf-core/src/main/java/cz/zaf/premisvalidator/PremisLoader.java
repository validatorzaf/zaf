package cz.zaf.premisvalidator;

import java.nio.file.Path;

import cz.zaf.common.xml.XmlDocumentLoader;

public class PremisLoader extends XmlDocumentLoader {

	public PremisLoader(final Path sourceFile) {
		super(sourceFile);
	}

}
