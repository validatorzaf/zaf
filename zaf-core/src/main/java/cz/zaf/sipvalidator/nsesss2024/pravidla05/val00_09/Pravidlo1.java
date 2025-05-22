package cz.zaf.sipvalidator.nsesss2024.pravidla05.val00_09;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;
import cz.zaf.sipvalidator.sip.SipInfo;

public class Pravidlo1 extends PravidloBase {

	static final public String VAL1 = "val1";
	static final public String VAL1_TEXT = "Soubor je validní proti schématům mets.xsd (v1.12), xlink.xsd (v2), nsesss.xsd (v4), nsesss-TrP.xsd.";
	static final public String VAL1_ZDROJ = "Požadavek 9.2.8 NSESSS; bod 1.1 přílohy č. 2 NSESSS.";
	static final public String VAL1_POPIS_CHYBY = "Datový balíček SIP není validní proti schématům http://www.loc.gov/standards/mets/mets.xsd, http://www.loc.gov/standards/mets/xlink.xsd, http://www.mvcr.cz/nsesss/v4/nsesss.xsd a https://www.mvcr.cz/nsesss/v4/nsesss-TrP.xsd.";

	public Pravidlo1() {
		super(VAL1, VAL1_TEXT, VAL1_POPIS_CHYBY, VAL1_ZDROJ);
	}

	@Override
	protected void kontrola() {
		var schema = SchemaResourceLoader.getCombined(
				XLinkNS.SCHEMA_RESOURCE,
				MetsNS.SCHEMA_RESOURCE_V_1_12_1,
				"/schema/nsesss/2024/dmBaseTypes.xsd",
				//"/schema/nsesss/2024/nsesss-common.xsd",
				"/schema/nsesss/2024/nsesss.xsd",
				"/schema/nsesss/2024/nsesss-TrP.xsd"
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
