package cz.zaf.sipvalidator.nsesss2024.pravidla00.vir00_09;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;

public class Pravidlo1 extends PravidloBase {

    static public final String KOD = "vir1";
    static final String textPravidla = "Datový balíček SIP neobsahuje hrozbu.";
    static final String popisChybyObecny = "Datový balíček SIP obsahuje hrozbu.";
    static final String zdroj = "§ 21 odst. 6 vyhlášky č. 259/2012 Sb.";

    public Pravidlo1() {
        super(KOD, textPravidla, popisChybyObecny, zdroj);
    }

    @Override
    protected void kontrola() {
        String hrozba = ctx.getHrozba();
        if (StringUtils.isNotEmpty(hrozba)) {
            throw new ZafException(BaseCode.CHYBA, hrozba);
        }
    }

}
