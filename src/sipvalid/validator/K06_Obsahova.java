/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sipvalid.sip.SIP_MAIN;
import sipvalid.sip.SIP_MAIN_helper;
import sipvalid.sip.SIP_MAIN_kontrola;
import sipvalid.sip.SIP_MAIN_kontrola_pravidlo;
import sipvalid.helper.Helper;
import sipvalid.helper.HelperArrayList;
import sipvalid.helper.HelperString;
import static sipvalid.validator.RozparsovaniSipSouboru.dokumenty;
import static sipvalid.validator.RozparsovaniSipSouboru.identifikatory;
import static sipvalid.validator.RozparsovaniSipSouboru.manipulace;
import static sipvalid.validator.RozparsovaniSipSouboru.metsDmdSec;
import static sipvalid.validator.RozparsovaniSipSouboru.metsHdr;
import static sipvalid.validator.RozparsovaniSipSouboru.metsMdWrap;
import static sipvalid.validator.RozparsovaniSipSouboru.metsMets;
import static sipvalid.validator.RozparsovaniSipSouboru.nazvy;
import static sipvalid.validator.RozparsovaniSipSouboru.parsedSAX_Sipsoubor;
import static sipvalid.validator.RozparsovaniSipSouboru.plneurcenySpisovyZnak;
import static sipvalid.validator.RozparsovaniSipSouboru.urceneCasoveObdobi;
import static sipvalid.validator.RozparsovaniSipSouboru.xmlData;
import static sipvalid.validator.RozparsovaniSipSouboru.zakladniEntity;


/**
 *
 * @author m000xz006159
 */
public class K06_Obsahova {
       private String chyba_neupresneno = "Neupřesněno.";
       private String popisChyby = "Pravidlo nesplněno.", misto_chyby = "";
       SIP_MAIN sipSoubor;
       
    public K06_Obsahova(int[] seznamPravidel, SIP_MAIN sipSoubor, boolean proved) throws IOException, ParseException {
        this.sipSoubor = sipSoubor;
        SIP_MAIN_kontrola k = new SIP_MAIN_kontrola("kontrola obsahu", proved);
        if(proved){
            for(int i = 0; i < seznamPravidel.length; i++){
//            long start = System.currentTimeMillis();    
                int j = seznamPravidel[i];
                try{
                    boolean jePravidloSplneno = udelejPravidloObsahovaSpolecna2018(j);
                    if(!jePravidloSplneno){
                        SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(j, getIDpravidla(j), false, popisChyby, j, misto_chyby);
                        k.setStav(false);
                        k.add(p);
                    }
                }
                catch(NullPointerException ex){ 
                    SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(j, getIDpravidla(j), false, "Nenalezeny základní elementy schématu mets a nsesss.", j, "Neupřesněno.");
                    k.setStav(false);
                    k.add(p);
                }
//                long konec = System.currentTimeMillis();
//                Date d = Helper.getDuration(start, konec);
//                System.out.println("    " +" p." + getIDpravidla(j) + "." + "   "  + "Doba: " + d);
            }
        }

        
        
        sipSoubor.getSeznamKontrol().add(k);
    } 
    
    private String getIDpravidla(int j){
        if(j == 32) return "93a";
        if(j == 41) return "54a";
        if(j == 42) return "61a";
        if(j == 43) return "94a";

        return Integer.toString(j);
    }
    
    private boolean udelejPravidloObsahovaSpolecna2018(int cisloPravidla) throws IOException, ParseException{
        boolean vysledek = false;
        switch(cisloPravidla){
            case 1:
                vysledek = pravidlo1();
                break;
            case 2:
                vysledek = pravidlo2();
                break;
            case 3:
                vysledek = pravidlo3();
                break;
            case 4:
                vysledek = pravidlo4();
                break;    
            case 5:
                vysledek = pravidlo5();
                break;
            case 6:
                vysledek = pravidlo6();
                break;
            case 7:
                vysledek = pravidlo7();
                break;
            case 8:
                vysledek = pravidlo8();
                break;
            case 9:
                vysledek = pravidlo9();
                break;    
            case 10:
                vysledek = pravidlo10();
                break;
            case 11:
                vysledek = pravidlo11();
                break;    
            case 12:
                vysledek = pravidlo12();
                break;
            case 13:
                vysledek = pravidlo13();
                break;
            case 14:
                vysledek = pravidlo14();
                break;
            case 15:
                vysledek = pravidlo15();
                break;    
            case 16:
                vysledek = pravidlo16();
                break;
            case 17:
                vysledek = pravidlo17();
                break;
            case 18:
                vysledek = pravidlo18();
                break;
            case 19:
                vysledek = pravidlo19();
                break;
            case 20:
                vysledek = pravidlo20();
                break;    
            case 21:
                vysledek = pravidlo21();
                break;
            case 22:
                vysledek = pravidlo22();
                break;
            case 23:
                vysledek = pravidlo23();
                break;
            case 24:
                vysledek = pravidlo24();
                break;
            case 25:
                vysledek = pravidlo25();
                break;
            case 26:
                vysledek = pravidlo26();
                break;    
            case 27:
                vysledek = pravidlo27();
                break;
            case 28:
                vysledek = pravidlo28();
                break;
            case 29:
                vysledek = pravidlo29();
                break;
            case 30:
                vysledek = pravidlo30();
                break;
            case 31:
                vysledek = pravidlo31();
                break;    
            case 32:
                vysledek = pravidlo32();
                break;
            case 33:
                vysledek = pravidlo33();
                break;
            case 34:
                vysledek = pravidlo34();
                break;
            case 35:
                vysledek = pravidlo35();
                break;
            case 36:
                vysledek = pravidlo36();
                break;    
            case 37:
                vysledek = pravidlo37();
                break;
            case 38:
                vysledek = pravidlo38();
                break;
            case 39:
                vysledek = pravidlo39();
                break;
            case 40:
                vysledek = pravidlo40();
                break;
            case 41:
                vysledek = pravidlo41();
                break;    
            case 42:
                vysledek = pravidlo42();
                break;
            case 43:
                vysledek = pravidlo43();
                break;    
            case 44:
                vysledek = pravidlo44();
                break;
            case 45:
                vysledek = pravidlo45();
                break;
            case 46:
                vysledek = pravidlo46();
                break;
            case 47:
                vysledek = pravidlo47();
                break;    
            case 48:
                vysledek = pravidlo48();
                break;
            case 49:
                vysledek = pravidlo49();
                break;
            case 50:
                vysledek = pravidlo50();
                break;
            case 51:
                vysledek = pravidlo51();
                break;
            case 52:
                vysledek = pravidlo52();
                break;    
            case 53:
                vysledek = pravidlo53();
                break;
            case 54:
                vysledek = pravidlo54();
                break;
            case 55:
                vysledek = pravidlo55();
                break;
            case 56:
                vysledek = pravidlo56();
                break;
            case 57:
                vysledek = pravidlo57();
                break;
            case 58:
                vysledek = pravidlo58();
                break;    
            case 59:
                vysledek = pravidlo59();
                break;
            case 60:
                vysledek = pravidlo60();
                break;
            case 61:
                vysledek = pravidlo61();
                break;
            case 62:
                vysledek = pravidlo62();
                break;
            case 63:
                vysledek = pravidlo63();
                break;    
            case 64:
                vysledek = pravidlo64();
                break;
            case 65:
                vysledek = pravidlo65();
                break;
            case 66:
                vysledek = pravidlo66();
                break;
            case 67:
                vysledek = pravidlo67();
                break;
            case 68:
                vysledek = pravidlo68();
                break;    
            case 69:
                vysledek = pravidlo69();
                break;
            case 70:
                vysledek = pravidlo70();
                break;
            case 71:
                vysledek = pravidlo71();
                break;
            case 72:
                vysledek = pravidlo72();
                break;
            case 73:
                vysledek = pravidlo73();
                break;    
            case 74:
                vysledek = pravidlo74();
                break;
            case 75:
                vysledek = pravidlo75();
                break;    
            case 76:
                vysledek = pravidlo76();
                break;
            case 77:
                vysledek = pravidlo77();
                break;
            case 78:
                vysledek = pravidlo78();
                break;
            case 79:
                vysledek = pravidlo79();
                break;    
            case 80:
                vysledek = pravidlo80();
                break;
            case 81:
                vysledek = pravidlo81();
                break;
            case 82:
                vysledek = pravidlo82();
                break;
            case 83:
                vysledek = pravidlo83();
                break;
            case 84:
                vysledek = pravidlo84();
                break;    
            case 85:
                vysledek = pravidlo85();
                break;
            case 86:
                vysledek = pravidlo86();
                break;
            case 87:
                vysledek = pravidlo87();
                break;
            case 88:
                vysledek = pravidlo88();
                break;
            case 89:
                vysledek = pravidlo89();
                break;
            case 90:
                vysledek = pravidlo90();
                break;    
            case 91:
                vysledek = pravidlo91();
                break;
            case 92:
                vysledek = pravidlo92();
                break;
            case 93:
                vysledek = pravidlo93();
                break;
            case 94:
                vysledek = pravidlo94();
                break;
            case 95:
                vysledek = pravidlo95();
                break;    
            case 96:
                vysledek = pravidlo96();
                break;  
            case 97:
                vysledek = pravidlo97();
                break;    
            case 98:
                vysledek = pravidlo98();
                break;                
        }
        
        return vysledek;
    }
    
    private boolean add_popisy(String chyba, boolean ret, String misto_chyby){
        popisChyby = chyba;
        this.misto_chyby = misto_chyby;
        return ret;
    }
    
    private String getJmenoIdentifikator(Node node){ 
        if(node != null){
            Node entity = getEntity(node);
            return getJmeno(entity) + " " + getIdentifikatory(entity) + ".";
        }
        return "";
    }
    
    private Node getEntity(Node node){
        String nodeName = node.getNodeName();
        Node entity;
        if(!isMainEnetity(nodeName)){
            entity = getEntity(node.getParentNode());
        }
        else{
            return node;
        }
        return entity;
    }
    
    private boolean isMainEnetity(String nodeName){
        boolean bol = (nodeName.equals("nsesss:SpisovyPlan") || nodeName.equals("nsesss:VecnaSkupina") || nodeName.equals("TypovySpis") || nodeName.equals("nsesss:Soucast") || nodeName.equals("nsesss:Dil") || nodeName.equals("nsesss:Spis") || nodeName.equals("nsesss:Dokument"));  
        return bol;
    }
    
    private String getIdentifikatory(Node node){
        String nodename = node.getNodeName();
        String hodnota = "nenalezeno", zdroj = "nenalezeno";
        Node identifikator;
        if(nodename.equals("nsesss:SpisovyPlan")){
            identifikator = ValuesGetter.getXChild(node, "nsesss:Identifikator");
            if(identifikator!= null){
                hodnota = identifikator.getTextContent();
                boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
                if(ma) zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
                return "(Ident. hodnota: "+ hodnota + ", zdroj: " + zdroj + ")";
            }
        }
        identifikator = ValuesGetter.getXChild(node,"nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
        if(identifikator!= null){
            hodnota = identifikator.getTextContent();
            boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
            if(ma) zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            return "(Ident. hodnota: "+ hodnota + ", zdroj: " + zdroj + ")";
        }
                
        return "(Ident. hodnota: "+ hodnota + ", zdroj: " + zdroj + ")";
    }
    
    private String get_misto_chyby(Node node){
        String point = node.getUserData("lineNumber").toString();
        String name = node.getNodeName();
        name = "<" + name + ">";
        return "Řádek " + point + ", element " + name + ".";
    }
    
    private String getJmeno(Node node){
        String jmeno = node.getNodeName();
        switch(jmeno){
            case "nsesss:SpisovyPlan": return "Spisový plán";
            case "nsesss:VecnaSkupina": return "Věcná skupina";
            case "nsesss:TypovySpis": return "Typový spis";
            case "nsesss:Soucast": return "Součást";
            case "nsesss:Dil": return "Díl";
            case "nsesss:Spis": return "Spis";
            case "nsesss:Dokument": return "Dokument";
        }
        return "";
    }
    
    //OBSAHOVÁ č.1 Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
    private boolean pravidlo1(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMets, "OBJID")){
            return add_popisy("Nenalezen atribut OBJID kořenového elementu <mets:mets>.", false, get_misto_chyby(metsMets));
        }
        if(!Helper.isStringNoEmpty(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"))){
            return add_popisy("Atribut OBJID kořenového elementu <mets:mets> není vyplněn.", false, get_misto_chyby(metsMets));
        }
        return true;
    }
    
