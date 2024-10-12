package cz.zaf.earkvalidator.layers.obs.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;

public class Rule25 extends AipRule {
	public static final String CODE = "obs25";
	public static final String RULE_TEXT = "Předání metadat v elementu mets/amdSec/rightsMD není možné.";
	public static final String RULE_ERROR = "Uveden neočekávaný element rightsMD.";
	public static final String RULE_SOURCE = "CZDAX-PMT0417";

	public Rule25() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		if(CollectionUtils.isEmpty(amdSecs)) {
			return;
		}
		for(AmdSecType amdSec: amdSecs) {
			// CZDAX-PMT0418
			// musi obsahovat jen digiprovMD
			List<MdSecType> rightsMDs = amdSec.getRightsMD();
			if(CollectionUtils.isNotEmpty(rightsMDs)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element mets/amdSec/rightsMD.", ctx.formatMetsPosition(amdSec));
			}
		}
	}


}
