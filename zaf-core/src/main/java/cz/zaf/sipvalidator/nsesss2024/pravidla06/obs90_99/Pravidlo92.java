package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.cz.ValidateIC;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

public class Pravidlo92 extends K06PravidloBase {

    static final public String OBS92 = "obs92";

    public Pravidlo92() {
        super(OBS92,
                "Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, přičemž vážený součet prvních sedmi číslic má po dělení jedenácti zbytek, který po odečtení od 11 a následném vydělení 10 má zbytek roven poslední číslici.",
                "Chybně je uvedeno IČO subjektu.", null);
    }

    //OBSAHOVÁ č.92 Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, jejichž vážený součet je dělitelný jedenácti beze zbytku.",
    @Override
    protected void kontrola() {
        List<Element> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Identifikator>.");
        }
        for (int i = 0; i < identifikatory.size(); i++) {
            Element identif = identifikatory.get(i);
            Element entita = kontrola.getEntity(identif);
            if (!ValuesGetter.hasAttribut(identif, "zdroj")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Elementu <nsesss:Identifikátor> chybí atribut zdroj. " + getJmenoIdentifikator(identif),
                        getMistoChyby(identif), kontrola.getEntityId(entita));
            }
            String str = ValuesGetter.getValueOfAttribut(identif, "zdroj");
            if (str.equals("IČ") || str.equals("IČO")) {
                String hodnota = identif.getTextContent();
                if (!ValidateIC.validate(hodnota)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "IČO není ve správném formátu. " + getJmenoIdentifikator(identif),
                            getMistoChyby(identif), kontrola.getEntityId(entita));

                }
            }
        }
    }

}
