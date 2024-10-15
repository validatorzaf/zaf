package cz.zaf.earkvalidator.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;

public class Rule26 extends AipRule {
	public static final String CODE = "obs26";
	public static final String RULE_TEXT = "Existuje nanejvýš jeden element mets/fileSec.";
	public static final String RULE_ERROR = "Chybně uvedeno více elementů mets/fileSec.";
	public static final String RULE_SOURCE = "CZDAX-PMT0505";

	public Rule26() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		// TODO: check existence of all files from the package here
	}


}
