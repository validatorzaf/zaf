package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schemas.mets.MetsNS;

public class Rule13 extends AipRule {
	public static final String CODE = "obs13";
	public static final String RULE_TEXT = "Popisná metadata v elementu mets/dmdSec mají uveden atribut STATUS.";
	public static final String RULE_ERROR = "U popisných metadat v elementu mets/dmdSec není uveden atribut STATUS.";
	public static final String RULE_SOURCE = "CZDAX-PMT0304";

	public Rule13() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		// For each group has to exists status CURRENT, all other statuses has to be SUPERSEDED
		Map<String, MdSecType> currentStatusPerGroup = new HashMap<>();
		Set<String> groups = new HashSet<>();
		
		for(MdSecType dmdSec: dmdSecs) {
			String status = dmdSec.getSTATUS();
			if(StringUtils.isBlank(status)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/@STATUS.", ctx.formatMetsPosition(dmdSec));
			}
			String groupId = dmdSec.getGROUPID();
			if(StringUtils.isBlank(groupId)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/@GROUPID.", ctx.formatMetsPosition(dmdSec));
			}
			groups.add(groupId);
			if(MetsNS.STATUS_CURRENT.equals(status)) {
				MdSecType prevCurrent = currentStatusPerGroup.put(groupId, dmdSec);
				if(prevCurrent != null) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/dmdSec/@STATUS, jen jeden může být CURRENT.", ctx.formatMetsPosition(dmdSec));
				}
			} else if(!MetsNS.STATUS_SUPERSEDED.equals(status)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/dmdSec/@STATUS.", ctx.formatMetsPosition(dmdSec));
			}
		}
		// check that all groups have status CURRENT
		if(currentStatusPerGroup.keySet().size()!=groups.size()) {
			// zjisteni kdo nema status CURRENT
			for(var groupId: groups) {
				if(currentStatusPerGroup.get(groupId)==null) {
					throw new ZafException(BaseCode.CHYBA, "Chybí popisná metadat se stavem CURRENT pro skupinu: "+groupId+".", 
							ctx.formatMetsPosition(ctx.getMets()));
				}
			}			
		}
	}


}
