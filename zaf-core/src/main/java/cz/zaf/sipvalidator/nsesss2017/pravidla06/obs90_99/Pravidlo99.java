package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.pdfa.ValidationResult;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

//
// Obsahova 99
//
// Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských
// elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element
// <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element
// <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta>, který
// obsahuje atribut forma_uchovani s hodnotou originál nebo originál ve
// výstupním datovém formátu a současně atribut verze s hodnotou nejvyššího
// čísla verze, potom jakýkoli element <mets:file>, který obsahuje atribut DMDID
// s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>
// příslušné komponenty a dále obsahuje atribut MIMETYPE s hodnotou
// application/pdf, reprezentuje příslušnou komponentu ve shodě s normou PDF/A.
//
//
// Původní znění:
// Pokud existuje jakýkoli element <nsesss:Komponenta>, který obsahuje atribut
// forma_uchovani s hodnotou originál ve výstupním datovém formátu, a současně
// jakýkoli element <mets:file>, který obsahuje atribut DMDID s hodnotou
// uvedenou v atributu ID příslušného elementu <nsesss:Komponenta> a současně
// atribut MIMETYPE s hodnotou application/pdf, potom souborový formát příslušné
// komponenty odpovídá normě PDF/A alespoň ve verzi 1b.
//
public class Pravidlo99 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo99.class);

    public static final String OBS99 = "obs99";

    VeraValidatorProxy veraValidatorProxy;

    public Pravidlo99() {
        super(OBS99,
                "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne a zároveň obsahuje element <nsesss:Komponenty>, ze všech dětských elementů <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou originál nebo originál ve výstupním datovém formátu a současně atribut verze s hodnotou nejvyššího čísla verze, potom jakýkoli element <mets:file>, který obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta> příslušné komponenty a dále obsahuje atribut MIMETYPE s hodnotou application/pdf, reprezentuje příslušnou komponentu ve shodě s normou PDF/A.",
                "Komponenta (počítačový soubor) v datovém formátu PDF není ve výstupním datovém formátu.",
                "§ 23 odst. 2 vyhlášky č. 259/2012 Sb.; Informační list NA, čá. 6/2020, č. 3/2020.");
    }

    @Override
    protected void kontrola() {

        Set<Node> digitDoks = new HashSet<>();

        List<Element> dokumentNodes = metsParser.getDokumenty();
        for (Element dokumentNode : dokumentNodes) {
            Element analogDokNode = ValuesGetter.getXChild(dokumentNode, NsessV3.EVIDENCNI_UDAJE,
                    NsessV3.MANIPULACE,
                    NsessV3.ANALOGOVY_DOKUMENT);
            if (analogDokNode != null) {
                String value = analogDokNode.getTextContent();
                if ("ne".equals(value)) {
                    digitDoks.add(dokumentNode);
                }
            }
        }

        // získání všech komponent ve výstupním datovém formátu
        List<Element> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        if (CollectionUtils.isEmpty(komponenty)) {
            return;
        }

        Map<Node, Map<Integer, Node>> kandidati = new HashMap<>();
        for (Element komponenta : komponenty) {
            // Kontrola, zda je soucast digit dokumentu?
            Node komponentyNode = komponenta.getParentNode();
            Node dokumentNode = komponentyNode.getParentNode();
            if (!digitDoks.contains(dokumentNode)) {
                continue;
            }

            String formaUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);

            // posuzujeme jen original ve vystupnim formatu nebo original
            if (!JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovani)
                    && !JmenaElementu.FORMA_UCHOVANI_ORIGINAL.equals(formaUchovani)) {
                continue;
            }

            // nalezeni/pridani mapy k rodici            
            Map<Integer, Node> mapaPoradi = kandidati.get(dokumentNode);
            if (mapaPoradi == null) {
                mapaPoradi = new HashMap<>();
                kandidati.put(dokumentNode, mapaPoradi);
            }

            String poradiKomponentyStr = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.PORADI);
            Integer poradiKomponeta = Integer.valueOf(poradiKomponentyStr);
            Node kandidat = mapaPoradi.get(poradiKomponeta);

            if (kandidat == null) {
                mapaPoradi.put(poradiKomponeta, komponenta);
            } else {
                // stavajici verze
                if (isVyssiVerze(kandidat, komponenta)) {
                    mapaPoradi.put(poradiKomponeta, komponenta);
                }
            }
        }
        // priprava mapy souboru
        Set<String> checkFiles = new HashSet<>();
        for (Map<Integer, Node> komponentyDlePoradi : kandidati.values()) {
            for (Node komponenta : komponentyDlePoradi.values()) {
                String id = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.ID);
                if (StringUtils.isEmpty(id)) {
                    Element elKomponenta = (Element) komponenta;
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Komponenta neni ve formátu Pdf/A (chybí atribut ID) " + komponenta,
                            komponenta, kontrola.getEntityId(elKomponenta));
                }
                checkFiles.add(id);
            }
        }

        List<Element> nodeListFileGrp = metsParser.getNodes(MetsElements.FILE_GRP);
        if (CollectionUtils.isEmpty(nodeListFileGrp)) {
            return;
        }
        for (Element fileGrpNode : nodeListFileGrp) {
            List<Element> fileNodes = ValuesGetter.getChildNodes(fileGrpNode, MetsElements.FILE);
            for (Element fileNode : fileNodes) {
                // overeni ID, zda ma byt soubor kontrolovan
                String dmdid = ValuesGetter.getValueOfAttribut(fileNode, JmenaElementu.DMDID);
                if (!checkFiles.contains(dmdid)) {
                    continue;
                }

                String mimeType = ValuesGetter.getValueOfAttribut(fileNode, "MIMETYPE");
                if ("application/pdf".equalsIgnoreCase(mimeType)) {
                    List<Element> flocatNodes = ValuesGetter.getChildNodes(fileNode, MetsElements.FLOCAT);
                    for (Element flocatNode : flocatNodes) {
                        checkPdfA(flocatNode);
                    }
                }
            }
        }
    }

    private boolean isVyssiVerze(Node puvodniKomp, Node novaKomp) {
        // nutne porovnat verze
        Integer verzeOrig = ValuesGetter.getAttribute(puvodniKomp, JmenaElementu.VERZE);
        Integer verzeNovy = ValuesGetter.getAttribute(novaKomp, JmenaElementu.VERZE);
        return (verzeNovy > verzeOrig);
    }

    private void checkPdfA(Node flocatNode) {
        if (!ValuesGetter.hasAttribut(flocatNode, "xlink:href")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Komponenta neni ve formátu Pdf/A (chybí xlink:href)" + flocatNode,
                    getMistoChyby(flocatNode));
        }
        String href = ValuesGetter.getValueOfAttribut(flocatNode, "xlink:href");
        // TODO: Dává toto smysl, asi musí být vždy komponenty
        if (!href.startsWith("komponenty")) {
            return;
        }
        href = HelperString.replaceSeparators(href);
        String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip())
                .replaceFirst("komponenty", "") + href;

        File file = new File(cestaKeKomponente);
        if (file.exists()) {
            try {
                if (veraValidatorProxy == null) {
                    veraValidatorProxy = VeraValidatorProxy.init();
                }
                ValidationResult vr = VeraValidatorProxy.validate(file.toPath());
                if (!vr.isCompliant()) {
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Komponenta neni ve formátu Pdf/A " + flocatNode
                            + ", detail: " + vr.getErrorMessage(),
                            getMistoChyby(flocatNode));
                }
            } catch (Throwable e) {
                log.debug("Failed to validate PDF.", e);
                nastavChybu(BaseCode.CHYBA, "Formátu Pdf/A se nepodařilo ověřit u komponenty " + flocatNode,
                        getMistoChyby(flocatNode));
            }
        }
    }
}
