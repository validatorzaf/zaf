package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.13 Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
public class Pravidlo13 extends K06PravidloBase {

    static final public String OBS13 = "obs13";

    public Pravidlo13() {
        super(OBS13,
                "Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
                "Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
                "Bod 2.2.17. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsMets = metsParser.getMetsRootNode();

        if (!ValuesGetter.hasChildWithName(metsMets, "mets:structMap")) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Kořenový element <mets:mets> nemá žádný dětský element <mets:structMap>.", metsMets);

        }
        if (!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:structMap")) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Kořenový element <mets:mets> má více než jeden dětský element <mets:structMap>.", metsMets);
        }

    }

}
