package cz.zaf.earkvalidator.layers.obs.obs00_09;

import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;

public class Rule09 extends AipRule {
	public static final String CODE = "obs09";
	public static final String RULE_TEXT = "Musí být uveden typ balíčku AIP v atributu: mets/metsHdr/@csip:OAISPACKAGETYPE.";
	public static final String RULE_ERROR = "Není uveden platný typ balíčku.";
	public static final String RULE_SOURCE = "CZDAX-PMT0204";

	public Rule09() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		MetsHdr metsHdr = ctx.getMets().getMetsHdr();
		if (metsHdr == null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element MetsHdr.", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
		Map<QName, String> otherAttrs = metsHdr.getOtherAttributes();
		if(otherAttrs==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:OAISPACKAGETYP.", 
					ctx.formatMetsPosition(metsHdr));			
		}
		String value = otherAttrs.get(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.OAISPACKAGETYPE));
		if(StringUtils.isBlank(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:OAISPACKAGETYP.", 
					ctx.formatMetsPosition(metsHdr));
		}
		if(!CSIPExtensionMETS_NS.AIP.equals(value)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu csip:OAISPACKAGETYP, value: "+value, 
					ctx.formatMetsPosition(metsHdr));
		}
	}


}
