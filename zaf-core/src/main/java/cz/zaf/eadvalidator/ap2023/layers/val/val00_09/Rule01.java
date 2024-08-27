package cz.zaf.eadvalidator.ap2023.layers.val.val00_09;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.ead.EadNS;
import cz.zaf.sipvalidator.sip.SipInfo;

public class Rule01 extends EadRule {
	
	static final public String CODE = "val1";
	
	public Rule01() {
		super(CODE,
				"Soubor je validní proti schématu ead3.xsd a cam.",
				"Soubor není validní proti příslušným schématům.",
				"Část 1.1.3 profilu EAD3 MV ČR");
	}

	@Override
	protected void evalImpl() {
		var schema = SchemaResourceLoader.get(EadNS.SCHEMA_RESOURCE);
		var validator = schema.newValidator();
		validator.setErrorHandler(new ValidationRuleErrorHandler());
		
		// TODO: User already parsed document instead of new parsing
		//DOMSource source = new DOMSource(ctx.getDocument());
		//try {        
        try (InputStream is = Files.newInputStream(ctx.getLoader().getFilePath())) {
            Source source = new StreamSource(is);
		
			validator.validate(source);
		} catch (SAXException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba SAX", e);			
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba IO", e);
		}
	}

}
