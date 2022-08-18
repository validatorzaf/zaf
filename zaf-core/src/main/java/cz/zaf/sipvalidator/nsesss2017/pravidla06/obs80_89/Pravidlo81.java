package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo81 extends K06PravidloBase {

    static final public String OBS81 = "obs81";

    public Pravidlo81() {
        super(OBS81,
                "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:DatumDo>, potom je jeho hodnota větší než <nsesss:DatumOd>.",
                "Není v souladu rozsah určeného časového období.",
                null);
    }

    //OBSAHOVÁ č.81 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:DatumDo>, 
    // potom je jeho hodnota větší než <nsesss:DatumOd>.
    @Override
    protected void kontrola() {
        List<Element> urceneCasoveObdobi = metsParser.getNodes(NsessV3.URCENE_CASOVE_OBDOBI);
        for (Element elUrcCasObdobi : urceneCasoveObdobi) {
            Element elEntita = kontrola.getEntity(elUrcCasObdobi);
            Element nodeDo = ValuesGetter.getXChild(elUrcCasObdobi, NsessV3.DATUM_DO);
            if (nodeDo != null) {
                Element nodeOd = ValuesGetter.getXChild(elUrcCasObdobi, NsessV3.DATUM_OD);
                if (nodeOd == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:DatumOd>.", elUrcCasObdobi,
                            kontrola.getEntityId(elEntita));
                }
                try {
                    Date dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
                    Date dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
                    if (!dateOd.before(dateDo)) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. OD: " + dateOd + ". DO: " + dateDo + ".",
                                getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                                kontrola.getEntityId(elEntita));
                    }
                } catch (ParseException ex) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnoty dat jsou v nepovoleném formátu.",
                            getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo),
                            kontrola.getEntityId(elEntita));
                }
            }
        }
    }

}
