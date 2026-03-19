package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import org.apache.commons.lang3.StringUtils;

public class Pravidlo90 extends K06PravidloBase {

    static final public String OBS90 = "obs90";

    public Pravidlo90() {
        super(OBS90,
                "Pokud existuje typový spis (<nsesss:TypovySpis>), jeho rodičovská entita věcná skupina (<nsesss:VecnaSkupina>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje> element <nsesss:UrcenoProTypoveSpisy> s hodnotou ano. Současně pokud existuje věcná skupina (<nsesss:VecnaSkupina>), která obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje> element <nsesss:UrcenoProTypoveSpisy> s hodnotou ano, potom všechny její dětské entity jsou typový spis (<nsesss:TypovySpis>).",
                "Chybně uvedeno určení věcné skupiny pro typové spisy.",
                "Požadavek 3.1.2 NSESSS; příloha č. 2 NSESSS, nsesss-common.xsd, ř. 540.");
    }

    //OBSAHOVÁ č.90 Pokud existuje typový spis (<nsesss:TypovySpis>), jeho rodičovská entita věcná skupina (<nsesss:VecnaSkupina>) 
    //obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje> element <nsesss:UrcenoProTypoveSpisy> s hodnotou ano. 
    //Současně pokud existuje věcná skupina (<nsesss:VecnaSkupina>), která obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje> element <nsesss:UrcenoProTypoveSpisy> 
    //s hodnotou ano, potom všechny její dětské entity jsou typový spis (<nsesss:TypovySpis>).
    
    @Override
    protected void kontrola() {
        List<Element> listTypoveSpisy = metsParser.getElements(NsesssV4.TYPOVY_SPIS);
        List<Element> listUrcenoProTypoveSpisy = metsParser.getElements(NsesssV4.URCENO_PRO_TYPOVE_SPISY);
        if (!CollectionUtils.isEmpty(listTypoveSpisy)) {
            for (Element elTypovySpis : listTypoveSpisy) {
                Element elVecnaSkupina = ValuesGetter.getXChild(elTypovySpis, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI, NsesssV4.MATERSKA_ENTITA, NsesssV4.VECNA_SKUPINA);
                if (elVecnaSkupina == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena rodičovská entita věcná skupina typového spisu.", getMistoChyby(elTypovySpis), kontrola.getEntityId(elTypovySpis));
                }
                Element elUrcenoProTypoveSpisy = ValuesGetter.getXChild(elVecnaSkupina, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.URCENO_PRO_TYPOVE_SPISY);
                if (elUrcenoProTypoveSpisy == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:UrcenoProTypoveSpisy>.", getMistoChyby(elVecnaSkupina), kontrola.getEntityId(elVecnaSkupina));
                }
                String strUrcenoProTypoveSpisy = elUrcenoProTypoveSpisy.getTextContent();
                if (!StringUtils.equals(strUrcenoProTypoveSpisy, "ano")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:UrcenoProTypoveSpisy> neobsahuje hodnotu ano.", getMistoChyby(elUrcenoProTypoveSpisy), kontrola.getEntityId(elVecnaSkupina));
                }
            }
        }
        if (!CollectionUtils.isEmpty(listUrcenoProTypoveSpisy)) {
            for (Element elUrcenoProTypoveSpisy : listUrcenoProTypoveSpisy) {
                String strUrcenoProTypoveSpisy = elUrcenoProTypoveSpisy.getTextContent();
                if (StringUtils.equals(strUrcenoProTypoveSpisy, "ano")) {
                    Element elVecnaSKupina = ValuesGetter.getXParent(elUrcenoProTypoveSpisy, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VECNA_SKUPINA);
                    if (elVecnaSKupina != null) {
                        Element elTypovySpis = ValuesGetter.getXParent(elVecnaSKupina, NsesssV4.MATERSKA_ENTITA, NsesssV4.TRIDENI, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TYPOVY_SPIS);
                        if (elTypovySpis == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen dětský element <nsesss:TypovySpis> elementu <nsesss:VecnaSkupina>, která obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje> element <nsesss:UrcenoProTypoveSpisy> s hodnotou ano.", getMistoChyby(elVecnaSKupina), kontrola.getEntityId(elVecnaSKupina));
                        }
                    }
                }
            }
        }
    }

}
