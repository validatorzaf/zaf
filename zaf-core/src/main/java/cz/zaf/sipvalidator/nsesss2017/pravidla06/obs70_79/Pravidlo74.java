package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo74 extends K06PravidloBaseOld {

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
    protected boolean kontrolaPravidla() {
        List<Node> nodes = metsParser.getNodes(NsessV3.POSUZOVANY_OKAMZIK);
        for (Node cas : nodes) {
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                return nastavChybu("Element <nsesss:PosuzovanyOkamzik> neobsahuje atribut datum. "
                        + getJmenoIdentifikator(cas), cas);
            }
        }
        return true;
    }

}
