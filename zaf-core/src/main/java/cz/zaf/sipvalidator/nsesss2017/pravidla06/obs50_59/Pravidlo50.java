package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo50 extends K06PravidloBaseOld {

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
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Element metsFile : nodeListMetsFile) {
            List<Node> childNodes = ValuesGetter.getChildNodes(metsFile);
            if(childNodes.size()==0) {
                return nastavChybu("Element <mets:file> nemá žádný dětský element <mets:FLocat>.", metsFile);
            }
            if(childNodes.size()>1) {
                return nastavChybu("Element <mets:file> má více dětský elementů, očekáván je právě jeden <mets:FLocat>.", metsFile);
            }
            if (!"mets:FLocat".equals(childNodes.get(0).getNodeName())) {
                return nastavChybu("Element <mets:file> neobsahuje právě jeden element <mets:FLocat>.", metsFile);
            }
        }
        return true;
    }

}
