package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

/**
 * OBSAHOVÁ 93a. Každá entita věcná skupina (&lt;nsesss:VecnaSkupina&gt;), jejíž
 * rodičovská entita je spisový plán (&lt;nsesss:SpisovyPlan&gt;), obsahuje v
 * hierarchii dětských elementů &lt;nsesss:EvidencniUdaje&gt;,
 * &lt;nsesss:Trideni&gt; elementy &lt;nsesss:JednoduchySpisovyZnak&gt; a
 * &lt;nsesss:PlneUrcenySpisovyZnak&gt; se stejnými hodnotami.
 *
 */
public class Pravidlo93a extends K06PravidloBase {
    
    static final public String OBS93A = "obs93a";
    
    public Pravidlo93a() {
        super(OBS93A,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.30 NSESSS.");
    }
    
    @Override
    protected void kontrola() {
        List<Element> vsList = metsParser.getNodes(NsesssV3.VECNA_SKUPINA);
        if (CollectionUtils.isEmpty(vsList)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:VecnaSkupina>");
        }
        for (Element vs : vsList) {
            Element spl = ValuesGetter.getXChild(vs, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI, NsesssV3.SPISOVY_PLAN);
            if (spl != null) {
                Element jsz = ValuesGetter.getXChild(vs, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                        NsesssV3.JEDNODUCHY_SPISOVY_ZNAK);
                if (jsz == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs),
                            vs,
                            kontrola.getEntityId(vs));
                }
                Element pusz = ValuesGetter.getXChild(vs, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                        NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
                if (pusz == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs),
                            vs, kontrola.getEntityId(vs));
                }
                if (!jsz.getTextContent().equals(pusz.getTextContent())) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs),
                            getMistoChyby(jsz) + " " + getMistoChyby(pusz),
                            kontrola.getEntityId(vs));
                }
            }
        }
    }
    
}
