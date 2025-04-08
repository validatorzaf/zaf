package cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;

// Datový balíček SIP je soubor v datovém formátu ZIP
// (jeho MIME Content-type je detekován jako application/zip) nebo složka.
public class Pravidlo1 extends PravidloBase {

    static final public String KOD = "dat1";

    public Pravidlo1() {
        super(KOD,
                "Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.",
                "Datový balíček SIP je chybně strukturován.",
                "Požadavek 9.2.11 NSESSS.");
    }

    @Override
    protected void kontrola() {
        SipInfo sipInfo = this.ctx.getContext().getSip();
        LoadType loadtype = sipInfo.getLoadType();

       switch (loadtype) {
        case LT_XML:
        	throw new ZafException(BaseCode.CHYBA, 
        			"Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu xml.");
        case LT_DIR:
            break;
        case LT_ZIP:
            switch (sipInfo.getLoadStatus()) {
            case ERR_ZIP_INCORRECT_STRUCTURE:
            	// SIP balíček rozbalen, chybná struktura v ZIP souboru
            	// zde vsak neni chybou
            	break;
            case OK:
                break;
            case ERR_UNKNOWN:
            case ERR_UNZIP_FAILED:
            default:
            	throw new ZafException(BaseCode.CHYBA,            	
            			"Datový balíček SIP není soubor v datovém formátu ZIP (jeho MIME Content-type není application/zip)."
            			);
            }
            break;
        case LT_UNKNOWN:
        default:
        	throw new ZafException(BaseCode.CHYBA,        	
        			"Datový balíček SIP není soubor v povoleném datovém formátu."
        			);
        }
    }

}
