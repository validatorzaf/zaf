package cz.zaf.eadvalidator.ap2023;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.validation.BaseRule;
import cz.zaf.schema.ead3.P;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public abstract class EadRule extends BaseRule<EadValidationContext> {

    protected EadValidationContext ctx;

    public EadRule(final String code, String description,
            String genericError,
            String ruleSource) {
        super(code, description, genericError, ruleSource);
    }

    @Override
    public void eval(final EadValidationContext ctx) {
        this.ctx = ctx;
        evalImpl();
        this.ctx = null;
    }

    //validace pro pravidla kde používámjedinečnost P
    public void checkSingleElementP(List<Object> listOfObject, Object parent) {
        P p = null;
        for (Object object : listOfObject) {
            if (object instanceof P pInst) {
                if (p != null) {
                    throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(p));
                }
                p = validateP(pInst);
            }
        }
        if (p == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:p>.", ctx.formatEadPosition(parent));
        }

    }

    public P validateP(P p) {
        // Kontrola obsahu p
        List<Serializable> pContentList = p.getContent();
        if (pContentList.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(p));
        }
        Serializable partContent = pContentList.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(p));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(p));
        }
        return p;
    }

    protected abstract void evalImpl();
}
