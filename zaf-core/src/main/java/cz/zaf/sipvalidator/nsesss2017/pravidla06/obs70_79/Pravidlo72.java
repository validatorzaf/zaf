package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo72 extends K06PravidloBase {

    static final public String OBS72 = "obs72";

    public Pravidlo72() {
        super(Pravidlo72.OBS72,
                "Pokud existuje jakýkoli element <nsesss:CasPouziti>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času opatření komponenty (počítačového souboru) elektronickým podpisem, elektronickou značkou nebo časovým razítkem.",
                "§ 4 odst. 7 písm. b) vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.72 Každý element <nsesss:CasPouziti> obsahuje atribut datum.",
    @Override
    protected void kontrola() {
        List<Element> casy = metsParser.getNodes(NsessV3.CAS_POUZITI);
        for (Element cas : casy) {
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <nsesss:CasPouziti> neobsahuje atribut datum. "
                        + getJmenoIdentifikator(cas), cas);
            }
        }
    }

}
