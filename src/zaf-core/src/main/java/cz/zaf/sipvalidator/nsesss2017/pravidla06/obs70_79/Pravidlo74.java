package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo74 extends K06PravidloBase {

    static final public String OBS74 = "obs74";

    public Pravidlo74(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo74.OBS74,
                "Pokud existuje jakýkoli element <nsesss:PosuzovanyOkamzik>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času, k němuž je vztaženo posuzování platnosti elektronického podpisu, elektronické značky nebo časového razítka.",
                "§ 4 odst. 7 písm. d) vyhlášky č. 259/2012 Sb.");
        // TODO Auto-generated constructor stub
    }

    //OBSAHOVÁ č.74 Každý element <nsesss:PosuzovanyOkamzik> obsahuje atribut datum.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList posuzovanyOkamzik = ValuesGetter.getAllAnywhere("nsesss:PosuzovanyOkamzik", metsParser.getDocument());
        if (posuzovanyOkamzik == null)
            return true;
        for (int i = 0; i < posuzovanyOkamzik.getLength(); i++) {
            Node cas = posuzovanyOkamzik.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                return nastavChybu("Element <nsesss:PosuzovanyOkamzik> neobsahuje atribut datum. "
                        + getJmenoIdentifikator(cas), getMistoChyby(cas));
            }
        }
        return true;
    }

}
