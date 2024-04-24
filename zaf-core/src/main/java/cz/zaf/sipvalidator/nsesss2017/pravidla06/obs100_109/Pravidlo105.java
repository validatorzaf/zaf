package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//
// "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských
// elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element
// <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element
// <nsesss:Komponenty>, mezi všemi dětskými elementy <nsesss:Komponenta>, v
// rámci kterých existuje takový element, který obsahuje atribut forma_uchovani
// s hodnotou originál a pro který jakýkoli element <mets:file>, který obsahuje
// atribut DMDID s hodnotou uvedenou v atributu ID elementu <nsesss:Komponenta>
// příslušné komponenty a dále atribut MIMETYPE s jednou z uvedených hodnot:
//
// application/msword
// application/vnd.openxmlformats-officedocument.wordprocessingml.document
// application/rtf
// application/vnd.oasis.opendocument.text
// application/vnd.apple.pages
//
// existuje element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani
// originál ve výstupním datovém formátu.
//
// Kontrola se neprovádí, pokud byla základní entita vyřízena/uzavřena do 31. 7. 2012 včetně.
//
//
public class Pravidlo105 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo105.class);

    static Set<String> checkedMimeTypes = new HashSet<>();
    static {
        checkedMimeTypes.add("application/msword");
        checkedMimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        checkedMimeTypes.add("application/rtf");
        checkedMimeTypes.add("application/vnd.oasis.opendocument.text");
        checkedMimeTypes.add("application/vnd.apple.pages");
    }

    public static final String OBS105 = "obs105";

    public Pravidlo105() {
        super(OBS105,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element <nsesss:Komponenty>, mezi všemi dětskými elementy <nsesss:Komponenta>, v rámci kterých existuje takový element, který obsahuje atribut forma_uchovani s hodnotou originál a pro který jakýkoli element <mets:file>, který obsahuje atribut DMDID s hodnotou uvedenou v atributu ID elementu <nsesss:Komponenta> příslušné komponenty a dále atribut MIMETYPE s jednou z uvedených hodnot:\r\n"
                        + "- application/msword\r\n"
                        + "- application/vnd.openxmlformats-officedocument.wordprocessingml.document\r\n"
                        + "- application/rtf\r\n"
                        + "- application/vnd.oasis.opendocument.text\r\n"
                        + "- application/vnd.apple.pages\r\n"
                        + "existuje element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani originál ve výstupním datovém formátu. Kontrola se neprovádí, pokud byla základní entita vyřízena/uzavřena do 31. 7. 2012 včetně.",
                "Uveden je chybně originál ve výstupním datovém formátu komponent (počítačových souborů).",
                "§ 23 odst. 2 vyhlášky č. 259/2012 Sb.; Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected void kontrola() {
        List<Element> zaklEntity = metsParser.getZakladniEntity();        
        // overeni datumu uzavreni/vyrizeni
        Set<Element> povoleneZaklEntity = zaklEntity.stream().filter(ze -> vratKontrolaVystupniFormat(ze))
                .collect(Collectors.toSet());
        
        Set<Element> digitDoks = new HashSet<>();

        List<Element> dokumentNodes = metsParser.getDokumenty();
        for (Element dokumentNode : dokumentNodes) {
            // overeni, zda je povolena zakl entita
            if (!ValuesGetter.jeSoucasti(dokumentNode, povoleneZaklEntity)) {
                continue;
            }
            
            Element analogDokNode = ValuesGetter.getXChild(dokumentNode, NsesssV3.EVIDENCNI_UDAJE,
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
        List<Element> komponenty = metsParser.getNodes(NsesssV3.KOMPONENTA);
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

            Map<Integer, List<Node>> komponentyDlePoradi = digitalniDokumenty.computeIfAbsent(dokumentNode,
                                                                                              dn -> new HashMap<>());
            List<Node> kompList = komponentyDlePoradi.computeIfAbsent(poradiKomponeta, pk -> new ArrayList<>());
            kompList.add(komponenta);
        }

        // TODO: Deduplicate code with rule 44
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        Map<String, Node> filesIdMap = new HashMap<>();
        for (Node metsFile : nodeListMetsFile) {
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            if (StringUtils.isEmpty(dmdid)) {
                nastavChybu("Element <mets:file> nemá atribut DMDID.", metsFile);
            }
            Node prevNode = filesIdMap.put(dmdid, metsFile);
            if (prevNode != null) {
                nastavChybu("Více elementů <mets:file> má shodnou hodnotu DMDID.", metsFile);
            }
        }

        // Kontrola dat
        for (Entry<Element, Map<Integer, List<Node>>> digitDok : digitalniDokumenty.entrySet()) {
            Element digiDokNode = digitDok.getKey();
            for (Entry<Integer, List<Node>> komponentyNaPozici : digitDok.getValue().entrySet()) {
                Integer pozice = komponentyNaPozici.getKey();
                List<Node> komps = komponentyNaPozici.getValue();
                kontrola(digiDokNode, pozice, komps, filesIdMap);
            }
        }
    }

    private void kontrola(Element digiDokNode, Integer pozice, List<Node> komps,
                             Map<String, Node> filesIdMap) {
        if (komps.size() == 0) {
            return;
        }
        // Kontrola existence ORIGINALU definovaneho typu
        boolean hasOriginalVeVystupnimFormatu = false;
        List<Node> origList = new ArrayList<>();
        for (Node komponenta : komps) {
            String formaUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);
            if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovani)) {
                hasOriginalVeVystupnimFormatu = true;
                continue;
            }
            if (!JmenaElementu.FORMA_UCHOVANI_ORIGINAL.equals(formaUchovani)) {
                continue;
            }
            // zjisteni ID 
            String id = ValuesGetter.getValueOfAttribut(komponenta, "ID");
            if (id == null) {
                continue;
            }
            Node fileNode = filesIdMap.get(id);
            if (fileNode == null) {
                continue;
            }
            String mimeType = ValuesGetter.getValueOfAttribut(fileNode, "MIMETYPE");
            if (checkedMimeTypes.contains(mimeType)) {
                origList.add(komponenta);
            }
        }
        if (origList.size() > 0) {
            if (!hasOriginalVeVystupnimFormatu) {
                nastavChybu(BaseCode.CHYBI_KOMPONENTA,
                            "Nenalezen originál ve výstupním formátu, existují originály, které ho vyžadují. Počet. "
                                    + origList.size(), origList,
                            K06_Obsahova.getEntityId(digiDokNode));
            }
        }
    }

}