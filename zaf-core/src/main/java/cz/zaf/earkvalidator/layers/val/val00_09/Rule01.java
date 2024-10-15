package cz.zaf.earkvalidator.layers.val.val00_09;

import java.io.IOException;

import javax.xml.transform.dom.DOMSource;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;

public class Rule01 extends AipRule {
	public static final String CODE = "val01";
	public static final String RULE_TEXT = "Soubor METS.xml v informačním balíčku MUSÍ odpovídat oficiálnímu schématu METS, verze 1.12.1.";
	public static final String RULE_ERROR = "Soubor neodpovídá schématu.";
	public static final String RULE_SOURCE = "CZDAX-PMT0002";

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		
		var schema = SchemaResourceLoader.getCombined(XLinkNS.SCHEMA_RESOURCE, CSIPExtensionMETS_NS.SCHEMA_RESOURCE, MetsNS.SCHEMA_RESOURCE_V_1_12_1);
		var validator = schema.newValidator();
		validator.setErrorHandler(new ValidationRuleErrorHandler());
		
		DOMSource source = new DOMSource(ctx.getMetsRootElement());
		try {        		
			validator.validate(source);
		} catch (SAXException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba SAX", e);			
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba IO", e);
		}
		try {
			// Parse to JAXB
			ctx.getLoader().loadMetsJaxb();
		} catch(Exception e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba JAXB", e);
		}
		
		if(ctx.getLoader().getMets()==null) {
			// toto by nemelo nastat
			throw new ZafException(BaseCode.NEZNAMA_CHYBA, "Po načtení pomocí JAXB chybí kořenový objekt");
		}
		
	}

}
