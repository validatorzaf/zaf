package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import java.util.UUID;

public class Rule37a extends EadRule {

    static final public String CODE = "obs37a";
    static final public String RULE_TEXT = "Element <archdesc> a každý element <c> obsahuje atribut \"id\", který je ve formátu \"uuid-\" a UUID verze 4.";
    static final public String RULE_ERROR = "Element <archdesc> nebo některý element <c> nemá atribut \"id\" nebo tento atribut neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 3.2.1 profilu EAD3 MV ČR";

    public Rule37a() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        String aid = archDesc.getId();
        validateID(archDesc, aid);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            String cid = c.getId();
            validateID(c, cid);
        });

    }

    private void validateID(Object parent, String id) {
        if (id == null) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu id.", ctx.formatEadPosition(parent));
        }
        if (!isValidUUID(id)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neodpovídající hodnota atributu id: " + id + ".", ctx.formatEadPosition(parent));
        }

    }

    private boolean isValidUUID(String id) {
        if (StringUtils.isBlank(id)) {
            return false;
        }

        if (!id.startsWith("uuid-")) {
            return false;
        }

        String uuidPart = id.substring(5);

        try {
            UUID uuid = UUID.fromString(uuidPart);

            return uuid.version() == 4;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
