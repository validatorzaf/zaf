package cz.zaf.earkvalidator.layers.obs.obs20_29;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;

public class Rule27 extends AipRule {
	public static final String CODE = "obs27";
	public static final String RULE_TEXT = "Element mets/fileSec má uveden atribut ID.";
	public static final String RULE_ERROR = "Element mets/fileSec nemá uveden atribut ID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0506";

	public Rule27() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		if(StringUtils.isBlank(filesec.getID()) ) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element mets/fileSec nemá uveden atribut ID.", ctx.formatMetsPosition(filesec));
		}
		// check format of ID, mask: uuid-<UUID>
		if(!ValidatorId.checkFormatId(filesec.getID())) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Chybná hodnota elementu mets/fileSec/@ID.", ctx.formatMetsPosition(filesec));
		}
	}


}
