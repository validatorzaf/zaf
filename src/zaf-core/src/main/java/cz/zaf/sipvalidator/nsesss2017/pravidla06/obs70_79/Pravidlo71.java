package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo71 extends K06PravidloBase {

    public Pravidlo71(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS71,
              "Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.",
                "Není v souladu datum otevření a datum uzavření spisového plánu, věcné skupiny, typového spisu, součásti, dílu nebo spisu.",
                null);
    }

    //OBSAHOVÁ č.71 V každém elementu <nsesss:Manipulace> obsahuje dětský element <nsesss:DatumOtevreni> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje.",
    // Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.
    @Override
    protected boolean kontrolaPravidla() {
        List<Node> manipulace = metsParser.getManipulace();
        if (manipulace == null) {
            return true;
        }
        int size = manipulace.size();
        for (int i = 0; i < size; i++) {
            Node manip_node = manipulace.get(i);
            Node nodeOtevreni = ValuesGetter.findChild(manip_node, "nsesss:DatumOtevreni");
            Node nodeUzavreni = ValuesGetter.findChild(manip_node, "nsesss:DatumUzavreni");
            if (nodeOtevreni != null && nodeUzavreni != null) {
                Date datumOtevreni;
                Date datumZavreni;
                try {
                    datumOtevreni = ValuesGetter.vytvorDate(nodeOtevreni, "yyyy-MM-dd");
                } catch (ParseException ex) {
                    return nastavChybu("Element <nsesss:DatumOtevreni> neobsahuje údaj ve správném formátu. "
                            + getJmenoIdentifikator(manip_node), getMistoChyby(nodeOtevreni));
                }
                try {
                    datumZavreni = ValuesGetter.vytvorDate(nodeUzavreni, "yyyy-MM-dd");
                } catch (ParseException ex) {
                    return nastavChybu("Element <nsesss:DatumUzavreni> neobsahuje údaj ve správném formátu. "
                            + getJmenoIdentifikator(manip_node), getMistoChyby(nodeUzavreni));
                }

                boolean jeToChronologicky = datumZavreni.after(datumOtevreni);
                boolean jeToStejny = datumZavreni.equals(datumOtevreni);
                if (!jeToChronologicky && !jeToStejny) {
                    return nastavChybu("Element <nsesss:DatumOtevreni> obsahuje větší hodnotu než element <nsesss:DatumUzavreni>. "
                            + getJmenoIdentifikator(manip_node), getMistoChyby(nodeOtevreni) + " "
                                    + getMistoChyby(nodeUzavreni));
                }

            }
        }

        return true;
    }

}