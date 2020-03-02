package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo87 extends K06PravidloBase {

    static final public String OBS87 = "obs87";

    public Pravidlo87(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo87.OBS87,
                "Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> je uveden i element <nsesss:DatumOdeslani>.",
                "Chybí příjemce nebo datum odeslání dokumentu.",
                "Příloha č. 2 NSESSS, ř. 1471 a 1481.");
    }

    //OBSAHOVÁ č.87 Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element 
    // <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. 
    // Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> 
    // je uveden i element <nsesss:DatumOdeslani>.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList vyrizeni = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", metsParser.getDocument());
        if (vyrizeni == null) {
            return true;
        }
        int size = vyrizeni.getLength();
        for (int i = 0; i < size; i++) {
            Node n = vyrizeni.item(i);
            Node datumOdeslani = ValuesGetter.getXChild(n, "nsesss:DatumOdeslani");
            Node prijemce = ValuesGetter.getXChild(n, "nsesss:Prijemce");
            if (datumOdeslani != null && prijemce == null) {
                return nastavChybu("Nenalezen element <nsesss:Prijemce>. " + getJmenoIdentifikator(n),
                                  getMistoChyby(n));
            }
            if (prijemce != null && datumOdeslani == null) {
                return nastavChybu("Nenalezen element <nsesss:DatumOdeslani>. " + getJmenoIdentifikator(n),
                                  getMistoChyby(n));
            }
        }
        return true;
    }

}
