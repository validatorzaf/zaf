package cz.zaf.premisvalidator.layers.obs.obs10_19;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.OriginalNameComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.Representation;
import cz.zaf.schemas.premis.PremisNS;

public class Rule14 extends PremisRule {

	public static final String CODE = "obs14";
	public static final String RULE_TEXT = "Archivní entita zachycená prostřednictvím agenta je správně uvedena.";
	public static final String RULE_ERROR = "Agent je chybně zapsán.";
	public static final String RULE_SOURCE = "CZDAX-PKG0401, CZDAX-PKG0402, CZDAX-PKG0403";


	public Rule14() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
	}
}
