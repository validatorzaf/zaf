package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs40_49;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

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
    protected void kontrola() {
        Element metsMets = metsParser.getMetsRootNode();
        Element fileSec = ValuesGetter.getXChild(metsMets, "mets:fileSec");
        if (fileSec == null) {
            return;
        }

        List<Element> fileGrpList = ValuesGetter.getChildNodes(fileSec, MetsElements.FILE_GRP);

        if (fileGrpList.size() != 1) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:fileSec> neobsahuje právě jeden element <mets:fileGrp>.", fileSec);
        }
    }

}
