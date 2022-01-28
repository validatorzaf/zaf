package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.97
// Pokud existuje více než jedna základní entita, všechny obsahují
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni>
// elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak>
// se stejnými hodnotami.
public class Pravidlo97 extends K06PravidloBaseOld {

	public static final String OBS97 = "obs97";

    public Pravidlo97() {
        super(OBS97,
                "Pokud existuje více než jedna základní entita, všechny obsahují v hierarchii dětských elementů "
                        + "<nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> "
                        + "se stejnými hodnotami.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.34 NSESSS.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        Node ze0 = zakladniEntity.get(0);
        Node n0_j = ValuesGetter.getXChild(ze0, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                           "nsesss:JednoduchySpisovyZnak");
        Node n0_p = ValuesGetter.getXChild(ze0, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                           "nsesss:PlneUrcenySpisovyZnak");
        if (n0_j == null)
            return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                    + getJmenoIdentifikator(ze0), getMistoChyby(ze0));
        if (n0_p == null)
            return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                    + getJmenoIdentifikator(ze0), getMistoChyby(ze0));
        String jednoduchy = n0_j.getTextContent();
        String plneUrceny = n0_p.getTextContent();

        for (int i = 1; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            Node n_j = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                              "nsesss:JednoduchySpisovyZnak");
            Node n_p = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                              "nsesss:PlneUrcenySpisovyZnak");
            if (n_j == null)
                return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
            if (n_p == null)
                return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
            String jednoduchySpZnZaklEnt = n_j.getTextContent();
            String plneUrcenySpZnZaklEnt = n_p.getTextContent();

            boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
            if (!b)
                return nastavChybu("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita),
                                  getMistoChyby(n0_j) + " " + getMistoChyby(n0_p) + " " + getMistoChyby(n_j) + " "
                                          + getMistoChyby(n_p));
        }
        return true;
    }

}
