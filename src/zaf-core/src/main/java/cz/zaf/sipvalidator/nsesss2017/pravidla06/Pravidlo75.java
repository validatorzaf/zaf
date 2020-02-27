package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo75 extends K06PravidloBase {

    public Pravidlo75(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS75,
                "Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
                "Není v souladu rozsah platnosti certifikátu elektronického podpisu, elektronické značky nebo časového razítka.",
                null);
    }

    //OBSAHOVÁ č.75 Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList posuzovanyOkamzik = ValuesGetter.getAllAnywhere("nsesss:Platnost", metsParser.getDocument());
        if (posuzovanyOkamzik == null)
            return true;
        for (int i = 0; i < posuzovanyOkamzik.getLength(); i++) {
            Node platnost = posuzovanyOkamzik.item(i);
            Node nodeOd = ValuesGetter.findChild(platnost, "nsesss:PlatnostOd");
            if (nodeOd == null) {
                return nastavChybu("Nenalezen element <nsesss:PlatnostOd>. " + getJmenoIdentifikator(platnost),
                                  getMistoChyby(platnost));
            }
            Node nodeDo = ValuesGetter.findChild(platnost, "nsesss:PlatnostDo");
            if (nodeDo == null) {
                return nastavChybu("Nenalezen element <nsesss:PlatnostDo>. " + getJmenoIdentifikator(platnost),
                                  getMistoChyby(platnost));
            }
            Date od, po;
            try {
                od = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
            } catch (ParseException ex) {
                return nastavChybu("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost),
                                  getMistoChyby(nodeOd));
            }
            try {
                po = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
            } catch (ParseException ex) {
                return nastavChybu("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost),
                                  getMistoChyby(nodeDo));
            }

            if (!(po.after(od) || po.equals(od))) {
                return nastavChybu("Element <nsesss:PlatnostOd> obsahuje větší hodnotu než element <nsesss:PlatnostDo>. "
                        + getJmenoIdentifikator(platnost),
                                   getMistoChyby(nodeOd) + " " + getMistoChyby(nodeDo));
            }
        }
        return true;
    }


}
