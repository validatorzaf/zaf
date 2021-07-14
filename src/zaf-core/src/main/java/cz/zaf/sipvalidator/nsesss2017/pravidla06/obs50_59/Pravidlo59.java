package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.CompareNodes;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.structmap.PairZdrojIdent;

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
        Map<PairZdrojIdent, List<Node> > identMap = new HashMap<>();        
        List<Node> identList = metsParser.getNodes(JmenaElementu.IDENTIFIKATOR);
        for(Node ident: identList) {
            // get parent entity
            Node parentNode = getEntityWithIdentifikator(ident);
            // Kontrola typu rodice
            if(!shouldBeChecked(parentNode)) {
                continue;
            }
            
            String hodnotaIdentifikatoru = ident.getTextContent();
            String hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(ident, "zdroj");
            
            PairZdrojIdent pair = new PairZdrojIdent(hodnotaAtrZdroj, hodnotaIdentifikatoru);
            
            List<Node> nodeList = identMap.computeIfAbsent(pair, (p) -> new ArrayList<>());
            nodeList.add(parentNode);
        }
        
        // Porovnani uzlu se shodnymi ident
        for(Entry<PairZdrojIdent, List<Node>> entry: identMap.entrySet()) {
            List<Node> nodeList = entry.getValue();

            Iterator<Node> it = nodeList.iterator();
            Node firstNode = it.next();
            while(it.hasNext()) {
                Node node = it.next();
                String hlaska = CompareNodes.compare(firstNode, node);
                if (!hlaska.equals("OK")) {
                    return nastavChybu("Entity/objekty mají stejné hodnoty v elementu identifikátor a atributu zdroj, ale různý obsah. "
                            + hlaska + " " + getJmenoIdentifikator(firstNode) +
                            " " + getJmenoIdentifikator(node),
                                getMistoChyby(firstNode) + " " + getMistoChyby(node));
                }
            }
        }
        return true;
    }
    
    public static boolean shouldBeChecked(Node node){
        switch(node.getNodeName()){
            case "nsesss:SpisovyPlan":
                return true;
            case "nsesss:VecnaSkupina":
                return true;
            case "nsesss:TypovySpis":
                return true;
            case "nsesss:Soucast":
                return true;
            case "nsesss:Spis":
                return true;
            case "nsesss:Dil":
                return true;
            case "nsesss:Dokument":
                return true;
            case "nsesss:Komponenta":
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

    public static Node getEntityWithIdentifikator(Node identifikator){
        if(identifikator.getParentNode().getNodeName().equals("nsesss:Identifikace")){
            if(identifikator.getParentNode().getParentNode().getNodeName().equals("nsesss:EvidencniUdaje")){
                return identifikator.getParentNode().getParentNode().getParentNode();
            }
            else{
                return identifikator.getParentNode().getParentNode();
            }
        }
        else{
            return identifikator.getParentNode();
        }
    }
}
