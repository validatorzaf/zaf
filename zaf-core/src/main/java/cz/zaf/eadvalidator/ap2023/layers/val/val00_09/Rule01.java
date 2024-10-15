package cz.zaf.eadvalidator.ap2023.layers.val.val00_09;

import java.io.IOException;
import javax.xml.transform.dom.DOMSource;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.cam.CamNS;
import cz.zaf.schemas.ead.EadNS;

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
		var schema = SchemaResourceLoader.getCombined(EadNS.SCHEMA_RESOURCE, CamNS.SCHEMA_RESOURCE);
		var validator = schema.newValidator();
		validator.setErrorHandler(new ValidationRuleErrorHandler());
		
		DOMSource source = new DOMSource(ctx.getRootElement());
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
		
		if(ctx.getLoader().getEad()==null) {
			// toto by nemelo nastat
			throw new ZafException(BaseCode.NEZNAMA_CHYBA, "Po načtení pomocí JAXB chybí kořenový objekt");
		}
		
	}

}
