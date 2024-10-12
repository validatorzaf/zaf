package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;

public class Rule17 extends AipRule {
	public static final String CODE = "obs17";
	public static final String RULE_TEXT = "Popisná metadata jsou uvedena ve složce descriptive.";
	public static final String RULE_ERROR = "Popisná metadata jsou uvedena v chybném umístění.";
	public static final String RULE_SOURCE = "CZDAX-PSP0107";

	public Rule17() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		
		for(MdSecType dmdSec: dmdSecs) {
			MdRef mdref = dmdSec.getMdRef();
			// CZDAX-PMT0306
			if(mdref == null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/dmdSec/mdRef.", ctx.formatMetsPosition(dmdSec));
			}
			
			// CZDAX-PMT0309
			String href = mdref.getHref();
			if(StringUtils.isBlank(href)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@href.", ctx.formatMetsPosition(mdref));
			}
			
			// CZDAX-PSP0107
			// check if href start with metadata/descriptive/
			if(!href.startsWith("metadata/descriptive/")) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/dmdSec/mdRef/@href.", ctx.formatMetsPosition(mdref));
			}			
		}
	}


}
