package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo83 extends K06PravidloBase {

    static final public String OBS83 = "obs83";

    public Pravidlo83() {
        super(OBS83,
                "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>.",
                "Není v souladu rozsah určeného časového období.",
                null);
    }

    //OBSAHOVÁ č.83 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element 
    // <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>."
    @Override
    protected void kontrola() {
        List<Element> urceneCasoveObdobi = metsParser.getNodes(NsesssV3.URCENE_CASOVE_OBDOBI);
        for (Element ucobdobi : urceneCasoveObdobi) {
            Element elEntita = kontrola.getEntity(ucobdobi);
            Element nodeDo = ValuesGetter.getXChild(ucobdobi, NsesssV3.ROK_DO);
            if (nodeDo != null) {
                Element nodeOd = ValuesGetter.getXChild(ucobdobi, NsesssV3.ROK_OD);
                if (nodeOd == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:RokOd>.", ucobdobi,
                            kontrola.getEntityId(elEntita));
                }
                Integer intOd = vratRok(nodeOd);
                Integer intDo = vratRok(nodeDo);

                if (!(intOd < intDo)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. OD: " + intOd + ". DO: " + intDo + ".",
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                            kontrola.getEntityId(elEntita));
                }
            }
        }
    }

}
