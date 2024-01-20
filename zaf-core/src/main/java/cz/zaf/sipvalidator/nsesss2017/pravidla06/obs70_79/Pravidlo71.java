package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo71 extends K06PravidloBase {

    static final public String OBS71 = "obs71";

    public Pravidlo71() {
        super(OBS71,
                "Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.",
                "Není v souladu datum otevření a datum uzavření spisového plánu, věcné skupiny, typového spisu, součásti, dílu nebo spisu.",
                null);
    }

    //OBSAHOVÁ č.71 V každém elementu <nsesss:Manipulace> obsahuje dětský element <nsesss:DatumOtevreni> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje.",
    // Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.
    @Override
    protected void kontrola() {
        List<Element> manipulace = metsParser.getNodes(NsessV3.MANIPULACE);
        for (Element manip_node : manipulace) {
            Element nodeOtevreni = ValuesGetter.findFirstChild(manip_node, NsessV3.DATUM_OTEVRENI);
            Element nodeUzavreni = ValuesGetter.findFirstChild(manip_node, NsessV3.DATUM_UZAVRENI);
            if (nodeOtevreni != null && nodeUzavreni != null) {
                Element entita = kontrola.getEntity(manip_node);
                Date datumOtevreni = null;
                Date datumZavreni = null;
                try {
                    datumOtevreni = ValuesGetter.vytvorDate(nodeOtevreni, "yyyy-MM-dd");
                } catch (ParseException ex) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:DatumOtevreni> neobsahuje údaj ve správném formátu. "
                            + getJmenoIdentifikator(manip_node), nodeOtevreni, kontrola.getEntityId(entita));
                }
                try {
                    datumZavreni = ValuesGetter.vytvorDate(nodeUzavreni, "yyyy-MM-dd");
                } catch (ParseException ex) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:DatumUzavreni> neobsahuje údaj ve správném formátu. "
                            + getJmenoIdentifikator(manip_node), nodeUzavreni, kontrola.getEntityId(entita));
                }

                boolean jeToChronologicky = datumZavreni.after(datumOtevreni);
                boolean jeToStejny = datumZavreni.equals(datumOtevreni);
                if (!jeToChronologicky && !jeToStejny) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:DatumOtevreni> obsahuje větší hodnotu než element <nsesss:DatumUzavreni>. "
                            + getJmenoIdentifikator(manip_node),
                            getMistoChyby(nodeOtevreni) + " " + getMistoChyby(nodeUzavreni), kontrola.getEntityId(entita));
                }
            }
        }
    }

}
