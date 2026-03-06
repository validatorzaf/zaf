package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.result.IndetifierWithSource;
import cz.zaf.sipvalidator.nsesss2024.CompareNodes;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;

public class Pravidlo59 extends K06PravidloBase {

    static final public String OBS59 = "obs59";

    public Pravidlo59() {
        super(OBS59,
                "Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:DruhDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.",
                "Uveden je vícekrát stejný spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument, komponenta, bezpečnostní kategorie, skartační režim nebo typ dokumentu nebo je vícekrát použit stejný identifikátor.",
                "Příloha č. 2 NSESSS, nsesss-common.xsd, ř. 127.");
    }

    //OBSAHOVÁ č.59 Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:DruhDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.
    @Override
    protected void kontrola() {
        Map<IndetifierWithSource, List<Element>> identMap = new HashMap<>();
        List<Element> identList = metsParser.getNodes(NsesssV4.IDENTIFIKATOR);
        for (Element ident : identList) {
            // get parent entity
            Element parentNode = getEntityWithIdentifikator(ident);
            // Kontrola typu rodice
            if (!shouldBeChecked(parentNode)) {
                continue;
            }

            String hodnotaIdentifikatoru = ident.getTextContent();
            String hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(ident, "zdroj");

            IndetifierWithSource pair = new IndetifierWithSource(hodnotaAtrZdroj, hodnotaIdentifikatoru);

            List<Element> nodeList = identMap.computeIfAbsent(pair, (p) -> new ArrayList<>());
            nodeList.add(parentNode);
        }

        // Porovnani uzlu se shodnymi ident
        for (Entry<IndetifierWithSource, List<Element>> entry : identMap.entrySet()) {
            List<Element> nodeList = entry.getValue();

            Iterator<Element> it = nodeList.iterator();
            Element firstNode = it.next();
            while (it.hasNext()) {
                Element node = it.next();
                String hlaska = CompareNodes.compare(firstNode, node);
                if (hlaska != null) {
                    nastavChybu(BaseCode.CHYBA, "Entity/objekty mají stejné hodnoty v elementu identifikátor a atributu zdroj, ale různý obsah. "
                            + hlaska + " " + getJmenoIdentifikator(firstNode)
                            + " " + getJmenoIdentifikator(node),
                            getMistoChyby(firstNode) + " " + getMistoChyby(node));
                }
            }
        }
    }

    public static boolean shouldBeChecked(Node node) {
        switch (node.getNodeName()) {
            case NsesssV4.SPISOVY_PLAN:
                return true;
            case NsesssV4.VECNA_SKUPINA:
                return true;
            case NsesssV4.TYPOVY_SPIS:
                return true;
            case NsesssV4.SOUCAST:
                return true;
            case NsesssV4.SPIS:
                return true;
            case NsesssV4.DIL:
                return true;
            case NsesssV4.DOKUMENT:
                return true;
            case NsesssV4.KOMPONENTA:
                return true;
            case NsesssV4.BEZPECNOSTNI_KATEGORIE:
                return true;
            case NsesssV4.SKARTACNI_REZIM:
                return true;
            case NsesssV4.DRUH_DOKUMENTU:
                return true;
        }
        return false;
    }

    public static Element getEntityWithIdentifikator(Element identifikator) {
        Element parentNode = (Element) identifikator.getParentNode();
        String parentName = parentNode.getNodeName();
        if (parentName.equals(NsesssV4.IDENTIFIKACE)) {
            parentNode = (Element) parentNode.getParentNode();
            parentName = parentNode.getNodeName();
            if (parentName.equals(NsesssV4.EVIDENCNI_UDAJE)) {
                return (Element) parentNode.getParentNode();
            } else {
                return parentNode;
            }
        } else {
            return parentNode;
        }
    }
}
