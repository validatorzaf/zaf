package cz.zaf.earkvalidator.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;

public class Rule02 extends AipRule {
	public static final String CODE = "ns02";
	public static final String RULE_TEXT = "Kořenový element obsahuje schéma xmlns=\"http://www.loc.gov/METS/\".";
	public static final String RULE_ERROR = "Není použito výchozí schéma: xmlns=\"http://www.loc.gov/METS/\".";
	public static final String RULE_SOURCE = "CZDAX-PSP0202"; 

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Element metsRoot = ctx.getMetsRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) metsRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		String value = (nsMapping!=null)?nsMapping.get(""):null;
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns");
		}
		if(!EarkConstants.NS_METS.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns: "+value); 
		}
		
	}
}
