package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
        String id, identifikator, zdroj;
        Node node;

        public AmdSecInfo(String id, String identifikator, String zdroj, Node node) {
            this.id = id;
            this.identifikator = identifikator;
            this.zdroj = zdroj;
            this.node = node;
        }

        public Node getNode() {
            return node;
        }
    }

    static public class MetsDivInfo {
        Node metsdiv;
        String dmdid, admid, type;
        MetsDivInfo rodic;
        boolean parent_right;

        public MetsDivInfo(Node metsdiv, String dmdid, String admid, String type, boolean getRodice) {
            this.metsdiv = metsdiv;
            this.dmdid = dmdid;
            this.admid = admid;
            this.type = type;
            if(getRodice){
                rodic = set_parent(); // set also parent_right
            }    
        }

        private MetsDivInfo set_parent(){
            Node parent = metsdiv.getParentNode();
            if(parent == null){
                parent_right = false;
                return new MetsDivInfo(parent, "ERR", "ERR", "ERR", false);
            }
            if(type.toLowerCase().equals("spisový plán")){
                if(parent.getNodeName().equals("mets:structMap")){
                    parent_right = true;
                    return new MetsDivInfo(parent, "MAP", "MAP", "MAP", false);
                }  
                else{
                    parent_right = false;
                    return new MetsDivInfo(parent, "ERR", "ERR", "ERR", false); 
                }
            }
            else{
                String admid2 = "ERR", dmdid2 = "ERR", type2 = "ERR";
                if(parent.getNodeName().equals("mets:div")){
                    parent_right = true;
                    if(ValuesGetter.hasAttribut(parent, "ADMID")) admid2 = ValuesGetter.getValueOfAttribut(parent, "ADMID");
                    if(ValuesGetter.hasAttribut(parent, "DMDID")) dmdid2 = ValuesGetter.getValueOfAttribut(parent, "DMDID");
                    if(ValuesGetter.hasAttribut(parent, "TYPE")) type2 = ValuesGetter.getValueOfAttribut(parent, "TYPE");
                    return new MetsDivInfo(parent, dmdid2, admid2, type2, false);
                } 
                else{
                    parent_right = false;
                    return new MetsDivInfo(parent, "ERR", "ERR", "ERR", false);  
                }
            }
        }

        public Node getMetsDiv() {
            return metsdiv;
        }
    }
    
    static public class DmdSecInfo {
        String id, identifikator, zdroj, type;
        Node node;
        DmdSecInfo rodic_zatrideni;
        String rodic_type = "";
        

        public DmdSecInfo(String type, String id, String identifikator, String zdroj, Node node, boolean getRodice) {
            this.type = type;
            this.id = id;
            this.identifikator = identifikator;
            this.zdroj = zdroj;
            this.node = node;
            if(getRodice){
                get_rodice();
            }
            
        }
        
        private void get_rodice(){
            Node rodic = null;
            
            switch(type){
                case "věcná skupina":
                   rodic = parent_vecna_skupina();
                break;
                case "součást":
                   rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:TypovySpis");
                   rodic_type = "součást";
                break;
                case "typový spis":
                   rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                   rodic_type = "věcná skupina";
                break;
                case "spis":
                   rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                   rodic_type = "věcná skupina";
                break;
                case "díl":
                   rodic = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:Soucast");
                   rodic_type = "součást";
                break;
                case "dokument":
                   rodic = parent_dokument();
                break;
                case "komponenta":
                   rodic = node.getParentNode().getParentNode();
                   rodic_type = "dokument";
                break;
            }
            String[] values = get_nodeValues(rodic);
            rodic_zatrideni = new DmdSecInfo(rodic_type, values[0], values[1], values[2], rodic, false);

        }
        
        private String[] get_nodeValues(Node node){
            if(node == null){
                String[] values = {"ERR", "ERR", "ERR"};
                return values;
            }
            String id2 = "ERR", identifikator2 = "ERR", zdroj2 = "ERR";
            if(node.getNodeName().equals("nsesss:SpisovyPlan")){
                Node n = ValuesGetter.getXChild(node, "nsesss:Identifikator");
                if(ValuesGetter.hasAttribut(node, "ID")){
                        id2 = ValuesGetter.getValueOfAttribut(node, "ID");
                    }
                if(n != null){
                    if(ValuesGetter.hasAttribut(n, "zdroj")){
                        zdroj2 = ValuesGetter.getValueOfAttribut(n, "zdroj");
                    }
                    identifikator2 = n.getTextContent(); 
                }
            }
            else{
                Node n = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                if(ValuesGetter.hasAttribut(node, "ID")){
                        id2 = ValuesGetter.getValueOfAttribut(node, "ID");
                    }
                if(n != null){
                    if(ValuesGetter.hasAttribut(n, "zdroj")){
                        zdroj2 = ValuesGetter.getValueOfAttribut(n, "zdroj");
                    }
                    identifikator2 = n.getTextContent(); 
                }
            }

            String[] values = {id2, identifikator2, zdroj2};
            return values;
        }
        
        private Node parent_dokument(){
            Node p;
            if(node.getParentNode().getNodeName().equals("mets:xmlData")){
                p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
                rodic_type = "věcná skupina";
                if(p == null){
                    p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
                }
            }
            else{
                p = node.getParentNode().getParentNode();
                if(p.getNodeName().equals("nsesss:Spis")){
                    rodic_type = "spis";
                }
                else{
                    rodic_type = "díl";
                }
            }
            return p;
        }
        
        private Node parent_vecna_skupina(){
            Node p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina");
            rodic_type = "věcná skupina";
            if(p == null){
                p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity", "nsesss:VecnaSkupina");
                if(p == null){
                    p = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
                    rodic_type = "spisový plán";
                }
            }
            return p;
        }

        public Node getNode() {
            return node;
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

        //jedinečnost
        if(!testAmdSecUniqueness()) {
            return false;
        }
        // KONEC TESTU mets:amdSec
        
        // TEST mets_div
        if(!readMetsDivList()) {
            return false;
        }
        // KONEC TESTU mets:div
        
        // TEST dmdSec
        if(!readDmdSecList()) {
            return false;
        }
        // KONEC TESTU dmdSec
        
        // TEST amdSec to metsdiv
        if(!compare_amdSec_with_metsDiv()) {
            return false;
        }
        // KONEC TESTU amdSec + metsdiv
        
        //TEST AMD TO DMDSEC
        if(!compare_amdSec_dmdSec()) {
            return false;
        }
        //KONEC TESTU AMD TO DMDSEC
                
        //TEST STRUKTURY PODLE METS DIV
        if(!compare_metsDiv_with_dmdSec_structure()) {
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
    public boolean compare_amdSec_dmdSec() {
        ArrayList<Node> errorList = new ArrayList<>(0);
        ArrayList<Node> missingAmdSec = new ArrayList<>(0);
        
        for(MetsDivInfo metsDivInfo: metsDivList) {
            AmdSecInfo amdInfo = this.amdSecMap.get(metsDivInfo.admid);
            if(amdInfo==null) {
                missingAmdSec.add(metsDivInfo.metsdiv);
                continue;
            }
            DmdSecInfo dmdInfo = this.dmdSecMap.get(metsDivInfo.dmdid);
            if(amdInfo==null||dmdInfo==null) {
                errorList.add(metsDivInfo.metsdiv);
                continue;
            }
            if(!Objects.equals(amdInfo.identifikator, dmdInfo.identifikator)) {
                errorList.add(amdInfo.node);
                errorList.add(dmdInfo.node);
                continue;                
            }
            if(!Objects.equals(amdInfo.zdroj, dmdInfo.zdroj)) {
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
    public boolean compare_metsDiv_with_dmdSec_structure() 
    {
        ArrayList<Node> errorList = new ArrayList<>(0);
        
        for(MetsDivInfo div: metsDivList) {
            // ?? proc to tady je
            if(!div.parent_right){
                // ? asi ma kazdy div rodice?
                errorList.add(div.metsdiv);                
                break;
            }
            
            DmdSecInfo dmd = dmdSecMap.get(div.dmdid);

            // zkontroluj rodice
            if (!dmd.rodic_zatrideni.id.equals(div.rodic.dmdid)) {
                errorList.add(dmd.node);
                errorList.add(dmd.rodic_zatrideni.node);
                errorList.add(div.metsdiv);
                errorList.add(div.rodic.metsdiv);
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

    private DmdSecInfo readIdentifikator(String type, String id, Node node) {
        Node identNode = ValuesGetter.findFirstChild(node, "nsesss:Identifikator");            
        if(identNode==null) {
            return null;
        }
        String zdroj = ValuesGetter.getValueOfAttribut(identNode, "zdroj");
        if(StringUtils.isEmpty(zdroj)) {
            return null;
        }
        String identifikator = identNode.getTextContent(); 
        if(StringUtils.isEmpty(identifikator)) {
            return null;
        }
        
        return new DmdSecInfo(type, id, identifikator, zdroj, identNode, true);
    }
    
    // Lze pouzit na uzly: nsesss:SpisovyPlan, nsesss:Skartacnirezim, nsesss:TypDokumentu
    private void readDmdSecInfosFromIdentifikator(String type, NodeList nodeList, 
                                 List<Node> errors) {
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = ValuesGetter.getValueOfAttribut(node, "ID");
            if(StringUtils.isEmpty(id)) {
                errors.add(node);
                continue;
            }
            DmdSecInfo dmdSecInfo = readIdentifikator(type, id, node);
            if(dmdSecInfo==null) {
                errors.add(node);
                continue;
            }
            dmdSecList.add(dmdSecInfo);
            if(dmdSecMap.put(id, dmdSecInfo)!=null) {
                errors.add(node);
                continue;
            }
        }
    }
    
    private DmdSecInfo readEvidUdajeIdentifikator(String type, String id, Node node) {
        Node identNode = ValuesGetter.getXChild(node, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
        if(identNode==null) {
            return null;
        }
        String zdroj = ValuesGetter.getValueOfAttribut(identNode, "zdroj");
        if(StringUtils.isEmpty(zdroj)) {
            return null;
        }
        String identifikator = identNode.getTextContent(); 
        if(StringUtils.isEmpty(identifikator)) {
            return null;
        }
        
        return new DmdSecInfo(type, id, identifikator, zdroj, identNode, true);
    }

    private void readDmdSecInfosFromEvidUdaje(String type, NodeList nodeList, 
                                              List<Node> errors) {
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String id = ValuesGetter.getValueOfAttribut(node, "ID");
            if(StringUtils.isEmpty(id)) {
                errors.add(node);
                continue;
            }
            DmdSecInfo dmdSecInfo = readEvidUdajeIdentifikator(type, id, node);
            if(dmdSecInfo==null) {
                errors.add(node);
                continue;
            }
            dmdSecList.add(dmdSecInfo);
            
            if(dmdSecMap.put(id, dmdSecInfo)!=null) {
                errors.add(node);
                continue;
            }
        }
    }

    private boolean readDmdSecList() {
        this.dmdSecList = new ArrayList<>();
        this.dmdSecMap = new HashMap<>();
        List<Node> errors = new ArrayList<>(0);

        NodeList skartacniRezimNodeList = metsParser.getElementsByTagName("nsesss:SkartacniRezim");
        readDmdSecInfosFromIdentifikator("skartační režim", skartacniRezimNodeList, errors);
        
        NodeList typDokumentuNodeList = metsParser.getElementsByTagName("nsesss:TypDokumentu");
        readDmdSecInfosFromIdentifikator("typ dokumentu", typDokumentuNodeList, errors);

        NodeList planyNodelist = metsParser.getElementsByTagName("nsesss:SpisovyPlan");
        readDmdSecInfosFromIdentifikator("spisový plán", planyNodelist, errors);
        
        NodeList skupinyNodelist = metsParser.getElementsByTagName("nsesss:VecnaSkupina");
        readDmdSecInfosFromEvidUdaje("věcná skupina", skupinyNodelist, errors);
        
        NodeList soucastiNodeList = metsParser.getElementsByTagName("nsesss:Soucast");
        readDmdSecInfosFromEvidUdaje("součást", soucastiNodeList, errors);

        NodeList typoveSpisyNodelist = metsParser.getElementsByTagName("nsesss:TypovySpis");
        readDmdSecInfosFromEvidUdaje("typový spis", typoveSpisyNodelist, errors);
        
        NodeList spisyNodelist = metsParser.getElementsByTagName("nsesss:Spis");
        readDmdSecInfosFromEvidUdaje("spis", spisyNodelist, errors);

        NodeList dilyNodelist = metsParser.getElementsByTagName("nsesss:Dil");
        readDmdSecInfosFromEvidUdaje("díl", dilyNodelist, errors);

        NodeList dokumentyNodelist = metsParser.getElementsByTagName("nsesss:Dokument");
        readDmdSecInfosFromEvidUdaje("dokument", dokumentyNodelist, errors);

        NodeList komponentyNodelist = metsParser.getElementsByTagName("nsesss:Komponenta");
        readDmdSecInfosFromEvidUdaje("komponenta", komponentyNodelist, errors);
       
        if(!errors.isEmpty()){
            String hlaska = errors.size()==1?"Nalezena chyba u elementu <mets:div>."
                    :"Nalezeny chyby u elementů <mets:div>.";

            return nastavChybu(hlaska, errors);
        }        
        return true;
    }


    private boolean readMetsDivList() {
        NodeList nodeList = metsParser.getDocument().getElementsByTagName("mets:div");
        if(nodeList.getLength() == 0){
            return nastavChybu("Nenalezen element <mets:div>.");
        }
        
        this.metsDivList = new ArrayList<>(nodeList.getLength());
        this.metsDivAmdMap = new HashMap<>();
        this.metsDivDmdMap = new HashMap<>();
        
        ArrayList<Node> errorList = new ArrayList<>(0);
        ArrayList<Node> duplicatedIdList = new ArrayList<>(0);
        
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            String dmdid = ValuesGetter.getValueOfAttribut(node, "DMDID");
            if(StringUtils.isEmpty(dmdid)) {
                errorList.add(node);
                continue;
            }
            String admid = ValuesGetter.getValueOfAttribut(node, "ADMID");
            if(StringUtils.isEmpty(admid)) {
                errorList.add(node);
                continue;
            }
            String type = ValuesGetter.getValueOfAttribut(node, "TYPE"); 
            if(StringUtils.isEmpty(type)) {
                errorList.add(node);
                continue;
            }

            MetsDivInfo metsdiv = new MetsDivInfo(node, dmdid, admid, type, true);
            metsDivList.add(metsdiv);
            
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
        
        return true;
    }


    private boolean readAmdSecList() {
        NodeList amdSecNodes = metsParser.getElementsByTagName("mets:amdSec");
        if(amdSecNodes.getLength() == 0){
            return nastavChybu("Nenalezen element <mets:amdSec>.");
        }        
        amdSecList = new ArrayList<>(amdSecNodes.getLength());
        
        List<Node> errorList = new ArrayList<>(0);

        for(int i = 0; i < amdSecNodes.getLength(); i++){
            Node node = amdSecNodes.item(i);
            
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
            
            // jestliže nastala nějaká chyba
            AmdSecInfo amdSec = new AmdSecInfo(id, identifikator, zdroj, node);
            amdSecList.add(amdSec);
        }
        
        if(errorList.size()>0) {
            // report error
            if(errorList.size()==1) {
                return nastavChybu("Nalezena chyba u elementu <mets:amdSec>.", errorList.get(0));
            } else {                
                return nastavChybu("Nalezeny chyby u elementů <mets:amdSec>.", errorList);
            }
        }
        
        return true;
    }

    public boolean testAmdSecUniqueness() {
        
        List<Node> duplicated = new ArrayList<>();
        
        this.amdSecMap = new HashMap<>();
        for(AmdSecInfo amdSecInfo: amdSecList) {
            AmdSecInfo currInfo = amdSecMap.get(amdSecInfo.id);
            if(currInfo!=null) {
                duplicated.add(currInfo.node);
                duplicated.add(amdSecInfo.node);
                continue;
            }
            amdSecMap.put(amdSecInfo.id, amdSecInfo);
        }
        
        if(duplicated.size()>0) {            
            nastavChybu("Nalezeny chyby duplicity u elementů <mets:amdSec>.", duplicated);
            return false;
        }
        return true;
        
        /*
        for(int i = 0; i < list.size(); i++){
            
            StructMap_Obj_amdSec amd_main = list.get(i);
            
            for(int j = 0; j < list.size(); j++){
                
                if(j != i){
                   StructMap_Obj_amdSec amd_second = list.get(j);
                   if(amd_main.id.equals(amd_second.id) || 
                           amd_main.id.equals(amd_second.identifikator) || 
                           amd_main.id.equals(amd_second.zdroj)){
                       
                       ArrayList<Node> node_list = new ArrayList<>();
                       node_list.add(amd_main.node);
                       node_list.add(amd_second.node);
                       
                       return new StructMap_Obj_return_bol_AL_node(false, node_list);
                   }
                }
            }  
        }
        return new StructMap_Obj_return_bol_AL_node(true, null);*/
    }    
}
