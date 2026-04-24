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
                "Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, přičemž vážený součet prvních sedmi číslic (váhy 8 až 2) se vydělí 11 a zbytek určuje kontrolní (osmou) číslici takto: pokud je zbytek 0, kontrolní číslice je 1; pokud je zbytek 1, kontrolní číslice je 0; pokud je jiný zbytek, kontrolní číslice je rozdíl zbytku od 11.",
                "Chybně je uvedeno IČO subjektu.", null);
    }

    //OBSAHOVÁ č.92 Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, přičemž vážený součet prvních sedmi číslic (váhy 8 až 2) se vydělí 11 a zbytek určuje kontrolní (osmou) číslici takto: pokud je zbytek 0, kontrolní číslice je 1; pokud je zbytek 1, kontrolní číslice je 0; pokud je jiný zbytek, kontrolní číslice je rozdíl zbytku od 11.
    @Override
    protected void kontrola() {
        List<Element> identifikatory = metsParser.getIdentifikatory();
        if (CollectionUtils.isEmpty(identifikatory)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Identifikator>.");
        }
        for (int i = 0; i < identifikatory.size(); i++) {
            Element identif = identifikatory.get(i);
            Element entita = getEntity(identif);
            if (!ValuesGetter.hasAttribut(identif, "zdroj")) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Elementu <nsesss:Identifikátor> chybí atribut zdroj. " + getJmenoIdentifikator(identif),
                        getMistoChyby(identif), getEntityId(entita));
            }
            String str = ValuesGetter.getValueOfAttribut(identif, "zdroj");
            if (str.equals("IČ") || str.equals("IČO")) {
                String hodnota = identif.getTextContent();
                if (!ValidateIC.validate(hodnota)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "IČO není ve správném formátu. " + getJmenoIdentifikator(identif),
                            getMistoChyby(identif), getEntityId(entita));

                }
            }
        }
    }

}
