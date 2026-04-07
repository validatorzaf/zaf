package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

/**
 * OBSAHOVÁ 93b Každá entita věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), 
 * obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> 
 * se stejnými hodnotami.
 */
public class Pravidlo93b extends K06PravidloBase {
    
    static final public String OBS93B = "obs93b";
    
    public Pravidlo93b() {
        super(OBS93B,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.12 NSESSS.");
    }
    
    @Override
    protected void kontrola() {
        List<Element> vsList = metsParser.getNodes(NsesssV4.VECNA_SKUPINA);
        if (CollectionUtils.isEmpty(vsList)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:VecnaSkupina>");
        }
        for (Element vs : vsList) {
            Element spl = ValuesGetter.getXChild(vs, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI, NsesssV4.SPISOVY_PLAN);
            if (spl != null) {
                Element jsz = ValuesGetter.getXChild(vs, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                        NsesssV4.JEDNODUCHY_SPISOVY_ZNAK);
                if (jsz == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs),
                            vs,
                            getEntityId(vs));
                }
                Element pusz = ValuesGetter.getXChild(vs, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI,
                        NsesssV4.PLNE_URCENY_SPISOVY_ZNAK);
                if (pusz == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs),
                            vs, getEntityId(vs));
                }
                if (!jsz.getTextContent().equals(pusz.getTextContent())) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs),
                            getMistoChyby(jsz) + " " + getMistoChyby(pusz),
                            getEntityId(vs));
                }
            }
        }
    }
    
}
