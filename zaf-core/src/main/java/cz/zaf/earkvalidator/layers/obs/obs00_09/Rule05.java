package cz.zaf.earkvalidator.layers.obs.obs00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;

public class Rule05 extends AipRule {
	public static final String CODE = "obs05";
	public static final String RULE_TEXT = "V elementu <mets> musí hodnota atribitu PROFILE být: https://stands.nacr.cz/da/2023/aip.xml.";
	public static final String RULE_ERROR = "Atribut PROFILE v <mets> chybí nebo má chybnou hodnotu.";
	public static final String RULE_SOURCE = "CZDAX-PMT0107";

	public Rule05() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		String profile = ctx.getMets().getPROFILE();
		if (profile == null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut PROFILE", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
		if(!profile.equals("https://stands.nacr.cz/da/2023/aip.xml")) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atribut PROFILE: "+profile, 
					ctx.formatMetsPosition(ctx.getMets()));
		}
	}


}
