package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.10 Element <mets:mets> obsahuje právě jeden dětský element
// <mets:metsHdr>.
public class Pravidlo10 extends K06PravidloBase {

    static final public String OBS10 = "obs10";

    public Pravidlo10() {
        super(OBS10,
                "Element <mets:mets> obsahuje dětský element <mets:metsHdr>.",
                "Chybí povinná část (záhlaví) struktury datového balíčku SIP.",
                "Bod 1.2 přílohy č. 2 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsMets = metsParser.getMetsRootNode();
        List<Element> metsHdrs = ValuesGetter.getChildNodes(metsMets, "mets:metsHdr");
        if (CollectionUtils.isEmpty(metsHdrs)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", metsMets);
        }
        // Ze schematu musi byt maximalne jeden
        Validate.isTrue(metsHdrs.size() == 1);
    }

}
