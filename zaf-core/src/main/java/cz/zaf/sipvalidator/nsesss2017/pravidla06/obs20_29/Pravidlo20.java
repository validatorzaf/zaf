package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import org.apache.commons.lang3.StringUtils;

//OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
public class Pravidlo20 extends K06PravidloBase {

    static final public String OBS20 = "obs20";

    public Pravidlo20() {
        super(OBS20,
                "Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
                "Chybí informace o původci.",
                "Bod 2.4. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElMetsAgent = metsParser.getNodes(MetsElements.AGENT);
        if (CollectionUtils.isEmpty(listElMetsAgent)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <mets:agent>.");
        }

        for (Element elMetsAgent : listElMetsAgent) {
            List<Element> listElMetsName = ValuesGetter.getChildNodes(elMetsAgent, "mets:name");
            int size = listElMetsName.size();
            if (size == 0) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:agent> neobsahuje žádný dětský element <mets:name>.", elMetsAgent);
            }
            if (size > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:agent> neobsahuje právě jeden dětský element <mets:name>.", elMetsAgent);
            }

            String hodnotaElMetsName = elMetsAgent.getTextContent();
            if (StringUtils.isBlank(hodnotaElMetsName)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU,
                        "Element <mets:agent> má nevyplněnou hodnotu u dětského elementu <mets:name>.", elMetsAgent);
            }
        }
    }

}
