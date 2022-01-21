package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.ArrayList;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//
// OBSAHOVÁ č.43a
//
// Pokud existuje element <mets:fileSec>, obsahuje právě jeden dětský element
// <mets:fileGrp>.
//
public class Pravidlo43a extends K06PravidloBase
{

    static final public String OBS43A = "obs43a";

    public Pravidlo43a() {
        super(OBS43A,
                "Pokud existuje element <mets:fileSec>, obsahuje právě jeden dětský element <mets:fileGrp>.",
                "Chybí připojení komponent (počítačových souborů) nebo je špatně strukturováno.",
                "Bod 2.14. přílohy č. 3 NSESSS.");
    }

    @Override
    protected boolean kontrolaPravidla() {
        Node metsMets = metsParser.getMetsRootNode();
        Node fileSec = ValuesGetter.getXChild(metsMets, "mets:fileSec");
        if (fileSec == null) {
            return true;
        }

        ArrayList<Node> fileGrpList = ValuesGetter.getChildList(fileSec, MetsElements.FILE_GRP);

        if (fileGrpList.size() != 1) {
            return nastavChybu("Element <mets:fileSec> neobsahuje právě jeden element <mets:fileGrp>.", fileSec);
        }
        return true;
    }

}
