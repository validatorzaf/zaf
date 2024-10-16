package cz.zaf.premisvalidator.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.premisvalidator.PremisRule;

public class Rule03 extends PremisRule {
	public static final String CODE = "ns03";
	public static final String RULE_TEXT = "Kořenový element obsahuje doplňující schémata: xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\".";
	public static final String RULE_ERROR = "Chybí odkaz na některé z povinných schémat: xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\".";
	public static final String RULE_SOURCE = "CZDAX-PMT0101";

	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Element metsRoot = ctx.getLoader().getRootElement();
		
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
	}
}
