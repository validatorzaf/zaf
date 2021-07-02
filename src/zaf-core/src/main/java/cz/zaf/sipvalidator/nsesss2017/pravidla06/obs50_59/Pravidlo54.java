package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.54 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, 
// potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) 
// v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu 
// v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> 
// odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec).
public class Pravidlo54 extends K06PravidloBase {

    static final public String OBS54 = "obs54";

    public Pravidlo54() {
        super(OBS54,
                "Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec).",
                "Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol.",
                "Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3).");
    }

    @Override
    protected boolean kontrolaPravidla() {
        List<Node> pevneKrizoveOdkazy = kontrola.getMetsParser().getKrizoveOdkazyPevnyAno();
        if (!pevneKrizoveOdkazy.isEmpty()) {
            return true;
        }
        ArrayList<Node> metsDiv = ValuesGetter.getAllAnywhereList("mets:div", metsParser.getDocument());
        ArrayList<Node> metsAmd = ValuesGetter.getAllAnywhereList("mets:amdSec", metsParser.getDocument());
        ArrayList<Node> spisoveplany = ValuesGetter.getAllAnywhereList("nsesss:SpisovyPlan", metsParser
                .getDocument());
        ArrayList<Node> vecneskupiny = ValuesGetter.getAllAnywhereList("nsesss:VecnaSkupina", metsParser
                .getDocument());
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereList("nsesss:Soucast", metsParser.getDocument());
        ArrayList<Node> typovespisy = ValuesGetter.getAllAnywhereList("nsesss:TypovySpis", metsParser
                .getDocument());
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereList("nsesss:Spis", metsParser.getDocument());
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereList("nsesss:Dil", metsParser.getDocument());
        //        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", metsParser.getDocument());
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereList("nsesss:Komponenta", metsParser
                .getDocument());
        int size_div = metsDiv.size();
        for (int i = 0; i < size_div; i++) {
            Node n_div = metsDiv.get(i);
            String dmdid = ValuesGetter.getValueOfAttribut(n_div, JmenaElementu.DMDID);
            if (StringUtils.isEmpty(dmdid)) {
                return nastavChybu("Element <mets:div> nemá atribut DMDID.", getMistoChyby(n_div));
            }
            String admid = ValuesGetter.getValueOfAttribut(n_div, "ADMID");
            if (StringUtils.isEmpty(admid)) {
                return nastavChybu("Element <mets:div> nemá atribut ADMID.", getMistoChyby(n_div));
            }
            String type = ValuesGetter.getValueOfAttribut(n_div, "TYPE");
            if(StringUtils.isEmpty(type)) {
                return nastavChybu("Element <mets:div> nemá atribut TYPE.", getMistoChyby(n_div));                
            }
            String nodetype = ValuesGetter.get_type_to_nsesss(type);

            Node node_dmd = null;
            //hledám odkaz na element v dmdSec
            if (type.equals("spisový plán")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisoveplany, "ID", dmdid);
            }
            if (type.equals("věcná skupina")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(vecneskupiny, "ID", dmdid);
            }
            if (type.equals("součást")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(soucasti, "ID", dmdid);
            }
            if (type.equals("typový spis")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(typovespisy, "ID", dmdid);
            }
            if (type.equals("spis")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisy, "ID", dmdid);
            }
            if (type.equals("díl")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(dily, "ID", dmdid);
            }
            if (type.equals("dokument")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(metsParser.getDokumenty(),
                                                                                "ID", dmdid);
            }
            if (type.equals("komponenta")) {
                node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(komponenty, "ID", dmdid);
            }
            if (node_dmd == null) {
                return nastavChybu("Nenalezen element v sekci mets:dmdSec s příslušným ID.",
                                   getMistoChyby(n_div));
            }

