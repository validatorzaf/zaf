package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.verapdf.core.EncryptedPdfException;
import org.verapdf.core.ModelParsingException;
import org.verapdf.core.ValidationException;
import org.verapdf.pdfa.Foundries;
import org.verapdf.pdfa.PDFAParser;
import org.verapdf.pdfa.PDFAValidator;
import org.verapdf.pdfa.VeraGreenfieldFoundryProvider;
import org.verapdf.pdfa.results.ValidationResult;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

// Obsahova 99
// pdf dokument musi vyhovovat standardu Pdf/A
public class Pravidlo99  extends K06PravidloBase {
	
	static final public String OBS99 = "obs99";
	
	static {
		VeraGreenfieldFoundryProvider.initialise();
	}

	public Pravidlo99(K06_Obsahova kontrola) {
		super(kontrola, Pravidlo99.OBS99, "Pokud je soubor ve formátu pdf musí vyhovovat standardu Pdf/A",
				"Chybný formát souboru", "§ 23 odst. 2 vyhlášky č. 259/2012 Sb.");
	}

	@Override
	protected boolean kontrolaPravidla() {

        // získání všech komponent ve výstupním datovém formátu
        NodeList komponenty = ValuesGetter.getAllAnywhere("nsesss:Komponenta", metsParser.getDocument());
        if (komponenty == null || komponenty.getLength() == 0) {
            return true;
        }

        Set<String> checkFiles = new HashSet<>();
        for (int ki = 0; ki < komponenty.getLength(); ki++) {
            Node komponenta = komponenty.item(ki);
            String formaUchovani = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.FORMA_UCHOVANI);
            if (JmenaElementu.FORMA_UCHOVANI_ORIGINAL_VE_VYST_DAT_FORMATU.equals(formaUchovani)) {
                String id = ValuesGetter.getValueOfAttribut(komponenta, JmenaElementu.ID);
                if (StringUtils.isEmpty(id)) {
                    return nastavChybu("Komponenta neni ve formátu Pdf/A (chybí atribut ID) " + komponenta,
                                       getMistoChyby(komponenta));
                }
                checkFiles.add(id);
            }
        }

		NodeList nodeListFileGrp = ValuesGetter.getAllAnywhere("mets:fileGrp", metsParser.getDocument());
		if (nodeListFileGrp==null) {
			return true;
		}
		for(int fg=0;fg<nodeListFileGrp.getLength();fg++) {
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
        href = HelperString.edit_path(href);
        String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip())
                .replaceFirst("komponenty", "") + href;

        File file = new File(cestaKeKomponente);
        if (file.exists()) {
            try (PDFAParser parser = Foundries.defaultInstance().createParser(
                                                                              new FileInputStream(
                                                                                      file))) {
                PDFAValidator validator = Foundries.defaultInstance().createValidator(parser
                        .getFlavour(), false);
                ValidationResult result = validator.validate(parser);
                if (!result.isCompliant()) {
                    return nastavChybu("Komponenta neni ve formátu Pdf/A " + flocatNode,
                                       getMistoChyby(flocatNode));
                }
            } catch (ModelParsingException | EncryptedPdfException | IOException
                    | ValidationException e) {
                return false;
            }
        }
        return true;
    }
}