package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// Obsahova 100
//
// Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy
// <nsesss:Komponenta>
// obsahují v atributu poradi hodnotu, která společně tvoří vzestupnou a
// souvislou řadu přirozených
// čísel počínaje 1, přičemž čísla se mohou opakovat a úvodní nuly se ignorují.
//
public class Pravidlo100 extends K06PravidloBaseOld {

    static Logger log = LoggerFactory.getLogger(Pravidlo100.class);

    public static final String OBS100 = "obs100";

    public Pravidlo100() {
        super(OBS100,
                "Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy <nsesss:Komponenta> obsahují v atributu poradi hodnotu, která společně tvoří vzestupnou a souvislou řadu přirozených čísel počínaje 1, přičemž čísla se mohou opakovat a úvodní nuly se ignorují.",
                "Uvedeno je chybně pořadí komponent (počítačových souborů).",
                "Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        // získání všech komponent ve výstupním datovém formátu
        List<Element> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }
        // sort by parent
        Node parentNode = null;
        TreeMap<Integer, List<Node> > treeMap = new TreeMap<>();
        for (Node komponenta : komponenty) {
            Node parent = komponenta.getParentNode();
            if (parent != parentNode) {
                if(!checkValues(treeMap)) {
                    return false;
                }
                // reset parent
                treeMap = new TreeMap<>();
                parentNode = parent;
            }
            Integer poradi = ValuesGetter.getAttribute(komponenta, "poradi");
            List<Node> l = treeMap.computeIfAbsent(poradi, p -> {
                return new ArrayList<>();
            });
            l.add(komponenta);
        }
        if(!checkValues(treeMap)) {
            return false;
        }

        return true;
    }

    private boolean checkValues(TreeMap<Integer, List<Node>> treeMap) {
        int pos = 1;
        for(Entry<Integer, List<Node>> e: treeMap.entrySet()) {
            int key = e.getKey();
            if(pos!=key) {
                return nastavChybu("Komponenta má chybné pořadí, poslední zjištěné pořadí je: " +
                        pos +
                        ", pořadí následující komponenty má však hodnotu: " +
                        key, e.getValue().get(0));
            }
            pos++;
        }
        return true;
    }

}
