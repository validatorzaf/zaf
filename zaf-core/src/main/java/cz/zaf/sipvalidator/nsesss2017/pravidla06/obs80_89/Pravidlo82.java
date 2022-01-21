package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo82 extends K06PravidloBase {

    static final public String OBS82 = "obs82";

    public Pravidlo82() {
        super(OBS82,
                "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
                "Není v souladu rozsah určeného časového období.",
                null);
    }

    //OBSAHOVÁ č.82 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> urceneCasoveObdobi = metsParser.getNodes(NsessV3.URCENE_CASOVE_OBDOBI);
        for (Node urcenecasoveobdobi: urceneCasoveObdobi) {
            Node nodeDo = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicDo");
            if (nodeDo != null) {
                Node nodeOd = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicOd");
                if (nodeOd == null) {
                    return nastavChybu("Nenalezen element <nsesss:MesicOd>. " + getJmenoIdentifikator(
                                                                                                     urcenecasoveobdobi),
                                       urcenecasoveobdobi);
                }
                Date dateOd, dateDo;
                try {
                    dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd", "yyyy-MM");
                    dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd", "yyyy-MM");

                } catch (ParseException ex) {
                    return nastavChybu("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(
                                                                                                         urcenecasoveobdobi),
                                       getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
                if (dateOd == null || dateDo == null) {
                    return nastavChybu("Hodnoty dat v nepovoleném formátu. " + getJmenoIdentifikator(
                                                                                                     urcenecasoveobdobi),
                                       getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
                if (!dateOd.before(dateDo)) {
                    return nastavChybu("Nesplněna podmínka pravidla. OD: " + dateOd + ". DO: " + dateDo + ". "
                            + getJmenoIdentifikator(urcenecasoveobdobi),
                                       getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
            }
        }
        return true;
    }

}
