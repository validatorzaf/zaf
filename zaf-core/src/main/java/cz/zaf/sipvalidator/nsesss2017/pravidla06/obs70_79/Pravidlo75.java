package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import java.util.ArrayList;

public class Pravidlo75 extends K06PravidloBase {

    static final public String OBS75 = "obs75";

    public Pravidlo75() {
        super(OBS75,
                "Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
                "Není v souladu rozsah platnosti certifikátu elektronického podpisu, elektronické značky nebo časového razítka.",
                null);
    }

    //OBSAHOVÁ č.75 Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
    @Override
    protected void kontrola() {
        List<Element> posuzovanyOkamzik = metsParser.getNodes(NsessV3.PLATNOST);
        for (Element platnost : posuzovanyOkamzik) {
            Element elEntita = kontrola.getEntity(platnost);
            Element nodeOd = ValuesGetter.findFirstChild(platnost, NsessV3.PLATNOST_OD);
            if (nodeOd == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlatnostOd>.", platnost,
                        kontrola.getEntityId(elEntita));
            }
            Element nodeDo = ValuesGetter.findFirstChild(platnost, NsessV3.PLATNOST_DO);
            if (nodeDo == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlatnostDo>.", platnost,
                        kontrola.getEntityId(elEntita));
            }
            Date od = null;
            Date po = null;
            try {
                od = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
            } catch (ParseException ex) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota data je ve špatném formátu.", platnost,
                        kontrola.getEntityId(elEntita));
            }
            try {
                po = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
            } catch (ParseException ex) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Hodnota data je ve špatném formátu.", platnost,
                        kontrola.getEntityId(elEntita));
            }

            if (po != null) {
                if (!(po.after(od) || po.equals(od))) {
                    List<Element> list = new ArrayList<>();
                    list.add(platnost);
                    list.add(nodeOd);
                    list.add(nodeDo);
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:PlatnostOd> obsahuje větší hodnotu než element <nsesss:PlatnostDo>.", list,
                            kontrola.getEntityId(elEntita));
                }
            }
        }
    }

}
