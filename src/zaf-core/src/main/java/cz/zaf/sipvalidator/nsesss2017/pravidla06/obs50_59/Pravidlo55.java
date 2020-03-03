package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo55 extends K06PravidloBase {

    static final public String OBS55 = "obs55";

    public Pravidlo55(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo55.OBS55,
                "Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.",
                "Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
                "Bod 2.19. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.55 Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.
    @Override
    protected boolean kontrolaPravidla() {
        NodeList nodeListDiv = ValuesGetter.getAllAnywhere("mets:div", metsParser.getDocument());
        if (nodeListDiv == null)
            return true;
        for (int i = 0; i < nodeListDiv.getLength(); i++) {
            Node div = nodeListDiv.item(i);
            boolean obsahuje = ValuesGetter.hasAttributValue(div, "TYPE", "komponenta");
            if (obsahuje) {
                ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(div, "mets:fptr");
                if (list.isEmpty()) {
                    return nastavChybu("Element <mets:div> neobsahuje element <mets:fptr>.", div);
                }
                if (list.size() > 1) {
                    return nastavChybu("Elementu <mets:div> obsahuje více elementů <mets:fptr>.", div);
                }
            }
        }
        return true;
    }
}
