package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.CompareNodes;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.PairZdrojIdent;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo59 extends K06PravidloBase {

    static final public String OBS59 = "obs59";

    public Pravidlo59() {
        super(OBS59,
                "Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.",
                "Uveden je vícekrát stejný spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument, komponenta, bezpečnostní kategorie, skartační režim nebo typ dokumentu nebo je vícekrát použit stejný identifikátor.",
                "Příloha č. 2 NSESSS, ř. 123.");
    }

    //OBSAHOVÁ č.59 Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.
    @Override
    protected boolean kontrolaPravidla() {
        Map<PairZdrojIdent, List<Node>> identMap = new HashMap<>();
        List<Node> identList = metsParser.getNodes(NsessV3.IDENTIFIKATOR);
        for (Node ident : identList) {
            // get parent entity
            Node parentNode = getEntityWithIdentifikator(ident);
            // Kontrola typu rodice
            if (!shouldBeChecked(parentNode)) {
                continue;
            }

            String hodnotaIdentifikatoru = ident.getTextContent();
            String hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(ident, "zdroj");

            PairZdrojIdent pair = new PairZdrojIdent(hodnotaAtrZdroj, hodnotaIdentifikatoru);

            List<Node> nodeList = identMap.computeIfAbsent(pair, (p) -> new ArrayList<>());
            nodeList.add(parentNode);
        }

        // Porovnani uzlu se shodnymi ident
        for (Entry<PairZdrojIdent, List<Node>> entry : identMap.entrySet()) {
            List<Node> nodeList = entry.getValue();

            Iterator<Node> it = nodeList.iterator();
            Node firstNode = it.next();
            while (it.hasNext()) {
                Node node = it.next();
                String hlaska = CompareNodes.compare(firstNode, node);
                if (hlaska!=null) {
                    return nastavChybu("Entity/objekty mají stejné hodnoty v elementu identifikátor a atributu zdroj, ale různý obsah. "
                            + hlaska + " " + getJmenoIdentifikator(firstNode) +
                            " " + getJmenoIdentifikator(node),
                                       getMistoChyby(firstNode) + " " + getMistoChyby(node));
                }
            }
        }
        return true;
    }

    public static boolean shouldBeChecked(Node node) {
        switch (node.getNodeName()) {
        case NsessV3.SPISOVY_PLAN:
            return true;
        case NsessV3.VECNA_SKUPINA:
            return true;
        case NsessV3.TYPOVY_SPIS:
            return true;
        case NsessV3.SOUCAST:
            return true;
        case NsessV3.SPIS:
            return true;
        case NsessV3.DIL:
            return true;
        case NsessV3.DOKUMENT:
            return true;
        case NsessV3.KOMPONENTA:
            return true;
        case "nsesss:BezpecnostniKategorie":
            return true;
        case "nsesss:SkartacniRezim":
            return true;
        case "nsesss:TypDokumentu":
            return true;
        }
        return false;
    }

    public static Node getEntityWithIdentifikator(Node identifikator) {
        Node parentNode = identifikator.getParentNode();
        String parentName = parentNode.getNodeName();
        if (parentName.equals("nsesss:Identifikace")) {
            parentNode = parentNode.getParentNode();
            parentName = parentNode.getNodeName();
            if (parentName.equals(NsessV3.EVIDENCNI_UDAJE)) {
                return parentNode.getParentNode();
            } else {
                return parentNode;
            }
        } else {
            return parentNode;
        }
    }
}
