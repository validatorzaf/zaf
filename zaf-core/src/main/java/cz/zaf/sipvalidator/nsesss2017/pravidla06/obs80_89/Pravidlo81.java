package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo81 extends K06PravidloBaseOld {

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
    protected boolean kontrolaPravidla() {
        List<Node> urceneCasoveObdobi = metsParser.getNodes(NsessV3.URCENE_CASOVE_OBDOBI);
        for (Node n: urceneCasoveObdobi) {
            Node nodeDo = ValuesGetter.getXChild(n, "nsesss:DatumDo");
            if (nodeDo != null) {
                Node nodeOd = ValuesGetter.getXChild(n, "nsesss:DatumOd");
                if (nodeOd == null) {
                    return nastavChybu("Nenalezen element <nsesss:DatumOd>. " + getJmenoIdentifikator(n),
                                      n);
                }
                try {
                    Date dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
                    Date dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
                    if (!dateOd.before(dateDo)) {
                        return nastavChybu("Nesplněna podmínka pravidla. OD: " + dateOd + ". DO: " + dateDo + ". "
                                + getJmenoIdentifikator(n), getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                    }
                } catch (ParseException ex) {
                    return nastavChybu("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(n),
                                      getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
            }
        }
        return true;
    }

}
