package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        NodeList nodeListMetsFptr = ValuesGetter.getAllAnywhere("mets:fptr", metsParser.getDocument());
        if (nodeListMetsFptr == null)
            return true;
        for (int i = 0; i < nodeListMetsFptr.getLength(); i++) {
            Node metsFptr = nodeListMetsFptr.item(i);
            if (!ValuesGetter.hasAttribut(metsFptr, "FILEID")) {
                return nastavChybu("Element <mets:fptr> neobsahuje atribut FILEID.", getMistoChyby(metsFptr));
            }
        }

        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        if (nodeListFlocat == null) {
            return nastavChybu("Nenalezen element <mets:FLocat>.");
        }
        for (int i = 0; i < nodeListMetsFptr.getLength(); i++) {
            Node fLocat = nodeListFlocat.item(i);
            Node metsFptr = nodeListMetsFptr.item(i);
            Node metsFile = ValuesGetter.getXParent(fLocat, "mets:file");
            if (metsFile == null) {
                return nastavChybu("Nenalezen element <mets:file>.");
            }
            if (!ValuesGetter.hasAttribut(metsFile, "ID")) {
                return nastavChybu("Element <mets:file> neobsahuje atribut ID.", getMistoChyby(metsFile));
            }
            String fptrFileId = ValuesGetter.getValueOfAttribut(metsFptr, "FILEID");
            String idFile = ValuesGetter.getValueOfAttribut(ValuesGetter.getXParent(fLocat, "mets:file"), "ID");
            if (!fptrFileId.equals(idFile)) {
                return nastavChybu("Hodnoty atributů si neodpovídají. FILEID: " + fptrFileId + " ID: " + idFile + ".",
                                   getMistoChyby(metsFptr) + " " + getMistoChyby(metsFile));
            }
        }
        return true;
    }

}
