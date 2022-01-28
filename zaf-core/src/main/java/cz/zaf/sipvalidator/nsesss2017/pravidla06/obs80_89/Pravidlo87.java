package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo87 extends K06PravidloBaseOld {

    static final public String OBS87 = "obs87";

    public Pravidlo87() {
        super(OBS87,
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
        List<Node> vyrizeni = metsParser.getNodes(NsessV3.VYRIZENI);        
        for (Node n: vyrizeni) {
            Node datumOdeslani = ValuesGetter.getXChild(n, "nsesss:DatumOdeslani");
            Node prijemce = ValuesGetter.getXChild(n, "nsesss:Prijemce");
            if (datumOdeslani != null && prijemce == null) {
                return nastavChybu("Nenalezen element <nsesss:Prijemce>. " + getJmenoIdentifikator(n),
                                  n);
            }
            if (prijemce != null && datumOdeslani == null) {
                return nastavChybu("Nenalezen element <nsesss:DatumOdeslani>. " + getJmenoIdentifikator(n),
                                  n);
            }
        }
        return true;
    }

}
