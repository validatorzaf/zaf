package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

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
        List<Element> casy = metsParser.getNodes(NsesssV3.CAS_POUZITI);
        for (Element cas : casy) {
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                Element elEntita = kontrola.getEntity(cas);
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <nsesss:CasPouziti> neobsahuje atribut datum.", cas, kontrola.getEntityId(elEntita));
            }
        }
    }

}
