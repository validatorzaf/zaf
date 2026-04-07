package cz.zaf.sipvalidator.nsesss2024.pravidla03.wf00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.MetsParser;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;

// Soubor obsahuje právě jeden kořenový element <mets:mets>.
public class Pravidlo1 extends PravidloBase {

    static final public String KOD = "wf1";

    public Pravidlo1() {
        super(KOD,
        		"Soubor je well-formed.",
                "Datový balíček SIP nedodržuje syntaxi jazyka XML.",
                "Požadavek 9.2.5 NSESSS.");
    }

    @Override
    protected void kontrola() {
        MetsParser metsParser = new MetsParser();
        metsParser.parse(ctx.getSip());
        ctx.setMetsParser(metsParser);
        if (metsParser.parserError != null) {
            throw new ZafException(BaseCode.CHYBA, metsParser.parserError);
        }
    }

}
