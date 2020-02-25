package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo83 extends K06PravidloBase {

    public Pravidlo83(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS83,
                "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>.",
                "Není v souladu rozsah určeného časového období.",
                null);
    }

    //OBSAHOVÁ č.83 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element 
    // <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>."
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> urceneCasoveObdobi = metsParser.getUrceneCasoveObdobi();
        if (urceneCasoveObdobi == null)
            return true;
        for (int i = 0; i < urceneCasoveObdobi.size(); i++) {
            Node ucobdobi = urceneCasoveObdobi.get(i);
            Node nodeDo = ValuesGetter.getXChild(ucobdobi, "nsesss:RokDo");
            if (nodeDo != null) {
                Node nodeOd = ValuesGetter.getXChild(ucobdobi, "nsesss:RokOd");
                if (nodeOd == null) {
                    return nastavChybu("Nenalezen element <nsesss:RokOd>. " + getJmenoIdentifikator(ucobdobi),
                                      getMistoChyby(ucobdobi));
                }
                boolean b = (ValuesGetter.overSpravnostRetezceProInt(nodeDo.getTextContent()) && ValuesGetter
                        .overSpravnostRetezceProInt(nodeDo.getTextContent()));
                if (!b) {
                    return nastavChybu("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(ucobdobi),
                                       getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
                }
                String d1 = nodeOd.getTextContent().substring(0, 4);
                String d2 = nodeDo.getTextContent().substring(0, 4);
                int intOd = Integer.parseInt(d1);
                int intDo = Integer.parseInt(d2);
                if (!(intOd < intDo)) {
                    return nastavChybu("Nesplněna podmínka pravidla. OD: " + intOd + ". DO: " + intDo + ". "
                            + getJmenoIdentifikator(ucobdobi), getMistoChyby(nodeOd) + " " + getMistoChyby(
                                                                                                                  nodeDo));
                }
            }
        }
        return true;
    }

}
