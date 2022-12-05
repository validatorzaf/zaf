package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import org.apache.commons.lang.StringUtils;

// Pokud existuje jakýkoli element mets:file, každý obsahuje atribut MIMETYPE, jeho hodnota musí odpovídat pravidlům pro tvorbu označení MIMETYPE 
// uvedených na https://www.iana.org/assignments/media-types/media-types.xhtml a musí odpovídat typu referencovaného souboru.
public class Pravidlo41 extends K06PravidloBase {

    static final public String OBS41 = "obs41";

    public Pravidlo41() {
        super(OBS41,
                "Pokud existuje jakýkoli element mets:file, každý obsahuje atribut MIMETYPE, jeho hodnota musí odpovídat pravidlům pro tvorbu označení MIMETYPE uvedených na https://www.iana.org/assignments/media-types/media-types.xhtml a musí odpovídat typu referencovaného souboru.",
                "Atribut MIMETYPE elementu mets:file neobsahuje správnou hodnotu nebo je prázdný.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }
    
    @Override
    protected void kontrola() {

        List<Element> listMetsFile = metsParser.getNodes(MetsElements.FILE);
        if (CollectionUtils.isEmpty(listMetsFile)) {
            return;
        }

        for (Element elMetsFile : listMetsFile) {
            String mimetype = elMetsFile.getAttribute("MIMETYPE");
            if (StringUtils.isBlank(mimetype)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> nemá atribut MIMETYPE.", elMetsFile);
            }
            if(mimetype.isEmpty()){
                nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Hodnota atributu MIMETYPE elementu <mets:file> je prázdná.", elMetsFile);
            }
            if(!isPrefix(mimetype)){
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Hodnota atributu MIMETYPE elementu <mets:file> neobsahuje správnou hodnotu.", elMetsFile);
            }
        }

    }

    private boolean isPrefix(String mimetype) {
        String[] povolenePrefixy = {"application, audio, font, example, image, message, model, multipart, text, video"};
        String povinnyZnak = "/";
        for (String povolenyPrefix : povolenePrefixy) {
            if (mimetype.startsWith(povolenyPrefix + povinnyZnak)) {
                return true;
            }
        }
        return false;
    }

}
