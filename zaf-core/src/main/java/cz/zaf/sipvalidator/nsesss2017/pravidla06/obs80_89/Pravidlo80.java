package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo80 extends K06PravidloBaseOld {

    static final public String OBS80 = "obs80";

    public Pravidlo80() {
        super(Pravidlo80.OBS80,
                "V jakémkoli elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, která je menší nebo rovna aktuálnímu roku.",
                "Uvedeno je chybně datum skartačního řízení (uváděný rok ještě nenadešel).",
                "§ 20 odst. 1 vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.80 V jakémkoli elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, 
    // která je menší nebo rovna aktuálnímu roku.
    @Override
    protected boolean kontrolaPravidla() {
        List<Element> skartacniRizeni = metsParser.getNodes(NsessV3.SKARTACNI_RIZENI);
        if (CollectionUtils.isEmpty(skartacniRizeni)) {
            return nastavChybu("Nenalezen element <nsesss:SkartacniRizeni>.");
        }
        for (Element skrizeni : skartacniRizeni) {
            Element datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if (datum == null) {
                return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni),
                                   skrizeni);
            }
            Integer date = vratRok(datum);
            if (date == null) {
                return false;
            }
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (!(date <= year)) {
                return nastavChybu("Hodnota elementu <nsesss:Datum> (" + date + ") je vyšší než aktuální rok. "
                        + getJmenoIdentifikator(skrizeni), datum);
            }
        }
        return true;
    }


}
