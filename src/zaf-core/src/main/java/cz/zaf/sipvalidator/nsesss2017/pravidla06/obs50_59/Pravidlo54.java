package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
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
        List<Node> metsDiv = metsParser.getNodes(MetsElements.DIV);
        
        Map<String, Node> amdIdMap = new HashMap<>();
        List<Node> metsAmd = metsParser.getNodes(MetsElements.AMD_SEC);
        addToIdMap(metsAmd, amdIdMap);
        
        Map<String, Node> nsesssIdMap = new HashMap<>();        
        
        List<Node> spisoveplany = metsParser.getNodes(NsessV3.SPISOVY_PLAN);
        addToIdMap(spisoveplany, nsesssIdMap);
        List<Node> vecneskupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
        addToIdMap(vecneskupiny, nsesssIdMap);
        List<Node> soucasti = metsParser.getNodes(NsessV3.SOUCAST);
        addToIdMap(soucasti, nsesssIdMap);
        List<Node> typovespisy = metsParser.getNodes(NsessV3.TYPOVY_SPIS);
        addToIdMap(typovespisy, nsesssIdMap);
        List<Node> spisy = metsParser.getNodes(NsessV3.SPIS);
        addToIdMap(spisy, nsesssIdMap);
        List<Node> dily = metsParser.getNodes(NsessV3.DIL);
        addToIdMap(dily, nsesssIdMap);
        List<Node> dokumenty = metsParser.getNodes(NsessV3.DOKUMENT);
        addToIdMap(dokumenty, nsesssIdMap);
        List<Node> komponenty = metsParser.getNodes(NsessV3.KOMPONENTA);
        addToIdMap(komponenty, nsesssIdMap);
        
        int nsesssSize = spisoveplany.size() + vecneskupiny.size() + soucasti.size()
            + typovespisy.size() + spisy.size() + dily.size() 
            + dokumenty.size() + komponenty.size();     

        int size_div = metsDiv.size();
        for (int i = 0; i < size_div; i++) {
            Node n_div = metsDiv.get(i);
            String dmdid = ValuesGetter.getValueOfAttribut(n_div, JmenaElementu.DMDID);
            if (StringUtils.isEmpty(dmdid)) {
                return nastavChybu("Element <mets:div> nemá atribut DMDID.", n_div);
            }
            String admid = ValuesGetter.getValueOfAttribut(n_div, "ADMID");
            if (StringUtils.isEmpty(admid)) {
                return nastavChybu("Element <mets:div> nemá atribut ADMID.", n_div);
            }
            String type = ValuesGetter.getValueOfAttribut(n_div, "TYPE");
            if(StringUtils.isEmpty(type)) {
                return nastavChybu("Element <mets:div> nemá atribut TYPE.", n_div);                
            }            

            // hledám odkaz na element v dmdSec
            Node node_dmd = nsesssIdMap.get(dmdid);            
            if (node_dmd == null) {
                return nastavChybu("Nenalezen platný odkazovaný element ze sekce mets:dmdSec.",
                                   n_div);
            }
            if (!ValuesGetter.get_type_to_nsesss(type).equals(node_dmd.getNodeName())) {
                return nastavChybu("Element mets:div s type " + type
                        + ", odkazuje na element jiného typu.", getMistoChyby(n_div)
                                + " " + getMistoChyby(node_dmd));
            }
            
            // porovnat div s dmd
            Node identifikator;
            if (node_dmd.getNodeName().equals("nsesss:SpisovyPlan")) {
                identifikator = ValuesGetter.getXChild(node_dmd, "nsesss:Identifikator");
            } else {
                identifikator = ValuesGetter.getXChild(node_dmd, NsessV3.EVIDENCNI_UDAJE,
                                                       "nsesss:Identifikace", "nsesss:Identifikator");
            }
            if (identifikator == null) {
                return nastavChybu("Nenalezen element nsesss:Identifikator.", node_dmd);
            }
            
            // ?? Tato kontrola asi patri jinam
            if (!ValuesGetter.hasAttribut(identifikator, "zdroj")) {
                return nastavChybu("Nenalezen atribut zdroj elementu nsesss:Identifikator.",
                                   node_dmd);
            }

            Node node_amd = amdIdMap.get(admid);
            if (node_amd == null) {
                return nastavChybu("Nenalezen element v sekci mets:amdSec s příslušným ID.",
                                   n_div);            
            }
            Node hodnotaId = ValuesGetter.getXChild(node_amd, "mets:digiprovMD",
                                                    "mets:mdWrap", "mets:xmlData",
                                                    "tp:TransakcniLogObjektu",
                                                    "tp:TransLogInfo", "tp:Objekt",
                                                    "tp:Identifikator", "tns:HodnotaID");
            if (hodnotaId == null) {
                return nastavChybu("Nenalezen element tns:HodnotaID v sekci mets:amdSec.",
                                   node_amd);
            }
            
            Node zdrojID = ValuesGetter.getXChild(node_amd, "mets:digiprovMD",
                                                  "mets:mdWrap", "mets:xmlData",
                                                  "tp:TransakcniLogObjektu",
                                                  "tp:TransLogInfo", "tp:Objekt",
                                                  "tp:Identifikator", "tns:ZdrojID");
            if (zdrojID == null) {
                return nastavChybu("Nenalezen element tns:ZdrojID v sekci mets:amdSec.",
                                   node_amd);
            }
            
            String hodnota_identifikator = identifikator.getTextContent();
            String hodnota_zdroj = ValuesGetter.getValueOfAttribut(identifikator,
                                                                   "zdroj");
            String amd_identifikator = hodnotaId.getTextContent();
            String amd_zdroj = zdrojID.getTextContent();

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
                    return nastavChybu("Element mets:div nemá správný rodičovský element.", n_div);
                }
            } else {
                if (!rodic_div.getNodeName().equals("mets:div")) {
                    return nastavChybu("Element mets:div nemá správný rodičovský element.", n_div);
                }
                String ro_dmdid = ValuesGetter.getValueOfAttribut(rodic_div, "DMDID");
                if (StringUtils.isEmpty(ro_dmdid)) {
                    return nastavChybu("Element <mets:div> nemá atribut DMDID.", rodic_div);
                }
                Node node_dmd_ro = nsesssIdMap.get(ro_dmdid);
                if (node_dmd_ro == null) {
                    return nastavChybu("Nenalezen element v sekci mets:dmdSec s příslušným ID.", rodic_div);                    
                }
                
                String ro_type = ValuesGetter.getValueOfAttribut(rodic_div, "TYPE");
                if (StringUtils.isEmpty(ro_type)) {
                    return nastavChybu("Element <mets:div> nemá atribut TYPE.", rodic_div);
                }
                
                String ro_node_type = ValuesGetter.get_type_to_nsesss(ro_type);
                String nodetype = ValuesGetter.get_type_to_nsesss(type);
                if (!ValuesGetter.parentCheck(nodetype, ro_node_type)) {
                    return nastavChybu("Element mets:div je špatně zatříděn. Neodpovídající rodičovský element.",
                                       getMistoChyby(n_div) + " " + getMistoChyby(rodic_div));
                }
            }
        }

        if (nsesssSize != metsDiv.size()) {
            return nastavChybu("Počet elementů <mets:div> neodpovídá počtu elementů v dmdSec.");
        }

        if (!pravidlo54_pocitadlo_amdsec(metsDiv.size())) {
            return nastavChybu("Počet elementů <mets:div> neodpovídá počtu elementů v amdSec.");
        }
        return true;
    }

    private void addToIdMap(List<Node> nodes, Map<String, Node> nsesssIdMap) {
        for(Node node: nodes) {
            String idValue = ValuesGetter.getValueOfAttribut(node, "ID");
            if(StringUtils.isNotEmpty(idValue)) {
                nsesssIdMap.put(idValue, node);
            }
        }
        
    }

    private boolean pravidlo54_pocitadlo_amdsec(int pocet_div) {
        List<Node> list = metsParser.getNodes(MetsElements.AMD_SEC);
        if (list == null)
            return false;
        int pocetAmd = list.size();
        boolean vysledek = pocetAmd == pocet_div;
        return vysledek;
    }

}