    //OBSAHOVÁ č.2 Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.
    private boolean pravidlo2(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMets, "LABEL")){
            return add_popisy("Nenalezen atribut LABEL kořenového elementu <mets:mets>.", false, get_misto_chyby(metsMets));
        } 
        String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
        if(hodLab.equals("Datový balíček pro provedení skartačního řízení") || hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) return true;
        if(!Helper.isStringNoEmpty(hodLab)){
            return add_popisy("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.", false, get_misto_chyby(metsMets));
        }
        return add_popisy("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: " + hodLab + ".", false, get_misto_chyby(metsMets));
    }
    
    //OBSAHOVÁ č.3 Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
    private boolean pravidlo3(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMets, "LABEL")){
            return add_popisy("Nenalezen atribut LABEL kořenového elementu <mets:mets>.", false, get_misto_chyby(metsMets));
        }
        String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
        if(hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) return true;
        if(!Helper.isStringNoEmpty(hodLab)){
            return add_popisy("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.", false, get_misto_chyby(metsMets));
        }
        return add_popisy("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: " + hodLab + ".", false, get_misto_chyby(metsMets));
    }
    
    //OBSAHOVÁ č.4 Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
    private boolean pravidlo4(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, get_misto_chyby(metsMets));
        if(!ValuesGetter.hasAttribut(metsMets, "xmlns:xsi")) return add_popisy("Nenalezen atribut xmlns:xsi kořenového elementu <mets:mets>.", false, get_misto_chyby(metsMets));
        String hod = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xsi"); 
        if(!Helper.isStringNoEmpty(hod)) return add_popisy("Atribut xmlns:xsi kořenového elementu <mets:mets> má prázdnou hodnotu.", false, get_misto_chyby(metsMets));
        if(!hod.equals("http://www.w3.org/2001/XMLSchema-instance")) return add_popisy("Atribut xmlns:xsi kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: " + hod + ".", false, get_misto_chyby(metsMets));
        return true;
    }
    
    // volny index
    private boolean pravidlo5(){

        return false;
    }
    // volny index
    private boolean pravidlo6(){
        return false;
    }
    //volny index
    private boolean pravidlo7(){
        return false;
    }
    //volny index
    private boolean pravidlo8(){
        return false;
    }
    
    //OBSAHOVÁ č.9 Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.
    private boolean pravidlo9(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMets, "xmlns:xlink")){
            return add_popisy("Nenalezen atribut xmlns:xlink kořenového elementu <mets:mets>.", false, get_misto_chyby(metsMets));
        }
        String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xlink");
        if(!hodnota.equals("http://www.w3.org/1999/xlink")){
            return add_popisy("Atribut xmlns:xlink kořenového elementu <mets:mets> neobsahuje hodnotu http://www.w3.org/1999/xlink.", false, get_misto_chyby(metsMets));
        }
        return true;
    }
    
    //OBSAHOVÁ č.10 Element <mets:mets> obsahuje právě jeden dětský element <mets:metsHdr>.",
    private boolean pravidlo10(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false,chyba_neupresneno);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:metsHdr")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", false, get_misto_chyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:metsHdr")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:metsHdr>.", false, get_misto_chyby(metsMets));
        }
        return true;
    }
    
    //OBSAHOVÁ č.11 Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
    private boolean pravidlo11(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:dmdSec")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:dmdSec>.", false, get_misto_chyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:dmdSec")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:dmdSec>.", false, get_misto_chyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.12 Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
    private boolean pravidlo12(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:amdSec")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:amdSec>.", false, get_misto_chyby(metsMets));
        }       
        return true;
    }
    
    //OBSAHOVÁ č.13 Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
    private boolean pravidlo13(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:structMap")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:structMap>.", false, get_misto_chyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:structMap")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:structMap>.", false, get_misto_chyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.14 Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
    private boolean pravidlo14(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsHdr, "LASTMODDATE")){
            return add_popisy("Element <mets:metsHdr> nemá atribut LASTMODDATE.", false, get_misto_chyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.15 Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
    private boolean pravidlo15(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsHdr, "CREATEDATE")){
            return add_popisy("Element <mets:metsHdr> nemá atribut CREATEDATE.", false, get_misto_chyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
    private boolean pravidlo16(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, chyba_neupresneno);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return add_popisy("Nenalezen element <mets:agent>.", false, get_misto_chyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "ORGANIZATION")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return add_popisy("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", false, get_misto_chyby(metsHdr));
        }
        if(pocitadlo > 1){
            return add_popisy("Element <mets:metsHdr> obsahuje více elementů <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", false, get_misto_chyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
    private boolean pravidlo17(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, chyba_neupresneno);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return add_popisy("Nenalezen element <mets:agent>.", false, get_misto_chyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "INDIVIDUAL")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return add_popisy("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", false, get_misto_chyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
    private boolean pravidlo18(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", parsedSAX_Sipsoubor);
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, chyba_neupresneno);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttributValue(node, "ROLE", "CREATOR")){
               pocitadlo++;
               ch += get_misto_chyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return add_popisy("Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", false, ch);
        return true;
    }
    
    //OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
    private boolean pravidlo19(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", parsedSAX_Sipsoubor);
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, chyba_neupresneno);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttribut(node, "ID")){
               pocitadlo++;
               ch += get_misto_chyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return add_popisy("Element <mets:agent> neobsahuje atribut ID.", false, ch);
        return true;
    }
    
    //OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
    private boolean pravidlo20(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", parsedSAX_Sipsoubor);
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, chyba_neupresneno);
        int pocitadlo = 0;
        int pocitadlo2 = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:name")){
                if(!Helper.isStringNoEmpty(ValuesGetter.getXChild(node, "mets:name").getTextContent())){
                    pocitadlo2++;
                    ch += get_misto_chyby(node) + " ";
                }
            } 
            else{
               pocitadlo ++; 
               ch += get_misto_chyby(node) + " ";
            } 
        }
        if(pocitadlo != 0){
            String h = "";
            if(pocitadlo2 != 0){
                h = "Ďetský element <mets:name> má prázdnou hodnotu.";
            }
            return add_popisy("Element <mets:agent> neobsahuje právě jeden dětský element <mets:name>." + h, false, ch);
        }
        if(pocitadlo2 != 0){
            return add_popisy("Element <mets:agent> má nevyplněnou hodnotu u dětského elementu <mets:name>.", false, ch);
        }
        return true;
    }
    
    //21 volný index
    private boolean pravidlo21(){
        return true;
    }
    
    //OBSAHOVÁ č.22 Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
    private boolean pravidlo22(){
        if(metsDmdSec == null) return add_popisy("Nenalezen element <mets:dmdSec>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasChildWithName(metsDmdSec, "mets:mdWrap")){
            return add_popisy("Element <mets:dmdSec> neobsahuje žádný dětský element <mets:mdWrap>.", false, get_misto_chyby(metsDmdSec));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsDmdSec, "mets:mdWrap")){
            return add_popisy("Element <mets:dmdSec> obsahuje více než jeden dětský element <mets:mdWrap>.", true, get_misto_chyby(metsDmdSec));
        }
        return true;
    }
    
    //OBSAHOVÁ č.23 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
    private boolean pravidlo23(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPEVERSION")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", false, get_misto_chyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPEVERSION");
        if(!Helper.isStringNoEmpty(g)){
            return add_popisy("Atribut MDTYPEVERSION elementu <mets:mdWrap> má prázdnou hodnotu.", false, get_misto_chyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPEVERSION", "3.0")){
            return add_popisy("Atribut MDTYPEVERSION elementu <mets:mdWrap> neobsahuje hodnotu 3.0.", false, get_misto_chyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.24 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
    private boolean pravidlo24(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "OTHERMDTYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", false, get_misto_chyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "OTHERMDTYPE");
        if(!Helper.isStringNoEmpty(g)){
            return add_popisy("Atribut OTHERMDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, get_misto_chyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "OTHERMDTYPE", "NSESSS")){
            return add_popisy("Atribut OTHERMDTYPE elementu <mets:mdWrap> neobsahuje hodnotu NSESSS.", false, get_misto_chyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.25 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    private boolean pravidlo25(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", false, get_misto_chyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPE");
        if(!Helper.isStringNoEmpty(g)){
            return add_popisy("Atribut MDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, get_misto_chyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPE", "OTHER")){
            return add_popisy("Atribut MDTYPE elementu <mets:mdWrap> neobsahuje hodnotu OTHER.", false, get_misto_chyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.26 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    private boolean pravidlo26(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MIMETYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", false, get_misto_chyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MIMETYPE");
        if(!Helper.isStringNoEmpty(g)){
            return add_popisy("Atribut MIMETYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, get_misto_chyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MIMETYPE", "text/xml")){
            return add_popisy("Atribut MIMETYPE elementu <mets:mdWrap> neobsahuje hodnotu text/xml.", false, get_misto_chyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.27 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    private boolean pravidlo27(){
        if(xmlData == null) return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, chyba_neupresneno);
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMdWrap, "mets:xmlData")){
            return add_popisy("Element <mets:mdWrap> obsahuje více dětských elementů <mets:xmlData>.", false, get_misto_chyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.28 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, 
    // potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element 
    // <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.
    private boolean pravidlo28(){
        ArrayList<Node> krizove_odkazy_pevny_ano = get_krizove_odkazy_pevny_ano();
        if(!krizove_odkazy_pevny_ano.isEmpty()){
            return true;
        }
        else{
           if(xmlData == null){
                return add_popisy("Nenalezen element <mets:xmlData>.", false, chyba_neupresneno);
            }
            if(xmlData.getChildNodes().getLength() == 0){
                return add_popisy("Element <mets:xmlData> neobsahuje žádné dětské elementy.", false, get_misto_chyby(xmlData));
            }
            ArrayList<Node> list = ValuesGetter.get_Node_Children(xmlData);
            if(!ValuesGetter.maRodicPouzeTytoPotomky(xmlData, "nsesss:Dil", "nsesss:Dokument", "nsesss:Spis")){
                String ch = "";
                for(int i = 0; i < list.size(); i++){
                    Node node = list.get(i);
                    String name = node.getNodeName();
                    if(!name.equals("nsesss:Dokument") || !name.equals("nsesss:Spis") || !name.equals("nsesss:Dil")){
                        ch += get_misto_chyby(list.get(i)) + " "; 
                    }
                }
                return add_popisy("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", false, ch);
            }
            if(list.size() > 1){
                String ch = "";
                String pop = "";
                for(int i = 1; i < list.size(); i++){
                    Node node = list.get(i);
                    ch += get_misto_chyby(node) + " "; 
                    pop += " " + getJmenoIdentifikator(node);
                }
                return add_popisy("Element <mets:xmlData> obsahuje více dětských elementů." + pop, false, ch);
            } 
        }
        return true;
    }
    
    //OBSAHOVÁ č.29 Pokud existuje element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom pro každý jeho výskyt
    // obsahuje element <mets:dmdSec> v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> dětský element <nsesss:Dil> nebo 
    // <nsesss:Dokument> nebo <nsesss:Spis> se stejnou hodnotou v dětském elementu <nsesss:Identifikator> a v jeho atributu zdroj.",
    private boolean pravidlo29(){
        ArrayList<Node> krizove_odkazy_pevny_ano = get_krizove_odkazy_pevny_ano();
        if(krizove_odkazy_pevny_ano == null || krizove_odkazy_pevny_ano.isEmpty()) return true;
        
        ArrayList<Node> list = ValuesGetter.get_Node_Children(xmlData);
        if(!ValuesGetter.maRodicPouzeTytoPotomky(xmlData, "nsesss:Dil", "nsesss:Dokument", "nsesss:Spis")){
                String ch = "";
                for(int i = 0; i < list.size(); i++){
                    Node node = list.get(i);
                    String name = node.getNodeName();
                    if(!name.equals("nsesss:Dokument") || !name.equals("nsesss:Spis") || !name.equals("nsesss:Dil")){
                        ch += get_misto_chyby(node) + " "; 
                    }
                }
                return add_popisy("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", false, ch);
        }
        
        for(int i = 0; i < krizove_odkazy_pevny_ano.size(); i++){
            Node krizovyOdkaz = krizove_odkazy_pevny_ano.get(i);
            Node materska_zakl_entita_eu = ValuesGetter.getXParent(krizovyOdkaz, "nsesss:Souvislosti", "nsesss:EvidencniUdaje");
            
            if(materska_zakl_entita_eu == null){
                return add_popisy("Element <nsesss:KrizovyOdkaz> je špatně zatříděn. Nenalezeny elementy <nsesss:Souvislosti> a <nsesss:EvidencniUdaje>.", false, get_misto_chyby(krizovyOdkaz));
            }
            Node za_ent = materska_zakl_entita_eu.getParentNode();
            Node identifikator_me = ValuesGetter.getXChild(materska_zakl_entita_eu, "nsesss:Identifikace", "nsesss:Identifikator");
            if(identifikator_me == null){
                return add_popisy("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí element <nsesss:Identifikator>.", false, get_misto_chyby(za_ent));
            }
            if(!ValuesGetter.hasAttribut(identifikator_me, "zdroj")){
                return add_popisy("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí atribut zdroj u elementu <nsesss:Identifikator>.", false, get_misto_chyby(identifikator_me));
            }
            String zdroj_me = ValuesGetter.getValueOfAttribut(identifikator_me, "zdroj");
            String ident_me = identifikator_me.getTextContent();
            Node identifikator_ko = ValuesGetter.getXChild(krizovyOdkaz, "nsesss:Identifikator");
            if(identifikator_ko == null){
                return add_popisy("Element <nsesss:KrizovyOdkaz> nemá dětský element <nsesss:Identifikator>.", false, get_misto_chyby(krizovyOdkaz));
            }
            if(!ValuesGetter.hasAttribut(identifikator_ko, "zdroj")){
                return add_popisy("Dětský element <nsesss:Identifikator> elementu <nsesss:KrizovyOdkaz> nemá atribut zdroj.", false, get_misto_chyby(identifikator_ko));
            }
            String zdroj_ko = ValuesGetter.getValueOfAttribut(identifikator_ko, "zdroj");
            String ident_ko = identifikator_ko.getTextContent();
            
            if(zdroj_me.equals(zdroj_ko) && ident_me.equals(ident_ko)){
                return add_popisy("Element <nsesss:KrizovyOdkaz> odkazuje na vlastní základní entitu.", false, get_misto_chyby(krizovyOdkaz));
            }
            
            int pocitadlo = 0;
            String ch = "";
                for(int k = 0; k < zakladniEntity.size(); k++){
                    Node zentita = zakladniEntity.get(k);
                    Node id_ze = ValuesGetter.getXChild(zentita, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                    if(id_ze == null){
                        return add_popisy("Nenalezen element <nsesss:Identifikator> základní entity.", false, get_misto_chyby(zentita));
                    }
                    if(!ValuesGetter.hasAttribut(id_ze, "zdroj")){
                        return add_popisy("Nenalezen atribut zdroj elementu <nsesss:Identifikator>.", false, get_misto_chyby(id_ze));
                    }
                    String hodnotaZdrojMatEnt = ValuesGetter.getValueOfAttribut(id_ze, "zdroj");
                    String hodnotaIdentMatEnt = id_ze.getTextContent();
                    if(zdroj_ko.equals(hodnotaZdrojMatEnt) &&  ident_ko.equals(hodnotaIdentMatEnt)){
                        pocitadlo++;
                        ch += get_misto_chyby(zentita) + " ";
                    }
                }
                if(pocitadlo == 0){
                    return add_popisy("Nenalezena žádná základní entita, na kterou odkazuje element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + ".", false, get_misto_chyby(krizovyOdkaz));
                }
                if(pocitadlo > 1){
                    return add_popisy("Element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + " odkazuje na více základních entit.", false, ch + get_misto_chyby(krizovyOdkaz));
                }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.30 Každý element <mets:amdSec> obsahuje atribut ID.",
    private boolean pravidlo30(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:amdSec>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasAttribut(node, "ID")){
                return add_popisy("Element <mets:amdSec> nemá atribut ID.", false, get_misto_chyby(node));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.31 Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
    private boolean pravidlo31(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:amdSec>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:digiprovMD")){
                return add_popisy("Element <mets:amdSec> neobsahuje právě jeden dětský element <mets:digiprovMD>.", false, get_misto_chyby(node));
            }
        }
        return true;
    }
    
    //32. OBSAHOVÁ 93a. Každá entia věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.
    private boolean pravidlo32(){
        NodeList vs_list = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
        for(int i = 0; i < vs_list.getLength(); i++){
            Node vs = vs_list.item(i);
            Node spl = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(spl != null){
                Node jsz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node pusz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");     
                if(jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs), false, get_misto_chyby(vs));
                if(pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs), false, get_misto_chyby(vs));
                if(!jsz.getTextContent().equals(pusz.getTextContent())){
                    return add_popisy("Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs), false, get_misto_chyby(jsz) + " " + get_misto_chyby(pusz));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
    private boolean pravidlo33(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(digiprovMD, "mets:mdWrap");
            if(list.isEmpty()){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(list.size() > 1){
                return add_popisy("Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.34 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s 
    // hodnotou 1.0.",
    private boolean pravidlo34(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPEVERSION")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", false, get_misto_chyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPEVERSION");
            if(!g.equals("1.0")){
                return add_popisy("Atribut MDTYPEVERSION neobsahuje hodnotu 1.0.", false, get_misto_chyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.35 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
    private boolean pravidlo35(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "OTHERMDTYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", false, get_misto_chyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "OTHERMDTYPE");
            if(!g.equals("TP")){
                return add_popisy("Atribut OTHERMDTYPE neobsahuje hodnotu TP.", false, get_misto_chyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.36 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    private boolean pravidlo36(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", false, get_misto_chyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPE");
            if(!g.equals("OTHER")){
                return add_popisy("Atribut MDTYPE neobsahuje hodnotu OTHER.", false, get_misto_chyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.37 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    private boolean pravidlo37(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MIMETYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", false, get_misto_chyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MIMETYPE");
            if(!g.equals("text/xml")){
                return add_popisy("Atribut MIMETYPE neobsahuje hodnotu text/xml.", false, get_misto_chyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.38 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    private boolean pravidlo38(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWprap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWprap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            if(ValuesGetter.getXChild(mdWprap, "mets:xmlData") == null){
                return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, get_misto_chyby(mdWprap));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(mdWprap, "mets:xmlData")){
                return add_popisy("Element <mets:mdWrap> neobsahuje právě jeden dětský element <mets:xmlData>.", false, get_misto_chyby(mdWprap));
            }
        }

        return true;
    }
    
    //OBSAHOVÁ č.39 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
    private boolean pravidlo39(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWr = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWr == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, get_misto_chyby(digiprovMD));
            }
            Node xDt = ValuesGetter.getXChild(mdWr, "mets:xmlData");
            if(xDt == null){
                return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, get_misto_chyby(mdWr));
            }
            Node tlo = ValuesGetter.getXChild(xDt, "tp:TransakcniLogObjektu");
            if(tlo == null){
                return add_popisy("Element <mets:xmlData> neobsahuje žádný dětský element <tp:TransakcniLogObjektu>.", false, get_misto_chyby(xDt));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(xDt , "tp:TransakcniLogObjektu")){
                return add_popisy("Element <mets:xmlData> neobsahuje právě jeden dětský element <tp:TransakcniLogObjektu>.", false, get_misto_chyby(xDt));
            }
        }

        return true;
    }
    
    //OBSAHOVÁ č.40 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <mets:mets> právě jeden dětský element <mets:fileSec>.",
    private boolean pravidlo40(){
        if(dokumenty == null) return true;
        for(int i =0; i < dokumenty.size(); i++){
            Node dokument = dokumenty.get(i);
            Node analogovyDokument = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(analogovyDokument == null){
                return add_popisy("Element <nsesss:Dokument> " + getIdentifikatory(dokument) +" neobsahuje element <nsesss:AnalogovyDokument>.", false, get_misto_chyby(dokument));
            }
            boolean maHodnotuNe = analogovyDokument.getTextContent().toLowerCase().equals("ne");
            if(maHodnotuNe){
                if(metsMets == null){
                    return add_popisy("SIP nemá kořenový element <mets:mets>.", false, chyba_neupresneno);
                }
                if(ValuesGetter.getXChild(metsMets, "mets:fileSec") == null){
                    return add_popisy("Element <mets:mets> neobsahuje žádný element <mets:fileSec>.", false, get_misto_chyby(metsMets));
                }
                if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:fileSec")){
                    return add_popisy("Element <mets:mets> neobsahuje právě jeden dětský element <mets:fileSec>.", false, get_misto_chyby(metsMets));
                }
                else{
                    return true;
                }
            }
        }
        return true;
    }
    
    // OBSAHOVÁ č.54a Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, 
    // potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou 
    // příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci 
    // amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, 
    // <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> 
    // odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), 
    // přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena 
    // v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).
    private boolean pravidlo41(){
        ArrayList<Node> krizoveodkazy = get_krizove_odkazy_pevny_ano();
        if(krizoveodkazy.isEmpty()) return true;
        // TEST mets:amdSec
        NodeList amdSec_nodelist = ValuesGetter.getAllAnywhere("mets:amdSec", parsedSAX_Sipsoubor);
        if(amdSec_nodelist == null){
            return add_popisy("Nenalezen element <mets:amdSec>.", false, chyba_neupresneno);
        }
        StructMap_Obj_return_bol_AL_Obj_amdSec ret_amd = StructMap_Metods.get_amdSec_list(amdSec_nodelist);
        if(!ret_amd.bol){
            String mistoCh = "";
            for(int i = 0; i < ret_amd.list.size(); i++){
                mistoCh += get_misto_chyby(ret_amd.list.get(i).node) + " ";  
            }
            String hlaska = "Nalezena chyba u elementu <mets:amdSec>.";
            if(ret_amd.list.size() > 1) hlaska = "Nalezeny chyby u elementů <mets:amdSec>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        ArrayList<StructMap_Obj_amdSec> amdSec_list = ret_amd.list;
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_amdSec = StructMap_Metods.test_amdSec_uniqueness(amdSec_list);
        if(!jedinecnost_amdSec.bol){
            String mistoCh = "";
            for(int i = 0; i < jedinecnost_amdSec.node_list.size(); i++){
                mistoCh += get_misto_chyby(jedinecnost_amdSec.node_list.get(i)) + " ";  
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:amdSec>.";
            if(ret_amd.list.size() > 1) hlaska = "Nalezeny chyby duplicity u elementů <mets:amdSec>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        // KONEC TESTU mets:amdSec
        
        // TEST mets_div
        NodeList metsdiv_nodelist = ValuesGetter.getAllAnywhere("mets:div", parsedSAX_Sipsoubor);
        if(metsdiv_nodelist == null){
            return add_popisy("Nenalezen element <mets:div>.", false, chyba_neupresneno);
        }
        StructMap_Obj_return_bol_AL_Obj_metsdiv ret_metsdiv = StructMap_Metods.get_metsdiv_list(metsdiv_nodelist);
        if(!ret_metsdiv.bol){
            String mistoCh = "";
            for(int i = 0; i < ret_metsdiv.list.size(); i++){
                mistoCh += get_misto_chyby(ret_metsdiv.list.get(i).metsdiv) + " ";  
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if(ret_amd.list.size() > 1) hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        ArrayList<StructMap_Obj_metsdiv> metsdiv_list = ret_metsdiv.list;
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_metsdiv = StructMap_Metods.test_metsdiv_uniqueness(metsdiv_list);
        if(!jedinecnost_metsdiv.bol){
            String mistoCh = "";
            for(int i = 0; i < jedinecnost_metsdiv.node_list.size(); i++){
                mistoCh += get_misto_chyby(jedinecnost_metsdiv.node_list.get(i)) + " ";  
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:div>.";
            if(ret_metsdiv.list.size() > 1) hlaska = "Nalezeny chyby duplicity u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        // KONEC TESTU mets:div
        
        // TEST dmdSec
        StructMap_Obj_return_bol_AL_Obj_dmdSec ret_dmdsec = StructMap_Metods.get_dmdsec_list();
        if(!ret_dmdsec.bol){
            String mistoCh = "";
            for(int i = 0; i < ret_dmdsec.list.size(); i++){
                mistoCh += get_misto_chyby(ret_dmdsec.list.get(i).node) + " ";  
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if(ret_dmdsec.list.size() > 1) hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        ArrayList<StructMap_Obj_dmdSec> metsdmdSec_list = ret_dmdsec.list;
        // KONEC TESTU dmdSec
        
        // TEST amdSec to metsdiv
        StructMap_Obj_return_bol_AL_node test_amd_to_div = StructMap_Metods.compare_amdSec_with_metsDiv(amdSec_list, metsdiv_list);
        if(!test_amd_to_div.bol){
            if(test_amd_to_div.node_list.size() == 1){
                return add_popisy("Element <mets:amdSec> neodkazuje na žádný element <mets:div>.", false, get_misto_chyby(test_amd_to_div.node_list.get(0)));
            }
            else{
                String ch = get_misto_chyby(test_amd_to_div.node_list.get(0));
                for(int i = 1; i < test_amd_to_div.node_list.size(); i++){
                    ch+= " " + get_misto_chyby(test_amd_to_div.node_list.get(i));
                }
                return add_popisy("Element <mets:amdSec> odkazuje na více elementů <mets:div>.", false, ch); 
            }
        }
        // TEST metsdiv to amd 
        StructMap_Obj_return_bol_AL_node test_div_toamd = StructMap_Metods.compare_metsDiv_with_amdSec(amdSec_list, metsdiv_list);
        if(!test_div_toamd.bol){
            if(test_div_toamd.node_list.size() == 1){
                return add_popisy("Element <mets:div> neodkazuje na žádný element <mets:amdSec>.", false, get_misto_chyby(test_div_toamd.node_list.get(0)));
            }
            else{
                String ch = get_misto_chyby(test_div_toamd.node_list.get(0));
                for(int i = 1; i < test_div_toamd.node_list.size(); i++){
                    ch+= " " + get_misto_chyby(test_div_toamd.node_list.get(i));
                }
                return add_popisy("Element <mets:div> odkazuje na více elementů <mets:amdScec>.", false, ch); 
            }
        }
        // KONEC TESTU amdSec + metsdiv
        
        //TEST AMD TO DMDSEC
        StructMap_Obj_return_bol_AL_node test_amd_to_dmd = StructMap_Metods.compare_amdSec_dmdSec(amdSec_list, metsdmdSec_list);
        if(!test_amd_to_dmd.bol){
            if(test_amd_to_dmd.node_list.size() == 1){
                String name = test_amd_to_dmd.node_list.get(0).getNodeName();
                if(name.equals("mets:amdSec")){
                    return add_popisy("Element <mets:amdSec> neodkazuje na žádný element v <mets:dmdSec>.", false, get_misto_chyby(test_amd_to_dmd.node_list.get(0)));
                }
                else{
                    return add_popisy("Element <" + name + "> neodkazuje na žádný element <mets:amdSec>.", false, get_misto_chyby(test_amd_to_dmd.node_list.get(0)));
                }       
            }
            else{
                String ch = get_misto_chyby(test_amd_to_dmd.node_list.get(0));
                for(int i = 1; i < test_amd_to_dmd.node_list.size(); i++){
                    ch+= " " + get_misto_chyby(test_amd_to_dmd.node_list.get(i));
                }
                return add_popisy("Elementy v <mets:dmdSec> neodkazují na žádný element v <mets:amdSec>.", false, ch); 
            }       
        }
        //KONEC TESTU AMD TO DMDSEC
        
        //TEST DIV TO DMDSEC
//        StructMap_Obj_return_bol_AL_node test_div_to_dmd = StructMap_Metods.compare_metsDiv_with_dmdSec(metsdmdSec_list, metsdiv_list);
//        if(!test_div_to_dmd.bol){
//            if(test_div_to_dmd.node_list.size() == 1){
//                String name = test_div_to_dmd.node_list.get(0).getNodeName();
//                if(name.equals("mets:div")){
//                    return add_popisy("Element <mets:div> neodkazuje na žádný element v <mets:dmdSec>.", chyba_vygenerovan, false, get_misto_chyby(test_div_to_dmd.node_list.get(0)));
//                }
//                else{
//                    return add_popisy("Element <" + name + "> neodkazuje na žádný element <mets:div>.", chyba_vygenerovan, false, get_misto_chyby(test_div_to_dmd.node_list.get(0)));
//                }       
//            }
//            else{
//                String ch = get_misto_chyby(test_div_to_dmd.node_list.get(0));
//                for(int i = 1; i < test_div_to_dmd.node_list.size(); i++){
//                    ch+= " " + get_misto_chyby(test_div_to_dmd.node_list.get(i));
//                }
//                return add_popisy("Elementy v <mets:dmdSec> neodkazují na žádný element <mets:div>.", chyba_vygenerovan, false, ch); 
//            }       
//        }        
        //KONEC TESTU DIV TO DMDSEC
        
        //TEST STRUKTURY PODLE METS DIV
        StructMap_Obj_return_bol_AL_node test_struktury = StructMap_Metods.compare_metsDiv_with_dmdSec_structure(metsdiv_list, metsdmdSec_list);
        if(!test_struktury.bol){
            if(test_struktury.node_list.size() == 1){
                return add_popisy("Element <mets:div> je špatně zatříděn.", false, get_misto_chyby(test_struktury.node_list.get(0)));
            }
            else{
                String ch = get_misto_chyby(test_struktury.node_list.get(0));
                for(int i = 1; i < test_struktury.node_list.size(); i++){
                    ch+= " " + get_misto_chyby(test_struktury.node_list.get(i));
                }
                return add_popisy("Element <mets:div> a jeho rodičovský element <mets:div> odkazují na chybné elementy v <mets:dmdSec>.", false, ch);
            }
        }
        //KONEC TESTU STRUKTURY
        
        return true;
    }
    
    // OBSAHOVÁ č.61a Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a 
    // současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.
    private boolean pravidlo42(){
        NodeList dokuments = ValuesGetter.getAllAnywhere("nsesss:Dokument", parsedSAX_Sipsoubor);
        if(dokuments == null){
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < dokuments.getLength(); i++)
        {   
            Node dokument = dokuments.item(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument") != null){
                Node mnozstvi = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument", "nsesss:VytvoreneMnozstvi");
                if(mnozstvi == null){
                    return add_popisy("Nenalezen povinný element <nsesss:VytvoreneMnozstvi>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
                }
                else{
                    String s = mnozstvi.getTextContent();
                    if(!Helper.isStringNoEmpty(s)){
                        return add_popisy("Element <nsesss:VytvoreneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(mnozstvi));
                    }
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.94a. Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), 
    // která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), 
    // obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s 
    // hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem.
    private boolean pravidlo43(){
        NodeList vecneSkupiny = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
//        NodeList typoveSpisy = ValuesGetter.getAllAnywhere("nsesss:TypovySpis", parsedDOM_SipSoubor);
        NodeList soucasti = ValuesGetter.getAllAnywhere("nsesss:Soucast", parsedSAX_Sipsoubor);
        
        if(vecneSkupiny != null){
            for(int i = 0; i < vecneSkupiny.getLength(); i++){
                Node vecnaSkupina = vecneSkupiny.item(i);
                boolean dite = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina") != null;
                if(dite){
                    Node plneUrceny_node = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                    if(plneUrceny_node == null){
                        return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vecnaSkupina), false, get_misto_chyby(vecnaSkupina));
                    }
                    String hodnota = plneUrceny_node.getTextContent();
                    if(!sz_ma_oddelovac_vsobe(hodnota)){
                        return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. " + getJmenoIdentifikator(vecnaSkupina), false, get_misto_chyby(plneUrceny_node));
                    }
                }
            }
        }
//        if(typoveSpisy!= null){
//            for(int i = 0; i < typoveSpisy.getLength(); i++){
//                Node plneUrceny_node = ValuesGetter.getXChild(typoveSpisy.item(i), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
//                if(plneUrceny_node == null){
//                    return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>.", false, get_misto_chyby(typoveSpisy.item(i)));
//                }
//                String hodnota = plneUrceny_node.getTextContent();
//                if(!sz_ma_oddelovac_vsobe(hodnota)){
//                    return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač.", false, get_misto_chyby(plneUrceny_node));
//                } 
//            }
//        }
        if(soucasti != null){
            for(int i = 0; i < soucasti.getLength(); i++){
                Node soucast = soucasti.item(i);     
                Node plneUrceny_node = ValuesGetter.getXChild(soucast, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                if(plneUrceny_node == null){
                    return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(soucast), false, get_misto_chyby(soucast));
                }
                String hodnota = plneUrceny_node.getTextContent();
                if(!sz_ma_oddelovac_vsobe(hodnota)){
                    return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. " + getJmenoIdentifikator(soucast), false, get_misto_chyby(plneUrceny_node));
                } 
            }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.44 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>.",
    private boolean pravidlo44(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        NodeList nodeListKomponenty = ValuesGetter.getAllAnywhere("nsesss:Komponenta", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        if(nodeListKomponenty == null || nodeListKomponenty.getLength() != nodeListMetsFile.getLength()){
            return add_popisy("Nenalezen žádný element <nsesss:Komponenta>.", false, chyba_neupresneno);
        }
        ArrayList<Obj_Node_String> files = new ArrayList<>(), komponents = new ArrayList<>();
        for(int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            Node komponenta = nodeListKomponenty.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "DMDID")){
                return add_popisy("Element <mets:file> nemá atribut DMDID.", false, get_misto_chyby(metsFile));
            }
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            files.add(new Obj_Node_String(metsFile, dmdid));
            if(!ValuesGetter.hasAttribut(komponenta, "ID")){
                return add_popisy("Element <nsesss:Komponenta> nemá atribut ID. " + getJmenoIdentifikator(komponenta), false, get_misto_chyby(komponenta));
            }
            String id = ValuesGetter.getValueOfAttribut(komponenta, "ID");
            komponents.add(new Obj_Node_String(komponenta, id));
        }
        String ch = "";
        String misto_ch = "";
        boolean vysledek = true;
        for(int f = 0; f < files.size(); f++){
            Obj_Node_String file = files.get(f);
            ArrayList<Obj_Node_String>  f_list = Comparator.obj_Node_String_howmany_in_list(file, komponents);
            if(f_list.size() != 1){
                vysledek = false;
                String mch = file.get_node().getUserData("lineNumber").toString();
                if(f_list.isEmpty()){
                    ch += "Element <mets:file> na řádku: " + mch +". neodkazuje na žádnou komponentu. ";
                    misto_ch += get_misto_chyby(file.get_node()) + " ";
                }
                else{
                    String komp = "";
                    for(int i = 0; i < f_list.size(); i++){
                        Obj_Node_String file2 = f_list.get(i);
                        if(i != f_list.size()-1){
                            komp += get_misto_chyby(file2.get_node()) + " ";

                        }
                        else{
                            komp += get_misto_chyby(file2.get_node()) + " ";
                        }
                    }
                    ch += "Element <mets:file> na řádku: " + mch +". odkazuje na více komponent: " + komp;
                    misto_ch += komp + " ";
                }
            }  
        }
        for(int k = 0; k < komponents.size(); k++){
            Obj_Node_String komponenta = komponents.get(k);
            ArrayList<Obj_Node_String>  k_list = Comparator.obj_Node_String_howmany_in_list(komponenta, files);
            if(k_list.size() != 1){
                Node komp = komponenta.get_node();
                vysledek = false;
                String mch = komp.getUserData("lineNumber").toString();
                if(k_list.isEmpty()){
                    ch += "Element <nsesss:Komponenta> na řádku: " + mch +". neodkazuje na žádný element <mets:file> " + getJmenoIdentifikator(komponenta.get_node());
                    misto_ch += get_misto_chyby(komp);
                }
                else{
                    String fil = "";
                    for(int i = 0; i < k_list.size(); i++){
                        Obj_Node_String komponenta2 = k_list.get(i);
                        Node kom = komponenta2.get_node();
                        if(i != k_list.size()-1){
                            fil += get_misto_chyby(kom) + " ";

                        }
                        else{
                            fil += get_misto_chyby(kom) + " ";
                        }
                    }
                    ch += "Element <nsesss:Komponenta> na řádku: " + mch +". odkazuje na více elementů <mets:file>: " + fil + " " + getJmenoIdentifikator(komp);
                    misto_ch += fil + " ";
                }
            }  
        }
        if(!vysledek){
            return add_popisy(ch, vysledek, misto_ch);
        }
        
        
        return vysledek;
    }
    
    //OBSAHOVÁ č.45 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového formátu příslušné komponenty číselníku IANA na URL: http://www.iana.org/assignments/media-types/media-types.xhtml.",
    private boolean pravidlo45() throws IOException{
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "MIMETYPE")){
                return add_popisy("Element <mets:file> nemá atribut MIMETYPE.", false, get_misto_chyby(metsFile));
            }
            String mimeType = ValuesGetter.getValueOfAttribut(metsFile, "MIMETYPE"); // application/pdf, text/plain
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Element <mets:file> nemá dětský element <mets:FLocat>.", false, get_misto_chyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> nemá atribut xlink:href.", false, get_misto_chyby(flocat));
            }
            
            String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
            xlinkHref = HelperString.edit_path(xlinkHref);
            //kvůli komponenty/
            int sep = xlinkHref.lastIndexOf(File.separator);
            if(sep == -1) return add_popisy("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: " + xlinkHref + ".", false, get_misto_chyby(flocat));
            String ko_over = xlinkHref.substring(0, sep);
            if(!ko_over.equals("komponenty")){
                return add_popisy("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: " + xlinkHref + ".", false, get_misto_chyby(flocat));
            }
//            String final_path;
//            if(xlinkHref.contains("komponenty")){
//                final_path = xlinkHref.replaceFirst("komponenty", "");
//            }
//            else{
//               final_path = xlinkHref; 
//            }
            
//            String cestaKeKomponente = sipSoubor.get_Package_Information().path_to_directory_komponenty.concat(File.separator + final_path);
            
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(sipSoubor);
            File file = new File(cestaKeKomponente);
            file = new File(file.getParentFile().getAbsolutePath() + File.separator + xlinkHref);
            if(!file.exists()){
                file = get_komponenta(file.getName());
                if(file == null || !file.exists()){
//                if(file == null){
//                    if(!final_path.contains(".")){
//                        return add_popisy("Nenalezena příslušná komponenta ve složce komponenty.", "SIP soubor byl špatně vygenerován, chybí komponenty.", false, get_misto_chyby(flocat) + " " + "V atributu xlink:href uveden chybný odkaz: " + name + ".");
//                    }
                    if(xlinkHref.contains(File.separator)){
                        int s = xlinkHref.lastIndexOf(File.separator);
                        String g = xlinkHref.substring(s+1);
                        xlinkHref = g;
                    }
                    return add_popisy("Nenalezena příslušná komponenta ve složce komponenty.", false, "Chybí soubor: " + xlinkHref + ".");
                }
            }  
            boolean jeSpravnyMime = FileMyme_Checker.getCompareType(file, mimeType);
            if(!jeSpravnyMime){
                String file_content_type = FileMyme_Checker.mimetype;
                // vyjimky sem
                if(file_content_type.equals("application/x-zip-compressed") || file_content_type.equals("application/x-dbf") || file_content_type.equals("application/pkcs7-signature")){
                    // datová schránka
                    // application/vnd.software602.filler.form-xml-zip
                    if(file_content_type.equals("application/pkcs7-signature")){
                        if(!mimeType.equals("application/vnd.software602.filler.form-xml-zip")){
                            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
                            BufferedReader reader = new BufferedReader(input);
                            boolean jedatovka = false;
                            while (!jedatovka && reader.readLine() != null) {
                                String line = reader.readLine();
                                if(line.contains("</q:dmHash><q:dmQTimestamp>")){
                                    jedatovka = true;
                                    input.close();
                                    reader.close();
                                }
                            } 
                            if(!jedatovka) return add_popisy("Komponenta je soubor typu: " + file_content_type + ", ale její deklarovaný MIMETYPE je: " + mimeType + ". Nejedná se o soubor datové schránky", false, "Soubor: " + file.getName() + ".");
                        }
                    }
                    if(file_content_type.equals("application/x-zip-compressed") && !mimeType.equals("application/zip")){
                        return add_popisy("Komponenta je soubor typu: " + file_content_type + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".", false, "Soubor: " + file.getName() + ".");
                    }
                    
                    if(file_content_type.equals("application/x-dbf") && !mimeType.equals("application/vnd.software602.filler.form-xml-zip")){
                        return add_popisy("Komponenta je soubor typu: " + file_content_type + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".", false, "Soubor: " + file.getName() + ".");
                    }
                }    
                else{
                    return add_popisy("Komponenta je soubor typu: " + file_content_type + ", ale její deklarovaný MIMETYPE je: " + mimeType + ".", false, "Soubor: " + file.getName() + ".");
                }
            }
        }

        return true;
    }
    
    //OBSAHOVÁ č.46 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
    private boolean pravidlo46(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile== null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
                return add_popisy("Element <mets:file> nemá atribut CHECKSUMTYPE.", false, get_misto_chyby(metsFile));
            }
            String hodnota = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
            if(!hodnota.equals("SHA-256") && !hodnota.equals("SHA-512")){
                return add_popisy("Atribut CHECKSUMTYPE obsahuje nepovolenou hodnotu: " + hodnota + ".", false, get_misto_chyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.47 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
    private boolean pravidlo47(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
                return add_popisy("Element <mets:file> nemá atribut CHECKSUMTYPE.", false, get_misto_chyby(metsFile));
            }
            String atributChecksumType = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", false, get_misto_chyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> nemá atribut xlink:href.", false, get_misto_chyby(flocat));
            }
            String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
            xlinkHref = HelperString.edit_path(xlinkHref);
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(sipSoubor);
            File komponenta = new File(cestaKeKomponente);
            komponenta = new File(komponenta.getParentFile().getAbsolutePath() + File.separator + xlinkHref);
            if(!komponenta.exists()){
//                komponenta = get_komponenta(komponenta.getName());
//            }
//            if(komponenta != null){
//                cestaKeKomponente = komponenta.getAbsolutePath();
                    if(xlinkHref.contains(File.separator)){
                        int s = xlinkHref.lastIndexOf(File.separator);
                        String g = xlinkHref.substring(s+1);
                        xlinkHref = g;
                    }
                    return add_popisy("Nenalezena příslušná komponenta ve složce komponenty.", false, "Chybí soubor: " + xlinkHref + ".");
            }
            String spoctenyCheckSum = "";
            try{
                if(atributChecksumType.equals("SHA-512") || atributChecksumType.equals("SHA-256")){
                    if(atributChecksumType.equals("SHA-512"))
                    {
                        InputStream is = new FileInputStream(komponenta);                
                        spoctenyCheckSum = DigestUtils.sha512Hex(is);              
                    }

                    if(atributChecksumType.equals("SHA-256"))
                    {
                        InputStream is = new FileInputStream(komponenta);                
                        spoctenyCheckSum = DigestUtils.sha256Hex(is);              
                    }                    
                }
                else{
                    if(xlinkHref.contains(File.separator)){
                        int s = xlinkHref.lastIndexOf(File.separator);
                        String g = xlinkHref.substring(s+1);
                        xlinkHref = g;
                    }
                    return add_popisy("Nepovolený algoritmus v atributu CHECKSUMTYPE.", false, get_misto_chyby(metsFile) + " Komponenta: " + xlinkHref + ".");
                }

            } 
            catch (IOException ex){
//                    sipSoubor.get_SIP_Validation().add_rule_obsahova(47, false, seznam_pravidla[47], ex.getLocalizedMessage(), "chyba v kódování SIP souboru");
                if(xlinkHref.contains(File.separator)){
                    int s = xlinkHref.lastIndexOf(File.separator);
                    String g = xlinkHref.substring(s+1);
                    xlinkHref = g;
                }
                return add_popisy("Nepodařilo se spočítat checksum souboru: " + xlinkHref + ".", false, "Nenalezen soubor " + xlinkHref + ".");
            }
            
            if(!ValuesGetter.hasAttribut(nodeListMetsFile.item(i), "CHECKSUM")){
                return add_popisy("Element <mets:file> nemá atribut CHECKSUM.", false, get_misto_chyby(metsFile));
            }
            
            String checkSum = ValuesGetter.getValueOfAttribut(nodeListMetsFile.item(i), "CHECKSUM");
            checkSum = checkSum.toLowerCase();        
            spoctenyCheckSum = spoctenyCheckSum.toLowerCase();

            if(!spoctenyCheckSum.equals(checkSum)){
                if(xlinkHref.contains(File.separator)){
                    int s = xlinkHref.lastIndexOf(File.separator);
                    String g = xlinkHref.substring(s+1);
                    xlinkHref = g;
                }
                return add_popisy("CHECKSUM neodpovídá CHECKSUMTYPE.", false, get_misto_chyby(nodeListMetsFile.item(i)) + " Soubor: " + xlinkHref + ".");
            }         
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.48 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
    private boolean pravidlo48(){

        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "SIZE")){
                return add_popisy("Element <mets:file> neobsahuje atribut SIZE.", false, get_misto_chyby(metsFile));
            }
            String hodnotaVelikosti = ValuesGetter.getValueOfAttribut(metsFile, "SIZE");
            popisChyby = "nenalezen atribut xlink:href elementu <mets:FLocat>";
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", false, get_misto_chyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:href.", false, get_misto_chyby(flocat));
            }
            String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
            xlinkHref = HelperString.edit_path(xlinkHref);
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(sipSoubor);
            File file = new File(cestaKeKomponente);
            file = new File(file.getParentFile().getAbsolutePath() + File.separator + xlinkHref);
            if(!file.exists()){
//                file = get_komponenta(file.getName());
//                if(file == null){
//                    if(!final_path.contains(".")){
//                        return add_popisy("Nenalezena příslušná komponenta v složce komponenty.", seznam_popis[48], false, get_misto_chyby(flocat) + " V atributu xlink:href uveden chybný odkaz: " + name + ".");
//                    }   
                    return add_popisy("Nenalezena příslušná komponenta v složce komponenty.", false, get_misto_chyby(flocat) + " Soubor: " + xlinkHref + ".");
//                }
            }
            String velikost = String.valueOf(file.length());
            if(!velikost.equals(hodnotaVelikosti)){
                return add_popisy("Velikost komponenty není totožná s metadaty.", false, get_misto_chyby(metsFile) + " Komponenta: " + file.getName() + ".");
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.49 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
    private boolean pravidlo49(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if (!ValuesGetter.hasAttribut(metsFile, "CREATED")){
                return add_popisy("Elenemt <mets:file> neobsahuje atribut CREATED.", false, get_misto_chyby(nodeListMetsFile.item(i)));
            }
        }   
        return true;
    }
    
    //OBSAHOVÁ č.50 Pokud existuje jakýkoli element <mets:file>, každý obsahuje právě jeden dětský element <mets:FLocat>.",
    private boolean pravidlo50(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        for(int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(ValuesGetter.getXChild(metsFile, "mets:FLocat") == null){
                return add_popisy("Element <mets:file> nemá žádný dětský element <mets:FLocat>.", false, get_misto_chyby(metsFile));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(nodeListMetsFile.item(i), "mets:FLocat")){
                return add_popisy("Element <mets:file> má více dětských elementů <mets:FLocat>.", false, get_misto_chyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.51 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
    private boolean pravidlo51(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:FLocat", parsedSAX_Sipsoubor);
        if(nodeListMetsFile == null) return true;
        int size = nodeListMetsFile.getLength();
        for(int i = 0; i < size; i++){
            Node n = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(n, "xlink:type")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:type.", false, get_misto_chyby(n));
            }
            if(!ValuesGetter.hasAttributValue(n, "xlink:type", "simple")){
                return add_popisy("Atribut xlink:type neobsahuje hodnotu simple.", false, get_misto_chyby(n));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.52 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:href s hodnotou, která odpovídá relativní cestě odkazu jakékoli komponenty uložené ve složce komponenty.",
    private boolean pravidlo52(){
        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", parsedSAX_Sipsoubor);
        ArrayList<String> seznam_z_xml = new ArrayList<>();
        if(nodeListFlocat == null) return true;
        for(int i = 0; i < nodeListFlocat.getLength(); i++){
            Node node = nodeListFlocat.item(i);
            if(!ValuesGetter.hasAttribut(node, "xlink:href")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:href.", false, get_misto_chyby(node));
            }
            String href = ValuesGetter.getValueOfAttribut(node, "xlink:href");
            if(!href.startsWith("komponenty")){
                return add_popisy("Špatně uvedená relativní cesta ke komponentě.", false, get_misto_chyby(node));
            }
            href = HelperString.edit_path(href);
            
            String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(sipSoubor).replaceFirst("komponenty", "") + href;
            File file = new File(cestaKeKomponente);
            if(!file.exists()){
                if(href.contains(File.separator)){
                    int s = href.lastIndexOf(File.separator);
                    String g = href.substring(s+1);
                    href = g;
                }
                return add_popisy("Komponenta " + href + " nenalezena.", false, get_misto_chyby(node));
            }
            seznam_z_xml.add(file.getName());
        }
        File[] listKomponent = new File(SIP_MAIN_helper.getCesta_komponenty(sipSoubor)).listFiles();
           for (File listKomponent1 : listKomponent) {
               String name = listKomponent1.getName();
               boolean jeVSeznamu = false;
               for(int y = 0; y < seznam_z_xml.size(); y++){
                   if(seznam_z_xml.get(y).equals(name)){
                       jeVSeznamu = true;
                   }
               }  if(!jeVSeznamu){
                   return add_popisy("Komponenta " + name + " ve složce komponenty nemá na sebe žádný odkaz z elementu <mets:FLocat>.", false, "komponenty" + File.separator + name);
               }
           }
        
        
        return true;
    }
    
    //OBSAHOVÁ č.53 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut LOCTYPE s hodnotou URL.",
    private boolean pravidlo53(){
        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", parsedSAX_Sipsoubor);
        if(nodeListFlocat == null) return true;
        for(int i = 0; i < nodeListFlocat.getLength(); i++){
            Node node = nodeListFlocat.item(i);
            if(!ValuesGetter.hasAttribut(node, "LOCTYPE")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut LOCTYPE.", false, get_misto_chyby(node));
            }
            if(!ValuesGetter.hasAttributValue(node, "LOCTYPE", "URL")){
                return add_popisy("Atribut LOCTYPE nemá hodnotu URL.", false, get_misto_chyby(node));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.54 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, 
    // potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) 
    // v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu 
    // v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> 
    // odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec).
    boolean pravidlo54(){
        ArrayList<Node> krizoveodkazy = get_krizove_odkazy_pevny_ano();
        if(!krizoveodkazy.isEmpty()) return true;  
        ArrayList<Node> metsDiv = ValuesGetter.getAllAnywhereArrayList("mets:div", parsedSAX_Sipsoubor);
        ArrayList<Node> metsAmd = ValuesGetter.getAllAnywhereArrayList("mets:amdSec", parsedSAX_Sipsoubor);
        ArrayList<Node> spisoveplany = ValuesGetter.getAllAnywhereArrayList("nsesss:SpisovyPlan", parsedSAX_Sipsoubor);
        ArrayList<Node> vecneskupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", parsedSAX_Sipsoubor);
        ArrayList<Node> typovespisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", parsedSAX_Sipsoubor);
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereArrayList("nsesss:Spis", parsedSAX_Sipsoubor);
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereArrayList("nsesss:Dil", parsedSAX_Sipsoubor);
//        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", parsedSAX_Sipsoubor);
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Komponenta", parsedSAX_Sipsoubor);
        int size_div = metsDiv.size();
        for(int i = 0; i < size_div; i++){  
            Node n_div = metsDiv.get(i);
            if(ValuesGetter.hasAttribut(n_div, "DMDID")){
                if(ValuesGetter.hasAttribut(n_div, "ADMID")){
                    if(ValuesGetter.hasAttribut(n_div, "TYPE")){
                        String dmdid = ValuesGetter.getValueOfAttribut(n_div, "DMDID");
                        String admid = ValuesGetter.getValueOfAttribut(n_div, "ADMID");
                        String type = ValuesGetter.getValueOfAttribut(n_div, "TYPE");
                        String nodetype = ValuesGetter.get_type_to_nsesss(type);
                        Node node_dmd = null;
                        //hledám odkaz na element v dmdSec
                        if(type.equals("spisový plán")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisoveplany, "ID", dmdid);
                        }
                        if(type.equals("věcná skupina")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(vecneskupiny, "ID", dmdid);
                        }
                        if(type.equals("součást")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(soucasti, "ID", dmdid);
                        }
                        if(type.equals("typový spis")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(typovespisy, "ID", dmdid);
                        }
                        if(type.equals("spis")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisy, "ID", dmdid);
                        }
                        if(type.equals("díl")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(dily, "ID", dmdid);
                        }
                        if(type.equals("dokument")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(dokumenty, "ID", dmdid);
                        }
                        if(type.equals("komponenta")){
                            node_dmd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(komponenty, "ID", dmdid);
                        }
//                        Node node_dmd = ValuesGetter.getNodeWithID(dmdid, nodetype, parsedDOM_SipSoubor);
                        if(node_dmd != null){
                            // porovnat div s dmd
                            Node identifikator;
                            if(node_dmd.getNodeName().equals("nsesss:SpisovyPlan")){
                                identifikator = ValuesGetter.getXChild(node_dmd, "nsesss:Identifikator"); 
                            }
                            else{
                                identifikator = ValuesGetter.getXChild(node_dmd, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                            }
                            if(identifikator != null){
                                if(ValuesGetter.hasAttribut(identifikator, "zdroj")){
                                    Node node_amd = ValuesGetter.getNodeByValueOfAtributFromSpecificList(metsAmd, "ID", admid);
                                    if(node_amd != null){
                                        Node hodnotaId = ValuesGetter.getXChild(node_amd, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:HodnotaID");
                                        Node zdrojID = ValuesGetter.getXChild(node_amd, "mets:digiprovMD", "mets:mdWrap", "mets:xmlData", "tp:TransakcniLogObjektu", "tp:TransLogInfo", "tp:Objekt", "tp:Identifikator", "tns:ZdrojID");
                                        if(hodnotaId != null){
                                            if(zdrojID != null){
                                                String hodnota_identifikator = identifikator.getTextContent();
                                                String hodnota_zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
                                                String amd_identifikator = hodnotaId.getTextContent();
                                                String amd_zdroj = zdrojID.getTextContent();
                                                
                                                if(!ValuesGetter.get_type_to_nsesss(type).equals(node_dmd.getNodeName())){
                                                    return add_popisy("Element mets:div s type " + type + ", odkazuje na element jiného typu.", false, get_misto_chyby(n_div) + " " + get_misto_chyby(node_dmd));
                                                }
                                                if(!hodnota_identifikator.equals(amd_identifikator)){
                                                    return add_popisy("Neshodují se hodnoty identifikátorů. " + getJmenoIdentifikator(identifikator), false, get_misto_chyby(identifikator) + " " + get_misto_chyby(hodnotaId));
                                                }
                                                if(!hodnota_zdroj.equals(amd_zdroj)){
                                                    return add_popisy("Neshodují se hodnoty zdrojů. " + getJmenoIdentifikator(identifikator), false, get_misto_chyby(identifikator) + " " + get_misto_chyby(zdrojID));
                                                }
                                                // zkontrolovat rodče
                                                Node rodic_div = n_div.getParentNode();
                                                if(type.equals("spisový plán")){
                                                    if(!rodic_div.getNodeName().equals("mets:structMap")){
                                                        return add_popisy("Element mets:div nemá správný rodičovský element.", false, get_misto_chyby(n_div));
                                                    }
                                                }
                                                else{
                                                    if(!rodic_div.getNodeName().equals("mets:div")){
                                                        return add_popisy("Element mets:div nemá správný rodičovský element.", false, get_misto_chyby(n_div));
                                                    }
                                                    if(ValuesGetter.hasAttribut(rodic_div, "DMDID")){
                                                        if(ValuesGetter.hasAttribut(rodic_div, "TYPE")){
                                                            String ro_dmdid = ValuesGetter.getValueOfAttribut(rodic_div, "DMDID");
                                                            String ro_type = ValuesGetter.getValueOfAttribut(rodic_div, "TYPE");
                                                            String ro_node_type = ValuesGetter.get_type_to_nsesss(ro_type);
//                                                            Node node_dmd_ro = ValuesGetter.getNodeWithID(ro_dmdid, ro_node_type, parsedDOM_SipSoubor);
                                                            Node node_dmd_ro = null;
                                                            if(ro_type.equals("spisový plán")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisoveplany, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("věcná skupina")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(vecneskupiny, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("součást")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(soucasti, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("typový spis")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(typovespisy, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("spis")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(spisy, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("díl")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(dily, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("dokument")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(dokumenty, "ID", ro_dmdid);
                                                            }
                                                            if(ro_type.equals("komponenta")){
                                                                node_dmd_ro = ValuesGetter.getNodeByValueOfAtributFromSpecificList(komponenty, "ID", ro_dmdid);
                                                            }
                                                            if(node_dmd_ro != null){
                                                                if(!ValuesGetter.parentCheck(nodetype, ro_node_type)){
                                                                    return add_popisy("Element mets:div je špatně zatříděn. Neodpovídající rodičovský element.", false, get_misto_chyby(n_div) + " " + get_misto_chyby(rodic_div));
                                                                }
                                                            } else return add_popisy("Nenalezen element v sekci mets:dmdSec s příslušným ID.", false, get_misto_chyby(rodic_div));
                                                        } else return add_popisy("Element <mets:div> nemá atribut TYPE.", false, get_misto_chyby(rodic_div));     
                                                    } else return add_popisy("Element <mets:div> nemá atribut DMDID.", false, get_misto_chyby(rodic_div));
                                                }
                                            } else return add_popisy("Nenalezen element tns:ZdrojID v sekci mets:amdSec.", false, get_misto_chyby(node_amd));
                                        } else return add_popisy("Nenalezen element tns:HodnotaID v sekci mets:amdSec.", false, get_misto_chyby(node_amd));
                                    } else return add_popisy("Nenalezen element v sekci mets:amdSec s příslušným ID.", false, get_misto_chyby(n_div));
                                } else return add_popisy("Nenalezen atribut zdroj elementu nsesss:Identifikator.", false, get_misto_chyby(node_dmd));
                            } else return add_popisy("Nenalezen element nsesss:Identifikator.", false, get_misto_chyby(node_dmd));
                        } else return add_popisy("Nenalezen element v sekci mets:dmdSec s příslušným ID.", false, get_misto_chyby(n_div));
                    } else return add_popisy("Element <mets:div> nemá atribut TYPE.", false, get_misto_chyby(n_div));
                } else return add_popisy("Element <mets:div> nemá atribut ADMID.", false, get_misto_chyby(n_div));      
            } else return add_popisy("Element <mets:div> nemá atribut DMDID.", false, get_misto_chyby(n_div));           
        } 

        if(pravidlo54_pocitadlo() != metsDiv.size()){
            return add_popisy("Počet elementů <mets:div> neodpovídá počtu elementů v dmdSec.", false, chyba_neupresneno);
        }

        if(!pravidlo54_pocitadlo_amdsec(metsDiv.size())){
            return add_popisy("Počet elementů <mets:div> neodpovídá počtu elementů v amdSec.", false, chyba_neupresneno);
        }
        return true;
    }
        
    //OBSAHOVÁ č.55 Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.
    private boolean pravidlo55(){
        NodeList nodeListDiv = ValuesGetter.getAllAnywhere("mets:div", parsedSAX_Sipsoubor);
        if(nodeListDiv == null) return true;
        for(int i = 0; i < nodeListDiv.getLength(); i++){
            Node div = nodeListDiv.item(i);
            boolean obsahuje = ValuesGetter.hasAttributValue(div, "TYPE", "komponenta");
            if(obsahuje){
                ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(div, "mets:fptr");
                if(list.isEmpty()){
                   return add_popisy("Element <mets:div> neobsahuje element <mets:fptr>.", false, get_misto_chyby(div)); 
                }
                if(list.size() > 1){
                    return add_popisy("Elementu <mets:div> obsahuje více elementů <mets:fptr>.", false, get_misto_chyby(div));
                }
            } 
        } 
        return true;
    }
    
    //OBSAHOVÁ č.56 Pokud existuje jakýkoli element <mets:fptr>, každý obsahuje atribut FILEID s hodnotou, která odpovídá hodnotě atributu ID elementu <mets:file> příslušné komponenty. Příslušnost vyjadřuje stejná hodnota atributu DMDID rodičovského elementu <mets:div> a elementu <mets:file>.",
    private boolean pravidlo56(){
        NodeList nodeListMetsFptr = ValuesGetter.getAllAnywhere("mets:fptr", parsedSAX_Sipsoubor);
        if(nodeListMetsFptr == null) return true;
        for(int i = 0; i < nodeListMetsFptr.getLength(); i++){  
            Node metsFptr = nodeListMetsFptr.item(i);
            if(!ValuesGetter.hasAttribut(metsFptr, "FILEID")){
                return add_popisy("Element <mets:fptr> neobsahuje atribut FILEID.", false, get_misto_chyby(metsFptr));
            }
        }

        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", parsedSAX_Sipsoubor);
        if(nodeListFlocat == null){
            return add_popisy("Nenalezen element <mets:FLocat>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeListMetsFptr.getLength(); i++){
            Node fLocat = nodeListFlocat.item(i);
            Node metsFptr = nodeListMetsFptr.item(i);
            Node metsFile = ValuesGetter.getXParent(fLocat, "mets:file");
            if(metsFile == null){
                return add_popisy("Nenalezen element <mets:file>.", false, chyba_neupresneno);
            }
            if(!ValuesGetter.hasAttribut(metsFile, "ID")){
                return add_popisy("Element <mets:file> neobsahuje atribut ID.", false, get_misto_chyby(metsFile));
            }
            String fptrFileId = ValuesGetter.getValueOfAttribut(metsFptr, "FILEID");
            String idFile = ValuesGetter.getValueOfAttribut(ValuesGetter.getXParent(fLocat, "mets:file"), "ID");
            if(!fptrFileId.equals(idFile)){
                return add_popisy("Hodnoty atributů si neodpovídají. FILEID: " + fptrFileId + " ID: " + idFile + ".", false, get_misto_chyby(metsFptr) + " " + get_misto_chyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.57 Jakýkoli element <nsesss:Identifikator> obsahuje neprázdnou hodnotu.",
    private boolean pravidlo57(){
//        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", parsedSAX_Sipsoubor);
        if(identifikatory == null){
            return add_popisy("Nenalezen žádný element <nsesss:Identifikator>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < identifikatory.size(); i++){
            Node identifikator = identifikatory.get(i);
            String str = identifikator.getTextContent();
            if(!Helper.isStringNoEmpty(str)){
                return add_popisy("Element <nsesss:Identifikator> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(identifikator), false, get_misto_chyby(identifikator));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.58 Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    private boolean pravidlo58(){
//        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", parsedSAX_Sipsoubor);
        if(identifikatory == null){
            return add_popisy("Nenalezen žádný element <nsesss:Identifikator>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < identifikatory.size(); i++){
            Node identifikator = identifikatory.get(i);
            if(!ValuesGetter.hasAttribut(identifikator, "zdroj")){
                return add_popisy("Element <nsesss:Identifikator> neobsahuje atribut zdroj.", false, get_misto_chyby(identifikator));
            }
            String str = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            if(!Helper.isStringNoEmpty(str)){
                return add_popisy("Atribut zdroj elementu <nsesss:Identifikator> má prázdnou hodnotu. " + getJmenoIdentifikator(identifikator), false, get_misto_chyby(identifikator));
            }
        }
        return true;
    }

    //OBSAHOVÁ č.59 Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.
    private boolean pravidlo59(){
        NodeList nlist = parsedSAX_Sipsoubor.getElementsByTagName("nsesss:Identifikator");
        int[] set = IntStream.range(0, nlist.getLength()).toArray();
        ArrayList<Integer> k_list = new ArrayList<>();
        for (int i : set) k_list.add(i);

        while(!k_list.isEmpty()){
            k_list = p59_specialMetod(nlist, k_list);
            if(k_list == null) return add_popisy(popisChyby, false, misto_chyby);
        }
        
        return true;
    }
    
    private ArrayList<Integer> p59_specialMetod(NodeList nlist, ArrayList<Integer> k_list){
        if(k_list.isEmpty()) return k_list;
        if(k_list.size() == 1){
            k_list.clear();
            return k_list;
        }  
        String hodnotaIdentifikatoru = nlist.item(k_list.get(0)).getTextContent();
        String hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(nlist.item(k_list.get(0)), "zdroj"); 
        
        for(int j = 1; j < k_list.size(); j++){
            Node node = nlist.item(k_list.get(j));
            String j_hodnotaIdentifikatoru = node.getTextContent();
            String j_hodnotaAtrZdroj = ValuesGetter.getValueOfAttribut(node, "zdroj");
            if(hodnotaIdentifikatoru.equals(j_hodnotaIdentifikatoru) && hodnotaAtrZdroj.equals(j_hodnotaAtrZdroj)){
                //zkontroluj zda jsou stejné
                Node prvni = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(0)));
                Node dalsi = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(j)));
                // přepsat
                if(ValuesGetter.checkEntity_IdentifikatorCompare(prvni)){
                    if(ValuesGetter.checkEntity_IdentifikatorCompare(dalsi)){
                    String hlaska = CompareNodes.compare(prvni, dalsi);
                        if(!hlaska.equals("OK")){
                            Node entita1 = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(0)));
                            Node entita2 = ValuesGetter.getEntityWithIdentifikator(nlist.item(k_list.get(j)));
                            
                            popisChyby = "Entity/objekty mají stejné hodnoty v elementu identifikátor a atributu zdroj, ale různý obsah. " + hlaska + " " + getJmenoIdentifikator(entita1) + " " + getJmenoIdentifikator(entita2);
                            misto_chyby = get_misto_chyby(entita1) + " " + get_misto_chyby(entita2);
                            return null;
                        }
                    }
                }
                //přidej ke kontrolovaným
                k_list.remove(j);
            }
        }
        k_list.remove(0);
        return k_list;
    }

    //OBSAHOVÁ č.60 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
    private boolean pravidlo60(){
        if(dokumenty == null){
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < dokumenty.size(); i++)
        {
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ne")){
                if(ValuesGetter.getXChild(dokument, "nsesss:Komponenty") == null){
                    return add_popisy("Nenalezen povinný element <nsesss:Komponenty>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.61 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    private boolean pravidlo61(){
        if(dokumenty == null){
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, chyba_neupresneno);
        }
        for(int i = 0; i < dokumenty.size(); i++)
        {      
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument") != null){
                if(ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi") == null){
                    return add_popisy("Nenalezen povinný element <nsesss:DoruceneMnozstvi>. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
                }
                if(ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi") != null){
                    String s = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi").getTextContent();
                    if(!Helper.isStringNoEmpty(s)){
                        return add_popisy("Element <nsesss:DoruceneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + getIdentifikatory(dokument) + ".", false, get_misto_chyby(dokument));
                    }
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.62 Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.
    private boolean pravidlo62(){
        NodeList jazyky = ValuesGetter.getAllAnywhere("nsesss:Jazyk", parsedSAX_Sipsoubor);
        if(jazyky == null) return true;
        String hodnotaJazyk;
        UrlJazykyParser parserZUrl = new UrlJazykyParser();
        for(int i = 0; i < jazyky.getLength(); i++)
        {
            Node jazyk = jazyky.item(i);
            hodnotaJazyk = jazyk.getTextContent(); 
            try {
                parserZUrl.NactiJazykyZUrl();
            } catch (IOException ex) {
                return add_popisy("Chyba programu - nepodařilo se načíst tabulku s hodnotami pro element <nsesss:Jazyk>.", false, chyba_neupresneno);
            }
            boolean jeObsazen = parserZUrl.jeObsazenJazyk(hodnotaJazyk);
            if(!jeObsazen)
                return add_popisy("Nenalezena hodnota odpovídající ISO 639-2:1998. " + getJmenoIdentifikator(jazyk), false, chyba_neupresneno);
        }
        return true; 
    }
    
    //OBSAHOVÁ č.63 Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, 
    // potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    private boolean pravidlo63(){
        NodeList vyrizenis = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", parsedSAX_Sipsoubor);
        NodeList vyrizeniUzavrenis = ValuesGetter.getAllAnywhere("nsesss:VyrizeniUzavreni", parsedSAX_Sipsoubor);
        if(vyrizenis != null){
            int size = vyrizenis.getLength();
            for(int i = 0; i < size; i++){
                Node n = vyrizenis.item(i);
                boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob", "jiný způsob");
                if(maZpusobSHodnotou){
                    Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                    if(oduvodneni == null){
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
                    }
                    if(!Helper.isStringNoEmpty(oduvodneni.getTextContent())){
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(n), false, get_misto_chyby(oduvodneni));
                    }
                }   
            }
        }
        if(vyrizeniUzavrenis != null){
            int size = vyrizeniUzavrenis.getLength();
            for(int j = 0; j < size; j++){
                Node n = vyrizeniUzavrenis.item(j);
                boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob", "jiný způsob");
                if(maZpusobSHodnotou){
                    Node oduvodneni = ValuesGetter.getXChild(n, "nsesss:Oduvodneni");
                    if(oduvodneni == null){
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
                    }
                    if(!Helper.isStringNoEmpty(oduvodneni.getTextContent())){
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " +  getJmenoIdentifikator(n), false, get_misto_chyby(oduvodneni));
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.64 Pokud je základní entitou (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> 
    // obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je součtem hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.",
    private boolean pravidlo64(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dokument")){
                Node skartacniRezim = ValuesGetter.getXChild(zakladnientita,"nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim");
                if(skartacniRezim == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                Node skartacniLhuta_node = ValuesGetter.getXChild(skartacniRezim, "nsesss:SkartacniLhuta");
                if(skartacniLhuta_node == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(skartacniRezim));
                }
                String skartacniLhuta = skartacniLhuta_node.getTextContent();
                Node rso = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                Node rsu = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if(rso == null){
                    return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                if(rsu == null){
                    return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                String rokSkartacniOperace = rso.getTextContent();
                String rokSpousteciUdalosti = rsu.getTextContent(); 
                
                try{
                    String chytac;
                    chytac = rokSkartacniOperace;
                    int hodnotaOperace = Integer.parseInt(rokSkartacniOperace);
                    chytac = skartacniLhuta;
                    int hodnotaLhuta = Integer.parseInt(skartacniLhuta);
                    chytac = rokSpousteciUdalosti;
                    int hodnotaUdalosti = Integer.parseInt(rokSpousteciUdalosti);
                    if(hodnotaOperace != hodnotaLhuta + hodnotaUdalosti + 1){
                        return add_popisy("Rok skartační operace: " + hodnotaOperace + ", lhůta: " + hodnotaLhuta + ", událost: " + hodnotaUdalosti + ". " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                    }
                }
                catch(NumberFormatException e){
//                    System.err.println("PRAVIDLO NSESSS Č.64. " + e);
                    return add_popisy("Zápis roku je uveden ve špatném formátu. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                
            }
        }
        return true; 
    }
    
    //OBSAHOVÁ č.65 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, 
    // která je rovna vyšší hodnotě, přičemž jednou hodnotou je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota roku skartační operace jakékoli dětské entity dokument (nsesss:Dokument>).",
    private boolean pravidlo65(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladniEntita = zakladniEntity.get(i);
            String jmeno = zakladniEntita.getNodeName();
            if(jmeno.equals("nsesss:Spis") || jmeno.equals("nsesss:Dil")){
                Node node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(zakladniEntita));
                }
                String str_rokSkartacniOperace_spis = node.getTextContent();
                // HODNOTA 1
                int hodnota_rokSkartacniOperace_spis; try{ hodnota_rokSkartacniOperace_spis = Integer.parseInt(str_rokSkartacniOperace_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: " + str_rokSkartacniOperace_spis + "." + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(node));}
                
                node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>." + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(zakladniEntita));
                }
                String str_rokSpousteciUdalosti_spis = node.getTextContent();
                int rokSpousteciUdalosti_spis;
                try{rokSpousteciUdalosti_spis = Integer.parseInt(str_rokSpousteciUdalosti_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: " + str_rokSpousteciUdalosti_spis + ". " + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(node));}

                node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(zakladniEntita));
                }
                String str_skartacniLhuta_spis = node.getTextContent();
                int skartacniLhuta_spis;
                try{skartacniLhuta_spis = Integer.parseInt(str_skartacniLhuta_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: " + str_skartacniLhuta_spis + ". " + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(node));}
                
                //HODNOTA 2
                int hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta = rokSpousteciUdalosti_spis + 1 + skartacniLhuta_spis; 
                
                // dokumenty 
                if(dokumenty == null || dokumenty.isEmpty()){
                    return add_popisy("Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladniEntita), false, get_misto_chyby(zakladniEntita));
                }
                Obj_Node_int dokument = new Obj_Node_int(null, 0);
                for(int j = 0; j < dokumenty.size(); j++){
                    Node dokumentze = dokumenty.get(j);
                    int int_finalhodnota_dok;
                    Node dok_lhuta = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                    if(dok_lhuta == null){
                        return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(dokumentze), false, get_misto_chyby(dokumentze));
                    }
                    String d_lhuta = dok_lhuta.getTextContent();

                    Node datum_dok = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument", "nsesss:DatumVytvoreni");
                    if(datum_dok == null) datum_dok = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DatumDoruceni");
                    if(datum_dok == null){
                        return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. "  + getJmenoIdentifikator(dokumentze), false, get_misto_chyby(dokumentze));
                    }
                    String d_vytvDor = datum_dok.getTextContent().substring(0, 4);
                    int int_dok_lhuta, int_dok_rok_vytvDor;
                    try{
                        int_dok_lhuta = Integer.parseInt(d_lhuta);
                    }
                    catch(NumberFormatException e){
                        return add_popisy("Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "  + getJmenoIdentifikator(dokumentze), false, get_misto_chyby(dok_lhuta));
                    }
                    try{
                        int_dok_rok_vytvDor = Integer.parseInt(d_vytvDor);
                    }
                    catch(NumberFormatException e){
                        return add_popisy("Element <" + datum_dok.getNodeName() + "> obsahuje hodnotu roku ve špatném formátu."  + getJmenoIdentifikator(dokumentze), false, get_misto_chyby(datum_dok));
                    }

                    int_finalhodnota_dok = int_dok_lhuta + 1 + int_dok_rok_vytvDor;
                    
                    if((int_finalhodnota_dok) > dokument.get_int()){
                        dokument = new Obj_Node_int(dokumentze, (int_finalhodnota_dok));
                    }
                }
                
                //HODNOTA 3 
                int hodnota_rokSkartacniOperace_dokument = dokument.get_int();
                
                //PODMÍNKA
                int hodnota_maximalni = Math.max(hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta, hodnota_rokSkartacniOperace_dokument);
                if(hodnota_rokSkartacniOperace_spis != hodnota_maximalni){
                    if(hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta != hodnota_rokSkartacniOperace_dokument){
                        return add_popisy("Rok uvedený v elementu <nsesss:RokSkartacniOperace>: " + hodnota_rokSkartacniOperace_spis + ", se nerovná nejvyšší hodnotě. Buď nejvyšší hodnotě z dětských elementů <nsesss:Dokument>: " + hodnota_rokSkartacniOperace_dokument + ", nebo součtu hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: " + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = " + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ". " + getJmenoIdentifikator(zakladniEntita) + " " + getJmenoIdentifikator(dokument.get_node()), false, get_misto_chyby(zakladniEntita) + " " + get_misto_chyby(dokument.get_node()));
                    }
                    else{
                        return add_popisy("Součet hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: " + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = " + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ", je roven nejvyšší hodnotě elementu <nsesss:RokSkartacniOperace> elementu <nsesss:Dokument>: " + hodnota_rokSkartacniOperace_dokument + ". Nerovná se však hodnotě elementu <nsesss:RokSkartacniOperace> základní entity: " +  hodnota_rokSkartacniOperace_spis + ". " + getJmenoIdentifikator(zakladniEntita) + " " + getJmenoIdentifikator(dokument.get_node()), false, get_misto_chyby(zakladniEntita) + " " + get_misto_chyby(dokument.get_node()));
                    }
                } 
            }
        }    
        
        return true;
    }
    
    //OBSAHOVÁ č.66 Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    private boolean pravidlo66(){
        
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node ze = zakladniEntity.get(i);
            Node node = ValuesGetter.getXChild(ze, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
            String str = node.getTextContent().substring(0, 4);
            boolean je = ValuesGetter.overSpravnostRetezceProInt(str);
            if(!je){
                return add_popisy("Element <nsesss:RokSkartacniOperace> obsahuje hodnotu ve špatném formátu. " + getJmenoIdentifikator(ze), false, get_misto_chyby(node));
            }
            int rokSkartacniOperace = Integer.parseInt(str);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if(rokSkartacniOperace > year){
                return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je větší, než aktuální rok. Hodnota: " + rokSkartacniOperace + ". " + getJmenoIdentifikator(ze), false, get_misto_chyby(node));
            }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
    private boolean pravidlo67(){

        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            String zeName = zakladnientita.getNodeName();
            if(zeName.equals("nsesss:Spis") || zeName.equals("nsesss:Dil")){
                Node n = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if(n == null){
                    return add_popisy("Nenalezen dětský element <nsesss:SkartacniZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                String skZnakME = n.getTextContent();
                ArrayList<Obj_Node_String> hodnotyDokumentu = new ArrayList<>();
                if(dokumenty == null){
                    return add_popisy("Nenalezen žádný dětský element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                for(int j = 0; j < dokumenty.size(); j++){
                Node dokument = dokumenty.get(j);
                Node nd = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if(nd == null){
                    return add_popisy("Nenalezen dětský element <nsesss:SkartacniZnak> elementu <nsesss:Dokument>. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(dokument));
                }
                String znak = nd.getTextContent();
                hodnotyDokumentu.add(new Obj_Node_String(dokument, znak));
                }
                switch(skZnakME){
                    case "A":
                        if(Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A") == null){
                            return add_popisy("Spis se skartačním znakem A neobsahuje žádný dokument se skartačním znakem A. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n));
                        }
                        break;
                    case "V":
                        Obj_Node_String obj_a = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A");
                        if(obj_a != null){
                            return add_popisy("Spis se skartačním znakem V obsahuje dokument se skartačním znakem A. " + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(obj_a.get_node()), false, get_misto_chyby(n) + " " + get_misto_chyby(obj_a.get_node()));
                        }
                        Obj_Node_String obj_v = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "V");
                        if(obj_a == null && obj_v == null){
                            return add_popisy("Spis se skartačním znakem V neobsahuje žádný dokument se skartačním znakem V. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n));
                        }
                        break;
                    case "S":
                        ArrayList<Obj_Node_String> list = Helper_Obj_Node.all_with_skartacni_znak(hodnotyDokumentu, "A", "V");
                        if(!list.isEmpty()){
                            String ch = "";
                            String iden = "";
                            for(int k = 0; k < list.size(); k++){
                                Node no = list.get(i).get_node();
                                ch += get_misto_chyby(no);
                                iden += " " + getJmenoIdentifikator(no);
                                if(k != list.size()-1) ch += " ";
                            }
                            return add_popisy("Spis se skartačním znakem S obsahuje dokument se skartačním znakem A nebo V. " + getJmenoIdentifikator(zakladnientita) + iden, false, get_misto_chyby(n) + " " + ch);
                        }
                        
                        
                        break; 
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.68 Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.
    private boolean pravidlo68(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dokument") || zakladnientita.getNodeName().equals("nsesss:Spis")){
                Node vecnaskupina = ValuesGetter.getFirstInNode(zakladnientita, "nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
                if(vecnaskupina == null){
                    return add_popisy("Nenalezena rodičovská entita věcná skupina základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                Node sr = ValuesGetter.getXChild(vecnaskupina, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim");
                if(sr == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(vecnaskupina), false, get_misto_chyby(vecnaskupina));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.69 Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
    private boolean pravidlo69(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node ze = zakladniEntity.get(i);
            if(ze.getNodeName().equals("nsesss:Dokument")){
                Node node = ValuesGetter.getXChild(ze, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:Vyrizeni>. " + getJmenoIdentifikator(ze), false, get_misto_chyby(ze));
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.70 Jakýkoli element <nsesss:Identifikator> není opakovatelný, pokud se nenachází v hierarchii elementů <nsesss:Komponenta>, <nsesss:EvidencniUdaje> a <nsesss:Identifikace>.",
    private boolean pravidlo70(){
//        NodeList id = ValuesGetter.getAllAnywhere("nsesss:Identifikator", parsedSAX_Sipsoubor);
//        ArrayList<Node> id = ValuesGetter.getAllAnywhereArrayList("nsesss:Identifikator", parsedSAX_Sipsoubor);
        if(identifikatory == null){
            return add_popisy("Nenalezen žádný element <nsesss:Identifikator>.", false, chyba_neupresneno);
        }
        int size = identifikatory.size();
        for(int i = 0; i < size; i++){ 
            Node identifikator = identifikatory.get(i);
            Node rodic = identifikator.getParentNode();
            if(ValuesGetter.getSpecificChildWithName(rodic, "nsesss:Identifikator").size() > 1){
                Node komponenta = ValuesGetter.getXParent(identifikator, "nsesss:Identifikace", "nsesss:EvidencniUdaje", "nsesss:Komponenta");
                if(komponenta == null){
                    Node entita = ValuesGetter.getXParent(identifikator, "nsesss:Identifikace", "nsesss:EvidencniUdaje").getParentNode();
                    
                    return add_popisy("Element <nsesss:Identifikator> se opakuje přes nesplnění podmínky pravidla. " + getJmenoIdentifikator(entita), false, get_misto_chyby(identifikator));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.71 V každém elementu <nsesss:Manipulace> obsahuje dětský element <nsesss:DatumOtevreni> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje.",
    // Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.
    private boolean pravidlo71(){
        if(manipulace == null) return true;
        int size = manipulace.size();
        for (int i = 0; i < size; i++) 
        {
            Node manip_node = manipulace.get(i);
            Node nodeOtevreni = ValuesGetter.findChild(manip_node, "nsesss:DatumOtevreni");
            Node nodeUzavreni = ValuesGetter.findChild(manip_node, "nsesss:DatumUzavreni");
            if(nodeOtevreni != null && nodeUzavreni != null){
                Date datumOtevreni;
                Date datumZavreni;
                try {
                    datumOtevreni = ValuesGetter.vytvorDate(nodeOtevreni,"yyyy-MM-dd");  
                } catch (ParseException ex) {
                    return add_popisy("Element <nsesss:DatumOtevreni> neobsahuje údaj ve správném formátu. " + getJmenoIdentifikator(manip_node), false, get_misto_chyby(nodeOtevreni));
                }
                try {
                    datumZavreni = ValuesGetter.vytvorDate(nodeUzavreni,"yyyy-MM-dd");
                } catch (ParseException ex) {
                    return add_popisy("Element <nsesss:DatumUzavreni> neobsahuje údaj ve správném formátu. " + getJmenoIdentifikator(manip_node), false, get_misto_chyby(nodeUzavreni));
                }

                boolean jeToChronologicky = datumZavreni.after(datumOtevreni);
                boolean jeToStejny = datumZavreni.equals(datumOtevreni);
                if(!jeToChronologicky && !jeToStejny){
                    return add_popisy("Element <nsesss:DatumOtevreni> obsahuje větší hodnotu než element <nsesss:DatumUzavreni>. " + getJmenoIdentifikator(manip_node), false, get_misto_chyby(nodeOtevreni) + " " + get_misto_chyby(nodeUzavreni));
                }
                 
            }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.72 Každý element <nsesss:CasPouziti> obsahuje atribut datum.",
    private boolean pravidlo72(){
        NodeList casy = ValuesGetter.getAllAnywhere("nsesss:CasPouziti", parsedSAX_Sipsoubor);
        if(casy == null) return true;
        for(int i = 0; i < casy.getLength(); i++){
            Node cas = casy.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if(!maDatum){
                return add_popisy("Element <nsesss:CasPouziti> neobsahuje atribut datum. " + getJmenoIdentifikator(cas), false, get_misto_chyby(cas));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.73 Každý element <nsesss:CasOvereni> obsahuje atribut datum.",
    private boolean pravidlo73(){
        NodeList casy = ValuesGetter.getAllAnywhere("nsesss:CasOvereni", parsedSAX_Sipsoubor);
        if(casy == null) return true;
        for(int i = 0; i < casy.getLength(); i++){
            Node cas = casy.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if(!maDatum){
                return add_popisy("Element <nsesss:CasOvereni> neobsahuje atribut datum. " + getJmenoIdentifikator(cas), false, get_misto_chyby(cas));
            }
        }        
        return true; 
    }
    
    //OBSAHOVÁ č.74 Každý element <nsesss:PosuzovanyOkamzik> obsahuje atribut datum.",
    private boolean pravidlo74(){
        NodeList posuzovanyOkamzik = ValuesGetter.getAllAnywhere("nsesss:PosuzovanyOkamzik", parsedSAX_Sipsoubor);
        if(posuzovanyOkamzik == null) return true;
        for(int i = 0; i < posuzovanyOkamzik.getLength(); i++){
            Node cas = posuzovanyOkamzik.item(i);
            boolean maDatum = ValuesGetter.hasAttribut(cas, "datum");
            if(!maDatum){
                return add_popisy("Element <nsesss:PosuzovanyOkamzik> neobsahuje atribut datum. " + getJmenoIdentifikator(cas), false, get_misto_chyby(cas));
            }
        }        
        return true;
    }
    
    //OBSAHOVÁ č.75 Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
    private boolean pravidlo75(){
        NodeList posuzovanyOkamzik = ValuesGetter.getAllAnywhere("nsesss:Platnost", parsedSAX_Sipsoubor);
        if(posuzovanyOkamzik == null) return true;
        for(int i = 0; i < posuzovanyOkamzik.getLength(); i++)
        {
            Node platnost = posuzovanyOkamzik.item(i);
            Node nodeOd = ValuesGetter.findChild(platnost, "nsesss:PlatnostOd");
            if(nodeOd == null){
                return add_popisy("Nenalezen element <nsesss:PlatnostOd>. " + getJmenoIdentifikator(platnost), false, get_misto_chyby(platnost));
            }
            Node nodeDo = ValuesGetter.findChild(platnost, "nsesss:PlatnostDo");
            if(nodeDo == null){
                return add_popisy("Nenalezen element <nsesss:PlatnostDo>. " + getJmenoIdentifikator(platnost), false, get_misto_chyby(platnost));
            }
            Date od, po;
            try {
                od = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
            }catch (ParseException ex){
                return add_popisy("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost), false, get_misto_chyby(nodeOd));}
            try {
                po = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd");
            }catch (ParseException ex){
                return add_popisy("Hodnota data je ve špatném formátu. " + getJmenoIdentifikator(platnost), false, get_misto_chyby(nodeDo));}
            
            if(!(po.after(od) || po.equals(od))){
                return add_popisy("Element <nsesss:PlatnostOd> obsahuje větší hodnotu než element <nsesss:PlatnostDo>. " + getJmenoIdentifikator(platnost), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.76 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom rodičovské entity obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> se stejnou hodnotou.",
    private boolean pravidlo76(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dil") || zakladnientita.getNodeName().equals("nsesss:Spis")){
                Node an_ze = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
                if(an_ze == null){
                    return add_popisy("Element <" + zakladnientita.getNodeName() + "> neobsahuje dětský element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                String analogovyzakladni = an_ze.getTextContent();
                
                if(dokumenty == null || dokumenty.isEmpty()){
                    return add_popisy("Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                for(int j = 0; j < dokumenty.size(); j++){
                    Node dokument = dokumenty.get(j);
                    Node node = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
                    if(node == null){
                        return add_popisy("Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(dokument));
                    }
                    String hodnota = node.getTextContent();
                    if(hodnota.equals("ano") && analogovyzakladni.equals("ne")){
                        return add_popisy("Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho základní entita. " + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(dokument), false, get_misto_chyby(zakladnientita) + " " + get_misto_chyby(dokument));
                    }
                }      
            }
            if(zakladnientita.getNodeName().equals("nsesss:Dokument")){
                Node and = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
                if(and == null){
                    return add_popisy("Element <nsesss:Dokument> neobsahuje dětský element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
                String analogovyzakladni = and.getTextContent();
                if(analogovyzakladni.equals("ano")){
                    ArrayList<Node> vecneSkupiny = ValuesGetter.getAllInNode(zakladnientita, "nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
                    if(vecneSkupiny == null || vecneSkupiny.isEmpty()){
                        return add_popisy("Nenalezen element <nsesss:VecnaSkupina>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                    }
                    for(int k = 0; k < vecneSkupiny.size(); k++){
                        Node vs = vecneSkupiny.get(k);
                        Node n = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
                        if(n == null){
                            return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. " + getJmenoIdentifikator(vs), false, get_misto_chyby(vs));
                        }
                        String ad = n.getTextContent();
                        if(ad.equals("ne")){
                            return add_popisy("Element <nsesss:Dokument> nemá stejnou hodnotu jako jeho věcná skupina. " + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(vs), false, get_misto_chyby(zakladnientita) + " " + get_misto_chyby(n));
                        }
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.77 Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
    private boolean pravidlo77(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            Node analogovy = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(analogovy != null){
                String analogovyZakladni = analogovy.getTextContent();
                if(analogovyZakladni.equals("ano")){
                    Node mnozstvi = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRizeni", "nsesss:Mnozstvi");
                    if(mnozstvi == null){
                        return add_popisy("Nenalezen element <nsesss:Mnozstvi> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                    }
                    if(!Helper.isStringNoEmpty(mnozstvi.getTextContent())){
                        return add_popisy("Element <nsesss:Mnozstvi> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(mnozstvi));
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.78 Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
    private boolean pravidlo78(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            ArrayList<Node> skartacniRizeni = ValuesGetter.getAllInNode(zakladnientita, "nsesss:SkartacniRizeni", parsedSAX_Sipsoubor);
            if(skartacniRizeni == null || skartacniRizeni.isEmpty()){
                return add_popisy("Nenalezen element <nsesss:SkartacniRizeni>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            }       
            if(skartacniRizeni.size() != 1){
                return add_popisy("Element <nsesss:SkartacniRizeni> je v základní entitě uveden vícekrát. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            }
            Node node = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRizeni");
            if(node == null){
                return add_popisy("Element <nsesss:SkartacniRizeni> není správně zatříděn. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(skartacniRizeni.get(0)));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.79 V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
    private boolean pravidlo79(){
        NodeList skartacniRizeni = ValuesGetter.getAllAnywhere("nsesss:SkartacniRizeni", parsedSAX_Sipsoubor);
        if(skartacniRizeni == null){
            Node entita = zakladniEntity.get(0);
            return add_popisy("Nenalezen element <nsesss:SkartacniRizeni>. " + getJmenoIdentifikator(entita), false, chyba_neupresneno);
        }
        for(int i = 0; i < skartacniRizeni.getLength(); i++){ 
            Node skrizeni = skartacniRizeni.item(i);
            Node datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if(datum == null){
                return add_popisy("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(skrizeni));
            }
            String strDatum = datum.getTextContent().substring(0, 4);

            Node dataceVyrazeni = ValuesGetter.getSourozencePrvnihoSeJmenem(skrizeni, "nsesss:DataceVyrazeni");
            if(dataceVyrazeni == null){
                return add_popisy("Nenalezen element <nsesss:DataceVyrazeni>. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(skrizeni));
            }
            Node rokSkOperace = ValuesGetter.getXChild(dataceVyrazeni, "nsesss:RokSkartacniOperace");
            if(rokSkOperace == null){
                return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(dataceVyrazeni));
            }
            String rokOperace = rokSkOperace.getTextContent();

            if(!ValuesGetter.overSpravnostRetezceProInt(strDatum)){
                return add_popisy("Hodnota roku v elementu <nsesss:Datum> uvedena ve špatném formátu. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(datum));
            }
            if(!ValuesGetter.overSpravnostRetezceProInt(rokOperace)){
                return add_popisy("Hodnota roku v elementu <nsesss:RokSkartacniOperace> uvedena ve špatném formátu. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(rokSkOperace));
            }
            
            int a = Integer.parseInt(strDatum);
            int b = Integer.parseInt(rokOperace);
            if(!(a >= b)){
                return add_popisy("Nesplněna podmínka pravidla." + " Datum: " + a + ". Rok skartační operace: " + b + ". " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(datum) + " " + get_misto_chyby(rokSkOperace));
            }
            
        }
        return true; 
    }
    
    //OBSAHOVÁ č.80 V jakémkoli elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, která je menší nebo rovna aktuálnímu roku.
    private boolean pravidlo80(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("nsesss:SkartacniRizeni", parsedSAX_Sipsoubor);
        if(nodeList == null){
            return add_popisy("Nenalezen element <nsesss:SkartacniRizeni>. " + getJmenoIdentifikator(zakladniEntity.get(0)), false, chyba_neupresneno);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node skrizeni = nodeList.item(i);
            Node datum = ValuesGetter.getXChild(skrizeni, "nsesss:Datum");
            if(datum == null){
                return add_popisy("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(skrizeni));
            }
            int year = Calendar.getInstance().get(Calendar.YEAR);
        
            String s = datum.getTextContent().substring(0, 4);
            boolean b = (ValuesGetter.overSpravnostRetezceProInt(s));
            if(b){
                int date = Integer.parseInt(s);
                if(!(date <= year)){
                    return add_popisy("Hodnota elementu <nsesss:Datum> (" + date +") je vyšší než aktuální rok. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(datum));
                }
            }
            else{
                return add_popisy("Hodnota elementu <nsesss:Datum> je v nepovoleném formátu. " + getJmenoIdentifikator(skrizeni), false, get_misto_chyby(datum));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.81 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:DatumDo>, potom je jeho hodnota větší než <nsesss:DatumOd>.
    private boolean pravidlo81(){
        if(urceneCasoveObdobi == null) return true;
        int size = urceneCasoveObdobi.size();
        for(int i = 0; i < size; i++){
            Node n = urceneCasoveObdobi.get(i);
            Node nodeDo = ValuesGetter.getXChild(n, "nsesss:DatumDo");
            if(nodeDo != null){
                Node nodeOd = ValuesGetter.getXChild(n, "nsesss:DatumOd");
                if(nodeOd == null){
                    return add_popisy("Nenalezen element <nsesss:DatumOd>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
                }
                try {
                    Date dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd");
                    Date dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd"); 
                    if(!dateOd.before(dateDo)){
                        return add_popisy("Nesplněna podmínka pravidla. OD: " + dateOd +". DO: " + dateDo + ". " + getJmenoIdentifikator(n), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                    }
                } catch (ParseException ex) {
                    return add_popisy("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(n), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
            }     
        } 
        return true;
    }
    
    //OBSAHOVÁ č.82 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
    private boolean pravidlo82(){
        if(urceneCasoveObdobi == null) return true;
        for(int i = 0; i < urceneCasoveObdobi.size(); i++){
            Node urcenecasoveobdobi = urceneCasoveObdobi.get(i);
            Node nodeDo = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicDo");
            if(nodeDo != null){
                Node nodeOd = ValuesGetter.getXChild(urcenecasoveobdobi, "nsesss:MesicOd");
                if(nodeOd == null){
                    return add_popisy("Nenalezen element <nsesss:MesicOd>. " + getJmenoIdentifikator(urcenecasoveobdobi), false, get_misto_chyby(urcenecasoveobdobi));
                }
                Date dateOd, dateDo;
                try {
                    dateOd = ValuesGetter.vytvorDate(nodeOd, "yyyy-MM-dd", "yyyy-MM");
                    dateDo = ValuesGetter.vytvorDate(nodeDo, "yyyy-MM-dd", "yyyy-MM"); 
                    
                } catch (ParseException ex) {
                    return add_popisy("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(urcenecasoveobdobi), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
                if(dateOd == null || dateDo == null){
                    return add_popisy("Hodnoty dat v nepovoleném formátu. " + getJmenoIdentifikator(urcenecasoveobdobi), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
                if(!dateOd.before(dateDo)){
                    return add_popisy("Nesplněna podmínka pravidla. OD: " + dateOd +". DO: " + dateDo + ". " + getJmenoIdentifikator(urcenecasoveobdobi), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
            }
        } 
        return true;
    }
    
    //OBSAHOVÁ č.83 Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>.",
    private boolean pravidlo83(){
        if(urceneCasoveObdobi == null) return true;
        for(int i = 0; i < urceneCasoveObdobi.size(); i++){
            Node ucobdobi = urceneCasoveObdobi.get(i);
            Node nodeDo = ValuesGetter.getXChild(ucobdobi, "nsesss:RokDo");
            if(nodeDo != null){
                Node nodeOd = ValuesGetter.getXChild(ucobdobi, "nsesss:RokOd");
                if(nodeOd == null){
                    return add_popisy("Nenalezen element <nsesss:RokOd>. " + getJmenoIdentifikator(ucobdobi), false, get_misto_chyby(ucobdobi));
                }
                boolean b = (ValuesGetter.overSpravnostRetezceProInt(nodeDo.getTextContent()) && ValuesGetter.overSpravnostRetezceProInt(nodeDo.getTextContent()));
                if(!b){
                    return add_popisy("Hodnoty dat jsou v nepovoleném formátu. " + getJmenoIdentifikator(ucobdobi), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
                String d1 = nodeOd.getTextContent().substring(0, 4);
                String d2 = nodeDo.getTextContent().substring(0, 4);
                int intOd = Integer.parseInt(d1);
                int intDo = Integer.parseInt(d2); 
                if(!(intOd < intDo)){
                    return add_popisy("Nesplněna podmínka pravidla. OD: " + intOd +". DO: " + intDo + ". " + getJmenoIdentifikator(ucobdobi), false, get_misto_chyby(nodeOd) + " " + get_misto_chyby(nodeDo));
                }
            }   
        } 
        return true;
    }
    
    //OBSAHOVÁ č.84 Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
    private boolean pravidlo84(){
        NodeList vyrizenis = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", parsedSAX_Sipsoubor);
        if(vyrizenis == null) return true;
        int size = vyrizenis.getLength();
        for(int i = 0; i < size; i++){
            Node n = vyrizenis.item(i);
            boolean maZpusobSHodnotou = ValuesGetter.getObsahujeRodicElementSHodnotou(n, "nsesss:Zpusob", "jiný způsob");
            if(maZpusobSHodnotou){
                Node obs_vyr = ValuesGetter.getXChild(n, "nsesss:ObsahVyrizeni");
                if( obs_vyr == null){
                    return add_popisy("Nenalezen element <nsesss:ObsahVyrizeni>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
                }
                if(!Helper.isStringNoEmpty(obs_vyr.getTextContent())){
                    return add_popisy("Element <nsesss:ObsahVyrizeni> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(n), false, get_misto_chyby(obs_vyr));
                }
            }   
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.85 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou.",
    private boolean pravidlo85(){
        for(int i = 0; i < dokumenty.size(); i++){
            Node dokument = dokumenty.get(i);
            Node analog = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(analog != null){
                String hodnota = analog.getTextContent();
                if(hodnota.equals("ano")){
                    Node uklalaciJednotka = ValuesGetter.getSourozencePrvnihoSeJmenem(analog, "nsesss:UkladaciJednotka");
                    if(uklalaciJednotka == null){
                        return add_popisy("Nenalezen element <nsesss:UkladaciJednotka>. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(analog));
                    }
                    if(!Helper.isStringNoEmpty(uklalaciJednotka.getTextContent())){
                        return add_popisy("Element <nsesss:UkladaciJednotka> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(uklalaciJednotka));
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.86 Pokud je základní entitou dokument (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:MaterskeEntity>.",
    private boolean pravidlo86(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dokument")){
                Node node = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskeEntity");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:MaterskeEntity>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.87 Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> je uveden i element <nsesss:DatumOdeslani>.",
    private boolean pravidlo87(){
        NodeList vyrizeni = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", parsedSAX_Sipsoubor);
        if(vyrizeni == null) return true;
        int size = vyrizeni.getLength();
        for(int i = 0; i < size; i++){
            Node n = vyrizeni.item(i);
            Node datumOdeslani = ValuesGetter.getXChild(n, "nsesss:DatumOdeslani");
            Node prijemce = ValuesGetter.getXChild(n, "nsesss:Prijemce");
            if(datumOdeslani != null && prijemce == null){
                return add_popisy("Nenalezen element <nsesss:Prijemce>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
            }
            if(prijemce != null &&  datumOdeslani == null){
                return add_popisy("Nenalezen element <nsesss:DatumOdeslani>. " + getJmenoIdentifikator(n), false, get_misto_chyby(n));
            }  
        }
        return true;
    }
    
    //OBSAHOVÁ č.88 Pokud element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:Vyrizeni> obsahuje dětský element <nsesss:DatumOdeslani>, pak element <nsesss:Vyrizeni> obsahuje element <nsesss:OdeslaneMnozstvi> s neprázdnou hodnotou.",
    private boolean pravidlo88(){
        for(int i = 0; i < dokumenty.size(); i++){
            Node dokument = dokumenty.get(i);
            Node analog = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(analog != null){
                String hodnota = analog.getTextContent();
                if(hodnota.equals("ano")){
                    Node datumOdeslani = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni", "nsesss:DatumOdeslani");
                    if(datumOdeslani != null){
                        Node node = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni", "nsesss:OdeslaneMnozstvi");
                        if(node == null){
                            return add_popisy("Nenalezen element <nsesss:OdeslaneMnozstvi>. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(dokument));
                        }
                        if(!Helper.isStringNoEmpty(node.getTextContent())){
                            return add_popisy("Element <nsesss:OdeslaneMnozstvi> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(node));
                        }
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.89 Pokud je základní entitou (<nsesss:Dokument>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>.",
    private boolean pravidlo89(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node entita = zakladniEntity.get(i);
            if(entita.getNodeName().equals("nsesss:Dokument")){
                Node node = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if(node == null) return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(entita), false, get_misto_chyby(entita));
                String s = node.getTextContent().substring(0, 4);
                boolean b = ValuesGetter.overSpravnostRetezceProInt(s);
                if(!b) return add_popisy("Hodnota elementu <nsesss:RokSpousteciUdalosti> uvedena v nepovoleném formátu. " + getJmenoIdentifikator(entita), false, get_misto_chyby(node));
                int rokUdalosti = Integer.parseInt(s);

                Node datum = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni", "nsesss:Datum");
                if(datum == null) return add_popisy("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(entita), false, get_misto_chyby(entita));
                String d = datum.getTextContent().substring(0, 4);
                boolean bo = ValuesGetter.overSpravnostRetezceProInt(d);
                if(!bo) return add_popisy("Hodnota elementu <nsesss:Datum> uvedena v nepovoleném formátu. " + getJmenoIdentifikator(entita), false, get_misto_chyby(datum));
                int dat = Integer.parseInt(d);
                if(!(rokUdalosti >= dat)) return add_popisy("Nesplněna podmínka pravidla. Událost: " + rokUdalosti + ". Datum: " + dat + ". " + getJmenoIdentifikator(entita), false, get_misto_chyby(node) + " " + get_misto_chyby(datum));
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.90 Pokud je základní entitou dokument (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
    private boolean pravidlo90(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node spis = zakladniEntity.get(i);
            if(spis.getNodeName().equals("nsesss:Spis")){
                Node node = ValuesGetter.getXChild(spis, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                Node node2 = ValuesGetter.getXChild(spis, "nsesss:EvidencniUdaje", "nsesss:VyrizeniUzavreni", "nsesss:Datum");
                if(node == null) return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(spis), false, get_misto_chyby(spis));
                if(node2 == null) return add_popisy("Nenalezen element <nsesss:Datum>. " + getJmenoIdentifikator(spis), false, get_misto_chyby(spis));
                String rok1 = node.getTextContent().substring(0, 4);
                String rok2 = node2.getTextContent().substring(0, 4);
                boolean b = (ValuesGetter.overSpravnostRetezceProInt(rok1) && ValuesGetter.overSpravnostRetezceProInt(rok1));
                if(!b) return add_popisy("Hodnoty dat jsou zaznamenány v nepovoleném formátu. " + getJmenoIdentifikator(spis), false, get_misto_chyby(node) + " " + get_misto_chyby(node2));
                int rokSpousteci = Integer.parseInt(rok1);
                int rokDatum = Integer.parseInt(rok2);
                if(!(rokSpousteci >= rokDatum)){
                    return add_popisy("Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum + ". " + getJmenoIdentifikator(spis), false, get_misto_chyby(node) + " " + get_misto_chyby(node2));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.91 Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
    private boolean pravidlo91(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node dil = zakladniEntity.get(i);
            if(dil.getNodeName().equals("nsesss:Dil")){
                Node node = ValuesGetter.getXChild(dil, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                Node node2 = ValuesGetter.getXChild(dil, "nsesss:EvidencniUdaje", "nsesss:Uzavreni", "nsesss:Datum");
                if(node == null) return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(dil), false, get_misto_chyby(node));
                if(node2 == null) return add_popisy("Nenalezen element <nsesss:Datum>. "  + getJmenoIdentifikator(dil), false, get_misto_chyby(node2));
                String rok1 = node.getTextContent().substring(0, 4);
                String rok2 = node2.getTextContent().substring(0, 4);
                boolean b = (ValuesGetter.overSpravnostRetezceProInt(rok1) && ValuesGetter.overSpravnostRetezceProInt(rok1));
                if(!b) return add_popisy("Hodnoty dat jsou zaznamenány v nepovoleném formátu. "  + getJmenoIdentifikator(dil), false, get_misto_chyby(node) + " " + get_misto_chyby(node2));
                int rokSpousteci = Integer.parseInt(rok1);
                int rokDatum = Integer.parseInt(rok2);
                if(!(rokSpousteci >= rokDatum)) return add_popisy("Nesplněna podmínka pravidla. Událost: " + rokSpousteci + ". Datum: " + rokDatum + ". "  + getJmenoIdentifikator(dil), false, get_misto_chyby(node) + " " + get_misto_chyby(node2));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.92 Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, jejichž vážený součet je dělitelný jedenácti beze zbytku.",
    private boolean pravidlo92(){
//        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", parsedSAX_Sipsoubor);
        if(identifikatory == null) return add_popisy("Nenalezen element <nsesss:Identifikator>.", false, chyba_neupresneno);
        for(int i = 0; i < identifikatory.size(); i++){
            Node identif = identifikatory.get(i);
            if(!ValuesGetter.hasAttribut(identif, "zdroj")){
                return add_popisy("Elementu <nsesss:Identifikátor> chybí atribut zdroj. " + getJmenoIdentifikator(identif), false, get_misto_chyby(identif));
            }
            String str = ValuesGetter.getValueOfAttribut(identif, "zdroj");
            if(str.equals("IČ") || str.equals("IČO")){
                String hodnota = identif.getTextContent();
                if(!Helper.icoCounter(hodnota)){
                    return add_popisy("IČO není ve správném formátu. " + getJmenoIdentifikator(identif), false, get_misto_chyby(identif));
                    
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.93 Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
    private boolean pravidlo93(){
        if(nazvy != null){
            for(int i = 0; i < nazvy.size(); i++){
                Node nazev = nazvy.get(i);
                String str = nazev.getTextContent();
                if(!Helper.isStringNoEmpty(str)){
                    return add_popisy("Element <nsesss:Nazev> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(nazev), false, get_misto_chyby(nazev));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.94 "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, 
    // jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
    private boolean pravidlo94(){
            if(plneurcenySpisovyZnak == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>.", false, chyba_neupresneno);
            for(int i = 0; i < plneurcenySpisovyZnak.size(); i++){
                Node pusz_node = plneurcenySpisovyZnak.get(i);
                Node jsz_node = ValuesGetter.getSourozencePrvnihoSeJmenem(pusz_node, "nsesss:JednoduchySpisovyZnak");
                if(jsz_node == null){
                    boolean b = ValuesGetter.isXParent(pusz_node, "nsesss:KrizovyOdkaz");
                    if(!b){
                        return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(pusz_node), false, get_misto_chyby(pusz_node));
                    }
                }
                else{
                    String jednoduchy = jsz_node.getTextContent();
                    String plneUrceny = pusz_node.getTextContent();
    //                boolean b = ValuesGetter.compareSpisoveZnaky(jednoduchy, plneUrceny);
                    if(!jednoduchy.equals(plneUrceny)){
                        if(!plneUrceny.endsWith(jednoduchy)){
                            return add_popisy("Část plně určeného spis. znaku za oddělovačem neodpovídá jedn. spis. znaku. " + getJmenoIdentifikator(pusz_node), false, get_misto_chyby(pusz_node) + " " + get_misto_chyby(jsz_node));
                        } 
                    }
                }
            }
            return true;
    }
    
    //OBSAHOVÁ č.95 Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), 
    // která obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s 
    //hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem, 
    // se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), 
    // přičemž hodnota jejího elementu <nsesss:PlneUrcenySpisovyZnak> v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> se rovná hodnotě elementu 
    // <nsesss:PlneUrcenySpisovyZnak> výchozí entity před posledním oddělovačem.",
    private boolean pravidlo95(){
        ArrayList<Node> vecneSkupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", parsedSAX_Sipsoubor);
        ArrayList<Node> typoveSpisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", parsedSAX_Sipsoubor);
        ArrayList<Node> list = new ArrayList<>();
        if(vecneSkupiny == null) return add_popisy("Nenalezen element <nsesss:VecnaSkupina>.", false, chyba_neupresneno);
        list.addAll(vecneSkupiny);
        if(soucasti != null) list.addAll(soucasti);
        if(typoveSpisy != null) list.addAll(typoveSpisy);
        
        int velikostListu = list.size();
        for(int i = 0; i < velikostListu; i++){
            Node entita = list.get(i);
            Node pu_entita = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(pu_entita == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(entita), false, get_misto_chyby(entita));
            Node je_entita = ValuesGetter.getSourozencePrvnihoSeJmenem(pu_entita, "nsesss:JednoduchySpisovyZnak");
            if(je_entita == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(entita), false, get_misto_chyby(entita));
            Node pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina", "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(pu_rodic == null) pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:TypovySpis", "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(pu_rodic == null) pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:Soucast", "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(pu_rodic == null) pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(pu_rodic != null){
                if(pu_rodic.getNodeName().equals("nsesss:SpisovyPlan")){
                    if(sz_ma_oddelovac_vsobe(pu_entita.getTextContent())){
                        return add_popisy("Spisový znak nejvyšší věcné skupiny v sobě nesmí obsahovat oddělovač. " + getJmenoIdentifikator(entita), false, get_misto_chyby(entita));
                    }
                }
                else{
                    String entita_jsz = je_entita.getTextContent();
                    String entita_pus = pu_entita.getTextContent();
                    String rodic_pus = pu_rodic.getTextContent();
                    boolean zacina = entita_pus.startsWith(rodic_pus);
                    if(!zacina){
                        return add_popisy("Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz + ". Plně urč. spis. znak entity: " + entita_pus + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". " + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic), false, get_misto_chyby(je_entita) + " " + get_misto_chyby(pu_entita) + " " + get_misto_chyby(pu_rodic));
                    }
                    boolean konci = entita_pus.endsWith(entita_jsz);
                    if(!konci){
                        return add_popisy("Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz + ". Plně urč. spis. znak entity: " + entita_pus + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". "  + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic), false, get_misto_chyby(je_entita) + " " + get_misto_chyby(pu_entita) + " " + get_misto_chyby(pu_rodic));
                    } 
                }
            }
        }
        
        
        
        return true;
    }
    
    //OBSAHOVÁ č.96 Každá základní entita a každá entita typový spis (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).",
    private boolean pravidlo96(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            Node n_zakl_jsz = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
            Node n_zakl_pusz = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(n_zakl_jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            if(n_zakl_pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
            String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();
            
            String jednoduchy, plneUrceny;
            if(zakladnientita.getNodeName().equals("nsesss:Dil")){
                Node n_soucast = ValuesGetter.getFirstInNode(zakladnientita, "nsesss:Soucast", parsedSAX_Sipsoubor);
                if(n_soucast == null) return add_popisy("Nenalezen element <nsesss:Soucast>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                Node n_soucast_jsz = ValuesGetter.getXChild(n_soucast, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                if(n_soucast_jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_soucast));
                Node n_soucast_pusz = ValuesGetter.getXChild(n_soucast, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                if(n_soucast_pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_soucast));
                
                Node n_typ = ValuesGetter.getFirstInNode(zakladnientita, "nsesss:TypovySpis", parsedSAX_Sipsoubor);
                if(n_typ == null) return add_popisy("Nenalezen element <nsesss:TypovySpis>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                Node n_typ_jsz = ValuesGetter.getXChild(n_typ, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                if(n_typ_jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_typ));
                Node n_typ_pusz = ValuesGetter.getXChild(n_typ, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                if(n_typ_pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_typ));
                
                
                jednoduchy = n_soucast_jsz.getTextContent();
                plneUrceny = n_soucast_pusz.getTextContent();
                
                String tyt_p = n_typ_pusz.getTextContent();
                String typ_j = n_typ_jsz.getTextContent();
                
                boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                if(!b) return add_popisy("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(n_soucast_jsz), false, get_misto_chyby(n_zakl_jsz) + " " + get_misto_chyby(n_zakl_pusz) + " " +get_misto_chyby(n_soucast_jsz) + " " + get_misto_chyby(n_soucast_pusz));
                
                ArrayList<Node> vecneSkupiny = ValuesGetter.getAllInNode(zakladnientita, "nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
                if(vecneSkupiny == null || vecneSkupiny.isEmpty()) return add_popisy("Nenalezen element <nsesss:VecnaSkupina> základní entity. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                //ss
                Node n_j = ValuesGetter.getXChild(vecneSkupiny.get(vecneSkupiny.size()-1), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node n_p = ValuesGetter.getXChild(vecneSkupiny.get(vecneSkupiny.size()-1), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                if(n_j == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(vecneSkupiny.get(vecneSkupiny.size()-1)));
                if(n_p == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(vecneSkupiny.get(vecneSkupiny.size()-1)));
                String jednoduchy_vs = n_j.getTextContent();
                String plneUrceny_vs = n_p.getTextContent();
                boolean b1 = jednoduchy_vs.equals(typ_j) && plneUrceny_vs.equals(tyt_p);
                if(!b1) return add_popisy("Nesplněna podmínka pravidla. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_zakl_jsz) + " " + get_misto_chyby(n_zakl_pusz) + " " +get_misto_chyby(n_typ_jsz) + " " + get_misto_chyby(n_typ_pusz));
            }
            else{
                ArrayList<Node> vecneSkupiny = ValuesGetter.getAllInNode(zakladnientita, "nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
                if(vecneSkupiny == null || vecneSkupiny.isEmpty()) return add_popisy("Nenalezen element <nsesss:VecnaSkupina> základní entity.", false, get_misto_chyby(zakladnientita));
                Node n_j = ValuesGetter.getXChild(vecneSkupiny.get(0), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node n_p = ValuesGetter.getXChild(vecneSkupiny.get(0), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                if(n_j == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(vecneSkupiny.get(0)));
                if(n_p == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "  + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(vecneSkupiny.get(0)));
                jednoduchy = n_j.getTextContent();
                plneUrceny = n_p.getTextContent();
                boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                if(!b) return add_popisy("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n_zakl_jsz) + " " + get_misto_chyby(n_zakl_pusz) + " " + get_misto_chyby(n_j) + " " + get_misto_chyby(n_p));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.97 Pokud existuje více než jedna základní entita, všechny obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
    private boolean pravidlo97(){
        if(zakladniEntity.size() > 1){
            Node ze0 = zakladniEntity.get(0);
            Node n0_j = ValuesGetter.getXChild(ze0, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
            Node n0_p = ValuesGetter.getXChild(ze0, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak"); 
            if(n0_j == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. " + getJmenoIdentifikator(ze0), false, get_misto_chyby(ze0));
            if(n0_p == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. " + getJmenoIdentifikator(ze0), false, get_misto_chyby(ze0));
            String jednoduchy = n0_j.getTextContent();
            String plneUrceny = n0_p.getTextContent();
            
            for(int i = 1; i < zakladniEntity.size(); i++){
                Node zakladnientita = zakladniEntity.get(i);
                Node n_j = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node n_p = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak"); 
                if(n_j == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                if(n_p == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                String jednoduchySpZnZaklEnt = n_j.getTextContent();
                String plneUrcenySpZnZaklEnt = n_p.getTextContent(); 
                
                boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                if(!b) return add_popisy("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(n0_j) + " " + get_misto_chyby(n0_p) + " " + get_misto_chyby(n_j) + " " + get_misto_chyby(n_p));
            }    
        }
        return true;
    }
    
    //OBSAHOVÁ č.98 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli dětské entity dokument (<nsesss:Dokument>).",
    private boolean pravidlo98(){
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dil") || zakladnientita.getNodeName().equals("nsesss:Spis")){
            Node n_zakl_jsz = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
            Node n_zakl_pusz = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if(n_zakl_jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            if(n_zakl_pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
            String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
            String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();
            
            String jednoduchy, plneUrceny;
            if(dokumenty == null || dokumenty.isEmpty()) return add_popisy("Nenalezen element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladnientita), false, get_misto_chyby(zakladnientita));
                for(int j = 0; j < dokumenty.size(); j++){
                    Node dokument = dokumenty.get(j);
                    Node n_j = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                    if(n_j == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(dokument), false, get_misto_chyby(dokument));
                    jednoduchy = n_j.getTextContent();
                    Node n_p = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                    if(n_p == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "  + getJmenoIdentifikator(dokument), false, get_misto_chyby(dokument));
                    plneUrceny = n_p.getTextContent();
                    
                    boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                    if(!b) return add_popisy("Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladnientita) + " "  + getJmenoIdentifikator(dokument), false, get_misto_chyby(n_zakl_jsz) + " " + get_misto_chyby(n_zakl_pusz) + " " + get_misto_chyby(n_j) + " " + get_misto_chyby(n_p));
                }
            }
        }
        return true;
    }
    
    private int pravidlo54_pocitadlo(){
        int a = 0;
        ArrayList<Node> plany = ValuesGetter.getAllAnywhereArrayList("nsesss:SpisovyPlan", parsedSAX_Sipsoubor);
        if(plany != null) a = pravidlo56upresneniPocitadla(plany);
        ArrayList<Node> skupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", parsedSAX_Sipsoubor);
        if(skupiny != null) a += pravidlo56upresneniPocitadla(skupiny);
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", parsedSAX_Sipsoubor);
        if(soucasti != null) a += pravidlo56upresneniPocitadla(soucasti);
        ArrayList<Node> typoveSpisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", parsedSAX_Sipsoubor);
        if(typoveSpisy != null) a += pravidlo56upresneniPocitadla(typoveSpisy);
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereArrayList("nsesss:Spis", parsedSAX_Sipsoubor);
        if(spisy != null) a += pravidlo56upresneniPocitadla(spisy);
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereArrayList("nsesss:Dil", parsedSAX_Sipsoubor);
        if(dily != null) a += pravidlo56upresneniPocitadla(dily);
        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", parsedSAX_Sipsoubor);
        if(dokumenty != null) a += pravidlo56upresneniPocitadla(dokumenty);
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Komponenta", parsedSAX_Sipsoubor);
        if(komponenty != null) a += pravidlo56upresneniPocitadla(komponenty);
        
        return a;
    }
    
    private boolean pravidlo54_pocitadlo_amdsec(int pocet_div){
        NodeList list = ValuesGetter.getAllAnywhere("mets:amdSec", parsedSAX_Sipsoubor);
        if(list == null) return false;
        int pocetAmd = list.getLength();
        boolean vysledek = pocetAmd == pocet_div;
        return vysledek;
    }

    private int pravidlo56upresneniPocitadla(ArrayList<Node> list){
        ArrayList<String> idcka = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Node n = list.get(i);
            String idn = ValuesGetter.getValueOfAttribut(n, "ID");
            if(!HelperArrayList.equalsString(idcka, idn)){
                idcka.add(idn);
            }
        }

        return idcka.size();
    }
    
    private ArrayList<Node> get_krizove_odkazy_pevny_ano(){
        ArrayList<Node> list = new ArrayList<>();
        NodeList krizoveOdkazy = ValuesGetter.getAllAnywhere("nsesss:KrizovyOdkaz", parsedSAX_Sipsoubor);
        if(krizoveOdkazy != null){
            list = new ArrayList<>();
            for(int i = 0; i < krizoveOdkazy.getLength(); i++){
                if(ValuesGetter.hasAttributValue(krizoveOdkazy.item(i), "pevny", "ano")){
                    list.add(krizoveOdkazy.item(i));
                }
            }
        }
        return list;
    }
    
    private File get_komponenta(String jmeno_souboru){
        File komponenty = new File(SIP_MAIN_helper.getCesta_komponenty(sipSoubor));
        if(komponenty.exists()){
            File[] seznam = komponenty.listFiles();
            for(File komponenta : seznam){
                String name = komponenta.getName();
                String nameWithOutExt = FilenameUtils.removeExtension(komponenta.getName());
                if(jmeno_souboru.equals(name) || jmeno_souboru.equals(nameWithOutExt)){
                    return komponenta;
                }
            }
        }      
        return null;
    }
    
    // na konci oddělovač nehlídá
    private boolean sz_ma_oddelovac_vsobe(String spisovy_znak){
        for(int i = 0; i < spisovy_znak.length()-1; i++){
            char c = spisovy_znak.charAt(i);
            if(c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.'){
                return true;
            }
            
        }
        
        return false;
    }

}
