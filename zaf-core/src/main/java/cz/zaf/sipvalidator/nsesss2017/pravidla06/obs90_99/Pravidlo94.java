package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo94 extends K06PravidloBase {

    static final public String OBS94 = "obs94";

    public Pravidlo94() {
        super(OBS94,
                "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.30 NSESSS.");
    }

    //OBSAHOVÁ č.94 "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, 
    // jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
    @Override
    protected void kontrola() {
        List<Element> plneurcenySpisovyZnak = metsParser.getNodes(NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (CollectionUtils.isEmpty(plneurcenySpisovyZnak)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>.");
        }
        for (Element puzNode : plneurcenySpisovyZnak) {
            // kontrola jen pokud je rodic nsesss:Trideni
            Element trideni = ValuesGetter.getParent(puzNode, NsessV3.TRIDENI);
            if (trideni == null) {
                continue;
            }

            Element jzNode = ValuesGetter.getSourozencePrvnihoSeJmenem(puzNode, NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
            Element entita = kontrola.getEntity(jzNode);
            if (jzNode == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. "
                        + getJmenoIdentifikator(puzNode), puzNode, kontrola.getEntityId(entita));
            }
            String jednoduchy = jzNode.getTextContent();
            String plneUrceny = puzNode.getTextContent();
            if (!jednoduchy.equals(plneUrceny)) {
                if (!plneUrceny.endsWith(jednoduchy)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Část plně určeného spis. znaku za oddělovačem neodpovídá jedn. spis. znaku. "
                            + getJmenoIdentifikator(puzNode),
                            getMistoChyby(puzNode) + " " + getMistoChyby(jzNode),
                            kontrola.getEntityId(entita));
                }
            }
        }
    }

}
