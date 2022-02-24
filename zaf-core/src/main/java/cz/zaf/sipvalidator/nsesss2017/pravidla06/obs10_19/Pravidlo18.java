package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import org.w3c.dom.Node;

//OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
public class Pravidlo18 extends K06PravidloBase {

    static final public String OBS18 = "obs18";

    public Pravidlo18() {
        super(OBS18, "Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
                "Uveden je chybně popis původce.",
                "Bod 2.3. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> listElementAgent = metsParser.getNodes(MetsElements.AGENT);
        if (CollectionUtils.isEmpty(listElementAgent)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:agent>.");
        }

        listElementAgent.forEach((elementAgent) -> {
            Node atrRole = elementAgent.getAttributeNode("ROLE");
            if (atrRole == null) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:agent> neobsahuje atribut ROLE", elementAgent);

            } else {
                String hodnotaAtrRole = atrRole.getTextContent();
                if (!"CREATOR".equals(hodnotaAtrRole)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                            "Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", elementAgent);
                }
            }

        });
    }

}
