package cz.zaf.premisvalidator.layers.val.val00_09;

import java.io.IOException;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schemas.premis.PremisNS;

public class Rule01 extends PremisRule {
	public static final String CODE = "val01";
	public static final String RULE_TEXT = "Soubor MUSÍ odpovídat oficiálnímu schématu PREMIS, verze 3.0.";
	public static final String RULE_ERROR = "Soubor neodpovídá schématu PREMIS.";
	public static final String RULE_SOURCE = "CZDAX-PMT0002";

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
		var schema = SchemaResourceLoader.getCombined(PremisNS.SCHEMA_RESOURCE);
		var validator = schema.newValidator();
		validator.setErrorHandler(new ValidationRuleErrorHandler());
		
		//StreamSource source = new StreamSource(ctx.getActiveFile().toFile());
		// DOMSource source = new DOMSource(ctx.getActiveFile().toFile());
		DOMSource source = new DOMSource(ctx.getLoader().getDocument());
		try {        		
			validator.validate(source);
		} catch (SAXException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba SAX", e);			
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba IO", e);
		}
		try {
			// Parse to JAXB
			ctx.getLoader().loadJaxb();
		} catch(Exception e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba JAXB", e);
		}
		
		if(ctx.getLoader().getRootObj()==null) {
			// toto by nemelo nastat
			throw new ZafException(BaseCode.NEZNAMA_CHYBA, "Po načtení pomocí JAXB chybí kořenový objekt");
		}
		
	}

}
