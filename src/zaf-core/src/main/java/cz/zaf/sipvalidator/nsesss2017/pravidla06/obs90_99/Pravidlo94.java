package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo94 extends K06PravidloBase {

    static final public String OBS94 = "obs94";

    public Pravidlo94(K06_Obsahova kontrola) {
        super(kontrola,
                Pravidlo94.OBS94,
                "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.30 NSESSS.");
    }

    //OBSAHOVÁ č.94 "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, 
    // jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> plneurcenySpisovyZnak = metsParser.getPlneurcenySpisovyZnak();
        if (plneurcenySpisovyZnak == null)
            return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>.",
                               K06_Obsahova.MISTO_CHYBY_NEUPRESNENO);
        for (int i = 0; i < plneurcenySpisovyZnak.size(); i++) {
            Node pusz_node = plneurcenySpisovyZnak.get(i);
            Node jsz_node = ValuesGetter.getSourozencePrvnihoSeJmenem(pusz_node, "nsesss:JednoduchySpisovyZnak");
            if (jsz_node == null) {
                boolean b = ValuesGetter.isXParent(pusz_node, "nsesss:KrizovyOdkaz");
                if (!b) {
                    return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(
                                                                                                                   pusz_node),
                                       getMistoChyby(pusz_node));
                }
            } else {
                String jednoduchy = jsz_node.getTextContent();
                String plneUrceny = pusz_node.getTextContent();
                //                boolean b = ValuesGetter.compareSpisoveZnaky(jednoduchy, plneUrceny);
                if (!jednoduchy.equals(plneUrceny)) {
                    if (!plneUrceny.endsWith(jednoduchy)) {
                        return nastavChybu("Část plně určeného spis. znaku za oddělovačem neodpovídá jedn. spis. znaku. "
                                + getJmenoIdentifikator(pusz_node), getMistoChyby(pusz_node) + " "
                                        + getMistoChyby(jsz_node));
                    }
                }
            }
        }
        return true;
    }

}
