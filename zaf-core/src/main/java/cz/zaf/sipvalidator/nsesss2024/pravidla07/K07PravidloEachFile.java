package cz.zaf.sipvalidator.nsesss2024.pravidla07;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;

/**
 * Vychozi implementace pro pravidla formatovych kontrol
 * 
 * Provadi iteraci pres vsechny soubory
 */
abstract public class K07PravidloEachFile extends K07PravidloBase {

    public K07PravidloEachFile(String kodPravidla, String textPravidla, String obecnyPopisChyby, String zdrojChyby) {
        super(kodPravidla, textPravidla, obecnyPopisChyby, zdrojChyby);
    }

    @Override
    final protected void kontrola() {
        List<Element> nodeListMetsFile = ctx.getMetsFiles();
        for (Element metsFile : nodeListMetsFile) {
            Element elFlocat = ValuesGetter.getXChild(metsFile, MetsElements.FLOCAT);
            if (elFlocat == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                            "Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", metsFile);
            }
            String xlinkHref = elFlocat.getAttribute("xlink:href");
            if (StringUtils.isBlank(xlinkHref)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                            "Element <mets:FLocat> nemá atribut xlink:href.", elFlocat);
            }

            xlinkHref = HelperString.replaceSeparators(xlinkHref);

            Path sipPath = ctx.getSip().getCesta();
            Path filePath = sipPath.resolve(xlinkHref);

            if (!Files.isRegularFile(filePath)) {
                nastavChybuSouboru(BaseCode.CHYBNA_KOMPONENTA,
                                   "Nenalezen příslušný soubor ve složce komponenty.",
                                   elFlocat,
                                   xlinkHref);
            }

            kontrolaSouboru(metsFile, elFlocat, xlinkHref, filePath);
        }
    }

    /**
     * Kontrola jednotliveho souboru
     * 
     * @param metsFile
     * @param elFlocat
     * @param xlinkHref
     * @param filePath
     */
    protected abstract void kontrolaSouboru(Element metsFile, Element elFlocat, String xlinkHref, Path filePath);

}
