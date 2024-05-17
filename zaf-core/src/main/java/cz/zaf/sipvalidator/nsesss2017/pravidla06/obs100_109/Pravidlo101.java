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
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

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
    protected void kontrola() {
        // získání všech komponent ve výstupním datovém formátu
        List<Element> komponenty = metsParser.getNodes(NsesssV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return;
        }

        // sort by parent
        Node parentNode = null;
        TreeMap<Integer, List<Element>> treeMap = new TreeMap<>();
        for (Element komponenta : komponenty) {
            Node parent = komponenta.getParentNode();
            if (parent != parentNode) {
                checkValues(treeMap);
                // reset parent
                treeMap = new TreeMap<>();
                parentNode = parent;
            }
            Integer poradi = ValuesGetter.getAttribute(komponenta, "poradi");
            List<Element> l = treeMap.computeIfAbsent(poradi, p -> {
                return new ArrayList<>();
            });
            l.add(komponenta);
        }
        checkValues(treeMap);

    }

    private void checkValues(TreeMap<Integer, List<Element>> treeMap) {
        for (Entry<Integer, List<Element>> entry : treeMap.entrySet()) {
            Integer position = entry.getKey();
            // check nodes
            List<Element> nodes = entry.getValue();
            Iterator<Element> it = nodes.iterator();
            Element firstNode = it.next();
            String druh = ValuesGetter.getValueOfAttribut(firstNode, "druh");
            while (it.hasNext()) {
                Element nextNode = it.next();
                String otherDruh = ValuesGetter.getValueOfAttribut(nextNode, "druh");
                if (!Objects.equals(druh, otherDruh)) {
                    List<Element> list = new ArrayList<>();
                    list.add(firstNode);
                    list.add(nextNode);
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Komponenta má druh odlišný od jiné komponenty na stejné pozici, pozice: " + position + ", "
                            + "očekávaná hodnota: " + druh + ", zjištěná hodnota: " + otherDruh,
                                list, K06_Obsahova.getEntityId(list));
                }
                firstNode = nextNode;
            }
        }
    }

}
