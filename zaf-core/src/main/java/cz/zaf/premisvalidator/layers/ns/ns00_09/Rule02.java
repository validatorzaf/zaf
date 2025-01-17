package cz.zaf.premisvalidator.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schemas.premis.PremisNS;

public class Rule02 extends PremisRule {
	public static final String CODE = "ns02";
	public static final String RULE_TEXT = "Kořenový element obsahuje schéma xmlns=\"http://www.loc.gov/premis/v3\".";
	public static final String RULE_ERROR = "Není použito výchozí schéma: xmlns=\"http://www.loc.gov/premis/v3\".";
	public static final String RULE_SOURCE = "CZDAX-PSP0202"; 

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Element root = ctx.getLoader().getRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) root.getUserData(PositionalXMLReader.NS_MAPPING);
		String value = (nsMapping!=null)?nsMapping.get(""):null;
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns");
		}
		if(!PremisNS.NS_PREMIS.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns: "+value); 
		}
		
	}
}
