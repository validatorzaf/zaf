package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo63 extends K06PravidloBase {

    static final public String OBS63 = "obs63";

    public Pravidlo63() {
        super(OBS63,
                "Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
                "Chybí odůvodnění vyřízení jiným způsobem.",
                "Příloha č. 2 NSESSS, ř. 1270.");
    }

    //OBSAHOVÁ č.63 Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, 
    // potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    @Override
    protected boolean kontrolaPravidla() {
        NodeList vyrizenis = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", metsParser.getDocument());
        NodeList vyrizeniUzavrenis = ValuesGetter.getAllAnywhere("nsesss:VyrizeniUzavreni", metsParser.getDocument());
        if (vyrizenis != null) {
            int size = vyrizenis.getLength();
            for (int i = 0; i < size; i++) {
                Node n = vyrizenis.item(i);
                boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                          "jiný způsob");
                if (maZpusobSHodnotou) {
                    Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                    if (oduvodneni == null) {
                        //                        Node rodic = n.getParentNode().getParentNode();
                        //                        String g = "";
                        //                        if(rodic != null) {
                        //                            g = " " + getJmeno(rodic);
                        //                            g+= " " + getIdentifikatory(rodic) + ".";
                        //                        }
                        return nastavChybu("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n),
                                           n);
                    }
                    if (!HelperString.hasContent(oduvodneni.getTextContent())) {
                        //                        Node rodic = n.getParentNode().getParentNode();
                        //                        String g = "";
                        //                        if(rodic != null) {
                        //                            g = " " + getJmeno(rodic);
                        //                            g+= " " + getIdentifikatory(rodic) + ".";
                        //                        }
                        return nastavChybu("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                      n),
                                           oduvodneni);
                    }
                }
            }
        }
        if (vyrizeniUzavrenis != null) {
            int size = vyrizeniUzavrenis.getLength();
            for (int j = 0; j < size; j++) {
                Node n = vyrizeniUzavrenis.item(j);
                boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob",
                                                                                          "jiný způsob");
                if (maZpusobSHodnotou) {
                    Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                    if (oduvodneni == null) {
                        //                        Node rodic = n.getParentNode().getParentNode();
                        //                        String g = "";
                        //                        if(rodic != null) {
                        //                            g = " " + getJmeno(rodic);
                        //                            g+= " " + getIdentifikatory(rodic) + ".";
                        //                        }
                        return nastavChybu("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n),
                                           n);
                    }
                    if (!HelperString.hasContent(oduvodneni.getTextContent())) {
                        //                        Node rodic = n.getParentNode().getParentNode();
                        //                        String g = "";
                        //                        if(rodic != null) {
                        //                            g = " " + getJmeno(rodic);
                        //                            g+= " " + getIdentifikatory(rodic) + ".";
                        //                        }
                        return nastavChybu("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(
                                                                                                                      n),
                                           oduvodneni);
                    }
                }
            }
        }
        return true;
    }

}
