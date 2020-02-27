package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo72 extends K06PravidloBase {

    public Pravidlo72(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS72,
                "Pokud existuje jakýkoli element <nsesss:CasPouziti>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času opatření komponenty (počítačového souboru) elektronickým podpisem, elektronickou značkou nebo časovým razítkem.",
                "§ 4 odst. 7 písm. d) vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.72 Každý element <nsesss:CasPouziti> obsahuje atribut datum.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList casy = ValuesGetter.getAllAnywhere("nsesss:CasPouziti", metsParser.getDocument());
        if (casy == null)
            return true;
        for (int i = 0; i < casy.getLength(); i++) {
            Node cas = casy.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                return nastavChybu("Element <nsesss:CasPouziti> neobsahuje atribut datum. " + getJmenoIdentifikator(
                                                                                                                    cas),
                                   getMistoChyby(cas));
            }
        }
        return true;
    }


}
