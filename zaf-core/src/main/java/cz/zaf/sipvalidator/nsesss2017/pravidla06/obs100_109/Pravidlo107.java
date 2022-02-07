package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs100_109;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// "Pokud existuje jakýkoli element <nsesss:Komponenta>, který obsahuje atribut
// forma_uchovani s hodnotou originál ve výstupním datovém formátu a současně
// atribut verze s hodnotou nejvyššího čísla verze, potom jakýkoli element
// <mets:file>, který obsahuje atribut DMDID s hodnotou uvedenou v atributu ID
// jakéhokoli elementu <nsesss:Komponenta> příslušné komponenty, obsahuje
// atribut MIMETYPE s jednou z uvedených hodnot:
//
// application/pdf
// image/png
// image/tiff
// image/jpeg
// video/mpeg
// image/gif
// audio/mpeg
// audio/x-wav
// application/xml
// application/xml-dtd
//
//
public class Pravidlo107 extends K06PravidloBaseOld {

    static Logger log = LoggerFactory.getLogger(Pravidlo107.class);

    public static final String OBS107 = "obs107";

    static Set<String> checkedMimeTypes = new HashSet<>();
    static {
        checkedMimeTypes.add("application/pdf");
        checkedMimeTypes.add("image/png");
        checkedMimeTypes.add("image/tiff");
        checkedMimeTypes.add("image/jpeg");
        checkedMimeTypes.add("video/mpeg");
        checkedMimeTypes.add("image/gif");
        checkedMimeTypes.add("audio/mpeg");
        checkedMimeTypes.add("audio/x-wav");
        checkedMimeTypes.add("application/xml");
        checkedMimeTypes.add("application/xml-dtd");
    }

    public Pravidlo107() {
        super(OBS107,
                "Pokud existuje jakýkoli element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou originál ve výstupním datovém formátu a současně atribut verze s hodnotou nejvyššího čísla verze, potom jakýkoli element <mets:file>, který obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta> příslušné komponenty, obsahuje atribut MIMETYPE s jednou z uvedených hodnot:\r\n"
                        + "- application/pdf\r\n"
                        + "- image/png\r\n"
                        + "- image/tiff\r\n"
                        + "- image/jpeg\r\n"
                        + "- video/mpeg\r\n"
                        + "- image/gif\r\n"
                        + "- audio/mpeg\r\n"
                        + "- audio/x-wav\r\n"
                        + "- application/xml\r\n"
                        + "- application/xml-dtd",
                "Komponenta (počítačový soubory) není ve výstupním datovém formátu.",
                "§ 23 odst. 2 vyhlášky č. 259/2012 Sb.; Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        // TODO: Deduplicate code with rule 44
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        if (CollectionUtils.isEmpty(nodeListMetsFile)) {
            return true;
        }
        Map<String, Node> filesIdMap = new HashMap<>();
        for (Element metsFile : nodeListMetsFile) {
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            if (StringUtils.isEmpty(dmdid)) {
                return nastavChybu("Element <mets:file> nemá atribut DMDID.", metsFile);
            }
            Node prevNode = filesIdMap.put(dmdid, metsFile);
            if (prevNode != null) {
                return nastavChybu("Více elementů <mets:file> má shodnou hodnotu DMDID.", metsFile);
            }
        }

        // Zjisteni seznamu komponent ciste digitalnich dokumentu
        List<Element> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return true;
        }

        // Ulozeni nejvyssich verzi
        // Struktura obsahuje: Dokument, Pozice, Komponenta s nejvyssi verzi
        Map<Node, Map<Integer, Node>> doks = new HashMap<>();
        for (Node komponenta : komponenty) {
            // Kontrola, zda je soucast digit dokumentu?
            Node komponentyNode = komponenta.getParentNode();
            Node dokumentNode = komponentyNode.getParentNode();

            Map<Integer, Node> komponentaDlePoradi = doks.computeIfAbsent(dokumentNode,
                                                                          dn -> new HashMap<>());

            String poradiKomponentyStr = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.PORADI);
            Integer poradiKomponeta = Integer.valueOf(poradiKomponentyStr);

            Node nejvyssiVerzeNode = komponentaDlePoradi.get(poradiKomponeta);
            if (nejvyssiVerzeNode != null) {
                // zjisteni aktualni nejvyssi verze
                Integer nejvyssiVerze = ValuesGetter.getAttribute(nejvyssiVerzeNode, JmenaElementu.VERZE);
                // novy kandidat
                Integer jinaVerze = ValuesGetter.getAttribute(komponenta, JmenaElementu.VERZE);
                if (jinaVerze > nejvyssiVerze) {
                    komponentaDlePoradi.put(poradiKomponeta, komponenta);
                }
            } else {
                komponentaDlePoradi.put(poradiKomponeta, komponenta);
            }
        }
        // Kontrola dat
        for (Entry<Node, Map<Integer, Node>> digitDok : doks.entrySet()) {
            Node digiDokNode = digitDok.getKey();
            for (Entry<Integer, Node> komponentyNaPozici : digitDok.getValue().entrySet()) {
                Integer pozice = komponentyNaPozici.getKey();
                Node komp = komponentyNaPozici.getValue();
                if (!kontrola(digiDokNode, pozice, komp, filesIdMap)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean kontrola(Node digiDokNode, Integer pozice, Node komponenta, Map<String, Node> filesIdMap) {
        // Overeni formy
        String formUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);
        if (!JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formUchovani)) {
            return true;
        }

        // zjisteni ID
        String id = ValuesGetter.getValueOfAttribut(komponenta, "ID");
        if (id == null) {
            return true;
        }
        Node fileNode = filesIdMap.get(id);
        if (fileNode == null) {
            return true;
        }
        String mimeType = ValuesGetter.getValueOfAttribut(fileNode, "MIMETYPE");
        if (!checkedMimeTypes.contains(mimeType)) {
            return nastavChybu("Originál ve výstupním formátu pro nejvyšší verzi komponenty nemá přípustný mimetype. Zjištěn typ: "
                    + mimeType, fileNode);
        }

        return true;
    }

}