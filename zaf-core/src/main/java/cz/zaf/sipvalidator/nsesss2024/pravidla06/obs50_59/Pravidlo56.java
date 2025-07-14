package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs50_59;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo56 extends K06PravidloBase {

    static final public String OBS56 = "obs56";

    public Pravidlo56() {
        super(OBS56,
                "Pokud existuje jakýkoli element <mets:fptr>, každý obsahuje atribut FILEID s hodnotou, která odpovídá hodnotě atributu ID elementu <mets:file> příslušné komponenty. Příslušnost vyjadřuje stejná hodnota atributu DMDID rodičovského elementu <mets:div> a elementu <mets:file>.",
                "Není v souladu provázání komponent (počítačových souborů) mezi částí počítačových souborů a strukturální mapou.",
                "Bod 2.19. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.56 Pokud existuje jakýkoli element <mets:fptr>, každý obsahuje atribut FILEID s hodnotou, která odpovídá hodnotě atributu ID elementu <mets:file> příslušné komponenty. Příslušnost vyjadřuje stejná hodnota atributu DMDID rodičovského elementu <mets:div> a elementu <mets:file>.",
    @Override
    protected void kontrola() {
        List<Element> nodeListMetsFptr = metsParser.getNodes(MetsElements.FPTR);
        if (!nodeListMetsFptr.isEmpty()) {

            Map<String, Node> fptrFileIdMap = new HashMap<>();
            for (Element metsFptr : nodeListMetsFptr) {
                String fileId = ValuesGetter.getValueOfAttribut(metsFptr, "FILEID");
                if (StringUtils.isEmpty(fileId)) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:fptr> neobsahuje atribut FILEID.", metsFptr);
                }
                fptrFileIdMap.put(fileId, metsFptr);
                // TODO: Check if multiple fptr do not have same FILEID
            }

            List<Element> nodeListFile = metsParser.getNodes(MetsElements.FILE);
            if (nodeListFile.isEmpty()) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <mets:file>.");
            }
            for (Element metsFile : nodeListFile) {
                String idFile = ValuesGetter.getValueOfAttribut(metsFile, "ID");
                if (StringUtils.isEmpty(idFile)) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> neobsahuje atribut ID.", metsFile);
                }
                Node fptrNode = fptrFileIdMap.get(idFile);
                if (fptrNode == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "K elementu <mets:file> nenalezen odpovídající element <mets:FLocat>.", metsFile);
                }
            }
        }
    }

}
