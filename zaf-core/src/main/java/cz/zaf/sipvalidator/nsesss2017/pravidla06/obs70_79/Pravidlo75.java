package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo75 extends K06PravidloBaseOld {

    static final public String OBS75 = "obs75";

    public Pravidlo75() {
        super(OBS75,
                "Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
                "Není v souladu rozsah platnosti certifikátu elektronického podpisu, elektronické značky nebo časového razítka.",
                null);
    }

    //OBSAHOVÁ č.75 Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
    @Override
    protected boolean kontrolaPravidla() {        
        List<Node> posuzovanyOkamzik = metsParser.getNodes(NsessV3.PLATNOST);
        for (Node platnost: posuzovanyOkamzik) {
            Node nodeOd = ValuesGetter.findFirstChild(platnost, "nsesss:PlatnostOd");
            if (nodeOd == null) {
                return nastavChybu("Nenalezen element <nsesss:PlatnostOd>. " + getJmenoIdentifikator(platnost),
                                  platnost);
            }
            Node nodeDo = ValuesGetter.findFirstChild(platnost, "nsesss:PlatnostDo");
            if (nodeDo == null) {
                return nastavChybu("Nenalezen element <nsesss:PlatnostDo>. " + getJmenoIdentifikator(platnost),
                                  platnost);
            }
            Date od, po;
            try {
                od = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
            } catch (ParseException ex) {
                return nastavChybu("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost),
                                  nodeOd);
            }
            try {
                po = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
            } catch (ParseException ex) {
                return nastavChybu("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost),
                                  nodeDo);
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
