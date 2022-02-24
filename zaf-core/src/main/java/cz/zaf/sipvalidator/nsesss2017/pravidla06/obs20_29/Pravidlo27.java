package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.27 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
public class Pravidlo27 extends K06PravidloBase {

    static final public String OBS27 = "obs27";

    public Pravidlo27() {
        super(OBS27,
                "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
                "Chybí povinná (popisná) část struktury datového balíčku SIP.",
                "Bod 2.7. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node xmlData = metsParser.getMetsXmlData();
        if (xmlData == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.");
        }
        Node metsMdWrap = metsParser.getMetsMdWrap();
        if (!ValuesGetter.hasOnlyOneChild_ElementNode(metsMdWrap, "mets:xmlData")) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Element <mets:mdWrap> obsahuje více dětských elementů <mets:xmlData>.", metsMdWrap);
        }
    }

}
