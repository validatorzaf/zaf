package cz.zaf.earkvalidator.layers.obs.obs00_09;

import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;

public class Rule03 extends AipRule {
	public static final String CODE = "obs03";
	public static final String RULE_TEXT = "V elementu <mets> musí být atribut csip:CONTENTINFORMATIONTYPE=\"OTHER\".";
	public static final String RULE_ERROR = "Atribut csip:CONTENTINFORMATIONTYPE v elementu <mets> chybí nebo má nesprávnou hodnotu.";
	public static final String RULE_SOURCE = "CZDAX-PMT0105, CZDAX-PMT0106";

	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		Map<QName, String> otherAttrs = ctx.getMets().getOtherAttributes();
		if(otherAttrs==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:CONTENTINFORMATIONTYPE", ctx.formatMetsPosition(ctx.getMets()));
		}
		
		String value = otherAttrs.get(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.CONTENTINFORMATIONTYPE));
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:CONTENTINFORMATIONTYPE", ctx.formatMetsPosition(ctx.getMets()));
		}
		if(!CSIPExtensionMETS_NS.OTHER.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu csip:CONTENTINFORMATIONTYPE, value: "+value,
					ctx.formatMetsPosition(ctx.getMets()));
		}
	}
}
