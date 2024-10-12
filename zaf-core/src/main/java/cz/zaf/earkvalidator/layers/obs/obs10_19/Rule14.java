package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schemas.eark.EarkCz;

public class Rule14 extends AipRule {
	public static final String CODE = "obs14";
	public static final String RULE_TEXT = "Popisná metadata v elementu mets/dmdSec mají uveden atribut GROUPID.";
	public static final String RULE_ERROR = "U popisných metadat v elementu mets/dmdSec není uveden atribut GROUPID.";
	public static final String RULE_SOURCE = "CZDAX-PMT0305";

	public Rule14() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		
		for(MdSecType dmdSec: dmdSecs) {
			String groupid = dmdSec.getGROUPID();
			if(StringUtils.isBlank(groupid)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/@GROUPID.", ctx.formatMetsPosition(dmdSec));
			}
			if(!EarkCz.GROUPID_INHERENT.equals(groupid)&&
					!EarkCz.GROUPID_CONTEXTUAL.equals(groupid)	) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut mets/dmdSec/@GROUPID obsahuje chybnou hodnotu: "+groupid, ctx.formatMetsPosition(dmdSec));
			}
		}
	}


}
