package cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09;

import java.io.File;
import java.nio.file.Path;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.DatCheckRuleBase;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.SipInfo;

public class Pravidlo3 extends DatCheckRuleBase {

    static final public String KOD = "dat3";

    public Pravidlo3() {
        super(KOD,
                "Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.",
                "Uvedena jsou chybně metadata a komponenty (počítačové soubory) v datovém balíčku SIP.",
                "Požadavek 11.2.2, 11.2.3 a 11.2.8 NSESSS.");
    }

    // Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a
    // složku pojmenovanou komponenty.
    @Override
    protected void kontrola() {
        boolean stav = false;
        String vypisChyby;

        SipInfo sipInfo = this.ctx.getContext().getSip();

        if (maJenPovoleneSoubory(sipInfo)) {
            if (ctx.getContext().maMetsXml()) {
                StringBuilder sb = new StringBuilder();
                sb.append("SIP balíček obsahuje právě jeden soubor \"mets.xml\"");
                if (SIP_MAIN_helper.maSlozku_komponenty(sipInfo)) {
                    sb.append(" a složku komponenty.");
                } else {
                    sb.append(".");
                }
                vypisChyby = sb.toString();
                stav = true;
            } else {
                vypisChyby = "SIP balíček neobsahuje právě jeden soubor \"mets.xml\".";
            }
        } else {
            vypisChyby = "SIP balíček obsahuje nepovolené soubory.";
        }

        if (!stav) {
            throw new ZafException(BaseCode.CHYBA, vypisChyby);
        }
    }

    public static boolean maJenPovoleneSoubory(SipInfo sip) {

        Path cesta = sip.getCesta();
        if (cesta == null) {
            // cesta neni definovana -> nelze kontrolovat
            return false;
        }
        File[] f = cesta.toFile().listFiles();
        if (f != null) {
            for (File s : f) {
                if (!(s.getName().toLowerCase().equals(SipInfo.KOMPONENTY) ||
                        s.getName().toLowerCase().equals(SipInfo.METS_XML))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
