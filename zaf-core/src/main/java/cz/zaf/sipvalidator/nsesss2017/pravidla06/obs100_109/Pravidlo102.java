package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//
// Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy
// <nsesss:Komponenta> se stejnou hodnotou atributu poradi obsahují v atributu
// verze hodnotu, která společně tvoří vzestupnou řadu přirozených čísel
// počínaje 1, přičemž čísla se neopakují a úvodní nuly se ignorují.
//
public class Pravidlo102 extends K06PravidloBase {
    
    static Logger log = LoggerFactory.getLogger(Pravidlo102.class);
    
    public static final String OBS102 = "obs102";
    
    public Pravidlo102() {
        super(OBS102,
                "Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy <nsesss:Komponenta> se stejnou hodnotou atributu poradi obsahují v atributu verze hodnotu, která společně tvoří vzestupnou řadu přirozených čísel počínaje 1, přičemž čísla se neopakují a úvodní nuly se ignorují.",
                "Uvedena je chybně verze komponent (počítačových souborů).",
                "Informační list NA, čá. 6/2020, č. 3/2020.");
    }
    
    @Override
    protected void kontrola() {
        // získání všech komponent ve výstupním datovém formátu
        List<Element> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return;
        }

        // sort by parent
        Node parentNode = null;
        // Mapa zaznamu
        // Obsahuje: <Pozice, <Verze, Node> >
        TreeMap<Integer, Map<Integer, Element>> treeMap = new TreeMap<>();
        for (Element komponenta : komponenty) {
            Element parent = (Element) komponenta.getParentNode();
            if (parent != parentNode) {
                checkValues(treeMap);
                // reset parent
                treeMap = new TreeMap<>();
                parentNode = parent;
            }
            Integer poradi = ValuesGetter.getAttribute(komponenta, "poradi");
            Map<Integer, Element> mapaVerzi = treeMap.computeIfAbsent(poradi, p -> {
                return new TreeMap<>();
            });
            Integer verze = ValuesGetter.getAttribute(komponenta, "verze");
            Element prevKomponenta = mapaVerzi.put(verze, komponenta);
            if (prevKomponenta != null) {
                List<Element> list = new ArrayList<>();
                list.add(komponenta);
                list.add(prevKomponenta);
                nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Existuje více komponent se shodným číslem verze, poradi: " + poradi + ", verze: " + verze,
                            list, K06_Obsahova.getEntityId(list));
            }
        }
        checkValues(treeMap);
        
    }
    
    private void checkValues(TreeMap<Integer, Map<Integer, Element>> treeMap) {
        for (Entry<Integer, Map<Integer, Element>> entry : treeMap.entrySet()) {
            Integer poradi = entry.getKey();
            Map<Integer, Element> mapaVerzi = entry.getValue();
            // kontrola seznamu verzi
            int expectedVerze = 1;
            for (Entry<Integer, Element> zaznamVerze : mapaVerzi.entrySet()) {
                Integer verze = zaznamVerze.getKey();
                if (expectedVerze != verze.intValue()) {
                    Element elKomponenta = zaznamVerze.getValue();
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Nalezeno neočekávané číslo verze. Pořadí komponenty: " + poradi
                            + ", verze: " + verze + ", očekávané číslo verze: " + expectedVerze,
                                elKomponenta, K06_Obsahova.getEntityId(elKomponenta));
                }
                expectedVerze++;
            }
        }
    }
    
}
