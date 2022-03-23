package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo74 extends K06PravidloBase {

    static final public String OBS74 = "obs74";

    public Pravidlo74() {
        super(OBS74,
                "Pokud existuje jakýkoli element <nsesss:PosuzovanyOkamzik>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času, k němuž je vztaženo posuzování platnosti elektronického podpisu, elektronické značky nebo časového razítka.",
                "§ 4 odst. 7 písm. f) vyhlášky č. 259/2012 Sb.");
        // TODO Auto-generated constructor stub
    }

    //OBSAHOVÁ č.74 Každý element <nsesss:PosuzovanyOkamzik> obsahuje atribut datum.",
    @Override
    protected void kontrola() {
        List<Element> nodes = metsParser.getNodes(NsessV3.POSUZOVANY_OKAMZIK);
        for (Element cas : nodes) {
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <nsesss:PosuzovanyOkamzik> neobsahuje atribut datum. "
                        + getJmenoIdentifikator(cas), cas);
            }
        }
    }

}
