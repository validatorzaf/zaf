package cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.ead.EadNS;

public class Rule02 extends EadRule {
	static final public String CODE = "ns2";

	public Rule02() {
        super(CODE,
                "Element <ead:ead> má atribut \"xmlns:ead\" o hodnotě \"http://ead3.archivists.org/schema/\".",
                "Umístění schématu standardu EAD je popsáno chybně.",
                "Část 1.4 profilu EAD3 MV ČR");		
	}

	@Override
	protected void evalImpl() {
		Element eadRoot = ctx.getRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) eadRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		String value = (nsMapping!=null)?nsMapping.get("ead"):null;		
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:ead");
		}
		if(!EadNS.NS_EADS.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:ead: "+value); 
		}
		
	}
}
