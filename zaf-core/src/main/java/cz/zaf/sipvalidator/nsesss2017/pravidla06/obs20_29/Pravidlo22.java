package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.22 Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
public class Pravidlo22 extends K06PravidloBase {

    static final public String OBS22 = "obs22";

    public Pravidlo22() {
        super(OBS22,
                "Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
                "Chybí povinná (popisná) část struktury datového balíčku SIP.",
                "Bod 2.7. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsDmdSec = metsParser.getMetsDmdSec();
        if (metsDmdSec == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:dmdSec>.");
        }
        if (!ValuesGetter.hasChildWithName(metsDmdSec, "mets:mdWrap")) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Element <mets:dmdSec> neobsahuje žádný dětský element <mets:mdWrap>.", metsDmdSec);
        }
        if (!ValuesGetter.hasOnlyOneChild_ElementNode(metsDmdSec, "mets:mdWrap")) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Element <mets:dmdSec> obsahuje více než jeden dětský element <mets:mdWrap>.", metsDmdSec);
        }
    }

}
