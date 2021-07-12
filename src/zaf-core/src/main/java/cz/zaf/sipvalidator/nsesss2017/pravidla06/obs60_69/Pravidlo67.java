package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.Helper_Obj_Node;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.Obj_Node_String;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo67 extends K06PravidloBase {

    static final public String OBS67 = "obs67";

    public Pravidlo67() {
        super(OBS67,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
                "Uveden je chybně skartační znak u dílu nebo spisu (stanovuje se podle nejvyššího skartačního znaku dokumentu).",
                "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }
        List<Node> dokumenty = metsParser.getDokumenty();

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Node zakladnientita = zakladniEntity.get(i);
            String zeName = zakladnientita.getNodeName();
            if (zeName.equals("nsesss:Spis") || zeName.equals("nsesss:Dil")) {
                Node n = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if (n == null) {
                    return nastavChybu("Nenalezen dětský element <nsesss:SkartacniZnak> základní entity. "
                            + getJmenoIdentifikator(zakladnientita), getMistoChyby(zakladnientita));
                }
                String skZnakME = n.getTextContent();
                ArrayList<Obj_Node_String> hodnotyDokumentu = new ArrayList<>();
                if (dokumenty == null || dokumenty.size() == 0) {
                    return nastavChybu("Nenalezen žádný dětský element <nsesss:Dokument>. " + getJmenoIdentifikator(
                                                                                                                   zakladnientita),
                                       getMistoChyby(zakladnientita));
                }
                for (int j = 0; j < dokumenty.size(); j++) {
                    Node dokument = dokumenty.get(j);
                    Node nd = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani",
                                                     "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                    if (nd == null) {
                        return nastavChybu("Nenalezen dětský element <nsesss:SkartacniZnak> elementu <nsesss:Dokument>. "
                                + getJmenoIdentifikator(dokument), getMistoChyby(dokument));
                    }
                    String znak = nd.getTextContent();
                    hodnotyDokumentu.add(new Obj_Node_String(dokument, znak));
                }
                switch (skZnakME) {
                case "A":
                    if (Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A") == null) {
                        return nastavChybu("Spis se skartačním znakem A neobsahuje žádný dokument se skartačním znakem A. "
                                + getJmenoIdentifikator(zakladnientita), n);
                    }
                    break;
                case "V":
                    Obj_Node_String obj_a = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A");
                    if (obj_a != null) {
                        return nastavChybu("Spis se skartačním znakem V obsahuje dokument se skartačním znakem A. "
                                + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(obj_a.get_node()),
                                           getMistoChyby(n) + " " + getMistoChyby(obj_a.get_node()));
                    }
                    Obj_Node_String obj_v = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "V");
                    if (obj_a == null && obj_v == null) {
                        return nastavChybu("Spis se skartačním znakem V neobsahuje žádný dokument se skartačním znakem V. "
                                + getJmenoIdentifikator(zakladnientita), n);
                    }
                    break;
                case "S":
                    ArrayList<Obj_Node_String> list = Helper_Obj_Node.all_with_skartacni_znak(hodnotyDokumentu, "A",
                                                                                              "V");
                    if (!list.isEmpty()) {
                        String ch = "";
                        String iden = "";
                        for (int k = 0; k < list.size(); k++) {
                            Node no = list.get(i).get_node();
                            ch += getMistoChyby(no);
                            iden += " " + getJmenoIdentifikator(no);
                            if (k != list.size() - 1)
                                ch += " ";
                        }
                        return nastavChybu("Spis se skartačním znakem S obsahuje dokument se skartačním znakem A nebo V. "
                                + getJmenoIdentifikator(zakladnientita) + iden, getMistoChyby(n) + " " + ch);
                    }

                    break;
                }
            }
        }
        return true;
    }

}
