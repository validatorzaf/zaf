package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo80 extends K06PravidloBase {

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
    protected void kontrola() {
        List<Element> skartacniRizeni = metsParser.getNodes(NsesssV3.SKARTACNI_RIZENI);
        if (CollectionUtils.isEmpty(skartacniRizeni)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRizeni>.");
        }
        for (Element skrizeni : skartacniRizeni) {
            Element datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if (datum == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni),
                        skrizeni, kontrola.getEntityId(skrizeni));
            }
            Integer date = vratRok(datum);

            int year = kontrola.getContext().getLocalDate().getYear();
            if (!(date <= year)) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota elementu <nsesss:Datum> (" + date + ") je vyšší než aktuální rok. "
                        + getJmenoIdentifikator(skrizeni), datum, kontrola.getEntityId(skrizeni));
            }
        }
    }

}
