package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;

public class Rule32 extends AipRule {
	public static final String CODE = "obs32";
	public static final String RULE_TEXT = "Existuje identifikátor ID skupiny souborů v elementu mets/fileSec/fileGrp.";
	public static final String RULE_ERROR = "Skupina souborů nemá uveden identifikátor.";
	public static final String RULE_SOURCE = "CZDAX-PMT0514";

	public Rule32() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		List<FileGrp> filegroups = filesec.getFileGrp();

		for(FileGrp filegrp: filegroups) {
			String id = filegrp.getID();
			if(id==null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Skupina souborů nemá uveden identifikátor.", ctx.formatMetsPosition(filegrp));
			}
			if(StringUtils.isBlank(id)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Skupina souborů nemá uveden identifikátor.", ctx.formatMetsPosition(filegrp));
			}
		}
	}


}
