package cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.DatCheckRuleBase;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

// Datový balíček SIP je soubor v datovém formátu ZIP
// (jeho MIME Content-type je detekován jako application/zip) nebo složka.
public class Pravidlo1 extends DatCheckRuleBase {

    static final public String KOD = "dat1";

    public Pravidlo1() {
        super(KOD,
                "Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.",
                "Datový balíček SIP je chybně strukturován.",
                "Požadavek 11.2.9 a 11.2.1 NSESSS.");
    }

    @Override
    protected void kontrola() {
        SipInfo sipInfo = this.ctx.getContext().getSip();
        LoadType loadtype = sipInfo.getLoadType();

        boolean stav = false;
        String vypisChyby;
        switch (loadtype) {
        case LT_XML:
            vypisChyby = "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu xml.";
            break;
        case LT_DIR:
            stav = true;
            vypisChyby = "SIP balíček byl nahrán jako složka.";
            break;
        case LT_ZIP:
            switch (sipInfo.getLoadStatus()) {
            case ERR_ZIP_INCORRECT_STRUCTURE:
                stav = true;
                vypisChyby = "SIP balíček rozbalen, chybná struktura v ZIP souboru.";
                break;
            case OK:
                stav = true;
                vypisChyby = "SIP balíček nahrán ze souboru typu zip.";
                break;
            case ERR_UNKNOWN:
            case ERR_UNZIP_FAILED:
            default:
                vypisChyby = "Datový balíček SIP není soubor v datovém formátu ZIP (jeho MIME Content-type není application/zip).";
                break;
            }
            break;
        case LT_UNKNOWN:
        default:
            vypisChyby = "Datový balíček SIP není soubor v povoleném datovém formátu.";
            break;
        }

        if (!stav) {
            throw new ZafException(BaseCode.CHYBA, vypisChyby);
        }
    }

}
