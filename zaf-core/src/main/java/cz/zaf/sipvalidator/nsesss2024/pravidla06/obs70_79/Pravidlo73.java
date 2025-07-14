package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo73 extends K06PravidloBase {

    static final public String OBS73 = "obs73";

    public Pravidlo73() {
        super(OBS73,
                "Pokud existuje jakýkoli element <nsesss:CasOvereni>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času ověření elektronického podpisu, elektronické značky nebo časového razítka.",
                "§ 4 odst. 7 písm. g) vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.73 Každý element <nsesss:CasOvereni> obsahuje atribut datum.",
    @Override
    protected void kontrola() {
        List<Element> nodes = metsParser.getNodes(NsesssV3.CAS_OVERENI);
        for (Element cas : nodes) {
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                Element elEntita = kontrola.getEntity(cas);
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <nsesss:CasOvereni> neobsahuje atribut datum.", cas, kontrola.getEntityId(elEntita));
            }
        }
    }
}
