/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo70;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo71;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo72;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo73;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo74;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo75;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo76;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo77;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo78;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs70_79.Pravidlo79;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo80;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo81;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo82;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo83;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo84;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo85;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo86;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo87;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo88;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs80_89.Pravidlo89;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo90;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo91;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo92;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo94;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo96;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo97;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo98;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Metods;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_amdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_dmdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_metsdiv;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_amdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_dmdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_metsdiv;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_node;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;


/**
 * Obsahova kontrola
 * 
 */
public class K06_Obsahova
        extends KontrolaBase
{
	
    static final public String NAME = "kontrola obsahu";

    // Identifikatory kontrol
    static final public String OBS1 = "obs1";
    static final public String OBS2 = "obs2";
    static final public String OBS3 = "obs3";
    static final public String OBS4 = "obs4";
    static final public String OBS9 = "obs9";

    static final public String OBS10 = "obs10";
    static final public String OBS11 = "obs11";
    static final public String OBS12 = "obs12";
    static final public String OBS13 = "obs13";
    static final public String OBS14 = "obs14";
    static final public String OBS15 = "obs15";
    static final public String OBS16 = "obs16";
    static final public String OBS17 = "obs17";
    static final public String OBS18 = "obs18";
    static final public String OBS19 = "obs19";

    static final public String OBS20 = "obs20";
    //static final public String OBS21 = "obs21";
    static final public String OBS22 = "obs22";
    static final public String OBS23 = "obs23";
    static final public String OBS24 = "obs24";
    static final public String OBS25 = "obs25";
    static final public String OBS26 = "obs26";
    static final public String OBS27 = "obs27";
    static final public String OBS28 = "obs28";
    static final public String OBS29 = "obs29";

    static final public String OBS30 = "obs30";
    static final public String OBS31 = "obs31";
    //static final public String OBS32 = "obs32";
    static final public String OBS33 = "obs33";
    static final public String OBS34 = "obs34";
    static final public String OBS35 = "obs35";
    static final public String OBS36 = "obs36";
    static final public String OBS37 = "obs37";
    static final public String OBS38 = "obs38";
    static final public String OBS39 = "obs39";

    static final public String OBS40 = "obs40";
    //static final public String OBS41 = "obs41";
    //static final public String OBS42 = "obs42";
    //static final public String OBS43 = "obs43";
    static final public String OBS44 = "obs44";
    static final public String OBS45 = "obs45";
    static final public String OBS46 = "obs46";
    static final public String OBS47 = "obs47";
    static final public String OBS48 = "obs48";
    static final public String OBS49 = "obs49";
	
    static final public String OBS50 = "obs50";
    static final public String OBS51 = "obs51";
    static final public String OBS52 = "obs52";
    static final public String OBS53 = "obs53";
    static final public String OBS54 = "obs54";
    static final public String OBS54A = "obs54a";
    static final public String OBS55 = "obs55";
    static final public String OBS56 = "obs56";
    static final public String OBS57 = "obs57";
    static final public String OBS58 = "obs58";
    static final public String OBS59 = "obs59";

    static final public String OBS60 = "obs60";
    static final public String OBS61 = "obs61";
    static final public String OBS61A = "obs61a";
    static final public String OBS62 = "obs62";
    static final public String OBS63 = "obs63";
    static final public String OBS64 = "obs64";
    static final public String OBS65 = "obs65";
    static final public String OBS66 = "obs66";
    static final public String OBS67 = "obs67";
    static final public String OBS68 = "obs68";
    static final public String OBS69 = "obs69";

    static final public String OBS70 = "obs70";
    static final public String OBS71 = "obs71";
    static final public String OBS72 = "obs72";
    static final public String OBS73 = "obs73";
    static final public String OBS74 = "obs74";
    static final public String OBS75 = "obs75";
    static final public String OBS76 = "obs76";
    static final public String OBS77 = "obs77";
    static final public String OBS78 = "obs78";
    static final public String OBS79 = "obs79";

    static final public String OBS80 = "obs80";
    static final public String OBS81 = "obs81";
    static final public String OBS82 = "obs82";
    static final public String OBS83 = "obs83";
    static final public String OBS84 = "obs84";
    static final public String OBS85 = "obs85";
    static final public String OBS86 = "obs86";
    static final public String OBS87 = "obs87";
    static final public String OBS88 = "obs88";
    static final public String OBS89 = "obs89";

    static final public String OBS90 = "obs90";
    static final public String OBS91 = "obs91";
    static final public String OBS92 = "obs92";
    static final public String OBS93 = "obs93";
    static final public String OBS93A = "obs93a";
    static final public String OBS94 = "obs94";
    static final public String OBS94A = "obs94a";
    static final public String OBS95 = "obs95";
    static final public String OBS96 = "obs96";
    static final public String OBS97 = "obs97";
    static final public String OBS98 = "obs98";
    // static final public String OBS99 = "obs99";

    static final public String MISTO_CHYBY_NEUPRESNENO = "Neupřesněno.";
    private String popisChyby = "Pravidlo nesplněno.";
    private String misto_chyby = "";
	SipInfo sipSoubor;
	private int[] seznamPravidel;

    private MetsParser metsParser;

    private Node metsMets;
    private Node metsHdr;
    private Node metsMdWrap;
    private Node metsDmdSec;

    private List<Node> zakladniEntity;
    private List<Node> dokumenty;

    private List<Node> identifikatory;

    private List<Node> manipulace;

    private Node xmlData;
    
    /**
     * Mapa kontrol
     */
    Map<String, Runnable> kontroly = new HashMap<>();
       
    public K06_Obsahova(int[] seznamPravidel) {
    	this.seznamPravidel = seznamPravidel;

        pridejPravidlo(OBS1, () -> pravidlo1());
        pridejPravidlo(OBS2, () -> pravidlo2());
        pridejPravidlo(OBS3, () -> pravidlo3());
        pridejPravidlo(OBS4, () -> pravidlo4());
        pridejPravidlo(OBS9, () -> pravidlo9());

        pridejPravidlo(new Pravidlo70(this));
        pridejPravidlo(new Pravidlo71(this));
        pridejPravidlo(new Pravidlo72(this));
        pridejPravidlo(new Pravidlo73(this));
        pridejPravidlo(new Pravidlo74(this));
        pridejPravidlo(new Pravidlo75(this));
        pridejPravidlo(new Pravidlo76(this));
        pridejPravidlo(new Pravidlo77(this));
        pridejPravidlo(new Pravidlo78(this));
        pridejPravidlo(new Pravidlo79(this));
        pridejPravidlo(new Pravidlo80(this));
        pridejPravidlo(new Pravidlo81(this));
        pridejPravidlo(new Pravidlo82(this));
        pridejPravidlo(new Pravidlo83(this));
        pridejPravidlo(new Pravidlo84(this));
        pridejPravidlo(new Pravidlo85(this));
        pridejPravidlo(new Pravidlo86(this));
        pridejPravidlo(new Pravidlo87(this));
        pridejPravidlo(new Pravidlo88(this));
        pridejPravidlo(new Pravidlo89(this));
        pridejPravidlo(new Pravidlo90(this));
        pridejPravidlo(new Pravidlo91(this));
        pridejPravidlo(new Pravidlo92(this));
        pridejPravidlo(new Pravidlo93(this));
        pridejPravidlo(new Pravidlo94(this));
        pridejPravidlo(new Pravidlo95(this));
        pridejPravidlo(new Pravidlo96(this));
        pridejPravidlo(new Pravidlo97(this));
        pridejPravidlo(new Pravidlo98(this));
    } 
    
    private void pridejPravidlo(K06PravidloBase pravidlo) {
        String id = pravidlo.getKodPravidla();
        pridejPravidlo(id, pravidlo);
    }

    private void pridejPravidlo(String id, Runnable pravidlo) {
        Runnable p = kontroly.get(id);
        Validate.isTrue(p == null, "Pravidlo jiz existuje: %s", id);
        kontroly.put(id, pravidlo);
    }

    private String getIDpravidla(int j){
        if (j == 32)
            return OBS93A;
        if (j == 41)
            return OBS54A;
        if (j == 42)
            return OBS61A;
        if (j == 43)
            return OBS94A;

        return "obs" + Integer.toString(j);
    }
    
    private boolean udelejPravidloObsahovaSpolecna2018(int cisloPravidla) throws IOException, ParseException{
        boolean vysledek = false;
        switch (cisloPravidla) {
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
        }
        
        return vysledek;
    }
    
    private boolean add_popisy(String chyba, boolean ret, String misto_chyby){
        popisChyby = chyba;
        this.misto_chyby = misto_chyby;
        return ret;
    }
    
    public String getJmenoIdentifikator(Node node) {
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
    
    public String getMistoChyby(Node node) {
        if (node == null) {
            return null;
        }
        Object userData = node.getUserData("lineNumber");
        if (userData == null) {
            return null;
        }
        String point = userData.toString();
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
    
    void pridejPravidlo(String idPravidla,
                                boolean jeOk,
                                String textPravidla,
                                String detailChyby,
                                String obecnyPopisChyby,
                                String mistoChyby,
                                String zdroj) {
        PravidloKontroly p = new PravidloKontroly(idPravidla,
                jeOk,
                textPravidla,
                detailChyby,
                obecnyPopisChyby,
                mistoChyby,
                zdroj);
        vysledekKontroly.add(p);

    }

    //OBSAHOVÁ č.1 Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
    private void pravidlo1() {
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;
        String obecnyPopisChyby = null;

        if(!ValuesGetter.hasAttribut(metsMets, "OBJID")){
            detailChyby = "Nenalezen atribut OBJID kořenového elementu <mets:mets>.";
            mistoChyby = getMistoChyby(metsMets);
        } else
        if (StringUtils.isBlank(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"))) {
            detailChyby = "Atribut OBJID kořenového elementu <mets:mets> není vyplněn.";
            mistoChyby = getMistoChyby(metsMets);
        } else {
            stav = true;
        }
        if (!stav) {
            obecnyPopisChyby = "Chybí identifikátor datového balíčku SIP.";
        }

        pridejPravidlo(OBS1,
                       stav,
                       "Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
                       detailChyby,
                       obecnyPopisChyby,
                       mistoChyby,
                       "Bod 2.1. přílohy č. 3 NSESSS.");
    }
    
    //OBSAHOVÁ č.2 Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.
    private void pravidlo2() {
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;
        String obecnyPopisChyby = null;

        if(!ValuesGetter.hasAttribut(metsMets, "LABEL")){
            detailChyby = "Nenalezen atribut LABEL kořenového elementu <mets:mets>.";
        } else {
            String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
            if (StringUtils.isBlank(hodLab)) {
                detailChyby = "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.";
            } else if (hodLab.equals("Datový balíček pro provedení skartačního řízení") ||
                    hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
                stav = true;
            } else {
                detailChyby = "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                        + hodLab + ".";
            }
        }
        if (!stav) {
            mistoChyby = getMistoChyby(metsMets);
            obecnyPopisChyby = "Uveden je chybně popis datového balíčku SIP.";
        }

        pridejPravidlo(OBS2,
                       stav,
                       "Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
                       detailChyby,
                       obecnyPopisChyby,
                       mistoChyby,
                       "Bod 2.1. přílohy č. 3 NSESSS.");
    }
    
    //OBSAHOVÁ č.3 Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
    private void pravidlo3() {
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;
        String obecnyPopisChyby = null;

        if(!ValuesGetter.hasAttribut(metsMets, "LABEL")){
            detailChyby = "Nenalezen atribut LABEL kořenového elementu <mets:mets>.";
        } else {
            String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
            if (StringUtils.isBlank(hodLab)) {
                detailChyby = "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.";
            } else if (!hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
                detailChyby = "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                        + hodLab + ".";
            } else {
                stav = true;
            }
        }
        if (!stav) {
            mistoChyby = getMistoChyby(metsMets);
            obecnyPopisChyby = "Uveden je chybně popis datového balíčku SIP.";
        }

        pridejPravidlo(OBS3,
                       stav,
                       "Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
                       detailChyby,
                       obecnyPopisChyby,
                       mistoChyby,
                       "Bod 2.1. přílohy č. 3 NSESSS.");
    }
    
    //OBSAHOVÁ č.4 Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
    private void pravidlo4() {
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;
        String obecnyPopisChyby = null;

        if (!ValuesGetter.hasAttribut(metsMets, "xmlns:xsi")) {
            detailChyby = "Nenalezen atribut xmlns:xsi kořenového elementu <mets:mets>.";
        } else {
            String hod = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xsi");
            if (StringUtils.isBlank(hod)) {
                detailChyby = "Atribut xmlns:xsi kořenového elementu <mets:mets> má prázdnou hodnotu.";
            } else if (!hod.equals("http://www.w3.org/2001/XMLSchema-instance")) {
                detailChyby = "Atribut xmlns:xsi kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                        + hod + ".";
            } else {
                stav = true;
            }
        }

        if (!stav) {
            mistoChyby = getMistoChyby(metsMets);
            obecnyPopisChyby = "Uvedena je chybně adresa jmenného prostoru schématu XML.";
        }

        pridejPravidlo(OBS4,
                       stav,
                       "Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
                       detailChyby,
                       obecnyPopisChyby,
                       mistoChyby,
                       "Bod 2.1. přílohy č. 3 NSESSS.");
    }
    
    //OBSAHOVÁ č.9 Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.
    private void pravidlo9() {
        boolean stav = false;
        String mistoChyby = null;
        String detailChyby = null;
        String obecnyPopisChyby = null;

        if(!ValuesGetter.hasAttribut(metsMets, "xmlns:xlink")){
            detailChyby = "Nenalezen atribut xmlns:xlink kořenového elementu <mets:mets>.";
        } else {
            String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xlink");
            if (!hodnota.equals("http://www.w3.org/1999/xlink")) {
                detailChyby = "Atribut xmlns:xlink kořenového elementu <mets:mets> neobsahuje hodnotu http://www.w3.org/1999/xlink.";
            } else {
                stav = true;
            }
        }

        if (!stav) {
            mistoChyby = getMistoChyby(metsMets);
            obecnyPopisChyby = "Uvedena je chybně adresa jmenného prostoru schématu XML.";
        }

        pridejPravidlo(OBS9,
                       stav,
                       "Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.",
                       detailChyby,
                       obecnyPopisChyby,
                       mistoChyby,
                       "Bod 2.1. přílohy č. 3 NSESSS.");
    }
    
    //OBSAHOVÁ č.10 Element <mets:mets> obsahuje právě jeden dětský element <mets:metsHdr>.",
    private boolean pravidlo10(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false,MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:metsHdr")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", false, getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:metsHdr")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:metsHdr>.", false, getMistoChyby(metsMets));
        }
        return true;
    }
    
    //OBSAHOVÁ č.11 Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
    private boolean pravidlo11(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:dmdSec")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:dmdSec>.", false, getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:dmdSec")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:dmdSec>.", false, getMistoChyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.12 Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
    private boolean pravidlo12(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:amdSec")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:amdSec>.", false, getMistoChyby(metsMets));
        }       
        return true;
    }
    
    //OBSAHOVÁ č.13 Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
    private boolean pravidlo13(){
        if(metsMets == null) return add_popisy("Nenalezen kořenový element <mets:mets>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:structMap")){
            return add_popisy("Kořenový element <mets:mets> nemá žádný dětský element <mets:structMap>.", false, getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:structMap")){
            return add_popisy("Kořenový element <mets:mets> má více než jeden dětský element <mets:structMap>.", false, getMistoChyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.14 Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
    private boolean pravidlo14(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsHdr, "LASTMODDATE")){
            return add_popisy("Element <mets:metsHdr> nemá atribut LASTMODDATE.", false, getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.15 Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
    private boolean pravidlo15(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsHdr, "CREATEDATE")){
            return add_popisy("Element <mets:metsHdr> nemá atribut CREATEDATE.", false, getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
    private boolean pravidlo16(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, MISTO_CHYBY_NEUPRESNENO);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return add_popisy("Nenalezen element <mets:agent>.", false, getMistoChyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "ORGANIZATION")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return add_popisy("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", false, getMistoChyby(metsHdr));
        }
        if(pocitadlo > 1){
            return add_popisy("Element <mets:metsHdr> obsahuje více elementů <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", false, getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
    private boolean pravidlo17(){
        if(metsHdr == null) return add_popisy("Nenalezen element <mets:metsHdr>.", false, MISTO_CHYBY_NEUPRESNENO);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return add_popisy("Nenalezen element <mets:agent>.", false, getMistoChyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "INDIVIDUAL")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return add_popisy("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", false, getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
    private boolean pravidlo18(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttributValue(node, "ROLE", "CREATOR")){
               pocitadlo++;
               ch += getMistoChyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return add_popisy("Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", false, ch);
        return true;
    }
    
    //OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
    private boolean pravidlo19(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttribut(node, "ID")){
               pocitadlo++;
               ch += getMistoChyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return add_popisy("Element <mets:agent> neobsahuje atribut ID.", false, ch);
        return true;
    }
    
    //OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
    private boolean pravidlo20(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return add_popisy("Nenalezen žádný element <mets:agent>.", false, MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        int pocitadlo2 = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:name")){
                if (StringUtils.isBlank(ValuesGetter.getXChild(node, "mets:name").getTextContent())) {
                    pocitadlo2++;
                    ch += getMistoChyby(node) + " ";
                }
            } 
            else{
               pocitadlo ++; 
               ch += getMistoChyby(node) + " ";
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
        if(metsDmdSec == null) return add_popisy("Nenalezen element <mets:dmdSec>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsDmdSec, "mets:mdWrap")){
            return add_popisy("Element <mets:dmdSec> neobsahuje žádný dětský element <mets:mdWrap>.", false, getMistoChyby(metsDmdSec));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsDmdSec, "mets:mdWrap")){
            return add_popisy("Element <mets:dmdSec> obsahuje více než jeden dětský element <mets:mdWrap>.", true, getMistoChyby(metsDmdSec));
        }
        return true;
    }
    
    //OBSAHOVÁ č.23 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
    private boolean pravidlo23(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPEVERSION")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", false, getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPEVERSION");
        if (StringUtils.isBlank(g)) {
            return add_popisy("Atribut MDTYPEVERSION elementu <mets:mdWrap> má prázdnou hodnotu.", false, getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPEVERSION", "3.0")){
            return add_popisy("Atribut MDTYPEVERSION elementu <mets:mdWrap> neobsahuje hodnotu 3.0.", false, getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.24 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
    private boolean pravidlo24(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "OTHERMDTYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", false, getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "OTHERMDTYPE");
        if (StringUtils.isBlank(g)) {
            return add_popisy("Atribut OTHERMDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "OTHERMDTYPE", "NSESSS")){
            return add_popisy("Atribut OTHERMDTYPE elementu <mets:mdWrap> neobsahuje hodnotu NSESSS.", false, getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.25 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    private boolean pravidlo25(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", false, getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPE");
        if (StringUtils.isBlank(g)) {
            return add_popisy("Atribut MDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPE", "OTHER")){
            return add_popisy("Atribut MDTYPE elementu <mets:mdWrap> neobsahuje hodnotu OTHER.", false, getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.26 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    private boolean pravidlo26(){
        if(metsMdWrap == null) return add_popisy("Nenalezen element <mets:mdWrap>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MIMETYPE")){
            return add_popisy("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", false, getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MIMETYPE");
        if (StringUtils.isBlank(g)) {
            return add_popisy("Atribut MIMETYPE elementu <mets:mdWrap> má prázdnou hodnotu.", false, getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MIMETYPE", "text/xml")){
            return add_popisy("Atribut MIMETYPE elementu <mets:mdWrap> neobsahuje hodnotu text/xml.", false, getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.27 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    private boolean pravidlo27(){
        if(xmlData == null) return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMdWrap, "mets:xmlData")){
            return add_popisy("Element <mets:mdWrap> obsahuje více dětských elementů <mets:xmlData>.", false, getMistoChyby(metsMdWrap));
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
                return add_popisy("Nenalezen element <mets:xmlData>.", false, MISTO_CHYBY_NEUPRESNENO);
            }
            if(xmlData.getChildNodes().getLength() == 0){
                return add_popisy("Element <mets:xmlData> neobsahuje žádné dětské elementy.", false, getMistoChyby(xmlData));
            }
            ArrayList<Node> list = ValuesGetter.get_Node_Children(xmlData);
            if(!ValuesGetter.maRodicPouzeTytoPotomky(xmlData, "nsesss:Dil", "nsesss:Dokument", "nsesss:Spis")){
                String ch = "";
                for(int i = 0; i < list.size(); i++){
                    Node node = list.get(i);
                    String name = node.getNodeName();
                    if(!name.equals("nsesss:Dokument") || !name.equals("nsesss:Spis") || !name.equals("nsesss:Dil")){
                        ch += getMistoChyby(list.get(i)) + " "; 
                    }
                }
                return add_popisy("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", false, ch);
            }
            if(list.size() > 1){
                String ch = "";
                String pop = "";
                for(int i = 1; i < list.size(); i++){
                    Node node = list.get(i);
                    ch += getMistoChyby(node) + " "; 
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
                        ch += getMistoChyby(node) + " "; 
                    }
                }
                return add_popisy("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", false, ch);
        }
        
        for(int i = 0; i < krizove_odkazy_pevny_ano.size(); i++){
            Node krizovyOdkaz = krizove_odkazy_pevny_ano.get(i);
            Node materska_zakl_entita_eu = ValuesGetter.getXParent(krizovyOdkaz, "nsesss:Souvislosti", "nsesss:EvidencniUdaje");
            
            if(materska_zakl_entita_eu == null){
                return add_popisy("Element <nsesss:KrizovyOdkaz> je špatně zatříděn. Nenalezeny elementy <nsesss:Souvislosti> a <nsesss:EvidencniUdaje>.", false, getMistoChyby(krizovyOdkaz));
            }
            Node za_ent = materska_zakl_entita_eu.getParentNode();
            Node identifikator_me = ValuesGetter.getXChild(materska_zakl_entita_eu, "nsesss:Identifikace", "nsesss:Identifikator");
            if(identifikator_me == null){
                return add_popisy("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí element <nsesss:Identifikator>.", false, getMistoChyby(za_ent));
            }
            if(!ValuesGetter.hasAttribut(identifikator_me, "zdroj")){
                return add_popisy("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí atribut zdroj u elementu <nsesss:Identifikator>.", false, getMistoChyby(identifikator_me));
            }
            String zdroj_me = ValuesGetter.getValueOfAttribut(identifikator_me, "zdroj");
            String ident_me = identifikator_me.getTextContent();
            Node identifikator_ko = ValuesGetter.getXChild(krizovyOdkaz, "nsesss:Identifikator");
            if(identifikator_ko == null){
                return add_popisy("Element <nsesss:KrizovyOdkaz> nemá dětský element <nsesss:Identifikator>.", false, getMistoChyby(krizovyOdkaz));
            }
            if(!ValuesGetter.hasAttribut(identifikator_ko, "zdroj")){
                return add_popisy("Dětský element <nsesss:Identifikator> elementu <nsesss:KrizovyOdkaz> nemá atribut zdroj.", false, getMistoChyby(identifikator_ko));
            }
            String zdroj_ko = ValuesGetter.getValueOfAttribut(identifikator_ko, "zdroj");
            String ident_ko = identifikator_ko.getTextContent();
            
            if(zdroj_me.equals(zdroj_ko) && ident_me.equals(ident_ko)){
                return add_popisy("Element <nsesss:KrizovyOdkaz> odkazuje na vlastní základní entitu.", false, getMistoChyby(krizovyOdkaz));
            }
            
            int pocitadlo = 0;
            String ch = "";
                for(int k = 0; k < zakladniEntity.size(); k++){
                    Node zentita = zakladniEntity.get(k);
                    Node id_ze = ValuesGetter.getXChild(zentita, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                    if(id_ze == null){
                        return add_popisy("Nenalezen element <nsesss:Identifikator> základní entity.", false, getMistoChyby(zentita));
                    }
                    if(!ValuesGetter.hasAttribut(id_ze, "zdroj")){
                        return add_popisy("Nenalezen atribut zdroj elementu <nsesss:Identifikator>.", false, getMistoChyby(id_ze));
                    }
                    String hodnotaZdrojMatEnt = ValuesGetter.getValueOfAttribut(id_ze, "zdroj");
                    String hodnotaIdentMatEnt = id_ze.getTextContent();
                    if(zdroj_ko.equals(hodnotaZdrojMatEnt) &&  ident_ko.equals(hodnotaIdentMatEnt)){
                        pocitadlo++;
                        ch += getMistoChyby(zentita) + " ";
                    }
                }
                if(pocitadlo == 0){
                    return add_popisy("Nenalezena žádná základní entita, na kterou odkazuje element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + ".", false, getMistoChyby(krizovyOdkaz));
                }
                if(pocitadlo > 1){
                    return add_popisy("Element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + " odkazuje na více základních entit.", false, ch + getMistoChyby(krizovyOdkaz));
                }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.30 Každý element <mets:amdSec> obsahuje atribut ID.",
    private boolean pravidlo30(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:amdSec>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasAttribut(node, "ID")){
                return add_popisy("Element <mets:amdSec> nemá atribut ID.", false, getMistoChyby(node));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.31 Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
    private boolean pravidlo31(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:amdSec>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:digiprovMD")){
                return add_popisy("Element <mets:amdSec> neobsahuje právě jeden dětský element <mets:digiprovMD>.", false, getMistoChyby(node));
            }
        }
        return true;
    }
    
    //32. OBSAHOVÁ 93a. Každá entia věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.
    private boolean pravidlo32(){
        NodeList vs_list = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", metsParser.getDocument());
        if(vs_list==null) {
            return add_popisy("Věcná skupina neexistuje", false, getMistoChyby(metsParser.getDocument()));
        }
        for(int i = 0; i < vs_list.getLength(); i++){
            Node vs = vs_list.item(i);
            Node spl = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(spl != null){
                Node jsz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node pusz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");     
                if(jsz == null) return add_popisy("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs), false, getMistoChyby(vs));
                if(pusz == null) return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs), false, getMistoChyby(vs));
                if(!jsz.getTextContent().equals(pusz.getTextContent())){
                    return add_popisy("Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs), false, getMistoChyby(jsz) + " " + getMistoChyby(pusz));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
    private boolean pravidlo33(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(digiprovMD, "mets:mdWrap");
            if(list.isEmpty()){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(list.size() > 1){
                return add_popisy("Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.34 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s 
    // hodnotou 1.0.",
    private boolean pravidlo34(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPEVERSION")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", false, getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPEVERSION");
            if(!g.equals("1.0")){
                return add_popisy("Atribut MDTYPEVERSION neobsahuje hodnotu 1.0.", false, getMistoChyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.35 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
    private boolean pravidlo35(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "OTHERMDTYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", false, getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "OTHERMDTYPE");
            if(!g.equals("TP")){
                return add_popisy("Atribut OTHERMDTYPE neobsahuje hodnotu TP.", false, getMistoChyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.36 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    private boolean pravidlo36(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", false, getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPE");
            if(!g.equals("OTHER")){
                return add_popisy("Atribut MDTYPE neobsahuje hodnotu OTHER.", false, getMistoChyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.37 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    private boolean pravidlo37(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MIMETYPE")){
                return add_popisy("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", false, getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MIMETYPE");
            if(!g.equals("text/xml")){
                return add_popisy("Atribut MIMETYPE neobsahuje hodnotu text/xml.", false, getMistoChyby(mdWrap));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.38 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    private boolean pravidlo38(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWprap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWprap == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            if(ValuesGetter.getXChild(mdWprap, "mets:xmlData") == null){
                return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, getMistoChyby(mdWprap));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(mdWprap, "mets:xmlData")){
                return add_popisy("Element <mets:mdWrap> neobsahuje právě jeden dětský element <mets:xmlData>.", false, getMistoChyby(mdWprap));
            }
        }

        return true;
    }
    
    //OBSAHOVÁ č.39 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
    private boolean pravidlo39(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return add_popisy("Nenalezen žádný element <mets:digiprovMD>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWr = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWr == null){
                return add_popisy("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", false, getMistoChyby(digiprovMD));
            }
            Node xDt = ValuesGetter.getXChild(mdWr, "mets:xmlData");
            if(xDt == null){
                return add_popisy("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", false, getMistoChyby(mdWr));
            }
            Node tlo = ValuesGetter.getXChild(xDt, "tp:TransakcniLogObjektu");
            if(tlo == null){
                return add_popisy("Element <mets:xmlData> neobsahuje žádný dětský element <tp:TransakcniLogObjektu>.", false, getMistoChyby(xDt));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(xDt , "tp:TransakcniLogObjektu")){
                return add_popisy("Element <mets:xmlData> neobsahuje právě jeden dětský element <tp:TransakcniLogObjektu>.", false, getMistoChyby(xDt));
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
                return add_popisy("Element <nsesss:Dokument> " + getIdentifikatory(dokument) +" neobsahuje element <nsesss:AnalogovyDokument>.", false, getMistoChyby(dokument));
            }
            boolean maHodnotuNe = analogovyDokument.getTextContent().toLowerCase().equals("ne");
            if(maHodnotuNe){
                if(metsMets == null){
                    return add_popisy("SIP nemá kořenový element <mets:mets>.", false, MISTO_CHYBY_NEUPRESNENO);
                }
                if(ValuesGetter.getXChild(metsMets, "mets:fileSec") == null){
                    return add_popisy("Element <mets:mets> neobsahuje žádný element <mets:fileSec>.", false, getMistoChyby(metsMets));
                }
                if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:fileSec")){
                    return add_popisy("Element <mets:mets> neobsahuje právě jeden dětský element <mets:fileSec>.", false, getMistoChyby(metsMets));
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
        NodeList amdSec_nodelist = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(amdSec_nodelist == null){
            return add_popisy("Nenalezen element <mets:amdSec>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        StructMap_Obj_return_bol_AL_Obj_amdSec ret_amd = StructMap_Metods.get_amdSec_list(amdSec_nodelist);
        if (!ret_amd.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < ret_amd.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_amd.getList().get(i).getNode()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:amdSec>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:amdSec>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        List<StructMap_Obj_amdSec> amdSec_list = ret_amd.getList();
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_amdSec = StructMap_Metods.test_amdSec_uniqueness(amdSec_list);
        if (!jedinecnost_amdSec.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < jedinecnost_amdSec.getList().size(); i++) {
                mistoCh += getMistoChyby(jedinecnost_amdSec.getList().get(i)) + " ";
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:amdSec>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby duplicity u elementů <mets:amdSec>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        // KONEC TESTU mets:amdSec
        
        // TEST mets_div
        NodeList metsdiv_nodelist = ValuesGetter.getAllAnywhere("mets:div", metsParser.getDocument());
        if(metsdiv_nodelist == null){
            return add_popisy("Nenalezen element <mets:div>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        StructMap_Obj_return_bol_AL_Obj_metsdiv ret_metsdiv = StructMap_Metods.get_metsdiv_list(metsdiv_nodelist);
        if(!ret_metsdiv.getBol()){
            String mistoCh = "";
            for (int i = 0; i < ret_metsdiv.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_metsdiv.getList().get(i).getMetsDiv()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        List<StructMap_Obj_metsdiv> metsdiv_list = ret_metsdiv.getList();
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_metsdiv = StructMap_Metods.test_metsdiv_uniqueness(metsdiv_list);
        if(!jedinecnost_metsdiv.getBol()){
            String mistoCh = "";
            for(int i = 0; i < jedinecnost_metsdiv.getList().size(); i++){
                mistoCh += getMistoChyby(jedinecnost_metsdiv.getList().get(i)) + " ";  
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:div>.";
            if (ret_metsdiv.getList().size() > 1)
                hlaska = "Nalezeny chyby duplicity u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        // KONEC TESTU mets:div
        
        // TEST dmdSec
        StructMap_Obj_return_bol_AL_Obj_dmdSec ret_dmdsec = StructMap_Metods.get_dmdsec_list(metsParser.getDocument());
        if (!ret_dmdsec.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < ret_dmdsec.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_dmdsec.getList().get(i).getNode()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if (ret_dmdsec.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return add_popisy(hlaska, false, mistoCh);
        }
        List<StructMap_Obj_dmdSec> metsdmdSec_list = ret_dmdsec.getList();
        // KONEC TESTU dmdSec
        
        // TEST amdSec to metsdiv
        StructMap_Obj_return_bol_AL_node test_amd_to_div = StructMap_Metods.compare_amdSec_with_metsDiv(amdSec_list, metsdiv_list);
        if(!test_amd_to_div.getBol()){
            if (test_amd_to_div.getList().size() == 1) {
                return add_popisy("Element <mets:amdSec> neodkazuje na žádný element <mets:div>.", false,
                                  getMistoChyby(test_amd_to_div.getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_amd_to_div.getList().get(0));
                for (int i = 1; i < test_amd_to_div.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_amd_to_div.getList().get(i));
                }
                return add_popisy("Element <mets:amdSec> odkazuje na více elementů <mets:div>.", false, ch); 
            }
        }
        // TEST metsdiv to amd 
        StructMap_Obj_return_bol_AL_node test_div_toamd = StructMap_Metods.compare_metsDiv_with_amdSec(amdSec_list, metsdiv_list);
        if(!test_div_toamd.getBol()){
            if (test_div_toamd.getList().size() == 1) {
                return add_popisy("Element <mets:div> neodkazuje na žádný element <mets:amdSec>.", false,
                                  getMistoChyby(test_div_toamd.getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_div_toamd.getList().get(0));
                for (int i = 1; i < test_div_toamd.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_div_toamd.getList().get(i));
                }
                return add_popisy("Element <mets:div> odkazuje na více elementů <mets:amdScec>.", false, ch); 
            }
        }
        // KONEC TESTU amdSec + metsdiv
        
        //TEST AMD TO DMDSEC
        StructMap_Obj_return_bol_AL_node test_amd_to_dmd = StructMap_Metods.compare_amdSec_dmdSec(amdSec_list, metsdmdSec_list);
        if(!test_amd_to_dmd.getBol()){
            if (test_amd_to_dmd.getList().size() == 1) {
                String name = test_amd_to_dmd.getList().get(0).getNodeName();
                if(name.equals("mets:amdSec")){
                    return add_popisy("Element <mets:amdSec> neodkazuje na žádný element v <mets:dmdSec>.", false,
                                      getMistoChyby(test_amd_to_dmd.getList().get(0)));
                }
                else{
                    return add_popisy("Element <" + name + "> neodkazuje na žádný element <mets:amdSec>.", false,
                                      getMistoChyby(test_amd_to_dmd.getList().get(0)));
                }       
            }
            else{
                String ch = getMistoChyby(test_amd_to_dmd.getList().get(0));
                for (int i = 1; i < test_amd_to_dmd.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_amd_to_dmd.getList().get(i));
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
        StructMap_Obj_return_bol_AL_node test_struktury = StructMap_Metods.compare_metsDiv_with_dmdSec_structure(
                                                                                                                 metsdiv_list,
                                                                                                                 metsdmdSec_list,
                                                                                                                 metsParser
                                                                                                                         .getDocument());
        if (!test_struktury.getBol()) {
            if (test_struktury.getList().size() == 1) {
                return add_popisy("Element <mets:div> je špatně zatříděn.", false, getMistoChyby(test_struktury
                        .getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_struktury.getList().get(0));
                for (int i = 1; i < test_struktury.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_struktury.getList().get(i));
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
        NodeList dokuments = ValuesGetter.getAllAnywhere("nsesss:Dokument", metsParser.getDocument());
        if(dokuments == null){
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < dokuments.getLength(); i++)
        {   
            Node dokument = dokuments.item(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument") != null){
                Node mnozstvi = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument", "nsesss:VytvoreneMnozstvi");
                if(mnozstvi == null){
                    return add_popisy("Nenalezen povinný element <nsesss:VytvoreneMnozstvi>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
                }
                else{
                    String s = mnozstvi.getTextContent();
                    if (StringUtils.isBlank(s)) {
                        return add_popisy("Element <nsesss:VytvoreneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(mnozstvi));
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
        NodeList vecneSkupiny = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", metsParser.getDocument());
//        NodeList typoveSpisy = ValuesGetter.getAllAnywhere("nsesss:TypovySpis", parsedDOM_SipSoubor);
        NodeList soucasti = ValuesGetter.getAllAnywhere("nsesss:Soucast", metsParser.getDocument());
        
        if(vecneSkupiny != null){
            for(int i = 0; i < vecneSkupiny.getLength(); i++){
                Node vecnaSkupina = vecneSkupiny.item(i);
                boolean dite = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:MaterskaEntita", "nsesss:VecnaSkupina") != null;
                if(dite){
                    Node plneUrceny_node = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
                    if(plneUrceny_node == null){
                        return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vecnaSkupina), false, getMistoChyby(vecnaSkupina));
                    }
                    String hodnota = plneUrceny_node.getTextContent();
                    if(!spisZnakObsahujeOddelovac(hodnota)){
                        return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. " + getJmenoIdentifikator(vecnaSkupina), false, getMistoChyby(plneUrceny_node));
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
                    return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(soucast), false, getMistoChyby(soucast));
                }
                String hodnota = plneUrceny_node.getTextContent();
                if(!spisZnakObsahujeOddelovac(hodnota)){
                    return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. " + getJmenoIdentifikator(soucast), false, getMistoChyby(plneUrceny_node));
                } 
            }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.44 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>.",
    private boolean pravidlo44(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        NodeList nodeListKomponenty = ValuesGetter.getAllAnywhere("nsesss:Komponenta", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        if(nodeListKomponenty == null || nodeListKomponenty.getLength() != nodeListMetsFile.getLength()){
            return add_popisy("Nenalezen žádný element <nsesss:Komponenta>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        ArrayList<Obj_Node_String> files = new ArrayList<>(), komponents = new ArrayList<>();
        for(int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            Node komponenta = nodeListKomponenty.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "DMDID")){
                return add_popisy("Element <mets:file> nemá atribut DMDID.", false, getMistoChyby(metsFile));
            }
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            files.add(new Obj_Node_String(metsFile, dmdid));
            if(!ValuesGetter.hasAttribut(komponenta, "ID")){
                return add_popisy("Element <nsesss:Komponenta> nemá atribut ID. " + getJmenoIdentifikator(komponenta), false, getMistoChyby(komponenta));
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
                    misto_ch += getMistoChyby(file.get_node()) + " ";
                }
                else{
                    String komp = "";
                    for(int i = 0; i < f_list.size(); i++){
                        Obj_Node_String file2 = f_list.get(i);
                        if(i != f_list.size()-1){
                            komp += getMistoChyby(file2.get_node()) + " ";

                        }
                        else{
                            komp += getMistoChyby(file2.get_node()) + " ";
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
                    misto_ch += getMistoChyby(komp);
                }
                else{
                    String fil = "";
                    for(int i = 0; i < k_list.size(); i++){
                        Obj_Node_String komponenta2 = k_list.get(i);
                        Node kom = komponenta2.get_node();
                        if(i != k_list.size()-1){
                            fil += getMistoChyby(kom) + " ";

                        }
                        else{
                            fil += getMistoChyby(kom) + " ";
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
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "MIMETYPE")){
                return add_popisy("Element <mets:file> nemá atribut MIMETYPE.", false, getMistoChyby(metsFile));
            }
            String mimeType = ValuesGetter.getValueOfAttribut(metsFile, "MIMETYPE"); // application/pdf, text/plain
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Element <mets:file> nemá dětský element <mets:FLocat>.", false, getMistoChyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> nemá atribut xlink:href.", false, getMistoChyby(flocat));
            }
            
            String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
            xlinkHref = HelperString.edit_path(xlinkHref);
            //kvůli komponenty/
            int sep = xlinkHref.lastIndexOf(File.separator);
            if(sep == -1) return add_popisy("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: " + xlinkHref + ".", false, getMistoChyby(flocat));
            String ko_over = xlinkHref.substring(0, sep);
            if(!ko_over.equals("komponenty")){
                return add_popisy("Element <mets:FLocat> má ve svém atributu xlink:href špatně uvedenou cestu ke komponentě: " + xlinkHref + ".", false, getMistoChyby(flocat));
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
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile== null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
                return add_popisy("Element <mets:file> nemá atribut CHECKSUMTYPE.", false, getMistoChyby(metsFile));
            }
            String hodnota = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
            if(!hodnota.equals("SHA-256") && !hodnota.equals("SHA-512")){
                return add_popisy("Atribut CHECKSUMTYPE obsahuje nepovolenou hodnotu: " + hodnota + ".", false, getMistoChyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.47 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
    private boolean pravidlo47(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
                return add_popisy("Element <mets:file> nemá atribut CHECKSUMTYPE.", false, getMistoChyby(metsFile));
            }
            String atributChecksumType = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", false, getMistoChyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> nemá atribut xlink:href.", false, getMistoChyby(flocat));
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
                    return add_popisy("Nepovolený algoritmus v atributu CHECKSUMTYPE.", false, getMistoChyby(metsFile) + " Komponenta: " + xlinkHref + ".");
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
                return add_popisy("Element <mets:file> nemá atribut CHECKSUM.", false, getMistoChyby(metsFile));
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
                return add_popisy("CHECKSUM neodpovídá CHECKSUMTYPE.", false, getMistoChyby(nodeListMetsFile.item(i)) + " Soubor: " + xlinkHref + ".");
            }         
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.48 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
    private boolean pravidlo48(){

        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "SIZE")){
                return add_popisy("Element <mets:file> neobsahuje atribut SIZE.", false, getMistoChyby(metsFile));
            }
            String hodnotaVelikosti = ValuesGetter.getValueOfAttribut(metsFile, "SIZE");
            popisChyby = "nenalezen atribut xlink:href elementu <mets:FLocat>";
            Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
            if(flocat == null){
                return add_popisy("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", false, getMistoChyby(metsFile));
            }
            if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:href.", false, getMistoChyby(flocat));
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
                    return add_popisy("Nenalezena příslušná komponenta v složce komponenty.", false, getMistoChyby(flocat) + " Soubor: " + xlinkHref + ".");
//                }
            }
            String velikost = String.valueOf(file.length());
            if(!velikost.equals(hodnotaVelikosti)){
                return add_popisy("Velikost komponenty není totožná s metadaty.", false, getMistoChyby(metsFile) + " Komponenta: " + file.getName() + ".");
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.49 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
    private boolean pravidlo49(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if (!ValuesGetter.hasAttribut(metsFile, "CREATED")){
                return add_popisy("Elenemt <mets:file> neobsahuje atribut CREATED.", false, getMistoChyby(nodeListMetsFile.item(i)));
            }
        }   
        return true;
    }
    
    //OBSAHOVÁ č.50 Pokud existuje jakýkoli element <mets:file>, každý obsahuje právě jeden dětský element <mets:FLocat>.",
    private boolean pravidlo50(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for(int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(ValuesGetter.getXChild(metsFile, "mets:FLocat") == null){
                return add_popisy("Element <mets:file> nemá žádný dětský element <mets:FLocat>.", false, getMistoChyby(metsFile));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(nodeListMetsFile.item(i), "mets:FLocat")){
                return add_popisy("Element <mets:file> má více dětských elementů <mets:FLocat>.", false, getMistoChyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.51 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
    private boolean pravidlo51(){
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        int size = nodeListMetsFile.getLength();
        for(int i = 0; i < size; i++){
            Node n = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(n, "xlink:type")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:type.", false, getMistoChyby(n));
            }
            if(!ValuesGetter.hasAttributValue(n, "xlink:type", "simple")){
                return add_popisy("Atribut xlink:type neobsahuje hodnotu simple.", false, getMistoChyby(n));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.52 Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:href s hodnotou, která odpovídá relativní cestě odkazu jakékoli komponenty uložené ve složce komponenty.",
    private boolean pravidlo52(){
        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        ArrayList<String> seznam_z_xml = new ArrayList<>();
        if(nodeListFlocat == null) return true;
        for(int i = 0; i < nodeListFlocat.getLength(); i++){
            Node node = nodeListFlocat.item(i);
            if(!ValuesGetter.hasAttribut(node, "xlink:href")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut xlink:href.", false, getMistoChyby(node));
            }
            String href = ValuesGetter.getValueOfAttribut(node, "xlink:href");
            if(!href.startsWith("komponenty")){
                return add_popisy("Špatně uvedená relativní cesta ke komponentě.", false, getMistoChyby(node));
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
                return add_popisy("Komponenta " + href + " nenalezena.", false, getMistoChyby(node));
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
        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        if(nodeListFlocat == null) return true;
        for(int i = 0; i < nodeListFlocat.getLength(); i++){
            Node node = nodeListFlocat.item(i);
            if(!ValuesGetter.hasAttribut(node, "LOCTYPE")){
                return add_popisy("Element <mets:FLocat> neobsahuje atribut LOCTYPE.", false, getMistoChyby(node));
            }
            if(!ValuesGetter.hasAttributValue(node, "LOCTYPE", "URL")){
                return add_popisy("Atribut LOCTYPE nemá hodnotu URL.", false, getMistoChyby(node));
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
        ArrayList<Node> metsDiv = ValuesGetter.getAllAnywhereArrayList("mets:div", metsParser.getDocument());
        ArrayList<Node> metsAmd = ValuesGetter.getAllAnywhereArrayList("mets:amdSec", metsParser.getDocument());
        ArrayList<Node> spisoveplany = ValuesGetter.getAllAnywhereArrayList("nsesss:SpisovyPlan", metsParser
                .getDocument());
        ArrayList<Node> vecneskupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", metsParser
                .getDocument());
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", metsParser.getDocument());
        ArrayList<Node> typovespisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", metsParser
                .getDocument());
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereArrayList("nsesss:Spis", metsParser.getDocument());
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereArrayList("nsesss:Dil", metsParser.getDocument());
        //        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", metsParser.getDocument());
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Komponenta", metsParser
                .getDocument());
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
                                                    return add_popisy("Element mets:div s type " + type + ", odkazuje na element jiného typu.", false, getMistoChyby(n_div) + " " + getMistoChyby(node_dmd));
                                                }
                                                if(!hodnota_identifikator.equals(amd_identifikator)){
                                                    return add_popisy("Neshodují se hodnoty identifikátorů. " + getJmenoIdentifikator(identifikator), false, getMistoChyby(identifikator) + " " + getMistoChyby(hodnotaId));
                                                }
                                                if(!hodnota_zdroj.equals(amd_zdroj)){
                                                    return add_popisy("Neshodují se hodnoty zdrojů. " + getJmenoIdentifikator(identifikator), false, getMistoChyby(identifikator) + " " + getMistoChyby(zdrojID));
                                                }
                                                // zkontrolovat rodče
                                                Node rodic_div = n_div.getParentNode();
                                                if(type.equals("spisový plán")){
                                                    if(!rodic_div.getNodeName().equals("mets:structMap")){
                                                        return add_popisy("Element mets:div nemá správný rodičovský element.", false, getMistoChyby(n_div));
                                                    }
                                                }
                                                else{
                                                    if(!rodic_div.getNodeName().equals("mets:div")){
                                                        return add_popisy("Element mets:div nemá správný rodičovský element.", false, getMistoChyby(n_div));
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
                                                                    return add_popisy("Element mets:div je špatně zatříděn. Neodpovídající rodičovský element.", false, getMistoChyby(n_div) + " " + getMistoChyby(rodic_div));
                                                                }
                                                            } else return add_popisy("Nenalezen element v sekci mets:dmdSec s příslušným ID.", false, getMistoChyby(rodic_div));
                                                        } else return add_popisy("Element <mets:div> nemá atribut TYPE.", false, getMistoChyby(rodic_div));     
                                                    } else return add_popisy("Element <mets:div> nemá atribut DMDID.", false, getMistoChyby(rodic_div));
                                                }
                                            } else return add_popisy("Nenalezen element tns:ZdrojID v sekci mets:amdSec.", false, getMistoChyby(node_amd));
                                        } else return add_popisy("Nenalezen element tns:HodnotaID v sekci mets:amdSec.", false, getMistoChyby(node_amd));
                                    } else return add_popisy("Nenalezen element v sekci mets:amdSec s příslušným ID.", false, getMistoChyby(n_div));
                                } else return add_popisy("Nenalezen atribut zdroj elementu nsesss:Identifikator.", false, getMistoChyby(node_dmd));
                            } else return add_popisy("Nenalezen element nsesss:Identifikator.", false, getMistoChyby(node_dmd));
                        } else return add_popisy("Nenalezen element v sekci mets:dmdSec s příslušným ID.", false, getMistoChyby(n_div));
                    } else return add_popisy("Element <mets:div> nemá atribut TYPE.", false, getMistoChyby(n_div));
                } else return add_popisy("Element <mets:div> nemá atribut ADMID.", false, getMistoChyby(n_div));      
            } else return add_popisy("Element <mets:div> nemá atribut DMDID.", false, getMistoChyby(n_div));           
        } 

        if(pravidlo54_pocitadlo() != metsDiv.size()){
            return add_popisy("Počet elementů <mets:div> neodpovídá počtu elementů v dmdSec.", false, MISTO_CHYBY_NEUPRESNENO);
        }

        if(!pravidlo54_pocitadlo_amdsec(metsDiv.size())){
            return add_popisy("Počet elementů <mets:div> neodpovídá počtu elementů v amdSec.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        return true;
    }
        
    //OBSAHOVÁ č.55 Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.
    private boolean pravidlo55(){
        NodeList nodeListDiv = ValuesGetter.getAllAnywhere("mets:div", metsParser.getDocument());
        if(nodeListDiv == null) return true;
        for(int i = 0; i < nodeListDiv.getLength(); i++){
            Node div = nodeListDiv.item(i);
            boolean obsahuje = ValuesGetter.hasAttributValue(div, "TYPE", "komponenta");
            if(obsahuje){
                ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(div, "mets:fptr");
                if(list.isEmpty()){
                   return add_popisy("Element <mets:div> neobsahuje element <mets:fptr>.", false, getMistoChyby(div)); 
                }
                if(list.size() > 1){
                    return add_popisy("Elementu <mets:div> obsahuje více elementů <mets:fptr>.", false, getMistoChyby(div));
                }
            } 
        } 
        return true;
    }
    
    //OBSAHOVÁ č.56 Pokud existuje jakýkoli element <mets:fptr>, každý obsahuje atribut FILEID s hodnotou, která odpovídá hodnotě atributu ID elementu <mets:file> příslušné komponenty. Příslušnost vyjadřuje stejná hodnota atributu DMDID rodičovského elementu <mets:div> a elementu <mets:file>.",
    private boolean pravidlo56(){
        NodeList nodeListMetsFptr = ValuesGetter.getAllAnywhere("mets:fptr", metsParser.getDocument());
        if(nodeListMetsFptr == null) return true;
        for(int i = 0; i < nodeListMetsFptr.getLength(); i++){  
            Node metsFptr = nodeListMetsFptr.item(i);
            if(!ValuesGetter.hasAttribut(metsFptr, "FILEID")){
                return add_popisy("Element <mets:fptr> neobsahuje atribut FILEID.", false, getMistoChyby(metsFptr));
            }
        }

        NodeList nodeListFlocat = ValuesGetter.getAllAnywhere("mets:FLocat", metsParser.getDocument());
        if(nodeListFlocat == null){
            return add_popisy("Nenalezen element <mets:FLocat>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeListMetsFptr.getLength(); i++){
            Node fLocat = nodeListFlocat.item(i);
            Node metsFptr = nodeListMetsFptr.item(i);
            Node metsFile = ValuesGetter.getXParent(fLocat, "mets:file");
            if(metsFile == null){
                return add_popisy("Nenalezen element <mets:file>.", false, MISTO_CHYBY_NEUPRESNENO);
            }
            if(!ValuesGetter.hasAttribut(metsFile, "ID")){
                return add_popisy("Element <mets:file> neobsahuje atribut ID.", false, getMistoChyby(metsFile));
            }
            String fptrFileId = ValuesGetter.getValueOfAttribut(metsFptr, "FILEID");
            String idFile = ValuesGetter.getValueOfAttribut(ValuesGetter.getXParent(fLocat, "mets:file"), "ID");
            if(!fptrFileId.equals(idFile)){
                return add_popisy("Hodnoty atributů si neodpovídají. FILEID: " + fptrFileId + " ID: " + idFile + ".", false, getMistoChyby(metsFptr) + " " + getMistoChyby(metsFile));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.57 Jakýkoli element <nsesss:Identifikator> obsahuje neprázdnou hodnotu.",
    private boolean pravidlo57(){
        //        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", metsParser.getDocument());
        if(identifikatory == null){
            return add_popisy("Nenalezen žádný element <nsesss:Identifikator>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < identifikatory.size(); i++){
            Node identifikator = identifikatory.get(i);
            String str = identifikator.getTextContent();
            if (StringUtils.isBlank(str)) {
                return add_popisy("Element <nsesss:Identifikator> obsahuje prázdnou hodnotu. " + getJmenoIdentifikator(identifikator), false, getMistoChyby(identifikator));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.58 Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    private boolean pravidlo58(){
        //        NodeList identifikatory = ValuesGetter.getAllAnywhere("nsesss:Identifikator", metsParser.getDocument());
        if(identifikatory == null){
            return add_popisy("Nenalezen žádný element <nsesss:Identifikator>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < identifikatory.size(); i++){
            Node identifikator = identifikatory.get(i);
            if(!ValuesGetter.hasAttribut(identifikator, "zdroj")){
                return add_popisy("Element <nsesss:Identifikator> neobsahuje atribut zdroj.", false, getMistoChyby(identifikator));
            }
            String str = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
            if (StringUtils.isBlank(str)) {
                return add_popisy("Atribut zdroj elementu <nsesss:Identifikator> má prázdnou hodnotu. " + getJmenoIdentifikator(identifikator), false, getMistoChyby(identifikator));
            }
        }
        return true;
    }

    //OBSAHOVÁ č.59 Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.
    private boolean pravidlo59(){
        NodeList nlist = metsParser.getDocument().getElementsByTagName("nsesss:Identifikator");
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
                            misto_chyby = getMistoChyby(entita1) + " " + getMistoChyby(entita2);
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
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < dokumenty.size(); i++)
        {
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ne")){
                if(ValuesGetter.getXChild(dokument, "nsesss:Komponenty") == null){
                    return add_popisy("Nenalezen povinný element <nsesss:Komponenty>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.61 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    private boolean pravidlo61(){
        if(dokumenty == null){
            return add_popisy("Nenalezen žádný element <nsesss:Dokument>.", false, MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < dokumenty.size(); i++)
        {      
            Node dokument = dokumenty.get(i);
            Node ad = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(ad == null){
                return add_popisy("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if(hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument") != null){
                if(ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi") == null){
                    return add_popisy("Nenalezen povinný element <nsesss:DoruceneMnozstvi>. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
                }
                if(ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi") != null){
                    String s = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DoruceneMnozstvi").getTextContent();
                    if (StringUtils.isBlank(s)) {
                        return add_popisy("Element <nsesss:DoruceneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + getIdentifikatory(dokument) + ".", false, getMistoChyby(dokument));
                    }
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.62 Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.
    private boolean pravidlo62(){
        NodeList jazyky = ValuesGetter.getAllAnywhere("nsesss:Jazyk", metsParser.getDocument());
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
                return add_popisy("Chyba programu - nepodařilo se načíst tabulku s hodnotami pro element <nsesss:Jazyk>.", false, MISTO_CHYBY_NEUPRESNENO);
            }
            boolean jeObsazen = parserZUrl.jeObsazenJazyk(hodnotaJazyk);
            if(!jeObsazen)
                return add_popisy("Nenalezena hodnota odpovídající ISO 639-2:1998. " + getJmenoIdentifikator(jazyk), false, MISTO_CHYBY_NEUPRESNENO);
        }
        return true; 
    }
    
    //OBSAHOVÁ č.63 Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, 
    // potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    private boolean pravidlo63(){
        NodeList vyrizenis = ValuesGetter.getAllAnywhere("nsesss:Vyrizeni", metsParser.getDocument());
        NodeList vyrizeniUzavrenis = ValuesGetter.getAllAnywhere("nsesss:VyrizeniUzavreni", metsParser.getDocument());
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
                        return add_popisy("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n), false, getMistoChyby(n));
                    }
                    if (StringUtils.isBlank(oduvodneni.getTextContent())) {
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " + getJmenoIdentifikator(n), false, getMistoChyby(oduvodneni));
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
                        return add_popisy("Nenalezen povinný element <nsesss:Oduvodneni>. " + getJmenoIdentifikator(n), false, getMistoChyby(n));
                    }
                    if (StringUtils.isBlank(oduvodneni.getTextContent())) {
//                        Node rodic = n.getParentNode().getParentNode();
//                        String g = "";
//                        if(rodic != null) {
//                            g = " " + getJmeno(rodic);
//                            g+= " " + getIdentifikatory(rodic) + ".";
//                        }
                        return add_popisy("Element <nsesss:Oduvodneni> má prázdnou hodnotu. " +  getJmenoIdentifikator(n), false, getMistoChyby(oduvodneni));
                    }
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.64 Pokud je základní entitou (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> 
    // obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je součtem hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.",
    private boolean pravidlo64(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dokument")){
                Node skartacniRezim = ValuesGetter.getXChild(zakladnientita,"nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim");
                if(skartacniRezim == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                Node skartacniLhuta_node = ValuesGetter.getXChild(skartacniRezim, "nsesss:SkartacniLhuta");
                if(skartacniLhuta_node == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(skartacniRezim));
                }
                String skartacniLhuta = skartacniLhuta_node.getTextContent();
                Node rso = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                Node rsu = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if(rso == null){
                    return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                if(rsu == null){
                    return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
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
                        return add_popisy("Rok skartační operace: " + hodnotaOperace + ", lhůta: " + hodnotaLhuta + ", událost: " + hodnotaUdalosti + ". " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                    }
                }
                catch(NumberFormatException e){
//                    System.err.println("PRAVIDLO NSESSS Č.64. " + e);
                    return add_popisy("Zápis roku je uveden ve špatném formátu. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                
            }
        }
        return true; 
    }
    
    //OBSAHOVÁ č.65 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, 
    // která je rovna vyšší hodnotě, přičemž jednou hodnotou je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota roku skartační operace jakékoli dětské entity dokument (nsesss:Dokument>).",
    private boolean pravidlo65(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }

        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladniEntita = zakladniEntity.get(i);
            String jmeno = zakladniEntita.getNodeName();
            if(jmeno.equals("nsesss:Spis") || jmeno.equals("nsesss:Dil")){
                Node node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. " + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(zakladniEntita));
                }
                String str_rokSkartacniOperace_spis = node.getTextContent();
                // HODNOTA 1
                int hodnota_rokSkartacniOperace_spis; try{ hodnota_rokSkartacniOperace_spis = Integer.parseInt(str_rokSkartacniOperace_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: " + str_rokSkartacniOperace_spis + "." + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(node));}
                
                node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSpousteciUdalosti");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:RokSpousteciUdalosti>." + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(zakladniEntita));
                }
                String str_rokSpousteciUdalosti_spis = node.getTextContent();
                int rokSpousteciUdalosti_spis;
                try{rokSpousteciUdalosti_spis = Integer.parseInt(str_rokSpousteciUdalosti_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je uvedena ve špatném formátu: " + str_rokSpousteciUdalosti_spis + ". " + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(node));}

                node = ValuesGetter.getXChild(zakladniEntita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(zakladniEntita));
                }
                String str_skartacniLhuta_spis = node.getTextContent();
                int skartacniLhuta_spis;
                try{skartacniLhuta_spis = Integer.parseInt(str_skartacniLhuta_spis);} catch(NumberFormatException e){return add_popisy("Hodnota roku elementu <nsesss:SkartacniLhuta> je uvedena ve špatném formátu: " + str_skartacniLhuta_spis + ". " + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(node));}
                
                //HODNOTA 2
                int hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta = rokSpousteciUdalosti_spis + 1 + skartacniLhuta_spis; 
                
                // dokumenty 
                if(dokumenty == null || dokumenty.isEmpty()){
                    return add_popisy("Nenalezen žádný element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladniEntita), false, getMistoChyby(zakladniEntita));
                }
                Obj_Node_int dokument = new Obj_Node_int(null, 0);
                for(int j = 0; j < dokumenty.size(); j++){
                    Node dokumentze = dokumenty.get(j);
                    int int_finalhodnota_dok;
                    Node dok_lhuta = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                    if(dok_lhuta == null){
                        return add_popisy("Nenalezen element <nsesss:SkartacniLhuta>. " + getJmenoIdentifikator(dokumentze), false, getMistoChyby(dokumentze));
                    }
                    String d_lhuta = dok_lhuta.getTextContent();

                    Node datum_dok = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:VlastniDokument", "nsesss:DatumVytvoreni");
                    if(datum_dok == null) datum_dok = ValuesGetter.getXChild(dokumentze, "nsesss:EvidencniUdaje", "nsesss:Puvod", "nsesss:DorucenyDokument", "nsesss:DatumDoruceni");
                    if(datum_dok == null){
                        return add_popisy("Nenalezen element <nsesss:RokSkartacniOperace>. "  + getJmenoIdentifikator(dokumentze), false, getMistoChyby(dokumentze));
                    }
                    String d_vytvDor = datum_dok.getTextContent().substring(0, 4);
                    int int_dok_lhuta, int_dok_rok_vytvDor;
                    try{
                        int_dok_lhuta = Integer.parseInt(d_lhuta);
                    }
                    catch(NumberFormatException e){
                        return add_popisy("Element <nsesss:SkartacniLhuta> obsahuje hodnotu roku ve špatném formátu. "  + getJmenoIdentifikator(dokumentze), false, getMistoChyby(dok_lhuta));
                    }
                    try{
                        int_dok_rok_vytvDor = Integer.parseInt(d_vytvDor);
                    }
                    catch(NumberFormatException e){
                        return add_popisy("Element <" + datum_dok.getNodeName() + "> obsahuje hodnotu roku ve špatném formátu."  + getJmenoIdentifikator(dokumentze), false, getMistoChyby(datum_dok));
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
                        return add_popisy("Rok uvedený v elementu <nsesss:RokSkartacniOperace>: " + hodnota_rokSkartacniOperace_spis + ", se nerovná nejvyšší hodnotě. Buď nejvyšší hodnotě z dětských elementů <nsesss:Dokument>: " + hodnota_rokSkartacniOperace_dokument + ", nebo součtu hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: " + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = " + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ". " + getJmenoIdentifikator(zakladniEntita) + " " + getJmenoIdentifikator(dokument.get_node()), false, getMistoChyby(zakladniEntita) + " " + getMistoChyby(dokument.get_node()));
                    }
                    else{
                        return add_popisy("Součet hodnot elementů <nsesss:RokSpousteciUdalosti> + 1 + <nsesss:SkartacniLhuta>: " + rokSpousteciUdalosti_spis + " + 1 + " + skartacniLhuta_spis + " = " + hodnota_rokSpudalostiPlusJednaPlusSkartacniLhuta + ", je roven nejvyšší hodnotě elementu <nsesss:RokSkartacniOperace> elementu <nsesss:Dokument>: " + hodnota_rokSkartacniOperace_dokument + ". Nerovná se však hodnotě elementu <nsesss:RokSkartacniOperace> základní entity: " +  hodnota_rokSkartacniOperace_spis + ". " + getJmenoIdentifikator(zakladniEntita) + " " + getJmenoIdentifikator(dokument.get_node()), false, getMistoChyby(zakladniEntita) + " " + getMistoChyby(dokument.get_node()));
                    }
                } 
            }
        }    
        
        return true;
    }
    
    //OBSAHOVÁ č.66 Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    private boolean pravidlo66(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }
        
        for(int i = 0; i < zakladniEntity.size(); i++){
            Node ze = zakladniEntity.get(i);
            Node node = ValuesGetter.getXChild(ze, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:DataceVyrazeni", "nsesss:RokSkartacniOperace");
            String str = node.getTextContent().substring(0, 4);
            boolean je = ValuesGetter.overSpravnostRetezceProInt(str);
            if(!je){
                return add_popisy("Element <nsesss:RokSkartacniOperace> obsahuje hodnotu ve špatném formátu. " + getJmenoIdentifikator(ze), false, getMistoChyby(node));
            }
            int rokSkartacniOperace = Integer.parseInt(str);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if(rokSkartacniOperace > year){
                return add_popisy("Hodnota roku elementu <nsesss:RokSkartacniOperace> je větší, než aktuální rok. Hodnota: " + rokSkartacniOperace + ". " + getJmenoIdentifikator(ze), false, getMistoChyby(node));
            }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.67 Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
    private boolean pravidlo67(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }

        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            String zeName = zakladnientita.getNodeName();
            if(zeName.equals("nsesss:Spis") || zeName.equals("nsesss:Dil")){
                Node n = ValuesGetter.getXChild(zakladnientita, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if(n == null){
                    return add_popisy("Nenalezen dětský element <nsesss:SkartacniZnak> základní entity. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                String skZnakME = n.getTextContent();
                ArrayList<Obj_Node_String> hodnotyDokumentu = new ArrayList<>();
                if(dokumenty == null){
                    return add_popisy("Nenalezen žádný dětský element <nsesss:Dokument>. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                for(int j = 0; j < dokumenty.size(); j++){
                Node dokument = dokumenty.get(j);
                Node nd = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if(nd == null){
                    return add_popisy("Nenalezen dětský element <nsesss:SkartacniZnak> elementu <nsesss:Dokument>. " + getJmenoIdentifikator(dokument), false, getMistoChyby(dokument));
                }
                String znak = nd.getTextContent();
                hodnotyDokumentu.add(new Obj_Node_String(dokument, znak));
                }
                switch(skZnakME){
                    case "A":
                        if(Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A") == null){
                            return add_popisy("Spis se skartačním znakem A neobsahuje žádný dokument se skartačním znakem A. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(n));
                        }
                        break;
                    case "V":
                        Obj_Node_String obj_a = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "A");
                        if(obj_a != null){
                            return add_popisy("Spis se skartačním znakem V obsahuje dokument se skartačním znakem A. " + getJmenoIdentifikator(zakladnientita) + " " + getJmenoIdentifikator(obj_a.get_node()), false, getMistoChyby(n) + " " + getMistoChyby(obj_a.get_node()));
                        }
                        Obj_Node_String obj_v = Helper_Obj_Node.has_any_skartacni_znak(hodnotyDokumentu, "V");
                        if(obj_a == null && obj_v == null){
                            return add_popisy("Spis se skartačním znakem V neobsahuje žádný dokument se skartačním znakem V. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(n));
                        }
                        break;
                    case "S":
                        ArrayList<Obj_Node_String> list = Helper_Obj_Node.all_with_skartacni_znak(hodnotyDokumentu, "A", "V");
                        if(!list.isEmpty()){
                            String ch = "";
                            String iden = "";
                            for(int k = 0; k < list.size(); k++){
                                Node no = list.get(i).get_node();
                                ch += getMistoChyby(no);
                                iden += " " + getJmenoIdentifikator(no);
                                if(k != list.size()-1) ch += " ";
                            }
                            return add_popisy("Spis se skartačním znakem S obsahuje dokument se skartačním znakem A nebo V. " + getJmenoIdentifikator(zakladnientita) + iden, false, getMistoChyby(n) + " " + ch);
                        }
                        
                        
                        break; 
                }
            }
        }    
        return true;
    }
    
    //OBSAHOVÁ č.68 Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.
    private boolean pravidlo68(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }

        for(int i = 0; i < zakladniEntity.size(); i++){
            Node zakladnientita = zakladniEntity.get(i);
            if(zakladnientita.getNodeName().equals("nsesss:Dokument") || zakladnientita.getNodeName().equals("nsesss:Spis")){
                Node vecnaskupina = ValuesGetter.getFirstInNode(zakladnientita, "nsesss:VecnaSkupina", metsParser
                        .getDocument());
                if(vecnaskupina == null){
                    return add_popisy("Nenalezena rodičovská entita věcná skupina základní entity. " + getJmenoIdentifikator(zakladnientita), false, getMistoChyby(zakladnientita));
                }
                Node sr = ValuesGetter.getXChild(vecnaskupina, "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim");
                if(sr == null){
                    return add_popisy("Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(vecnaskupina), false, getMistoChyby(vecnaskupina));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.69 Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
    private boolean pravidlo69(){
        if (zakladniEntity == null) {
            return add_popisy("Chybí základní entity.", false, null);
        }

        for(int i = 0; i < zakladniEntity.size(); i++){
            Node ze = zakladniEntity.get(i);
            if(ze.getNodeName().equals("nsesss:Dokument")){
                Node node = ValuesGetter.getXChild(ze, "nsesss:EvidencniUdaje", "nsesss:Vyrizeni");
                if(node == null){
                    return add_popisy("Nenalezen element <nsesss:Vyrizeni>. " + getJmenoIdentifikator(ze), false, getMistoChyby(ze));
                }
            }
        }    
        return true;
    }

    
    private int pravidlo54_pocitadlo(){
        int a = 0;
        ArrayList<Node> plany = ValuesGetter.getAllAnywhereArrayList("nsesss:SpisovyPlan", metsParser.getDocument());
        if(plany != null) a = pravidlo56upresneniPocitadla(plany);
        ArrayList<Node> skupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", metsParser.getDocument());
        if(skupiny != null) a += pravidlo56upresneniPocitadla(skupiny);
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", metsParser.getDocument());
        if(soucasti != null) a += pravidlo56upresneniPocitadla(soucasti);
        ArrayList<Node> typoveSpisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", metsParser
                .getDocument());
        if(typoveSpisy != null) a += pravidlo56upresneniPocitadla(typoveSpisy);
        ArrayList<Node> spisy = ValuesGetter.getAllAnywhereArrayList("nsesss:Spis", metsParser.getDocument());
        if(spisy != null) a += pravidlo56upresneniPocitadla(spisy);
        ArrayList<Node> dily = ValuesGetter.getAllAnywhereArrayList("nsesss:Dil", metsParser.getDocument());
        if(dily != null) a += pravidlo56upresneniPocitadla(dily);
        ArrayList<Node> dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", metsParser.getDocument());
        if(dokumenty != null) a += pravidlo56upresneniPocitadla(dokumenty);
        ArrayList<Node> komponenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Komponenta", metsParser
                .getDocument());
        if(komponenty != null) a += pravidlo56upresneniPocitadla(komponenty);
        
        return a;
    }
    
    private boolean pravidlo54_pocitadlo_amdsec(int pocet_div){
        NodeList list = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
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
            if(!idcka.contains(idn)){
                idcka.add(idn);
            }
        }

        return idcka.size();
    }
    
    private ArrayList<Node> get_krizove_odkazy_pevny_ano(){
        ArrayList<Node> list = new ArrayList<>();
        NodeList krizoveOdkazy = ValuesGetter.getAllAnywhere("nsesss:KrizovyOdkaz", metsParser.getDocument());
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
    static public boolean spisZnakObsahujeOddelovac(String spisovy_znak) {
        for(int i = 0; i < spisovy_znak.length()-1; i++){
            char c = spisovy_znak.charAt(i);
            if(c == ' ' || c == '-' || c == '_' || c == '\\' || c == '/' || c == '.'){
                return true;
            }
            
        }
        
        return false;
    }


	@Override
    public void provedKontrolu() {
        // Nastaveni promenych pro kontroly
        // bude nutne casem prepracovat
        this.metsParser = ctx.getMetsParser();
        this.metsMets = metsParser.getMetsRootNode();
        this.metsHdr = metsParser.getMetsHdr();
        this.metsMdWrap = metsParser.getMetsMdWrap();
        this.metsDmdSec = metsParser.getMetsDmdSec();
        this.xmlData = metsParser.getMetsXmlData();
        this.zakladniEntity = metsParser.getZakladniEntity();
        this.dokumenty = metsParser.getDokumenty();
        this.manipulace = metsParser.getManipulace();
        this.identifikatory = metsParser.getIdentifikatory();
		
		this.sipSoubor = ctx.getSip();

		for (int i = 0; i < seznamPravidel.length; i++) {
			int j = seznamPravidel[i];

            provedKontrolu(j);
		}
		
	}

    /**
     * Vykonani jedne kontroly
     * 
     * @param j
     *            index kontroly
     */
    private void provedKontrolu(int j) {
        String idPravidla = getIDpravidla(j);

        // novy zpusob volani kontrol
        Runnable metodaKontroly = kontroly.get(idPravidla);
        if (metodaKontroly != null) {
            metodaKontroly.run();
            return;
        }

        popisChyby = null;
        misto_chyby = null;

        // zavolani pravidla
        boolean jePravidloSplneno = false;
        try {
            jePravidloSplneno = udelejPravidloObsahovaSpolecna2018(j);
        } catch (IOException e) {
            popisChyby = "IOException: " + e.getLocalizedMessage();
        } catch (ParseException e) {
            popisChyby = "ParseException: " + e.getLocalizedMessage();
        }

        String popisObecnyChyby = null;
        if (!jePravidloSplneno) {
            popisObecnyChyby = ZpravyObsahoveKontroly.get_popis_Obsahova(j);
        }
        PravidloKontroly p = new PravidloKontroly(idPravidla,
                jePravidloSplneno,
                ZpravyObsahoveKontroly.get_text_Obsahova(j),
                popisChyby,
                popisObecnyChyby,
                misto_chyby,
                ZpravyObsahoveKontroly.get_zdroje_Obsahova(j));
        vysledekKontroly.add(p);
    }

    @Override
	public String getNazev() {
		return NAME;
	}

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.OBSAHOVA;
    }

    public List<Node> getZakladniEnity() {
        return zakladniEntity;
    }

    public List<Node> getDokumenty() {
        return dokumenty;
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }
}
