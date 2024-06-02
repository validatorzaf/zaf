package cz.zaf.eadvalidator.ap2023.layers.enc.enc00_09;

import cz.zaf.eadvalidator.ap2023.EadRule;

public class Rule01 extends EadRule {
    static final public String CODE = "kod1";

    public Rule01() {
        super(CODE,
                "Znakovou sadou souboru je Unicode/UCS v kódování UTF-8 bez BOM (Byte Order Mark).",
                "Znaková sada datového balíčku SIP není Unicode/UCS v kódování UTF-8 bez BOM (Byte order mark).",
                "Část 1.1.5 profilu EAD3 MV ČR");
    }

    @Override
    protected void evalImpl() {
        // ctx.getLoader();

    }

}
