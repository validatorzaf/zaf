package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo 
// spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, 
// <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, 
// která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), 
// přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
public class Pravidlo67 extends K06PravidloBase {

    static final public String OBS67 = "obs67";

    public Pravidlo67() {
        super(OBS67,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
                "Uveden je chybně skartační znak u dílu nebo spisu (stanovuje se podle nejvyššího skartačního znaku dokumentu).",
                "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.");
    }
    
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            return false;
        }

        Set<Node> zakladniDokumenty = new HashSet<>();
        List<Node> spisyDily = new ArrayList<>();
        for (Node zakladnientita: zakladniEntity) {
            String zeName = zakladnientita.getNodeName();
            if (NsessV3.SPIS.equals(zeName) || NsessV3.DIL.equals(zeName)) {
                spisyDily.add(zakladnientita);
            } else 
            if (NsessV3.DOKUMENT.equals(zeName)) {
                zakladniDokumenty.add(zakladnientita);
            }            
        }
        if(spisyDily.size()==0) {
            return true;
        }
        
        List<Node> dokumenty = metsParser.getDokumenty();
        if (dokumenty == null || dokumenty.size() == 0) {
            return nastavChybu("Nenalezen žádný dětský element <nsesss:Dokument>.", spisyDily);
        }
        // priprava sk. znaku dle zakladnich entit
        Map<Node, Set<String> > skZnaky = new HashMap<>();
        for(Node dokument: dokumenty) {
            // zakladni dokumenty se nezahrnuji
            if(zakladniDokumenty.contains(dokument)) {
                continue;
            }
            
            // nalezeni rodicovske zakl. entity
            Node dokumentyNode = ValuesGetter.getXParent(dokument, "nsesss:Dokumenty");
            if(dokumentyNode==null) {
                return nastavChybu("Nenalezen rodičovský element <nsesss:Dokumenty> elementu <nsesss:Dokument>. "
                        + getJmenoIdentifikator(dokument), dokument);
            }
            Node rodicNode = dokumentyNode.getParentNode();
            if(rodicNode==null) {
                return nastavChybu("Nenalezen rodičovský element elementu <nsesss:Dokument>. "
                        + getJmenoIdentifikator(dokument), dokument);
            }
            Set<String> skZnakyDokumentu = skZnaky.computeIfAbsent(rodicNode, n -> new HashSet<>() );
            
            Node nd = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Vyrazovani",
                                             "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
            if (nd == null) {
                return nastavChybu("Nenalezen dětský element <nsesss:SkartacniZnak> elementu <nsesss:Dokument>. "
                        + getJmenoIdentifikator(dokument), dokument);
            }
            String znak = nd.getTextContent();
            skZnakyDokumentu.add(znak);
        }
        
        for(Node spisDil: spisyDily) {
            Set<String> skZnakyDokumentu = skZnaky.get(spisDil);
            if(skZnakyDokumentu==null) {
                return nastavChybu("Nenalezeny žádné <nsesss:SkartacniZnak> dětských elementu <nsesss:Dokument> základní entity."
                        + getJmenoIdentifikator(spisDil), spisDil);                
            }
            if(!kontrola(spisDil, skZnakyDokumentu)) {
                return false;
            }
        }

        return true;
    }

    private boolean kontrola(Node zakladnientita, Set<String> skZnakyDokumentu) {
        Node n = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE, 
                                        "nsesss:Vyrazovani",
                                        "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
        if (n == null) {
            return nastavChybu("Nenalezen dětský element <nsesss:SkartacniZnak> základní entity. "
                    + getJmenoIdentifikator(zakladnientita), zakladnientita);
        }
        String skZnakME = n.getTextContent();

        switch (skZnakME) {
        case "A":
            if (!skZnakyDokumentu.contains("A")) {
                return nastavChybu("Spis se skartačním znakem A neobsahuje žádný dokument se skartačním znakem A. "
                        + getJmenoIdentifikator(zakladnientita), n);
            }
            break;
        case "V":
            if (skZnakyDokumentu.contains("A")) {
                return nastavChybu("Spis se skartačním znakem V obsahuje dokument se skartačním znakem A. "
                        + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(zakladnientita),
                        zakladnientita);
            }
            if (!skZnakyDokumentu.contains("V")) {
                return nastavChybu("Spis se skartačním znakem V neobsahuje žádný dokument se skartačním znakem V. "
                        + getJmenoIdentifikator(zakladnientita), n);
            }
            break;
        case "S":
            if (skZnakyDokumentu.contains("A")||skZnakyDokumentu.contains("V")) {
                return nastavChybu("Spis se skartačním znakem S obsahuje dokument se skartačním znakem A nebo V. "
                        + getJmenoIdentifikator(zakladnientita), zakladnientita);
            
            }
            break;
        }
        return true;
    }    
}
