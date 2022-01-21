package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
    protected boolean kontrolaPravidla() {
        List<Node> urceneCasoveObdobi = metsParser.getNodes(NsessV3.URCENE_CASOVE_OBDOBI);
        for (Node ucobdobi: urceneCasoveObdobi) {
            Node nodeDo = ValuesGetter.getXChild(ucobdobi, "nsesss:RokDo");
            if (nodeDo != null) {
                Node nodeOd = ValuesGetter.getXChild(ucobdobi, "nsesss:RokOd");
                if (nodeOd == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokOd>. " + getJmenoIdentifikator(ucobdobi),
                                      ucobdobi);
                }
                Integer intOd = vratRok(nodeOd);
                if (intOd == null) {
                    return false;
                }
                Integer intDo = vratRok(nodeDo);
                if (intDo == null) {
                    return false;
                }
                if (!(intOd < intDo)) {
                    return nastavChybu("Nesplněna podmínka pravidla. OD: " + intOd + ". DO: " + intDo + ". "
                            + getJmenoIdentifikator(ucobdobi), 
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
            }
        }
        return true;
    }

}
