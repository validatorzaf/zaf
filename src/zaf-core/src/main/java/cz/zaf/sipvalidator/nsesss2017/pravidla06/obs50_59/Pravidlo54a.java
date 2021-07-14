package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.structmap.PairZdrojIdent;

// OBSAHOVÁ č.54a Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, 
// potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou 
// příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci 
// amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, 
// <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> 
// odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), 
// přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena 
// v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).
public class Pravidlo54a  extends K06PravidloBase {

    static final public String OBS54A = "obs54a";
       
    static public class AmdSecInfo {
        String id;
        Node node;
        PairZdrojIdent pzi;

        public AmdSecInfo(final String id, final PairZdrojIdent pzi, final Node node) {
            this.id = id;
            this.pzi = pzi;
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }

    static public class MetsDivInfo {
        Node metsdiv;
        private MetsDivInfo parent;

        public MetsDivInfo(Node metsdiv) {
            this.metsdiv = metsdiv;
        }
        
        String getDmdId() {
            return ValuesGetter.getValueOfAttribut(metsdiv, "DMDID");
        }
        
        String getAdmId() {
            return ValuesGetter.getValueOfAttribut(metsdiv, "ADMID");
        }

        String getType() {
            return ValuesGetter.getValueOfAttribut(metsdiv, "TYPE");
        }
        
        Node getParentNode() {
            return metsdiv.getParentNode();
        }

        public Node getMetsDiv() {
            return metsdiv;
        }

        public void setParent(final MetsDivInfo parent) {
            this.parent = parent;            
        }
        public MetsDivInfo getParent() {
            return parent;
        }
    }
    
    static public class DmdSecInfo {
        private String id;
        //, type;
        Node node;
        
        Node parentEntity;
        PairZdrojIdent pzi;

        private DmdSecInfo(final String id, final Node node) {
            // this.type = type;
            this.id = id;
            this.node = node;            
        }
        
        static DmdSecInfo valueOf(Node node) {
            String id = ValuesGetter.getValueOfAttribut(node, "ID");
            if (StringUtils.isEmpty(id)) {
                return null;
            }
            DmdSecInfo dmdSecInfo = new DmdSecInfo(id, node);
            switch (node.getNodeName()) {
            case "nsesss:SpisovyPlan":
                if (!dmdSecInfo.readZdrojInfo()) {
                    return null;
                }
                break;
            case "nsesss:VecnaSkupina":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                if (!dmdSecInfo.initParentVecnaSkupina()) {
                    return null;
                }
                break;
            case "nsesss:Dokument":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                if (!dmdSecInfo.initParentDokument()) {
                    return null;
                }
                break;
            case "nsesss:Spis":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                dmdSecInfo.parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                                 "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                break;
            case "nsesss:Komponenta":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                dmdSecInfo.parentEntity = node.getParentNode().getParentNode();
                break;
            case "nsesss:Soucast":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                dmdSecInfo.parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                                 "nsesss:MaterskaEntita", "nsesss:TypovySpis");
                break;
            case "nsesss:TypovySpis":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                dmdSecInfo.parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                                 "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                break;
            case "nsesss:Dil":
                if (!dmdSecInfo.readEvidUdajeIdentifikator()) {
                    return null;
                }
                dmdSecInfo.parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                                 "nsesss:MaterskaEntita", "nsesss:Soucast");
                break;
            default:
                return null;
            }
            return dmdSecInfo;
        }
        
        private boolean readIdent(Node identNode) {
            if(identNode==null) {
                return false;
            }
            String zdroj = ValuesGetter.getValueOfAttribut(identNode, "zdroj");
            if(StringUtils.isEmpty(zdroj)) {
                return false;
            }
            String identifikator = identNode.getTextContent(); 
            if(StringUtils.isEmpty(identifikator)) {
                return false;
            }
            pzi = new PairZdrojIdent(zdroj, identifikator);
            return true;
        }

        private boolean readZdrojInfo() {
            Node identNode = ValuesGetter.findFirstChild(node, "nsesss:Identifikator");            
            return readIdent(identNode);
        }
        
        private boolean readEvidUdajeIdentifikator() {
            Node identNode = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
            return readIdent(identNode);
        }
                                
        private boolean initParentVecnaSkupina() {
            parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
            // rodic_type = "věcná skupina";
            if(parentEntity == null){
                parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
                if(parentEntity == null){
                    parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
                    //rodic_type = "spisový plán";
                    if(parentEntity==null) {
                        return false;
                    }                    
                }
            }
            return true;
        }

