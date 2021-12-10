package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských
// elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element
// <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element
// <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta> se
// stejnou hodnotou atributu poradi, mezi kterými neexistuje žádný element
// <nsesss:Komponenta> s hodnotou atributu forma_uchovani originál ve výstupním
// datovém formátu, existuje právě jeden element <nsesss:Komponenta>, který
// obsahuje atribut forma_uchovani s hodnotou originál a současně atribut verze
// s hodnotou nejvyššího čísla verze.
//
public class Pravidlo104 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo104.class);

    public static final String OBS104 = "obs104";

    public Pravidlo104() {
        super(OBS104,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta> se stejnou hodnotou atributu poradi, mezi kterými neexistuje žádný element <nsesss:Komponenta> s hodnotou atributu forma_uchovani originál ve výstupním datovém formátu, existuje právě jeden element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou originál a současně atribut verze s hodnotou nejvyššího čísla verze.",
                "Uvedena je chybně verze komponent (počítačových souborů).",
                "Uvedena je chybně verze originálu komponent (počítačových souborů).");
    }

    @Override
    protected boolean kontrolaPravidla() {
        Set<Node> digitDoks = new HashSet<>();

        List<Node> dokumentNodes = metsParser.getDokumenty();
        for (Node dokumentNode : dokumentNodes) {
            Node analogDokNode = ValuesGetter.getXChild(dokumentNode, NsessV3.EVIDENCNI_UDAJE,
                                                        "nsesss:Manipulace",
                                                        "nsesss:AnalogovyDokument");
            if (analogDokNode != null) {
                String value = analogDokNode.getTextContent();
                if ("ne".equals(value)) {
                    digitDoks.add(dokumentNode);
                }
            }
        }

        // Zjisteni seznamu komponent ciste digitalnich dokumentu
        List<Node> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }

        //
        // Mapa <Dokument, <Poradi, List<Komponenta> > >
        //
        Map<Node, Map<Integer, List<Node>>> digitalniDokumenty = new HashMap<>();
        for (Node komponenta : komponenty) {
            // Kontrola, zda je soucast digit dokumentu?
            Node komponentyNode = komponenta.getParentNode();
            Node dokumentNode = komponentyNode.getParentNode();
            if (!digitDoks.contains(dokumentNode)) {
                continue;
            }

            String poradiKomponentyStr = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.PORADI);
            Integer poradiKomponeta = Integer.valueOf(poradiKomponentyStr);

            Map<Integer, List<Node>> komponentyDlePoradi = digitalniDokumenty.computeIfAbsent(dokumentNode,
                                                                                              dn -> new HashMap<>());
            List<Node> kompList = komponentyDlePoradi.computeIfAbsent(poradiKomponeta, pk -> new ArrayList<>());
            kompList.add(komponenta);
        }
        // Kontrola dat
        for (Entry<Node, Map<Integer, List<Node>>> digitDok : digitalniDokumenty.entrySet()) {
            Node digiDokNode = digitDok.getKey();
            for (Entry<Integer, List<Node>> komponentyNaPozici : digitDok.getValue().entrySet()) {
                Integer pozice = komponentyNaPozici.getKey();
                List<Node> komps = komponentyNaPozici.getValue();
                if (!kontrola(digiDokNode, pozice, komps))
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean kontrola(Node digiDokNode, Integer pozice, List<Node> komps) {
        if (komps.size() == 0) {
            return true;
        }
        Integer verzeOriginal = null;
        Integer nejvyssiVerze = null;
        //
        for (Node komp : komps) {
            // ulozeni nejvyssi verze
            Integer verze = ValuesGetter.getAttribute(komp, JmenaElementu.VERZE);
            if(nejvyssiVerze==null || nejvyssiVerze<verze) {
                nejvyssiVerze = verze;
            }

            String formaUchovani = ValuesGetter.getValueOfAttribut(komp, JmenaElementu.FORMA_UCHOVANI);
            // pokud existuje komponenta ve vystupnim dat formatu, je vse ok 
            if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovani)) {
                return true;
            }
            // zde nas zajimaji jen komponenty ve formatu ORIGINAL
            if (!JmenaElementu.FORMA_UCHOVANI_ORIGINAL.equals(formaUchovani)) {
                continue;
            }

            if (verzeOriginal != null) {
                return nastavChybu("Existuje více komponent s formou uchování originál, pozice: " + pozice
                        + ", kolidující verze: " + verze, komp);
            }

            verzeOriginal = verze;
        }

        // Kontrola, zda originál byl nalezen
        if (verzeOriginal == null) {
            return nastavChybu("Nebyla nalezena komponenta s formou uchování originál, pozice: " + pozice,
                               digiDokNode);
        }

        // Kontrola, zda je nejvyssi verze
        if (verzeOriginal < nejvyssiVerze) {
            return nastavChybu("Komponenta s formou uchování originál nemá nejvyšší číslo verze, pozice: " + pozice
                    + ", nejvyšší číslo verze: " + nejvyssiVerze
                    + ", číslo verze originálu: " + verzeOriginal,
                               digiDokNode);
        }
        return true;
    }
}