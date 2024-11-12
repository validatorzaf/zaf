package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr.Agent;
import cz.zaf.schema.mets_1_12_1.MetsType.MetsHdr.Agent.Note;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;
import cz.zaf.schemas.mets.MetsNS;

public class Rule10 extends AipRule {
	public static final String CODE = "obs10";
	public static final String RULE_TEXT = "Musí být uveden agent odpovídající za vznik balíčku.";
	public static final String RULE_ERROR = "Není správně uveden agent v elementu mets/metsHdr/agent.";
	public static final String RULE_SOURCE = "CZDAX-PMT0205, CZDAX-PMT0206, CZDAX-PMT0207, CZDAX-PMT0208, CZDAX-PMT0209, CZDAX-PMT0210, CZDAX-PMT0211";

	public Rule10() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		MetsHdr metsHdr = ctx.getMets().getMetsHdr();
		if (metsHdr == null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element MetsHdr.", 
					ctx.formatMetsPosition(ctx.getMets()));
		}
		List<Agent> agents = metsHdr.getAgent();
		if (CollectionUtils.isEmpty(agents)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/metsHdr/agent.", 
					ctx.formatMetsPosition(metsHdr));
		}
		// Kontrola jednotlivych agentu
		for (Agent agent : agents) {
			// agent TYPE
			if (agent.getTYPE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/metsHdr/agent/@TYPE.", 
						ctx.formatMetsPosition(agent));
			}
			if(!MetsNS.AGENT_TYPE_OTHER.equals(agent.getTYPE())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/metsHdr/agent/@TYPE, value: "+agent.getTYPE(), 
						ctx.formatMetsPosition(agent));				
			}
			// agent OTHERTYPE
			if (agent.getOTHERTYPE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/metsHdr/agent/@OTHERTYPE.", 
						ctx.formatMetsPosition(agent));
			}
			if(!CSIPExtensionMETS_NS.AGENT_OTHERTYPE_SOFTWARE.equals(agent.getOTHERTYPE())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/metsHdr/agent/@OTHERTYPE, value: "+agent.getOTHERTYPE(), 
						ctx.formatMetsPosition(agent));				
			}
			
			// agent ROLE
			if(agent.getROLE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/metsHdr/agent/@ROLE.", 
						ctx.formatMetsPosition(agent));
			}
			if(!MetsNS.AGENT_ROLE_CREATOR.equals(agent.getROLE())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/metsHdr/agent/@ROLE, value: "+agent.getROLE(), 
						ctx.formatMetsPosition(agent));				
			}
			
			String name = agent.getName();
			if(StringUtils.isBlank(name) ) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Nenalezen element mets/metsHdr/agent/name.", 
						ctx.formatMetsPosition(agent));
			}
			
			List<Note> notes = agent.getNote();
			if(notes.size() == 0) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/metsHdr/agent/name.",
						ctx.formatMetsPosition(agent));
			}
			if(notes.size() > 1) {
				throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nenalezen element mets/metsHdr/agent/name.",
						ctx.formatMetsPosition(agent));
			}
			Note note = agent.getNote().get(0);
			if(StringUtils.isBlank(note.getValue())) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Nenalezen element mets/metsHdr/agent/name.",
						ctx.formatMetsPosition(agent));
			}
			// overeni existence atributu csip:NOTETYPE
			Map<QName, String> otherAttrs = note.getOtherAttributes();
			if(otherAttrs==null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:NOTETYPE.", 
						ctx.formatMetsPosition(note));
			}
			String value = otherAttrs.get(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.NOTETYPE));
			if(StringUtils.isBlank(value)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:NOTETYPE.", 
						ctx.formatMetsPosition(note));
			}
			if(!CSIPExtensionMETS_NS.SOFTWARE_VERSION.equals(value)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu csip:NOTETYPE, value: "+value, 
						ctx.formatMetsPosition(note));
			}
		}
	}
}