        private boolean initParentDokument() {
            if(node.getParentNode().getNodeName().equals("mets:xmlData")){
                parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                // rodic_type = "věcná skupina";
                if(parentEntity == null){
                    parentEntity = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
                    if(parentEntity==null) {
                        return false;
                    }
                }
            } else {
                parentEntity = node.getParentNode().getParentNode();
                if(parentEntity==null) {
                    return false;
                }
                /*
                if(parentEntity.getNodeName().equals("nsesss:Spis")){
                 
                    rodic_type = "spis";
                } else {
                    rodic_type = "díl";
                }*/
            }            
            return true;
        }
        
        public Node getNode() {
            return node;
        }

        public String getId() {
            return id;
        }
        
        public Node getParentEntity() {
            return parentEntity;
        }

        public String getParentEntityId() {
            if(parentEntity==null) {
                return null;                
            }
            return ValuesGetter.getValueOfAttribut(parentEntity, "ID");
        }
    }
    

    public Pravidlo54a() {
    	super(OBS54A,
    			"Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).",
    			"Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol nebo je struktura uvedena chybně s ohledem na existenci pevného křížového odkazu.",    			
    			"Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3)."
    			);
    }
    
    List<AmdSecInfo> amdSecList;
    Map<String, AmdSecInfo> amdSecMap;
    
    List<MetsDivInfo> metsDivList;
    Map<String, MetsDivInfo> metsDivAmdMap;
    Map<String, MetsDivInfo> metsDivDmdMap;
    
    private List<DmdSecInfo> dmdSecList;
    private Map<String, DmdSecInfo> dmdSecMap;
    private Map<PairZdrojIdent, List<DmdSecInfo> > dmdSecPziMap;

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> pevneKrizoveOdkazy = metsParser.getKrizoveOdkazyPevnyAno();
        if(pevneKrizoveOdkazy.isEmpty()) {
            return true;
        }
        // TEST mets:amdSec
        if(!readAmdSecList()) {
            return false;
        }
        Validate.notNull(amdSecList);
        
        // TEST mets_div
        if(!readMetsDivList()) {
            return false;
        }
        // KONEC TESTU mets:div
        
        // TEST dmdSec
        if(!readDmdSecList()) {
            return false;
        }
        
        // TEST amdSec to metsdiv
        if(!compare_amdSec_with_metsDiv()) {
            return false;
        }
        
        //TEST AMD TO DMDSEC
        if(!compareAmdSecWithDmdSec()) {
            return false;
        }
                
        //TEST STRUKTURY PODLE METS DIV
        if(!compareMetsDivWithDmdSec()) {
            return false;
        }
        //KONEC TESTU STRUKTURY
        
        return true;
	}
	
    public boolean compare_amdSec_with_metsDiv() {
        ArrayList<Node> errorList = new ArrayList<>(0);

        for(AmdSecInfo amdSec: amdSecList) {
            // kontrola existence zaznamu v metsDiv
            if(!this.metsDivAmdMap.containsKey(amdSec.id)) {
                errorList.add(amdSec.getNode());
            }
        }
        
        if(!errorList.isEmpty()){
            return nastavChybu("Element <mets:amdSec> neodkazuje na žádný element <mets:div>.", errorList);
        }        
        return true;
    }
        
    // na prvním místě v seznamu je amdSec a na dalších ty metsy který se dublovaly
    public boolean compareAmdSecWithDmdSec() {
        ArrayList<Node> errorList = new ArrayList<>(0);
        ArrayList<Node> missingAmdSec = new ArrayList<>(0);
        
        for(MetsDivInfo metsDivInfo: metsDivList) {
            AmdSecInfo amdInfo = this.amdSecMap.get(metsDivInfo.getAdmId());
            if(amdInfo==null) {
                missingAmdSec.add(metsDivInfo.metsdiv);
                continue;
            }
            DmdSecInfo dmdInfo = this.dmdSecMap.get(metsDivInfo.getDmdId());
            if(amdInfo==null||dmdInfo==null) {
                errorList.add(metsDivInfo.metsdiv);
                continue;
            }
            if(!Objects.equals(amdInfo.pzi, dmdInfo.pzi)) {
                errorList.add(amdInfo.node);
                errorList.add(dmdInfo.node);
                continue;
            }
        }
        
        if(missingAmdSec.size()>0) {
            return nastavChybu("Element <mets:div> neodkazuje na žádný element <mets:amdSec>.", missingAmdSec);        
        }
        if(errorList.size()>0) {
            return nastavChybu("Elementy v <mets:dmdSec> a <mets:amdSec> na sebe neodkazují.", errorList);
        }
        
        return true;
    }
    
    /**
     * Kontrola shodnosti rodicu
     * 
     * Pri kontrola je porovnano ID rodicu
     * @return
     */
    public boolean compareMetsDivWithDmdSec() 
    {
        ArrayList<Node> errorList = new ArrayList<>(0);
        
        for(MetsDivInfo div: metsDivList) {
            DmdSecInfo dmd = dmdSecMap.get(div.getDmdId());
            Validate.notNull(dmd);
            
            MetsDivInfo divParent = div.getParent();
            Node dmdParent = dmd.getParentEntity();
            
            if(divParent==null&&dmdParent==null){
                continue;
            }
            if(divParent==null) {
                errorList.add(dmd.getNode());
                errorList.add(div.getMetsDiv());
                errorList.add(dmdParent);                
                break;
            }
            if(dmdParent==null) {
                errorList.add(dmd.getNode());
                errorList.add(div.getMetsDiv());
                errorList.add(divParent.getMetsDiv());
                break;
            }

            // zkontroluj rodice - identifikatory            
            DmdSecInfo dmdParentInfo = this.dmdSecMap.get(dmd.getParentEntityId());
            if(dmdParentInfo==null) {
                errorList.add(dmdParent);
                break;
            }
            DmdSecInfo dmdParentInfo2 = this.dmdSecMap.get(divParent.getDmdId());
            if(dmdParentInfo==dmdParentInfo2) {
                continue;
            }            
            if (!Objects.equals(dmdParentInfo.pzi, dmdParentInfo2.pzi)) {
                errorList.add(dmd.getNode());
                errorList.add(div.getMetsDiv());
                errorList.add(divParent.getMetsDiv());
                errorList.add(dmdParent);
                break;
            }

        }
        if(errorList.size()>0) {
            String chyba;            
            if (errorList.size() == 1) {
                chyba = "Element <mets:div> je špatně zatříděn.";
            } else{
                chyba = "Element <mets:div> a jeho rodičovský element <mets:div> odkazují na chybné elementy v <mets:dmdSec>.";
            }
            return nastavChybu(chyba, errorList);
        }
        
        return true;
    } 
    
    private boolean readDmdSecList() {
        this.dmdSecList = new ArrayList<>();
        this.dmdSecMap = new HashMap<>();
        this.dmdSecPziMap = new HashMap<>();
        List<Node> errors = new ArrayList<>(0);
        
        readDmdsecs(metsParser.getNodes(JmenaElementu.SPISOVY_PLAN), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.VECNA_SKUPINA), errors);
        readDmdsecs(metsParser.getDokumenty(), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.SPIS), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.KOMPONENTA), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.SOUCAST), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.TYPOVY_SPIS), errors);
        readDmdsecs(metsParser.getNodes(JmenaElementu.DIL), errors);
        
        if(!errors.isEmpty()){
            String hlaska = errors.size()==1?"Nalezena chyba u elementu <mets:div>."
                    :"Nalezeny chyby u elementů <mets:div>.";

            return nastavChybu(hlaska, errors);
        }        
        return true;
    }

    private void readDmdsecs(List<Node> nodes, List<Node> errors) {
        for(Node node: nodes) {
            readDmdsec(node, errors);
        }        
    }
    
    private void readDmdsec(Node node, List<Node> errors) {
        DmdSecInfo dmdSecInfo = DmdSecInfo.valueOf(node);
        if(dmdSecInfo==null) {
            errors.add(node);
            return;
        }
        dmdSecList.add(dmdSecInfo);
        if(dmdSecMap.put(dmdSecInfo.getId(), dmdSecInfo)!=null) {
            errors.add(node);
            return;
        }
        List<DmdSecInfo> lst = dmdSecPziMap.computeIfAbsent(dmdSecInfo.pzi, pzi -> new ArrayList<>());
        lst.add(dmdSecInfo);        
    }

    private boolean readMetsDivList() {
        List<Node> nodeList = metsParser.getNodes(MetsElements.DIV);
        if(nodeList.size() == 0){
            return nastavChybu("Nenalezen element <mets:div>.");
        }
        
        this.metsDivList = new ArrayList<>(nodeList.size());
        this.metsDivAmdMap = new HashMap<>();
        this.metsDivDmdMap = new HashMap<>();
        // pomocna mapa pro vytvoreni vazeb na rodice
        Map<Node, MetsDivInfo> metsdivNodes = new HashMap<>();
        
        ArrayList<Node> errorList = new ArrayList<>(0);
        ArrayList<Node> duplicatedIdList = new ArrayList<>(0);
        
        for(Node node: nodeList) {
            MetsDivInfo metsdiv = new MetsDivInfo(node);

            String dmdid = metsdiv.getDmdId();
            if(StringUtils.isEmpty(dmdid)) {
                errorList.add(node);
                continue;
            }
            String admid = metsdiv.getAdmId();
            if(StringUtils.isEmpty(admid)) {
                errorList.add(node);
                continue;
            }
            String type = metsdiv.getType(); 
            if(StringUtils.isEmpty(type)) {
                errorList.add(node);
                continue;
            }
            
            metsDivList.add(metsdiv);
            metsdivNodes.put(node, metsdiv);
            
            // priprava mapy
            if(metsDivAmdMap.containsKey(admid)) {
                duplicatedIdList.add(node);
                continue;
            }
            if(metsDivDmdMap.containsKey(dmdid)) {
                duplicatedIdList.add(node);
                continue;
            }
            metsDivAmdMap.put(admid, metsdiv);
            metsDivDmdMap.put(dmdid, metsdiv);
        }

        if(!errorList.isEmpty()){
            String hlaska = (errorList.size() == 1)? "Nalezena chyba u elementu <mets:div>."
                    :"Nalezeny chyby u elementů <mets:div>.";            
            return nastavChybu(hlaska, errorList);
        }

        if(!duplicatedIdList.isEmpty()) {
            return nastavChybu("Nalezeny chyby duplicity u elementů <mets:div>.", duplicatedIdList);
        }
        
        // vytvoreni vazeb na rodice
        for(MetsDivInfo metsdiv: metsDivList) {
            Node node = metsdiv.getParentNode();
            MetsDivInfo parent = metsdivNodes.get(node);
            if(parent!=null) {
                metsdiv.setParent(parent);
            }
        }
        
        return true;
    }


    /**
     * Precteni struktury <mets:amdSec>
     * @return
     */
    private boolean readAmdSecList() {
        List<Node> amdSecNodes = metsParser.getNodes(MetsElements.AMD_SEC);
        if(CollectionUtils.isEmpty(amdSecNodes)){
            return nastavChybu("Nenalezen element <mets:amdSec>.");
        }        
        amdSecList = new ArrayList<>(amdSecNodes.size());
        amdSecMap = new HashMap<>();
        Map<PairZdrojIdent, Node> amdIdents = new HashMap<>();
        List<Node> errorList = new ArrayList<>(0);
        List<Node> duplicated = new ArrayList<>();

        for(Node node: amdSecNodes) {            
            String id = ValuesGetter.getValueOfAttribut(node, "ID");
            if(StringUtils.isEmpty(id)) {
                errorList.add(node);
                continue;
            }

            // <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID>
            Node nodeIdentifikator = ValuesGetter.getXChild(node, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator");
            if(nodeIdentifikator==null) {
                continue;
            }
            Node nodeHodnotaId = ValuesGetter.findFirstChild(nodeIdentifikator, "tns:HodnotaID");
            if(nodeHodnotaId==null) {
                errorList.add(nodeIdentifikator);
                continue;                
            }
            String identifikator = nodeHodnotaId.getTextContent();
            
            Node nodeZdroj = ValuesGetter.findFirstChild(nodeIdentifikator, "tns:ZdrojID");
            if(nodeZdroj==null) {
                errorList.add(nodeIdentifikator);
                continue;                                
            }
            String zdroj = nodeZdroj.getTextContent();
            
            PairZdrojIdent pzi = new PairZdrojIdent(zdroj, identifikator);
            
            // jestliže nastala nějaká chyba
            AmdSecInfo amdSec = new AmdSecInfo(id, pzi, node);
            amdSecList.add(amdSec);
            amdSecMap.put(id, amdSec);
            
            Node prevPziNode = amdIdents.put(pzi, node);
            if(prevPziNode!=null) {
                duplicated.add(node);
                duplicated.add(prevPziNode);
            }
        }
        
        if(errorList.size()>0) {
            // report error
            if(errorList.size()==1) {
                return nastavChybu("Nalezena chyba u elementu <mets:amdSec>.", errorList.get(0));
            } else {                
                return nastavChybu("Nalezeny chyby u elementů <mets:amdSec>.", errorList);
            }
        }
        if(duplicated.size()>0) {
            nastavChybu("Nalezeny chyby duplicity u elementů <mets:amdSec>.", duplicated);
            return false;            
        }
        
        return true;
    }
}
