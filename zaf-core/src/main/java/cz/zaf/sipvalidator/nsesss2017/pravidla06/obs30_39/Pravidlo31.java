package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.31 Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
public class Pravidlo31 extends K06PravidloBase {

    static final public String OBS31 = "obs31";

    public Pravidlo31() {
        super(OBS31,
                "Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
                "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
                "Bod 2.10. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElAmdSec = metsParser.getNodes(MetsElements.AMD_SEC);
        if (CollectionUtils.isEmpty(listElAmdSec)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:amdSec>.");
        }

        for (Element elAmdSec : listElAmdSec) {
            List<Element> listElDigiproMD = ValuesGetter.getChildNodes(elAmdSec, "mets:digiprovMD");
            int size = listElDigiproMD.size();
            if (size == 0) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:amdSec> neobsahuje žádný dětský element <mets:digiprovMD>.", elAmdSec);
            }
            if (size > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:amdSec> obsahuje více dětských elementů <mets:digiprovMD>.", elAmdSec);
            }
        }
    }
}
