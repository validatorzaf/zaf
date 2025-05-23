package cz.zaf.earkvalidator.layers.obs.obs00_09;

import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;

public class Rule04a extends AipRule {
	public static final String CODE = "obs04a";
	public static final String RULE_TEXT = "V elementu <mets> musí atribut csip:OTHERCONTENTINFORMATIONTYPE mít hodnotu 'change_request_v1_0'.";
	public static final String RULE_ERROR = "Atribut csip:OTHERCONTENTINFORMATIONTYPE v elementu <mets> chybí nebo má chybnou hodnotu.";
	public static final String RULE_SOURCE = "4.2.1. Základní podoba balíčku, CZDAX-PPR0202, CZDAX-PMT0106, CZDAX-SZB0101, CZDAX-VSB0101";

	public Rule04a() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		Map<QName, String> otherAttrs = ctx.getMets().getOtherAttributes();
		if(otherAttrs==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:OTHERCONTENTINFORMATIONTYPE", ctx.formatMetsPosition(ctx.getMets()));
		}
		
		String value = otherAttrs.get(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.OTHERCONTENTINFORMATIONTYPE));
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:OTHERCONTENTINFORMATIONTYPE", ctx.formatMetsPosition(ctx.getMets()));
		}
		if(!EarkCz.CONTENT_TYPE_CHANGE_REQUEST.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu csip:OTHERCONTENTINFORMATIONTYPE, value: "+value,
					ctx.formatMetsPosition(ctx.getMets()));
		}
		
	}

}
