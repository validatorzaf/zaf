package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
    protected boolean kontrolaPravidla() {
        List<Node> nodeListMetsFptr = metsParser.getNodes(MetsElements.FPTR);        
        if (nodeListMetsFptr.size() == 0) {
            return true;
        }

        Map<String, Node> fptrFileIdMap = new HashMap<>();
        for (Node metsFptr: nodeListMetsFptr) {
            String fileId = ValuesGetter.getValueOfAttribut(metsFptr, "FILEID");
            if (StringUtils.isEmpty(fileId)) {
                return nastavChybu("Element <mets:fptr> neobsahuje atribut FILEID.", metsFptr);
            }
            fptrFileIdMap.put(fileId, metsFptr);
            // TODO: Check if multiple fptr do not have same FILEID
        }

        List<Node> nodeListFlocat = metsParser.getNodes(MetsElements.FLOCAT);
        if (nodeListFlocat.size() == 0) {
            return nastavChybu("Nenalezen element <mets:FLocat>.");
        }
        for(Node fLocat: nodeListFlocat) {
            Node metsFile = ValuesGetter.getXParent(fLocat, "mets:file");
            if (metsFile == null) {
                return nastavChybu("Nenalezen rodičovský element <mets:file>.");
            }
            String idFile = ValuesGetter.getValueOfAttribut(metsFile, "ID");
            if(StringUtils.isEmpty(idFile)) {
                return nastavChybu("Element <mets:file> neobsahuje atribut ID.", metsFile);
            }
            Node fptrNode = fptrFileIdMap.get(idFile);
            if(fptrNode==null) {
                return nastavChybu("K elementu <mets:file> nenalezen odpovídající element <mets:FLocat>.", metsFile);
            }
        }        
        return true;
    }

}
