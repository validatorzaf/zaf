package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// Pokud existuje jakýkoli element mets:file, každý obsahuje atribut MIMETYPE,
// jeho hodnota musí odpovídat pravidlům pro tvorbu označení MIMETYPE
// uvedených na https://www.iana.org/assignments/media-types/media-types.xhtml a
// musí odpovídat typu referencovaného souboru.
//
//
// Rozšířená kontrola souladu s:
//
//
//
//
public class Pravidlo41 extends K06PravidloBase {

    static final public String OBS41 = "obs41";

    static final Set<String> POVOLENE_PREFIXY = new HashSet<>();
    static {
        POVOLENE_PREFIXY.add("application");
        POVOLENE_PREFIXY.add("audio");
        POVOLENE_PREFIXY.add("font");
        POVOLENE_PREFIXY.add("example");
        POVOLENE_PREFIXY.add("image");
        POVOLENE_PREFIXY.add("message");
        POVOLENE_PREFIXY.add("model");
        POVOLENE_PREFIXY.add("multipart");
        POVOLENE_PREFIXY.add("text");
        POVOLENE_PREFIXY.add("video");
    }

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
            checkSyntax(mimetype, elMetsFile);
        }

    }

    private void checkSyntax(String mimetype, Element elMetsFile) {
        String mimetypeLower = mimetype.toLowerCase();
        int separatorPos = mimetypeLower.indexOf('/');
        if (separatorPos < 0) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Hodnota atributu MIMETYPE elementu <mets:file> neobsahuje lomítko (oddělovač).",
                        elMetsFile);
        }
        String type = mimetypeLower.substring(0, separatorPos);
        String subtype = mimetypeLower.substring(separatorPos + 1);

        // check type
        if (!POVOLENE_PREFIXY.contains(type)) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Hodnota atributu MIMETYPE elementu <mets:file> neobsahuje povolený typ, hodnota: " + mimetype,
                        elMetsFile);
        }

        // check subtype
        if (subtype.length() == 0) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Hodnota atributu MIMETYPE elementu <mets:file> neobsahuje podtype, hodnota: " + mimetype,
                        elMetsFile);
        }
    }

}
