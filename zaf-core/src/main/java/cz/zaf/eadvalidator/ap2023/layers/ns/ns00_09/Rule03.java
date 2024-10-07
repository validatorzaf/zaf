package cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.cam.CamNS;

public class Rule03 extends EadRule {
	static final public String CODE = "ns3";
	
	public Rule03() {
        super(CODE,
                "Element <ead:ead> má atribut \"xmlns:cam\" o hodnotě \"http://cam.tacr.cz/2019\".",
                "Umístění schématu systému CAM je popsáno chybně.",
                "Část 1.4 profilu EAD3 MV ČR");		
	}

	@Override
	protected void evalImpl() {
		Element eadRoot = ctx.getRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) eadRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		String value = (nsMapping!=null)?nsMapping.get("cam"):null;		
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:cam");
		}
		if(!CamNS.NS_CAM.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:cam: "+value); 
		}		
	}
	
}
