package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
public class Pravidlo17 extends K06PravidloBase {

    static final public String OBS17 = "obs17";

    public Pravidlo17() {
        super(OBS17,
                "Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
                "Uveden je chybně popis původce.",
                "Bod 1.3 přílohy č. 2 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsHdr = metsParser.getMetsHdr();

        List<Element> nodes = metsParser.getNodes(MetsElements.AGENT);
        if (CollectionUtils.isEmpty(nodes)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:agent>.", metsHdr);

        }
        int pocitadlo = 0;
        for (Node node : nodes) {
            if (ValuesGetter.hasAttributValue(node, "TYPE", "INDIVIDUAL")) {
                pocitadlo++;
            }
        }
        if (pocitadlo == 0) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", metsHdr);
        }
    }

}
