package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.98
// Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>),
// obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>,
// <nsesss:Trideni>
// elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se
// stejnými hodnotami,
// jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>,
// <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli
// dětské entity dokument
// (<nsesss:Dokument>).",

public class Pravidlo98 extends K06PravidloBase {

    public static final String OBS98 = "obs98";

    public Pravidlo98() {
        super(OBS98,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli dětské entity dokument (<nsesss:Dokument>).",
                "Chybně jsou uvedeny spisové znaky.",
                "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }
        List<Node> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            return false;
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals(NsessV3.DIL) ||
                    zakladnientita.getNodeName().equals(NsessV3.SPIS)) {
                if (!kontrolaEntity(zakladnientita, dokumenty)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean kontrolaEntity(Node zakladnientita, List<Node> dokumenty) {
        Node n_zakl_jsz = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                 "nsesss:JednoduchySpisovyZnak");
        if (n_zakl_jsz == null) {
            detailChyby = "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladnientita);
            mistoChyby = getMistoChyby(zakladnientita);
            return false;
        }
        Node n_zakl_pusz = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                  "nsesss:PlneUrcenySpisovyZnak");
        if (n_zakl_pusz == null) {
            detailChyby = "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladnientita);
            mistoChyby = getMistoChyby(zakladnientita);
            return false;
        }
        String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
        String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

        String jednoduchy, plneUrceny;

        for (int j = 0; j < dokumenty.size(); j++) {
            Node dokument = dokumenty.get(j);
            Node n_j = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                              "nsesss:JednoduchySpisovyZnak");
            if (n_j == null) {
                return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument),
                                   getMistoChyby(dokument));
            }
            jednoduchy = n_j.getTextContent();
            Node n_p = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                              "nsesss:PlneUrcenySpisovyZnak");
            if (n_p == null)
                return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument), getMistoChyby(dokument));

            plneUrceny = n_p.getTextContent();

            boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
            if (!b)
                return nastavChybu(
                                   "Nesplněna podmínka pravidla. " + kontrola.getJmenoIdentifikator(zakladnientita)
                                           + " "
                                           + kontrola.getJmenoIdentifikator(dokument),
                                   getMistoChyby(n_zakl_jsz) + " "
                                           + getMistoChyby(n_zakl_pusz) + " " + getMistoChyby(n_j) + " "
                                           + getMistoChyby(n_p));
        }
        return true;
    }

}
