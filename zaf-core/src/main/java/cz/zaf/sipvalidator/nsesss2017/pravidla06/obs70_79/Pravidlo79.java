package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo79 extends K06PravidloBaseOld {

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
    protected boolean kontrolaPravidla() {
        List<Element> skartacniRizeni = metsParser.getNodes(NsessV3.SKARTACNI_RIZENI);
        if (CollectionUtils.isEmpty(skartacniRizeni)) {
            return nastavChybu("Nenalezen element <nsesss:SkartacniRizeni>.");
        }
        for (Element skrizeni : skartacniRizeni) {
            Element dataceVyrazeni = ValuesGetter.getSourozencePrvnihoSeJmenem(skrizeni, "nsesss:DataceVyrazeni");
            if (dataceVyrazeni == null) {
                return nastavChybu("Nenalezen element <nsesss:DataceVyrazeni>. " + getJmenoIdentifikator(skrizeni),
                                   skrizeni);
            }
            Node rokSkOperace = ValuesGetter.getXChild(dataceVyrazeni, NsessV3.ROK_SKARTACNI_OPERACE);
            if (rokSkOperace == null) {
                return nastavChybu("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(skrizeni),
                                   dataceVyrazeni);
            }
            Integer rokOperace = vratRok(rokSkOperace);
            if (rokOperace == null) {
                return false;
            }

            Node datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if (datum == null) {
                return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni),
                                   skrizeni);
            }
            Integer rokSkRizeni = vratRok(datum);
            if (rokSkRizeni == null) {
                return false;
            }

            if (!(rokSkRizeni >= rokOperace)) {
                return nastavChybu("Nesplněna podmínka pravidla." + " Datum: " + rokSkRizeni
                        + ". Rok skartační operace: " + rokOperace
                        + ". " + getJmenoIdentifikator(skrizeni),
                                   getMistoChyby(datum) + " " + getMistoChyby(rokSkOperace));
            }

        }
        return true;
    }

}