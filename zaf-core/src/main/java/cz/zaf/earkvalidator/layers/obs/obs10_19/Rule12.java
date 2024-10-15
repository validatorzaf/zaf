package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;

public class Rule12 extends AipRule {
	public static final String CODE = "obs12";
	public static final String RULE_TEXT = "Popisná metadata v elementu mets/dmdSec mají uveden atribut CREATED.";
	public static final String RULE_ERROR = "U popisných metadat v elementu mets/dmdSec není uveden atribut CREATED.";
	public static final String RULE_SOURCE = "CZDAX-PMT0303";

	public Rule12() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		for(MdSecType dmdSec: dmdSecs) {
			XMLGregorianCalendar created = dmdSec.getCREATED();
			if(created == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/@CREATED.", ctx.formatMetsPosition(dmdSec));
			}
		}
	}


}
