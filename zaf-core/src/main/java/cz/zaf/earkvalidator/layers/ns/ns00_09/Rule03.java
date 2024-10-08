package cz.zaf.earkvalidator.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;

public class Rule03 extends AipRule {
	public static final String CODE = "ns03";
	public static final String RULE_TEXT = "Kořenový element obsahuje doplňující schémata: xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\", xmlns:xlink=\"http://www.w3.org/1999/xlink\", xmlns:csip=\"https://DILCIS.eu/XML/METS/CSIPExtensionMETS\".";
	public static final String RULE_ERROR = "Chybí odkaz na některé z povinných schémat: xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\", xmlns:xlink=\"http://www.w3.org/1999/xlink\", xmlns:csip=\"https://DILCIS.eu/XML/METS/CSIPExtensionMETS\".";
	public static final String RULE_SOURCE = "CZDAX-PMT0101";

	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Element metsRoot = ctx.getMetsRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) metsRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		if(nsMapping==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezeno mapování pro namespace.");
		}

		// xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		String value = nsMapping.get("xsi");
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:xsi");
		}
		if(!EarkConstants.NS_XSI.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:xsi=\""+value+"\""); 
		}
		
		// xmlns:xlink="http://www.w3.org/1999/xlink"
		value = nsMapping.get("xlink");
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:xlink");
		}
		if(!EarkConstants.NS_XLINK.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:xlink=\""+value+"\""); 
		}
		
		// xmlns:csip="https://DILCIS.eu/XML/METS/CSIPExtensionMETS"
		value = nsMapping.get("csip");
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:csip");
		}
		if(!CSIPExtensionMETS_NS.NS_CSIP.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:csip=\""+value+"\""); 
		}
	}
}
