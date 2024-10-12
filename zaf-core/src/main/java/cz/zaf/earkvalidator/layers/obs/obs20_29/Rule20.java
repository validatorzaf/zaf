package cz.zaf.earkvalidator.layers.obs.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schemas.mets.MetsNS;

public class Rule20 extends AipRule {
	public static final String CODE = "obs20";
	public static final String RULE_TEXT = "Administrativní metadata v elementu mets/amdSec/digiprovMD mají uveden atribut STATUS s očekávanou hodnotou.";
	public static final String RULE_ERROR = "Administrativní metadata v elementu mets/amdSec/digiprovMD nemají správně uveden atribut STATUS.";
	public static final String RULE_SOURCE = "CZDAX-PMT0405";

	public Rule20() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		if(CollectionUtils.isEmpty(amdSecs)) {
			return;
		}
		for(AmdSecType amdSec: amdSecs) {
			// CZDAX-PMT0403
			List<MdSecType> digiProvs = amdSec.getDigiprovMD();
			if(CollectionUtils.isEmpty(digiProvs)) {
				continue;
			}
			for(MdSecType mdSec: digiProvs) {
				String status = mdSec.getSTATUS();
				if(!MetsNS.STATUS_CURRENT.equals(status)) {
					throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/Sec/@STATUS.", ctx.formatMetsPosition(mdSec));
				}				
			}
		}
	}
}
