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
    static final public String RULE_TEXT = "Element <archdesc> a každý element <c> obsahuje atribut \"base\", jehož hodnota má podobu URI odpovídající standardu LinkedData nebo obsahuje attrbite \"id\", který je ve formátu \"uuid-\" a UUID verze 4.";
    static final public String RULE_ERROR = "Element <archdesc> nebo některý element <c> nemá atribut \"base\", či \"id\" a atribut neobsahuje hodnotu v požadovaném formátu.";
    static final public String RULE_SOURCE = "Část 3.2 profilu EAD3 MV ČR";

    public Rule37() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        String baseA = archDesc.getBase();
        if(baseA!=null) {
        	validateBase(baseA, archDesc);
        }
        String aid = archDesc.getId();
        if(aid!=null) {
        	validateID(archDesc, aid);
        }
        if(aid==null&&baseA==null) {
        	throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu id nebo base.", ctx.formatEadPosition(archDesc));
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            String baseC = c.getBase();
            if(baseC!=null) {
            	validateBase(baseC, c);
            }
            String cid = c.getId();
            if(cid!=null) {
            	validateID(c, cid);
            }
            if(baseC==null&&cid==null) {
            	throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu id nebo base.", ctx.formatEadPosition(c));
            }
        });

    }

    private void validateID(Object parent, String id) {
        if (id == null) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu id.", ctx.formatEadPosition(parent));
        }
        if (!Rule37a.isValidUUID(id)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neodpovídající hodnota atributu id: " + id + ".", ctx.formatEadPosition(parent));
        }
        ctx.markValidatedAttribute(parent, "id");
    }

    private void validateBase(String base, Object element) {
        if (StringUtils.isEmpty(base)) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut base není vyplněn.", ctx.formatEadPosition(element));
        }
        if (!isURIValid(base)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut base obsahuje nevalidní hodnotu.", ctx.formatEadPosition(element));
        }
        ctx.markValidatedAttribute(element, "base");
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
