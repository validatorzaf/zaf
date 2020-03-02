package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo70 extends K06PravidloBase {

    static final public String OBS70 = "obs70";

    public Pravidlo70(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo70.OBS70,
                "Jakýkoli element <nsesss:Identifikator> není opakovatelný, pokud se nenachází v hierarchii elementů <nsesss:Komponenta>, <nsesss:EvidencniUdaje> a <nsesss:Identifikace>.",
                "Uveden je chybně identifikátor věcné skupiny, typového spisu, součásti, dílu, spisu nebo dokumentu.",
                "Příloha č. 2 NSESSS, ř. 497.");
    }

    //OBSAHOVÁ č.70 Jakýkoli element <nsesss:Identifikator> není opakovatelný, pokud se nenachází v hierarchii elementů <nsesss:Komponenta>, <nsesss:EvidencniUdaje> a <nsesss:Identifikace>.",
    @Override
    protected boolean kontrolaPravidla() {
        //        NodeList id = ValuesGetter.getAllAnywhere("nsesss:Identifikator", metsParser.getDocument());
        //        ArrayList<Node> id = ValuesGetter.getAllAnywhereArrayList("nsesss:Identifikator", metsParser.getDocument());
        List<Node> identifikatory = metsParser.getIdentifikatory();
        if (identifikatory == null) {
            return nastavChybu("Nenalezen žádný element <nsesss:Identifikator>.");
        }
        int size = identifikatory.size();
        for (int i = 0; i < size; i++) {
            Node identifikator = identifikatory.get(i);
            Node rodic = identifikator.getParentNode();
            if (ValuesGetter.getSpecificChildWithName(rodic, "nsesss:Identifikator").size() > 1) {
                Node komponenta = ValuesGetter.getXParent(identifikator, "nsesss:Identifikace", "nsesss:EvidencniUdaje",
                                                          "nsesss:Komponenta");
                if (komponenta == null) {
                    Node entita = ValuesGetter.getXParent(identifikator, "nsesss:Identifikace", "nsesss:EvidencniUdaje")
                            .getParentNode();

                    return nastavChybu("Element <nsesss:Identifikator> se opakuje přes nesplnění podmínky pravidla. "
                            + getJmenoIdentifikator(entita), identifikator);
                }
            }
        }
        return true;
    }

}
