package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// Pokud existuje jakýkoli element <nsesss:Komponenty>, ze všech dětských
// elementů <nsesss:Komponenta> se stejnou hodnotou atributu poradi, mezi
// kterými existuje jakýkoli element <nsesss:Komponenta> s hodnotou atributu
// forma_uchovani originál ve výstupním datovém formátu, existuje právě jeden
// element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou
// originál ve výstupním datovém formátu a současně atribut verze s hodnotou
// nejvyššího čísla verze.
//
public class Pravidlo106 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo106.class);

    public static final String OBS106 = "obs106";

    public Pravidlo106() {
        super(OBS106,
                "Pokud existuje jakýkoli element <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta> se stejnou hodnotou atributu poradi, mezi kterými existuje jakýkoli element <nsesss:Komponenta> s hodnotou atributu forma_uchovani originál ve výstupním datovém formátu, existuje právě jeden element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou originál ve výstupním datovém formátu a současně atribut verze s hodnotou nejvyššího čísla verze.",
                "Uvedena je chybně verze originálu ve výstupním datovém formátu komponent (počítačových souborů).",
                "Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        // Zjisteni seznamu komponent ciste digitalnich dokumentu
        List<Node> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }
        Map<Node, Map<Integer, List<Node>>> doks = new HashMap<>();
        for (Node komponenta : komponenty) {
            // Kontrola, zda je soucast digit dokumentu?
            Node komponentyNode = komponenta.getParentNode();
            Node dokumentNode = komponentyNode.getParentNode();

            String poradiKomponentyStr = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.PORADI);
            Integer poradiKomponeta = Integer.valueOf(poradiKomponentyStr);

            Map<Integer, List<Node>> komponentyDlePoradi = doks.computeIfAbsent(dokumentNode,
                                                                                dn -> new HashMap<>());
            List<Node> kompList = komponentyDlePoradi.computeIfAbsent(poradiKomponeta, pk -> new ArrayList<>());
            kompList.add(komponenta);
        }
        // Kontrola dat
        for (Entry<Node, Map<Integer, List<Node>>> digitDok : doks.entrySet()) {
            Node digiDokNode = digitDok.getKey();
            for (Entry<Integer, List<Node>> komponentyNaPozici : digitDok.getValue().entrySet()) {
                Integer pozice = komponentyNaPozici.getKey();
                List<Node> komps = komponentyNaPozici.getValue();
                if (!kontrola(digiDokNode, pozice, komps)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean kontrola(Node dokNode, Integer pozice, List<Node> komps) {

        Node kompVeVystFormatu = null;
        Integer nejvyssiVerze = null;

        for (Node komp : komps) {
            String formUchovani = ValuesGetter.getValueOfAttribut(komp, JmenaElementu.FORMA_UCHOVANI);
            Integer verze = ValuesGetter.getAttribute(komp, JmenaElementu.VERZE);
            if (nejvyssiVerze == null || verze > nejvyssiVerze) {
                nejvyssiVerze = verze;
                if (kompVeVystFormatu != null) {
                    return nastavChybu("Existuje vyšší verze nežli je komponenta ve výstupním formátu, pozice: "
                            + pozice
                            + ", kolidující verze: " + verze, komp);
                }
                if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formUchovani)) {
                    kompVeVystFormatu = komp;
                }
            } else {
                // nejedna se o nejvyssi verze -> nesmi byt vystupni format
                if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formUchovani)) {
                    return nastavChybu("Existuje vyšší verze nežli je komponenta ve výstupním formátu, pozice: "
                            + pozice
                            + ", kolidující verze: " + verze, komp);
                }
            }
        }
        return true;
    }

}