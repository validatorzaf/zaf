package cz.zaf.eadvalidator.ap2023.layers.fvl01.fvl00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.XmlCode;
import cz.zaf.eadvalidator.ap2023.EadLoader;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.layers.fvl01.FVLRule;

public class Rule01 extends FVLRule {

    static final public String CODE = "fvl1";

    public Rule01() {
        super(CODE,
                "Soubor je ve formátu XML",
                "Soubor není ve formátu XML.",
                "Předpoklad pro použití formátu EAD.");
    }

    @Override
    public void eval(EadValidationContext ctx) {
        EadLoader eadLoader = ctx.getLoader();
        if (eadLoader.getDocument() == null) {
            if (eadLoader.getParserError() != null) {
                throw new ZafException(XmlCode.FAILED_TO_PARSE, "Soubor nelze načíst.", eadLoader.getParserError());
            }
            throw new ZafException(BaseCode.CHYBA, "Soubor nelze načíst.");
        }
    }

}
