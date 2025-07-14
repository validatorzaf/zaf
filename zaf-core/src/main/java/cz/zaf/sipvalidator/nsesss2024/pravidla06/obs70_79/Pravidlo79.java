package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs70_79;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo79 extends K06PravidloBase {

    static final public String OBS79 = "obs79";

    public Pravidlo79() {
        super(OBS79,
                "V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
                "Není v souladu datum skartačního řízení a roku skartační operace.",
                null);
    }

    //OBSAHOVÁ č.79 V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, 
    // v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> 
    // uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
    @Override
    protected void kontrola() {
        List<Element> skartacniRizeni = metsParser.getNodes(NsesssV3.SKARTACNI_RIZENI);
        if (CollectionUtils.isEmpty(skartacniRizeni)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRizeni>.");
        }
        for (Element skrizeni : skartacniRizeni) {
            Element dataceVyrazeni = ValuesGetter.getSourozencePrvnihoSeJmenem(skrizeni, NsesssV3.DATACE_VYRAZENI);
            if (dataceVyrazeni == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:DataceVyrazeni>. " + getJmenoIdentifikator(skrizeni),
                        skrizeni, kontrola.getEntityId(skrizeni));
            }
            Element rokSkOperace = ValuesGetter.getXChild(dataceVyrazeni, NsesssV3.ROK_SKARTACNI_OPERACE);
            if (rokSkOperace == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(skrizeni),
                        dataceVyrazeni, kontrola.getEntityId(skrizeni));
            }
            Integer rokOperace = vratRok(rokSkOperace); //když null zavolá nastavChybu() sám

            Element datum = ValuesGetter.getXChild(skrizeni, NsesssV3.DATUM);
            if (datum == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni),
                        skrizeni, kontrola.getEntityId(skrizeni));
            }
            Integer rokSkRizeni = vratRok(datum);

            if (!(rokSkRizeni >= rokOperace)) {
                nastavChybu(BaseCode.CHYBA, "Nesplněna podmínka pravidla." + " Datum: " + rokSkRizeni
                        + ". Rok skartační operace: " + rokOperace
                        + ". " + getJmenoIdentifikator(skrizeni),
                        getMistoChyby(datum) + " " + getMistoChyby(rokSkOperace),
                        kontrola.getEntityId(skrizeni));
            }
        }
    }

}
