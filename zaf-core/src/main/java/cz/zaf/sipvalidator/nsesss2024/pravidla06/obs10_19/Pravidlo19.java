package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs10_19;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
public class Pravidlo19 extends K06PravidloBase {

    static final public String OBS19 = "obs19";

    public Pravidlo19() {
        super(OBS19,
                "Každý element <mets:agent> obsahuje atribut ID.",
                "Uveden je chybně popis původce.",
                "Bod 1.3 přílohy č. 2 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElMetsAgent = metsParser.getNodes(MetsElements.AGENT);
        if (CollectionUtils.isEmpty(listElMetsAgent)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:agent>.");
        }

        List<Node> errorList = new ArrayList<>(0);
        listElMetsAgent.stream().filter((node) -> (!ValuesGetter.hasAttribut(node, "ID"))).forEachOrdered((node) -> {
            errorList.add(node);
        });

        if (!errorList.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:agent> neobsahuje atribut ID.", errorList);
        }
    }

}
