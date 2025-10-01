package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.Ap2023Constants;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schemas.ead.EadNS;

public class Rule36 extends EadRule {

	static final public String CODE = "obs36";
	static final public String RULE_TEXT = "\"Každý element <ead:c> má atribut \"\"level\"\" o některé z následujících hodnot:\r\n"
			+ "- subfonds\r\n"
			+ "- series\r\n"
			+ "- file\r\n"
			+ "- item\r\n"
			+ "- otherlevel\r\n"
			+ "Pokud má hodnotu \"\"otherlevel\"\", má element <ead:c> dále atribut \"\"otherlevel\"\" o hodnotě \"\"itempart\"\".\"";
	static final public String RULE_ERROR = "Některý z elementů <ead:c> nemá atribut \"level\" nebo tento atribut obsahuje nepovolenou hodnotu.";
	static final public String RULE_SOURCE = "Část 3.1 profilu EAD3 MV ČR";
	
	/**
	 * Mapa povolenych typu urovne a jejich moznych rodicovskych urovni
	 */
	static private final Map<String, Set<String>> allowedlevelTypes = new HashMap<>();
	{
		allowedlevelTypes.put(EadNS.LEVEL_SUBFONDS, Set.of(EadNS.LEVEL_FONDS));
		allowedlevelTypes.put(EadNS.LEVEL_SERIES, Set.of(EadNS.LEVEL_SERIES, EadNS.LEVEL_SUBFONDS, EadNS.LEVEL_FONDS));
		allowedlevelTypes.put(EadNS.LEVEL_FILE, Set.of(EadNS.LEVEL_SERIES, EadNS.LEVEL_FILE));
		allowedlevelTypes.put(EadNS.LEVEL_ITEM, Set.of(EadNS.LEVEL_SERIES, EadNS.LEVEL_FILE));
		allowedlevelTypes.put(Ap2023Constants.LEVEL_ITEMPART, Set.of(EadNS.LEVEL_ITEM));
	}
	
	public Rule36() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		// shodne s obs35
		Archdesc archDesc = ctx.getEad().getArchdesc();
		if(!EadNS.LEVEL_FONDS.equals(archDesc.getLevel())) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribut level=\"fonds\" na kořeni", ctx.formatEadPosition(archDesc));
		}
		
		if(StringUtils.isNotBlank(archDesc.getOtherlevel())) {
			throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Chybně uvedený atribut otherlevel na kořeni", ctx.formatEadPosition(archDesc));
		}
		
		ctx.getEadLevelIterator().iterate((c, parent) -> {
			// zjisteni aktualni urovne
			String levelType = c.getLevel();
			String otherLevel = c.getOtherlevel();
			if(EadNS.LEVEL_OTHERLEVEL.equals(levelType)) {
				if(StringUtils.isBlank(otherLevel)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribut otherlevel na elementu", ctx.formatEadPosition(c));					
				}
				levelType = otherLevel;
			} else {
				if(!StringUtils.isBlank(otherLevel)) {
					throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Chybně uvedený atribut otherlevel na elementu", ctx.formatEadPosition(c));
				}
			}
			Set<String> allowedParents = allowedlevelTypes.get(levelType);
			if(allowedParents==null) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nepovolená hodnota atributu level na elementu", ctx.formatEadPosition(c));
			}
			
			// zjisteni urovne rodice
			String parentLevelType;
			if(parent==null) {
				parentLevelType = EadNS.LEVEL_FONDS; 
			} else {
				parentLevelType = parent.getLevel();
				if(EadNS.LEVEL_OTHERLEVEL.equals(parentLevelType)) {
					parentLevelType = parent.getOtherlevel();
				}
			}			
			if(!allowedParents.contains(parentLevelType)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nepovolená hodnota atributu level na elementu ve vztahu k rodiči", ctx.formatEadPosition(c));
			}
		});
	}

}
