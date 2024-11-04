package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule05 extends PremisRule {

	public static final String CODE = "obs05";
	public static final String RULE_TEXT = "Software (speciální agent) má uveden správně popis.";
	public static final String RULE_ERROR = "Chybný popis agenta reprezentující software.";
	public static final String RULE_SOURCE = "CZDAX-PMS0603, CZDAX-PMS0604, CZDAX-PMS0606";


	public Rule05() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		for(AgentComplexType agent: premis.getAgent()) {
			StringPlusAuthority agentType = agent.getAgentType();
			if(agentType==null || StringUtils.isBlank(agentType.getValue())) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nalezen agent bez uvedení typu.", ctx.formatPosition(agent));
			}
			if(!PremisConstants.AGENT_TYPE_SOFTWARE.equals(agentType.getValue())) {
				continue;
			}
			List<StringPlusAuthority> names = agent.getAgentName();
			if(CollectionUtils.isEmpty(names)) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Software nemá uvedeno jméno.", ctx.formatPosition(agent));
			}
			if(names.size()>1) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Software má uvedeno více jmén.", ctx.formatPosition(agent));
			}
			StringPlusAuthority name = names.get(0);
			if(StringUtils.isBlank(name.getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Software nemá uvedeno platné jméno.", ctx.formatPosition(name));
			}
		}
	}
}
