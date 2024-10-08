package cz.zaf.earkvalidator.layers.obs.obs00_09;

import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.Mets;

public class Rule01  extends AipRule {
	public static final String CODE = "obs01";
	public static final String RULE_TEXT = "Element <mets> obsahuje atribut OBJID s neprázdnou hodnotou, hodnota je shodná s označením složky.";
	public static final String RULE_ERROR = "Chybná hodnota atributu OBJID v koření METS.xml.";
	public static final String RULE_SOURCE = "CZDAX-PMT0102";

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		Mets mets = ctx.getMets();
		String objId = mets.getOBJID();
		if(StringUtils.isBlank(objId)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Prazdná hodnota atributu OBJID");
		}
		Path aipPath = ctx.getLoader().getAipPath();
		String dirName = aipPath.getFileName().toString();
		if(!objId.equals(dirName)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
					"Složka a OBJID mají rozdílnou hodnotu.",
					ctx.formatMetsPosition(mets));
		}
	}

}
