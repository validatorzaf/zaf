package cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.ead.EadNS;

public class Rule02a extends EadRule {
	static final public String CODE = "ns2a";

	public Rule02a() {
        super(CODE,
                "Element <ead> má atribut \"xmlns\" o hodnotě \"http://ead3.archivists.org/schema/\".",
                "Umístění schématu standardu EAD je popsáno chybně.",
                "Část 1.4 profilu EAD3 MV ČR");		
	}

	@Override
	protected void evalImpl() {
		Element eadRoot = ctx.getRootElement();
		
		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) eadRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		
		if(ctx.getPrefixNsEad()!=null) {
			throw new ZafException(BaseCode.CHYBA, "Prefix pro EAD by měl být prázdný, hodnota: "+ctx.getPrefixNsEad()+".");
		}
		
		String ns = "";
		String value = (nsMapping!=null)?nsMapping.get(ns):null;
				
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut "+getXmlnsEad()+".");
		}
		if(!EadNS.NS_EADS.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota atributu "+getXmlnsEad()+": "+value+"."); 
		}
	}
	
	String getXmlnsEad() {
		return StringUtils.isEmpty(ctx.getPrefixNsEad())?"xmlns":"xmlns:"+ctx.getPrefixNsEad();
	}
}
