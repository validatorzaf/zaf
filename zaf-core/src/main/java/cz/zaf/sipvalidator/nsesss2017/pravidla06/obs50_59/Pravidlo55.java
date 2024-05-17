package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo55 extends K06PravidloBase {

    static final public String OBS55 = "obs55";

    public Pravidlo55() {
        super(OBS55,
                "Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.",
                "Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
                "Bod 2.19. přílohy č. 3 NSESSS.");
    }

    //OBSAHOVÁ č.55 Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.
    @Override
    protected void kontrola() {
        List<Element> nodeListDiv = metsParser.getNodes(MetsElements.DIV);

        for (Element div : nodeListDiv) {
            boolean obsahuje = ValuesGetter.hasAttributValue(div, "TYPE", "komponenta");
            if (obsahuje) {
                List<Element> list = ValuesGetter.getChildNodes(div, "mets:fptr");
                if (list.isEmpty()) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <mets:div> neobsahuje element <mets:fptr>.", div);
                }
                if (list.size() > 1) {
                    nastavChybu(BaseCode.NEPOVOLENY_ELEMENT, "Elementu <mets:div> obsahuje více elementů <mets:fptr>.", div);
                }
            }
        }
    }
}
