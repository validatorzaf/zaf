package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.CompareNodes;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo59 extends K06PravidloBase {

    static final public String OBS59 = "obs59";

    public Pravidlo59(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo59.OBS59,
                "Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.",
                "Uveden je vícekrát stejný spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument, komponenta, bezpečnostní kategorie, skartační režim nebo typ dokumentu nebo je vícekrát použit stejný identifikátor.",
                "Příloha č. 2 NSESSS, ř. 123.");
    }

    //OBSAHOVÁ č.59 Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.
    @Override
    protected boolean kontrolaPravidla() {
        NodeList nlist = metsParser.getDocument().getElementsByTagName("nsesss:Identifikator");
        int[] set = IntStream.range(0, nlist.getLength()).toArray();
        ArrayList<Integer> k_list = new ArrayList<>();
        for (int i : set)
            k_list.add(i);

        while (!k_list.isEmpty()) {
            k_list = p59_specialMetod(nlist, k_list);
            if (k_list == null)
                return false;
        }

        return true;
    }

    private ArrayList<Integer> p59_specialMetod(NodeList nlist, ArrayList<Integer> k_list) {
        if (k_list.isEmpty())
            return k_list;
        if (k_list.size() == 1) {
            k_list.clear();
            return k_list;
        }
        String hodnotaIdentifikatoru = nlist.item(k_list.get(0)).getTextContent();
        String hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(nlist.item(k_list.get(0)), "zdroj");

        for (int j = 1; j < k_list.size(); j++) {
            Node node = nlist.item(k_list.get(j));
            String j_hodnotaIdentifikatoru = node.getTextContent();
            String j_hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(node, "zdroj");
            if (hodnotaIdentifikatoru.equals(j_hodnotaIdentifikatoru) && hodnotaAtrZdroj.equals(j_hodnotaAtrZdroj)) {
                //zkontroluj zda jsou stejné
                Node prvni = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(0)));
                Node dalsi = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(j)));
                // přepsat
                if (ValuesGetter.checkEntity_IdentifikatorCompare(prvni)) {
                    if (ValuesGetter.checkEntity_IdentifikatorCompare(dalsi)) {
                        String hlaska = CompareNodes.compare(prvni, dalsi);
                        if (!hlaska.equals("OK")) {
                            Node entita1 = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(0)));
                            Node entita2 = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(j)));

                            nastavChybu("Entity/objekty mají stejné hodnoty v elementu identifikátor a atributu zdroj, ale různý obsah. "
                                    + hlaska + " " + getJmenoIdentifikator(entita1) +
                                    " " + getJmenoIdentifikator(entita2),
                                        getMistoChyby(entita1) + " " + getMistoChyby(entita2));
                            return null;
                        }
                    }
                }
                //přidej ke kontrolovaným
                k_list.remove(j);
            }
        }
        k_list.remove(0);
        return k_list;
    }

}
