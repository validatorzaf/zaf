package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo50 extends K06PravidloBase {

    static final public String OBS50 = "obs50";

    public Pravidlo50() {
        super(OBS50,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje právě jeden dětský element <mets:FLocat>.",
                "Chybí připojení komponenty (počítačového souboru) do datového balíčku SIP nebo je provedeno chybně.",
                "Bod 2.16. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.50 Pokud existuje jakýkoli element <mets:file>, každý obsahuje právě jeden dětský element <mets:FLocat>.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if (nodeListMetsFile == null)
            return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++) {
            Node metsFile = nodeListMetsFile.item(i);
            if (ValuesGetter.getXChild(metsFile, "mets:FLocat") == null) {
                return nastavChybu("Element <mets:file> nemá žádný dětský element <mets:FLocat>.", metsFile);
            }
            if (!ValuesGetter.hasOnlyOneChild_ElementNode(nodeListMetsFile.item(i), "mets:FLocat")) {
                return nastavChybu("Element <mets:file> má více dětských elementů <mets:FLocat>.", metsFile);
            }
        }
        return true;
    }

}
