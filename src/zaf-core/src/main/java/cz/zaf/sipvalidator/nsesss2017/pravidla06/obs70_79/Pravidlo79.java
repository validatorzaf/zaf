package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo79 extends K06PravidloBase {

    public Pravidlo79(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS79,
                "V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
                "Není v souladu datum skartačního řízení a roku skartační operace.",
                null);
    }

    //OBSAHOVÁ č.79 V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, 
    // v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> 
    // uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList skartacniRizeni = ValuesGetter.getAllAnywhere("nsesss:SkartacniRizeni", metsParser.getDocument());
        if (skartacniRizeni == null) {
            List<Node> zakladniEntity = predpokladZakladniEntity();
            if (zakladniEntity == null) {
                return false;
            }

            Node entita = zakladniEntity.get(0);
            return nastavChybu("Nenalezen element <nsesss:SkartacniRizeni>. " + getJmenoIdentifikator(entita));
        }
        for (int i = 0; i < skartacniRizeni.getLength(); i++) {
            Node skrizeni = skartacniRizeni.item(i);
            Node datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if (datum == null) {
                return nastavChybu("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni),
                                   skrizeni);
            }

            Node dataceVyrazeni = ValuesGetter.getSourozencePrvnihoSeJmenem(skrizeni, "nsesss:DataceVyrazeni");
            if (dataceVyrazeni == null) {
                return nastavChybu("Nenalezen element <nsesss:DataceVyrazeni>. " + getJmenoIdentifikator(skrizeni),
                                   skrizeni);
            }
            Node rokSkOperace = ValuesGetter.getXChild(dataceVyrazeni, "nsesss:RokSkartacniOperace");
            if (rokSkOperace == null) {
                return nastavChybu("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(skrizeni),
                                   dataceVyrazeni);
            }
            String rokOperace = rokSkOperace.getTextContent();

            String strDatum = datum.getTextContent().substring(0, 4);
            if (!ValuesGetter.overSpravnostRetezceProInt(strDatum)) {
                return nastavChybu("Hodnota roku v elementu <nsesss:Datum> uvedena ve špatném formátu. "
                        + getJmenoIdentifikator(skrizeni),
                                   datum);
            }
            if (!ValuesGetter.overSpravnostRetezceProInt(rokOperace)) {
                return nastavChybu("Hodnota roku v elementu <nsesss:RokSkartacniOperace> uvedena ve špatném formátu. "
                        + getJmenoIdentifikator(skrizeni),
                                   rokSkOperace);
            }

            int a = Integer.parseInt(strDatum);
            int b = Integer.parseInt(rokOperace);
            if (!(a >= b)) {
                return nastavChybu("Nesplněna podmínka pravidla." + " Datum: " + a + ". Rok skartační operace: " + b
                        + ". " + getJmenoIdentifikator(skrizeni),
                                   getMistoChyby(datum) + " " + getMistoChyby(rokSkOperace));
            }

        }
        return true;
    }

}
