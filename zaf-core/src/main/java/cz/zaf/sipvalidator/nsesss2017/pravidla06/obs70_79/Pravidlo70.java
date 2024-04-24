package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//
// OBSAHOVÁ č.70 Jakýkoli element <nsesss:Identifikator> není opakovatelný,
// pokud se nenachází v hierarchii elementů <nsesss:Komponenta>,
// <nsesss:EvidencniUdaje> a <nsesss:Identifikace>."
//
public class Pravidlo70 extends K06PravidloBase {
    
    static final public String OBS70 = "obs70";
    
    public Pravidlo70() {
        super(OBS70,
                "Jakýkoli element <nsesss:Identifikator> není opakovatelný, pokud se nenachází v hierarchii elementů <nsesss:Komponenta>, <nsesss:EvidencniUdaje> a <nsesss:Identifikace>.",
                "Uveden je chybně identifikátor věcné skupiny, typového spisu, součásti, dílu, spisu nebo dokumentu.",
                "Příloha č. 2 NSESSS, ř. 497.");
    }

    @Override
    protected void kontrola() {
        //        NodeList id = ValuesGetter.getAllAnywhere("nsesss:Identifikator", metsParser.getDocument());
        //        ArrayList<Node> id = ValuesGetter.getAllAnywhereArrayList("nsesss:Identifikator", metsParser.getDocument());
        List<Element> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Identifikator>.");
        }
        int size = identifikatory.size();
        for (int i = 0; i < size; i++) {
            Element identifikator = identifikatory.get(i);
            Element rodic = (Element) identifikator.getParentNode();
            if (ValuesGetter.getChildNodes(rodic, NsesssV3.IDENTIFIKATOR).size() > 1) {
                Element komponenta = ValuesGetter.getXParent(identifikator, NsesssV3.IDENTIFIKACE,
                        NsesssV3.EVIDENCNI_UDAJE, NsesssV3.KOMPONENTA);
                if (komponenta == null) {
                    Element entita = (Element) ValuesGetter.getXParent(identifikator, NsesssV3.IDENTIFIKACE,
                            NsesssV3.EVIDENCNI_UDAJE).getParentNode();
                    
                    nastavChybu(BaseCode.NEPOVOLENY_ELEMENT, "Element <nsesss:Identifikator> se opakuje přes nesplnění podmínky pravidla. "
                            + getJmenoIdentifikator(entita), identifikator, kontrola.getEntityId(entita));
                }
            }
        }
    }
    
}
