package cz.zaf.sipvalidator.nsesss2024.pravidla00.vir00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;

public class Pravidlo1 extends PravidloBase {

    static public final String KOD = "vir1";
    static final String textPravidla = "Datový balíček SIP neobsahuje hrozbu.";
    static final String popisChybyObecny = "Datový balíček SIP obsahuje hrozbu.";
    static final String zdroj = "§ 21 odst. 6 vyhlášky č. 259/2012 Sb.";

    private final boolean kontrolaOk;
    private final String errorDescr;

    public Pravidlo1(final boolean kontrolaOk,
                     final String errorDescr) {
        super(KOD, textPravidla, popisChybyObecny, zdroj);
        this.kontrolaOk = kontrolaOk;
        this.errorDescr = errorDescr;
    }

    @Override
    protected void kontrola() {
        if (!kontrolaOk) {
            String descr = errorDescr;
            if (descr == null) {
                descr = "Chybové hlášení nebylo předáno.";
            }
            throw new ZafException(BaseCode.CHYBA, descr);
        }
    }

}
