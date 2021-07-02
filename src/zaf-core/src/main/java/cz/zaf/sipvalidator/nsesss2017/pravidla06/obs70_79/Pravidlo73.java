package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo73 extends K06PravidloBase {

    static final public String OBS73 = "obs73";

    public Pravidlo73() {
        super(OBS73,
                "Pokud existuje jakýkoli element <nsesss:CasOvereni>, každý obsahuje atribut datum.",
                "Chybí strojový zápis času ověření elektronického podpisu, elektronické značky nebo časového razítka.",
                "§ 4 odst. 7 písm. d) vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.73 Každý element <nsesss:CasOvereni> obsahuje atribut datum.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList casy = ValuesGetter.getAllAnywhere("nsesss:CasOvereni", metsParser.getDocument());
        if (casy == null)
            return true;
        for (int i = 0; i < casy.getLength(); i++) {
            Node cas = casy.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if (!maDatum) {
                return nastavChybu("Element <nsesss:CasOvereni> neobsahuje atribut datum. "
                        + getJmenoIdentifikator(cas),
                                   getMistoChyby(cas));
            }
        }
        return true;
    }


}
