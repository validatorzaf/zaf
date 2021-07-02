package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.pdfa.ValidationResult;
import cz.zaf.sipvalidator.pdfa.VeraValidatorProxy;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

// Obsahova 99
// pdf dokument musi vyhovovat standardu Pdf/A
public class Pravidlo99 extends K06PravidloBase {

    static Logger log = LoggerFactory.getLogger(Pravidlo99.class);

    public static final String OBS99 = "obs99";

    VeraValidatorProxy veraValidatorProxy;

    public Pravidlo99() {
        super(OBS99, "Pokud je soubor ve formátu pdf musí vyhovovat standardu Pdf/A",
                "Chybný formát souboru", 
                "§ 23 odst. 2 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected boolean kontrolaPravidla() {

        // získání všech komponent ve výstupním datovém formátu
        NodeList komponenty = ValuesGetter.getAllAnywhere("nsesss:Komponenta", metsParser.getDocument());
        if (komponenty == null || komponenty.getLength() == 0) {
            return true;
        }

        Map<Node, Map<Integer, Node>> kandidati = new HashMap<>();
        for (int ki = 0; ki < komponenty.getLength(); ki++) {
            Node komponenta = komponenty.item(ki);
            String formaUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);

            // posuzujeme jen original a original ve vystupnim formatu
            if (!JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovani) &&
                    !JmenaElementu.FORMA_UCHOVANI_ORIGINAL.equals(formaUchovani))
                continue;

            // nalezeni/pridani mapy k rodici
            Node parent = komponenta.getParentNode();
            Map<Integer, Node> mapaPoradi = kandidati.get(parent);
            if (mapaPoradi == null) {
                mapaPoradi = new HashMap<>();
                kandidati.put(parent, mapaPoradi);
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
                    return nastavChybu("Komponenta neni ve formátu Pdf/A (chybí atribut ID) " + komponenta,
                                       getMistoChyby(komponenta));
                }
                checkFiles.add(id);
            }
        }

        NodeList nodeListFileGrp = ValuesGetter.getAllAnywhere("mets:fileGrp", metsParser.getDocument());
        if (nodeListFileGrp == null) {
            return true;
        }
        for (int fg = 0; fg < nodeListFileGrp.getLength(); fg++) {
            Node fileGrpNode = nodeListFileGrp.item(fg);
            ArrayList<Node> fileNodes = ValuesGetter.getChildList(fileGrpNode, "mets:file");
            for (Node fileNode : fileNodes) {
                // overeni ID, zda ma byt soubor kontrolovan
                String dmdid = ValuesGetter.getValueOfAttribut(fileNode, JmenaElementu.DMDID);
                if (!checkFiles.contains(dmdid)) {
                    continue;
                }

                String mimeType = ValuesGetter.getValueOfAttribut(fileNode, "MIMETYPE");
                if ("application/pdf".equalsIgnoreCase(mimeType)) {
                    ArrayList<Node> flocatNodes = ValuesGetter.getChildList(fileNode, "mets:FLocat");
                    for (Node flocatNode : flocatNodes) {
                        if (!checkPdfA(flocatNode)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;

    }

    private boolean isVyssiVerze(Node puvodniKomp, Node novaKomp) {
        String formaUchovaniOrig = ValuesGetter.getValueOfAttribut(puvodniKomp, JmenaElementu.FORMA_UCHOVANI);
        String formaUchovaniNovy = ValuesGetter.getValueOfAttribut(novaKomp, JmenaElementu.FORMA_UCHOVANI);
        if (Objects.equals(formaUchovaniOrig, formaUchovaniNovy)) {
            // shodny typ -> nutne porovnat verze
            Integer verzeOrig = ValuesGetter.getAttribute(puvodniKomp, JmenaElementu.VERZE);
            Integer verzeNovy = ValuesGetter.getAttribute(novaKomp, JmenaElementu.VERZE);
            return (verzeNovy > verzeOrig);
        }
        if(JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovaniOrig)) {
            // orig je ve vystupnim formatu -> nova nemuze byt lepsi
            return false;
        }
        if(JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovaniNovy)) {
            return true;
        }
        // Toto by nemelo nastat
        return false;
    }

    private boolean checkPdfA(Node flocatNode) {
        if (!ValuesGetter.hasAttribut(flocatNode, "xlink:href")) {
            return nastavChybu("Komponenta neni ve formátu Pdf/A (chybí xlink:href)" + flocatNode,
                               getMistoChyby(flocatNode));
        }
        String href = ValuesGetter.getValueOfAttribut(flocatNode, "xlink:href");
        // TODO: Dává toto smysl, asi musí být vždy komponenty
        if (!href.startsWith("komponenty")) {
            return true;
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
                ValidationResult vr = veraValidatorProxy.validate(file.toPath());
                if (!vr.isCompliant()) {
                    return nastavChybu("Komponenta neni ve formátu Pdf/A " + flocatNode +
                            ", detail: " + vr.getErrorMessage(),
                                       getMistoChyby(flocatNode));
                }
            } catch (Throwable e) {
                log.error("Failed to validate PDF, exception: {}", e);
                return nastavChybu("Formátu Pdf/A se nepodařilo ověřit u komponenty " + flocatNode,
                                   getMistoChyby(flocatNode));
            }
        }
        return true;
    }
}
