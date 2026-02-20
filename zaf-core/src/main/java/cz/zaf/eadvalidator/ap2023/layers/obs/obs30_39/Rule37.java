package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import java.net.URI;
import java.net.URISyntaxException;

public class Rule37 extends EadRule {

    static final public String CODE = "obs37";
    static final public String RULE_TEXT = "Element <archdesc> a každý element <c> obsahuje atribut \"base\", jehož hodnota má podobu URI odpovídající standardu LinkedData.";
    static final public String RULE_ERROR = "Element <archdesc> nebo některý element <c> nemá atribut \"base\" nebo tento atribut neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 3.2 profilu EAD3 MV ČR";

    public Rule37() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        String baseA = archDesc.getBase();
        validateBase(baseA, archDesc);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            String baseC = c.getBase();
            validateBase(baseC, c);
        });

    }

    private void validateBase(String base, Object element) {
        if (StringUtils.isEmpty(base)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut base není vyplněn.", ctx.formatEadPosition(element));
        }
        if (!isURIValid(base)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut base obsahuje nevalidní hodnotu.", ctx.formatEadPosition(element));
        }
    }

    private boolean isURIValid(String base) {
        try {
            URI uri = new URI(base);
            if (!uri.isAbsolute()) {
                return false;
            }
            String scheme = uri.getScheme();
            return ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))
                    && uri.getHost() != null
                    && uri.getPath() != null
                    && !uri.getPath().isEmpty();
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
