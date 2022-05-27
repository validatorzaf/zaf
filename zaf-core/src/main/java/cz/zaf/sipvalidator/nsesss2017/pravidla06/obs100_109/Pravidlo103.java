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
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských
// elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element
// <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element
// <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta> se
// stejnou hodnotou atributu poradi existuje právě jeden element
// <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou
// originál.
//
public class Pravidlo103 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo103.class);

    public static final String OBS103 = "obs103";

    public Pravidlo103() {
        super(OBS103,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta> se stejnou hodnotou atributu poradi existuje právě jeden element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou originál.",
                "Uveden je chybně originál komponent (počítačových souborů).",
                "Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected void kontrola() {
        Set<Node> digitDoks = new HashSet<>();

        List<Element> dokumentNodes = metsParser.getDokumenty();
        for (Element dokumentNode : dokumentNodes) {
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
        List<Element> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return;
        }

        //
        // Mapa <Dokument, <Poradi, List<Komponenta> > >
        //
        Map<Element, Map<Integer, List<Node>>> digitalniDokumenty = new HashMap<>();
        for (Node komponenta : komponenty) {
            // Kontrola, zda je soucast digit dokumentu?
            Node komponentyNode = komponenta.getParentNode();
            Element dokumentNode = (Element) komponentyNode.getParentNode();
            if (!digitDoks.contains(dokumentNode)) {
                continue;
            }

            String poradiKomponentyStr = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.PORADI);
            Integer poradiKomponeta = Integer.valueOf(poradiKomponentyStr);
            
            Map<Integer, List<Node>> komponentyDlePoradi = digitalniDokumenty.computeIfAbsent(dokumentNode, dn -> new HashMap<>() );
            List<Node> kompList = komponentyDlePoradi.computeIfAbsent(poradiKomponeta, pk -> new ArrayList<>());
            kompList.add(komponenta);
        }
        
        // Kontrola dat
        for (Entry<Element, Map<Integer, List<Node>>> digitDok : digitalniDokumenty.entrySet()) {
            for (Entry<Integer, List<Node>> komponentyNaPozici : digitDok.getValue().entrySet()) {
                // Nalezeni originalu
                Node original = null;
                for (Node komponenta : komponentyNaPozici.getValue()) {
                    String formaUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);
                    if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL.equals(formaUchovani)) {
                        if (original != null) {
                            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                                        "Dokument má více komponent se shodným pořadím označených jako originál. poradi: "
                                                + komponentyNaPozici.getKey(), digitDok.getKey(),
                                        kontrola.getEntityId(digitDok.getKey()));
                        }
                        original = komponenta;
                    }
                }
                if (original == null) {
                    nastavChybu(BaseCode.CHYBA,
                                "Dokument neobsahuje komponentu označenou jako originál. poradi: "
                            + komponentyNaPozici.getKey(), digitDok.getKey(),
                            kontrola.getEntityId(digitDok.getKey()));
                }
            }
        }
    }

}