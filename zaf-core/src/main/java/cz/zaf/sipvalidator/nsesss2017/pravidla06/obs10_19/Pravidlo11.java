package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.exceptions.codes.XmlCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.11 Element <mets:mets> obsahuje právě jeden dětský element
// <mets:dmdSec>.
public class Pravidlo11 extends K06PravidloBase {

    static final public String OBS11 = "obs11";

    public Pravidlo11() {
        super(OBS11,
                "Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
                "Chybí povinná (popisná) část struktury datového balíčku SIP.",
                "Bod 2.6. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsMets = metsParser.getMetsRootNode();
        List<Element> dmdSecNodes = ValuesGetter.getChildNodes(metsMets, "mets:dmdSec");
        if (CollectionUtils.isEmpty(dmdSecNodes)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Kořenový element <mets:mets> nemá žádný dětský element <mets:dmdSec>.", metsMets);
        }
        if (dmdSecNodes.size() > 1) {
            nastavChybu(XmlCode.NEOCEKAVANY_ELEMENT,
                    "Kořenový element <mets:mets> má více než jeden dětský element <mets:dmdSec>.", metsMets);
        }
    }

}
