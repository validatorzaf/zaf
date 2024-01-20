package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.12 Element <mets:mets> obsahuje alespoň jeden element
// <mets:amdSec>.
public class Pravidlo12 extends K06PravidloBase {

    static final public String OBS12 = "obs12";

    public Pravidlo12() {
        super(OBS12,
                "Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
                "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
                "Bod 2.9. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsMets = metsParser.getMetsRootNode();

        if (!ValuesGetter.hasChildWithName(metsMets, MetsElements.AMD_SEC)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Kořenový element <mets:mets> nemá žádný dětský element <mets:amdSec>.", metsMets);
        }
    }
}
