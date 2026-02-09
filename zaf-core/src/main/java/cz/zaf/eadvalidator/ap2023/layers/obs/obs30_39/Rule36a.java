package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.ead.EadNS;


public class Rule36a extends EadRule {

    static final public String CODE = "obs36a";
    static final public String RULE_TEXT = """
    Každý element <c> má atribut "level" o některé z následujících hodnot:
    - subfonds
    - series
    - file
    - item
    - otherlevel
    Pokud má hodnotu "otherlevel", má element <c> dále atribut "otherlevel" o hodnotě "itempart". Současně platí, že rodič je stejného nebo vyššího typu dle pravidel hierarchického popisu.
    """;
    static final public String RULE_ERROR = "Některý z elementů <c> nemá atribut \"level\" nebo tento atribut obsahuje nepovolenou hodnotu.";
    static final public String RULE_SOURCE = "Část 3.1 profilu EAD3 MV ČR";

    private final Set<String> allowed = Set.of(EadNS.LEVEL_SUBFONDS, EadNS.LEVEL_SERIES, EadNS.LEVEL_FILE, EadNS.LEVEL_ITEM, EadNS.LEVEL_OTHERLEVEL);

    public Rule36a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        ctx.getEadLevelIterator().iterate((c, parent) -> {
            String level = c.getLevel();
            if (StringUtils.isEmpty(level)) {
                throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí hodnota atributu level.", ctx.formatEadPosition(c));
            }
            if (!allowed.contains(level)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu level: " + level + ".", ctx.formatEadPosition(c));
            }
        });
    }

}
