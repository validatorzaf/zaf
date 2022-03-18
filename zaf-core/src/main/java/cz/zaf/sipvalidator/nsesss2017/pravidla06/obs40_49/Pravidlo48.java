package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.io.File;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import org.apache.commons.lang.StringUtils;

//OBSAHOVÁ č.48 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
public class Pravidlo48 extends K06PravidloBase {

    static final public String OBS48 = "obs48";

    public Pravidlo48() {

        super(OBS48,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
                "Chybí velikost komponenty (počítačového souboru) nebo je uvedena chybně.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            String hodnotaAtrSize = metsFile.getAttribute("SIZE");
            if (StringUtils.isBlank(hodnotaAtrSize)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:file> neobsahuje atribut SIZE.", metsFile);
            }

            Element elFLocat = ValuesGetter.getXChild(metsFile, MetsElements.FLOCAT);
            if (elFLocat == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", metsFile);
            }

            String hodnotaAtrXlinkHref = elFLocat.getAttribute("xlink:href");
            if (StringUtils.isBlank(hodnotaAtrXlinkHref)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:FLocat> neobsahuje atribut xlink:href.", elFLocat);
            }

            hodnotaAtrXlinkHref = HelperString.replaceSeparators(hodnotaAtrXlinkHref);
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip());
            File file = new File(cestaKeKomponente);
            file = new File(file.getParentFile().getAbsolutePath() + File.separator + hodnotaAtrXlinkHref);
            if (!file.exists()) {
                nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                        "Nenalezena příslušná komponenta v složce komponenty.",
                        getMistoChyby(elFLocat) + " Soubor: " + hodnotaAtrXlinkHref + ".");
            }
            String velikost = String.valueOf(file.length());
            if (!velikost.equals(hodnotaAtrSize)) {
                nastavChybu(BaseCode.CHYBNA_KOMPONENTA,
                        "Velikost komponenty není totožná s metadaty.",
                        getMistoChyby(metsFile) + " Komponenta: " + file.getName() + ".");
            }
        }
    }
}
