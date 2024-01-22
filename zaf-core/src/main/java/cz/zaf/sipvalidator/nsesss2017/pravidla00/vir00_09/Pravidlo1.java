package cz.zaf.sipvalidator.nsesss2017.pravidla00.vir00_09;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.VirCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.VirCheckRule;

public class Pravidlo1 implements VirCheckRule {

    static public final String KOD = "vir1";
    static final String textPravidla = "Datový balíček SIP neobsahuje hrozbu.";
    static final String popisChybyObecny = "Datový balíček SIP obsahuje hrozbu.";
    static final String zdroj = "§ 21 odst. 6 vyhlášky č. 259/2012 Sb.";

    @Override
    public String getCode() {
        return KOD;
    }

    @Override
    public String getRuleSource() {
        return zdroj;
    }

    @Override
    public String getDescription() {
        return textPravidla;
    }

    @Override
    public String getGenericError() {
        return popisChybyObecny;
    }

    @Override
    public void eval(VirCheckContext ctx) {

        if (ctx.isKontrolaOk()) {
            // everythimg ok
            // nop
        } else {
            String descr = ctx.getErrorDescr();
            if (descr == null) {
                descr = "Chybové hlášení nebylo předáno.";
            }
            throw new ZafException(BaseCode.CHYBA, descr);
        }
    }

}
