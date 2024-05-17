package cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.DatCheckRuleBase;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

public class Pravidlo2 extends DatCheckRuleBase {

    static final public String KOD = "dat2";

    public Pravidlo2() {
        super(KOD,
                "Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.",
                "Uvedeno je chybně označení datového balíčku SIP.",
                "Požadavek 11.2.9 NSESSS.");
    }

    // Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, 
    // po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.
    @Override
    protected void kontrola() {
        SipInfo sipInfo = this.ctx.getContext().getSip();

        LoadType loadtype = sipInfo.getLoadType();
        boolean stav = false;
        String vypisChyby;
        if (loadtype == LoadType.LT_ZIP) { //byl to zip
            String bezKoncovky = sipInfo.getNameZip().substring(0, sipInfo.getNameZip().length() - 4);
            if (sipInfo.getName().equals(bezKoncovky)) {
                stav = true;
                vypisChyby = "Nahraný soubor typu zip obsahoval očekávaný SIP balíček.";
            } else {
                vypisChyby = "Nahraný soubor typu zip neobsahoval očekávaný SIP balíček, očekávaná hodnota: "
                        + bezKoncovky + ", zjištěná hodnota:" + sipInfo.getName();
            }
        } else {
            stav = true;
            vypisChyby = "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu " + loadtype.getName() + ".";
        }

        if (!stav) {
            throw new ZafException(BaseCode.CHYBA, vypisChyby);
        }
    }

}
