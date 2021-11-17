package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// Obsahova 101
//
// Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy
// <nsesss:Komponenta> se stejnou hodnotou atributu poradi obsahují stejnou
// hodnotu atributu druh.
//
public class Pravidlo101 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo101.class);

    public static final String OBS101 = "obs101";

    public Pravidlo101() {
        super(OBS101,
                "Pokud existuje jakýkoli element <nsesss:Komponenty>, všechny dětské elementy <nsesss:Komponenta> se stejnou hodnotou atributu poradi obsahují stejnou hodnotu atributu druh.",
                "Uveden je chybně druh komponent (počítačových souborů).",
                "Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        // získání všech komponent ve výstupním datovém formátu
        List<Node> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }

        // sort by parent
        Node parentNode = null;
        TreeMap<Integer, List<Node>> treeMap = new TreeMap<>();
        for (Node komponenta : komponenty) {
            Node parent = komponenta.getParentNode();
            if (parent != parentNode) {
                if (!checkValues(treeMap)) {
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
        if (!checkValues(treeMap)) {
            return false;
        }

        return true;
    }

    private boolean checkValues(TreeMap<Integer, List<Node>> treeMap) {
        for (Entry<Integer, List<Node>> entry : treeMap.entrySet()) {
            Integer position = entry.getKey();
            // check nodes
            List<Node> nodes = entry.getValue();
            Iterator<Node> it = nodes.iterator();
            Node firstNode = it.next();
            String druh = ValuesGetter.getValueOfAttribut(firstNode, "druh");
            while (it.hasNext()) {
                Node nextNode = it.next();
                String otherDruh = ValuesGetter.getValueOfAttribut(nextNode, "druh");
                if (!Objects.equals(druh, otherDruh)) {
                    return nastavChybu("Komponenta má druh odlišný od jiné komponenty na stejné pozici, pozice: "
                            + position + ", očekávaná hodnota: " + druh
                            + ", zjištěná hodnota: " + otherDruh,
                                       nextNode);
                }
            }
        }
        return true;
    }

}
