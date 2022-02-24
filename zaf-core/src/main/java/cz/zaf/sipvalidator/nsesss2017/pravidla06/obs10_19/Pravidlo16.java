package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
public class Pravidlo16 extends K06PravidloBase {

    static final public String OBS16 = "obs16";

    public Pravidlo16() {
        super(OBS16,
                "Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
                "Uveden je chybně popis původce.",
                "Bod 2.3. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsHdr = metsParser.getMetsHdr();

        List<Element> nodes = metsParser.getNodes(MetsElements.AGENT);
        if (CollectionUtils.isEmpty(nodes)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <mets:agent>.", metsHdr);
            return;
        }
        int pocitadlo = 0;
        for (Node node : nodes) {
            if (ValuesGetter.hasAttributValue(node, "TYPE", "ORGANIZATION")) {
                pocitadlo++;
            }
        }
        if (pocitadlo == 0) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", metsHdr);
        }
        if (pocitadlo > 1) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Element <mets:metsHdr> obsahuje více elementů <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", metsHdr);
        }
    }

}
