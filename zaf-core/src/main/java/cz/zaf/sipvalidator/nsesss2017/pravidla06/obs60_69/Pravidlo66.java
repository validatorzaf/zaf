package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo66 extends K06PravidloBase {

    static final public String OBS66 = "obs66";

    public Pravidlo66() {
        super(OBS66,
                "Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
                "U dílu, spisu nebo dokumentu nelze provést skartační řízení, protože ještě nenadešel uváděný rok skartační operace.",
                "§ 20 odst. 1 vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.66 Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element elZakladniEntity = zakladniEntity.get(i);
            Element elRokSkratacniOperace = ValuesGetter.getXChild(elZakladniEntity, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                    NsessV3.DATACE_VYRAZENI, NsessV3.ROK_SKARTACNI_OPERACE);
            if (elRokSkratacniOperace == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen podřízený element <" + NsessV3.ROK_SKARTACNI_OPERACE + ">",
                        elZakladniEntity, kontrola.getEntityId(elZakladniEntity));
            }

            Integer rokSkartacniOperace = vratRok(elRokSkratacniOperace);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (rokSkartacniOperace > year) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota roku elementu <nsesss:RokSkartacniOperace> je větší, než aktuální rok. Hodnota: "
                        + rokSkartacniOperace + ". " + getJmenoIdentifikator(elZakladniEntity),
                        elRokSkratacniOperace,
                        kontrola.getEntityId(elZakladniEntity));
            }
        }
    }

}