            // porovnat div s dmd
            Node identifikator;
            if (node_dmd.getNodeName().equals("nsesss:SpisovyPlan")) {
                identifikator = ValuesGetter.getXChild(node_dmd, "nsesss:Identifikator");
            } else {
                identifikator = ValuesGetter.getXChild(node_dmd, "nsesss:EvidencniUdaje",
                                                       "nsesss:Identifikace", "nsesss:Identifikator");
            }
            if (identifikator != null) {
                if (ValuesGetter.hasAttribut(identifikator, "zdroj")) {
                    Node node_amd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(metsAmd, "ID",
                                                                                         admid);
                    if (node_amd != null) {
                        Node hodnotaId = ValuesGetter.getXChild(node_amd, "mets:digiprovMD",
                                                                "mets:mdWrap", "mets:xmlData",
                                                                "tp:TransakcniLogObjektu",
                                                                "tp:TransLogInfo", "tp:Objekt",
                                                                "tp:Identifikator", "tns:HodnotaID");
                        Node zdrojID = ValuesGetter.getXChild(node_amd, "mets:digiprovMD",
                                                              "mets:mdWrap", "mets:xmlData",
                                                              "tp:TransakcniLogObjektu",
                                                              "tp:TransLogInfo", "tp:Objekt",
                                                              "tp:Identifikator", "tns:ZdrojID");
                        if (hodnotaId != null) {
                            if (zdrojID != null) {
                                String hodnota_identifikator = identifikator.getTextContent();
                                String hodnota_zdroj = ValuesGetter.getValueOfAttribut(identifikator,
                                                                                       "zdroj");
                                String amd_identifikator = hodnotaId.getTextContent();
                                String amd_zdroj = zdrojID.getTextContent();

                                if (!ValuesGetter.get_type_to_nsesss(type).equals(node_dmd
                                        .getNodeName())) {
                                    return nastavChybu("Element mets:div s type " + type
                                            + ", odkazuje na element jiného typu.", getMistoChyby(n_div)
                                                    + " " + getMistoChyby(node_dmd));
                                }
                                if (!hodnota_identifikator.equals(amd_identifikator)) {
                                    return nastavChybu("Neshodují se hodnoty identifikátorů. "
                                            + getJmenoIdentifikator(identifikator), getMistoChyby(
                                                                                                  identifikator)
                                                    + " " + getMistoChyby(hodnotaId));
                                }
                                if (!hodnota_zdroj.equals(amd_zdroj)) {
                                    return nastavChybu("Neshodují se hodnoty zdrojů. "
                                            + getJmenoIdentifikator(identifikator), getMistoChyby(
                                                                                                  identifikator)
                                                    + " " + getMistoChyby(zdrojID));
                                }
                                // zkontrolovat rodče
                                Node rodic_div = n_div.getParentNode();
                                if (type.equals("spisový plán")) {
                                    if (!rodic_div.getNodeName().equals("mets:structMap")) {
                                        return nastavChybu("Element mets:div nemá správný rodičovský element.",
                                                           getMistoChyby(n_div));
                                    }
                                } else {
                                    if (!rodic_div.getNodeName().equals("mets:div")) {
                                        return nastavChybu("Element mets:div nemá správný rodičovský element.",
                                                           getMistoChyby(n_div));
                                    }
                                    if (ValuesGetter.hasAttribut(rodic_div, "DMDID")) {
                                        if (ValuesGetter.hasAttribut(rodic_div, "TYPE")) {
                                            String ro_dmdid = ValuesGetter.getValueOfAttribut(rodic_div,
                                                                                              "DMDID");
                                            String ro_type = ValuesGetter.getValueOfAttribut(rodic_div,
                                                                                             "TYPE");
                                            String ro_node_type = ValuesGetter.get_type_to_nsesss(
                                                                                                  ro_type);
                                            //                                                            Node node_dmd_ro = ValuesGetter.getNodeWithID(ro_dmdid, ro_node_type, parsedDOM_SipSoubor);
                                            Node node_dmd_ro = null;
                                            if (ro_type.equals("spisový plán")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(spisoveplany,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("věcná skupina")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(vecneskupiny,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("součást")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(soucasti,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("typový spis")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(typovespisy,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("spis")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(spisy,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("díl")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(dily,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("dokument")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(metsParser
                                                                .getDokumenty(),
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (ro_type.equals("komponenta")) {
                                                node_dmd_ro = ValuesGetter
                                                        .getNodeByValueOfAtributFromSpecificList(komponenty,
                                                                                                 "ID",
                                                                                                 ro_dmdid);
                                            }
                                            if (node_dmd_ro != null) {
                                                if (!ValuesGetter.parentCheck(nodetype, ro_node_type)) {
                                                    return nastavChybu("Element mets:div je špatně zatříděn. Neodpovídající rodičovský element.",
                                                                       getMistoChyby(n_div) + " "
                                                                               + getMistoChyby(rodic_div));
                                                }
                                            } else
                                                return nastavChybu("Nenalezen element v sekci mets:dmdSec s příslušným ID.",
                                                                   getMistoChyby(rodic_div));
                                        } else
                                            return nastavChybu("Element <mets:div> nemá atribut TYPE.",
                                                               getMistoChyby(rodic_div));
                                    } else
                                        return nastavChybu("Element <mets:div> nemá atribut DMDID.",
                                                           getMistoChyby(rodic_div));
                                }
                            } else
                                return nastavChybu("Nenalezen element tns:ZdrojID v sekci mets:amdSec.",
                                                   getMistoChyby(node_amd));
                        } else
                            return nastavChybu("Nenalezen element tns:HodnotaID v sekci mets:amdSec.",
                                               getMistoChyby(node_amd));
                    } else
                        return nastavChybu("Nenalezen element v sekci mets:amdSec s příslušným ID.",
                                           getMistoChyby(n_div));
                } else
                    return nastavChybu("Nenalezen atribut zdroj elementu nsesss:Identifikator.",
                                       getMistoChyby(node_dmd));
            } else
                return nastavChybu("Nenalezen element nsesss:Identifikator.", getMistoChyby(node_dmd));
        }

        if (pravidlo54_pocitadlo() != metsDiv.size()) {
            return nastavChybu("Počet elementů <mets:div> neodpovídá počtu elementů v dmdSec.");
        }

        if (!pravidlo54_pocitadlo_amdsec(metsDiv.size())) {
            return nastavChybu("Počet elementů <mets:div> neodpovídá počtu elementů v amdSec.");
        }
        return true;
    }

    private int pravidlo54_pocitadlo() {
        int a = 0;
        ArrayList<Node> plany = ValuesGetter.getAllAnywhereList("nsesss:SpisovyPlan", metsParser.getDocument());
        if (plany != null)
            a = pravidlo56upresneniPocitadla(plany);
        ArrayList<Node> skupiny = ValuesGetter.getAllAnywhereList("nsesss:VecnaSkupina", metsParser.getDocument());
        if (skupiny != null)
            a += pravidlo56upresneniPocitadla(skupiny);
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereList("nsesss:Soucast", metsParser.getDocument());
        if (soucasti != null)
            a += pravidlo56upresneniPocitadla(soucasti);
        ArrayList<Node> typoveSpisy = ValuesGetter.getAllAnywhereList("nsesss:TypovySpis", metsParser
                .getDocument());
        if (typoveSpisy != null)
            a += pravidlo56upresneniPocitadla(typoveSpisy);
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereList("nsesss:Spis", metsParser.getDocument());
        if (spisy != null)
            a += pravidlo56upresneniPocitadla(spisy);
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereList("nsesss:Dil", metsParser.getDocument());
        if (dily != null)
            a += pravidlo56upresneniPocitadla(dily);
        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereList("nsesss:Dokument", metsParser.getDocument());
        if (dokumenty != null)
            a += pravidlo56upresneniPocitadla(dokumenty);
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereList("nsesss:Komponenta", metsParser
                .getDocument());
        if (komponenty != null)
            a += pravidlo56upresneniPocitadla(komponenty);

        return a;
    }

    private boolean pravidlo54_pocitadlo_amdsec(int pocet_div) {
        NodeList list = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if (list == null)
            return false;
        int pocetAmd = list.getLength();
        boolean vysledek = pocetAmd == pocet_div;
        return vysledek;
    }

    private int pravidlo56upresneniPocitadla(ArrayList<Node> list) {
        ArrayList<String> idcka = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Node n = list.get(i);
            String idn = ValuesGetter.getValueOfAttribut(n, "ID");
            if (!idcka.contains(idn)) {
                idcka.add(idn);
            }
        }

        return idcka.size();
    }

}
