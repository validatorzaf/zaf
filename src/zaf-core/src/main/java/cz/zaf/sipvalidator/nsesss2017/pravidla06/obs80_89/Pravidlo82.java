package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo82 extends K06PravidloBase {

    static final public String OBS82 = "obs82";

    public Pravidlo82(K06_Obsahova kontrola) {
        super(kontrola, Pravidlo82.OBS82,
                "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
                "Není v souladu rozsah určeného časového období.",
                null);
    }

    //OBSAHOVÁ č.82 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> urceneCasoveObdobi = metsParser.getUrceneCasoveObdobi();
        if (urceneCasoveObdobi == null) {
            return true;
        }
        for (int i = 0; i < urceneCasoveObdobi.size(); i++) {
            Node urcenecasoveobdobi = urceneCasoveObdobi.get(i);
            Node nodeDo = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicDo");
            if (nodeDo != null) {
                Node nodeOd = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicOd");
                if (nodeOd == null) {
                    return nastavChybu("Nenalezen element <nsesss:MesicOd>. " + getJmenoIdentifikator(
                                                                                                     urcenecasoveobdobi),
                                       getMistoChyby(urcenecasoveobdobi));
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
