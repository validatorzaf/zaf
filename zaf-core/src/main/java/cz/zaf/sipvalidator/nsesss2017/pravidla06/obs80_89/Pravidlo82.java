package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

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
    protected void kontrola() {
        List<Element> urceneCasoveObdobi = metsParser.getNodes(NsessV3.URCENE_CASOVE_OBDOBI);
        for (Element urcenecasoveobdobi : urceneCasoveObdobi) {
            Element nodeDo = ValuesGetter.getXChild(urcenecasoveobdobi, NsessV3.MESIC_DO);
            if (nodeDo != null) {
                Element nodeOd = ValuesGetter.getXChild(urcenecasoveobdobi, NsessV3.MESIC_OD);
                if (nodeOd == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:MesicOd>. " + getJmenoIdentifikator(urcenecasoveobdobi),
                            urcenecasoveobdobi);
                }
                Date dateOd = null, dateDo = null;
                try {
                    dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd", "yyyy-MM");
                    dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd", "yyyy-MM");
                } catch (ParseException ex) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(urcenecasoveobdobi),
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
                if (dateOd == null || dateDo == null) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnoty dat v nepovoleném formátu. " + getJmenoIdentifikator(urcenecasoveobdobi),
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                } else if (!dateOd.before(dateDo)) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. OD: " + dateOd + ". DO: " + dateDo + ". "
                            + getJmenoIdentifikator(urcenecasoveobdobi),
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
            }
        }
    }

}
