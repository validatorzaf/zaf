package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

public class Pravidlo89 extends K06PravidloBase {

    static final public String OBS89 = "obs89";

    public Pravidlo89() {
        super(OBS89,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>) obsahuje element <nsesss:TrvalySkartacniSouhlas> s hodnotou ne.",
                "Na dokument je uplatněn trvalý skartační souhlas.",
                "Požadavek 3.1.2 NSESSS; příloha č. 2 NSESSS, nsesss-common.xsd, ř. 548.");
    }

    //OBSAHOVÁ č.89 Každá entita věcná skupina (<nsesss:VecnaSkupina>) obsahuje element <nsesss:TrvalySkartacniSouhlas> s hodnotou ne.
    @Override
    protected void kontrola() {

        List<Element> listVecnaSkupina = metsParser.getElements(NsesssV4.VECNA_SKUPINA);
        if (!CollectionUtils.isEmpty(listVecnaSkupina)) {
            for (Element elVecnaKupina : listVecnaSkupina) {
                Element elTrvalySkartacniSouhlas = ValuesGetter.getXChild(elVecnaKupina, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRVALY_SKARTACNI_SOUHLAS);
                if (elTrvalySkartacniSouhlas == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:TrvalySkartacniSouhlas>.",
                            getMistoChyby(elVecnaKupina), kontrola.getEntityId(elVecnaKupina));
                }
                String hodnota = elTrvalySkartacniSouhlas.getTextContent();
                if (!StringUtils.equals(hodnota, "ne")) {
                    nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:TrvalySkartacniSouhlas> není správně vyplněn.",
                            getMistoChyby(elTrvalySkartacniSouhlas), kontrola.getEntityId(elVecnaKupina));
                }
            }
        }
    }

}
