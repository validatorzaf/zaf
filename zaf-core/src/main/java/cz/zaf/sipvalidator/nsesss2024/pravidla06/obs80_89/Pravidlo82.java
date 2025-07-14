package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

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
    protected void kontrola() {
        List<Element> urceneCasoveObdobi = metsParser.getNodes(NsesssV3.URCENE_CASOVE_OBDOBI);
        for (Element urcenecasoveobdobi : urceneCasoveObdobi) {
            Element elEntita = kontrola.getEntity(urcenecasoveobdobi);
            Element nodeDo = ValuesGetter.getXChild(urcenecasoveobdobi, NsesssV3.MESIC_DO);
            if (nodeDo != null) {
                Element nodeOd = ValuesGetter.getXChild(urcenecasoveobdobi, NsesssV3.MESIC_OD);
                if (nodeOd == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:MesicOd>.", urcenecasoveobdobi,
                            kontrola.getEntityId(elEntita));
                }
                Date dateOd = null, dateDo = null;
                try {
                    dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd", "yyyy-MM");
                    dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd", "yyyy-MM");
                } catch (ParseException ex) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnoty dat jsou v nepovoleném formátu.",
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                            kontrola.getEntityId(elEntita));
                }
                if (dateOd == null || dateDo == null) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnoty dat v nepovoleném formátu.",
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                            kontrola.getEntityId(elEntita));
                } else if (!dateOd.before(dateDo)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. OD: " + dateOd + ". DO: " + dateDo + ".",
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                            kontrola.getEntityId(elEntita));
                }
            }
        }
    }

}
