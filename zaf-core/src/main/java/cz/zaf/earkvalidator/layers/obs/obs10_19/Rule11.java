package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.MdSecType;

public class Rule11 extends AipRule {
	public static final String CODE = "obs11";
	public static final String RULE_TEXT = "Popisná metadata v elementu mets/dmdSec mají uveden atribut ID.";
	public static final String RULE_ERROR = "U popisných metadat v elementu mets/dmdSec není uveden atribut ID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0302";

	public Rule11() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		for(MdSecType dmdSec: dmdSecs) {
			if(dmdSec.getID() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/@ID.", ctx.formatMetsPosition(dmdSec));
			}
			// check format of ID, mask: uuid-<UUID>
			if(!ValidatorId.checkFormatId(dmdSec.getID())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atribut mets/dmdSec/@ID.", ctx.formatMetsPosition(dmdSec));
			}
		}
	}


}
