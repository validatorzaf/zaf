package cz.zaf.sipvalidator.nsesss2017.pravidla05.val00_09;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.XmlCode;
import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.schemas.cam.CamNS;
import cz.zaf.schemas.ead.EadNS;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.sipvalidator.nsesss2017.PravidloBase;
import cz.zaf.sipvalidator.sip.SipInfo;

public class Pravidlo1 extends PravidloBase {

	static final public String VAL1 = "val1";
	static final public String VAL1_TEXT = "Soubor je validní proti schématům mets.xsd (v1.11), xlink.xsd (v2), nsesss.xsd (v3), nsesss-TrP.xsd, ess_ns.xsd a dmBaseTypes.xsd (v2.1).";
	static final public String VAL1_ZDROJ = "Požadavek 11.2.5 NSESSS, bod 2.1. přílohy č. 3 NSESSS.";
	static final public String VAL1_POPIS_CHYBY = "Datový balíček SIP není validní proti schématům http://www.loc.gov/standards/mets/mets.xsd, http://www.loc.gov/standards/mets/xlink.xsd, http://www.mvcr.cz/nsesss/v3/nsesss.xsd, http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd, http://www.mvcr.cz/nsesss/v3/ess_ns.xsd a http://www.mvcr.cz/nsesss/v3/dmBaseTypes.xsd.";

	public Pravidlo1() {
		super(VAL1, VAL1_TEXT, VAL1_POPIS_CHYBY, VAL1_ZDROJ);
	}

	

	@Override
	protected void kontrola() {
		var schema = SchemaResourceLoader.getCombined(
				XLinkNS.SCHEMA_RESOURCE,
				MetsNS.SCHEMA_RESOURCE_V_1_11,
				"/schema/nsesss/2017/dmBaseTypes.xsd",
				"/schema/nsesss/2017/nsesss.xsd",
				"/schema/nsesss/2017/ess_ns.xsd",
				"/schema/nsesss/2017/TransakcniProtokolNavrh_verze17.xsd"
				);
		Validator nasValidator = schema.newValidator();
		nasValidator.setErrorHandler(new ValidationRuleErrorHandler());
		
        SipInfo file = ctx.getContext().getSip();
        try (InputStream is = Files.newInputStream(file.getCestaMets())) {
            Source xmlFile = new StreamSource(is);
            
            nasValidator.validate(xmlFile);
        } catch (IOException e) {
            throw new ZafException(BaseCode.CHYBA, "Chyba IO", e);
        } catch (SAXException e) {
        	throw new ZafException(BaseCode.CHYBA, "Chyba SAX", e);
        }
 	}
}
