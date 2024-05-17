package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.30 Každý element <mets:amdSec> obsahuje atribut ID.",
public class Pravidlo30 extends K06PravidloBase {

    static final public String OBS30 = "obs30";

    public Pravidlo30() {
        super(OBS30,
                "Každý element <mets:amdSec> obsahuje atribut ID.",
                "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
                "Bod 2.9. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> nodeList = metsParser.getNodes(MetsElements.AMD_SEC);
        if (CollectionUtils.isEmpty(nodeList)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:amdSec>.");
        }
        for (Element node : nodeList) {
            if (!ValuesGetter.hasAttribut(node, "ID")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:amdSec> nemá atribut ID.", node);
            }
        }
    }

}
