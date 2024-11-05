package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.PremisConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.AgentIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import cz.zaf.schema.premis3.StringPlusAuthority;

public class Rule04 extends PremisRule {

	public static final String CODE = "obs04";
	public static final String RULE_TEXT = "Každý agent má uveden svůj identifikátor dle specifikace.";
	public static final String RULE_ERROR = "Agent má uveden chybný identifikátor.";
	public static final String RULE_SOURCE = "CZDAX-PMS0201, CZDAX-PMS0601";


	public Rule04() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();		
		for(AgentComplexType agent: premis.getAgent()) {
			if(agent.getAgentIdentifier() == null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element agent/agentIdentifier.", ctx.formatPosition(agent));
			}
			List<AgentIdentifierComplexType> agentIdents = agent.getAgentIdentifier();
			boolean localIdentFound = false;
			for(AgentIdentifierComplexType ident: agentIdents) {
				StringPlusAuthority identType = ident.getAgentIdentifierType();
				if (!PremisConstants.IDENT_TYPE_LOCAL.equals(identType.getValue())) {
					continue;
				}
				
				if(localIdentFound) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
							"Duplicitní element elementu agent/agentIdentifier/agentIdentifierType='local'.",
							ctx.formatPosition(ident));
				}

				String identValue = ident.getAgentIdentifierValue();
				if (!ValidatorId.checkFormatId(identValue)) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
							"Chybná hodnota elementu agent/agentIdentifier, hodnota: "+identValue,
							ctx.formatPosition(ident));
				}
				localIdentFound = true;
			}
			
			if(!localIdentFound) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element agent/agentIdentifier/agentIdentifierType='local'.", ctx.formatPosition(ctx.getLoader().getRootObj()));
			}
		}
	}
}
