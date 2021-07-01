/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo35;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo36;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo37;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo38;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo39;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo40;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo44;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo45;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo46;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo47;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo48;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49.Pravidlo49;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo50;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo51;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo52;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo53;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo54a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo55;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo56;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo57;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo58;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59.Pravidlo59;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo60;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo61a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo62;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo63;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo64;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo65;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo66;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo67;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo68;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69.Pravidlo69;
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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo94a;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo95;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo96;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo97;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo98;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo99;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
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
	
    static final public String OBS93A = "obs93a";

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

        pridejPravidlo(new Pravidlo35(this));
        pridejPravidlo(new Pravidlo36(this));
        pridejPravidlo(new Pravidlo37(this));
        pridejPravidlo(new Pravidlo38(this));
        pridejPravidlo(new Pravidlo39(this));
        pridejPravidlo(new Pravidlo40(this));
        pridejPravidlo(new Pravidlo54a(this));
        pridejPravidlo(new Pravidlo61a(this));
        pridejPravidlo(new Pravidlo94a(this));
        pridejPravidlo(new Pravidlo44(this));
        pridejPravidlo(new Pravidlo45(this));
        pridejPravidlo(new Pravidlo46(this));
        pridejPravidlo(new Pravidlo47(this));
        pridejPravidlo(new Pravidlo48(this));
        pridejPravidlo(new Pravidlo49(this));
        pridejPravidlo(new Pravidlo50(this));
        pridejPravidlo(new Pravidlo51(this));
        pridejPravidlo(new Pravidlo52(this));
        pridejPravidlo(new Pravidlo53(this));
        pridejPravidlo(new Pravidlo54(this));
        pridejPravidlo(new Pravidlo55(this));
        pridejPravidlo(new Pravidlo56(this));
        pridejPravidlo(new Pravidlo57(this));
        pridejPravidlo(new Pravidlo58(this));
        pridejPravidlo(new Pravidlo59(this));
        pridejPravidlo(new Pravidlo60(this));
        pridejPravidlo(new Pravidlo61(this));
        pridejPravidlo(new Pravidlo62(this));
        pridejPravidlo(new Pravidlo63(this));
        pridejPravidlo(new Pravidlo64(this));
        pridejPravidlo(new Pravidlo65(this));
        pridejPravidlo(new Pravidlo66(this));
        pridejPravidlo(new Pravidlo67(this));
        pridejPravidlo(new Pravidlo68(this));
        pridejPravidlo(new Pravidlo69(this));
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
        pridejPravidlo(new Pravidlo99(this));
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
            return Pravidlo54a.OBS54A;
        if (j == 42)
            return Pravidlo61a.OBS61A;
        if (j == 43)
            return Pravidlo94a.OBS94A;

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
        }
        
        return vysledek;
    }
    
    private boolean nastavChybu(String chyba, String misto_chyby){
        popisChyby = chyba;
        this.misto_chyby = misto_chyby;
        return false;
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
    
    public String getIdentifikatory(Node node) {
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
        Object lineNumber = node.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME);
        if (lineNumber == null) {
            return null;
        }
        Object colNumber = node.getUserData(PositionalXMLReader.COLUMN_NUMBER);
        StringBuilder sb = new StringBuilder();
        sb.append("Řádek ").append(lineNumber);
        if (colNumber != null) {
            sb.append(":").append(colNumber);
        }
        sb.append(", element <").append(node.getNodeName()).append(">.");
        return sb.toString();
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
        if (!HelperString.hasContent(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"))) {
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
        if(metsMets == null) return nastavChybu("Nenalezen kořenový element <mets:mets>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:metsHdr")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:metsHdr")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:metsHdr>.", getMistoChyby(metsMets));
        }
        return true;
    }
    
    //OBSAHOVÁ č.11 Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
    private boolean pravidlo11(){
        if(metsMets == null) return nastavChybu("Nenalezen kořenový element <mets:mets>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:dmdSec")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:dmdSec>.", getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:dmdSec")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:dmdSec>.", getMistoChyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.12 Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
    private boolean pravidlo12(){
        if(metsMets == null) return nastavChybu("Nenalezen kořenový element <mets:mets>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:amdSec")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:amdSec>.", getMistoChyby(metsMets));
        }       
        return true;
    }
    
    //OBSAHOVÁ č.13 Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
    private boolean pravidlo13(){
        if(metsMets == null) return nastavChybu("Nenalezen kořenový element <mets:mets>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:structMap")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:structMap>.", getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:structMap")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:structMap>.", getMistoChyby(metsMets));
        }        
        return true;
    }
    
    //OBSAHOVÁ č.14 Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
    private boolean pravidlo14(){
        if(metsHdr == null) return nastavChybu("Nenalezen element <mets:metsHdr>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsHdr, "LASTMODDATE")){
            return nastavChybu("Element <mets:metsHdr> nemá atribut LASTMODDATE.", getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.15 Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
    private boolean pravidlo15(){
        if(metsHdr == null) return nastavChybu("Nenalezen element <mets:metsHdr>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsHdr, "CREATEDATE")){
            return nastavChybu("Element <mets:metsHdr> nemá atribut CREATEDATE.", getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
    private boolean pravidlo16(){
        if(metsHdr == null) return nastavChybu("Nenalezen element <mets:metsHdr>.", MISTO_CHYBY_NEUPRESNENO);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return nastavChybu("Nenalezen element <mets:agent>.", getMistoChyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "ORGANIZATION")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return nastavChybu("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", getMistoChyby(metsHdr));
        }
        if(pocitadlo > 1){
            return nastavChybu("Element <mets:metsHdr> obsahuje více elementů <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
    private boolean pravidlo17(){
        if(metsHdr == null) return nastavChybu("Nenalezen element <mets:metsHdr>.", MISTO_CHYBY_NEUPRESNENO);
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return nastavChybu("Nenalezen element <mets:agent>.", getMistoChyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "INDIVIDUAL")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return nastavChybu("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", getMistoChyby(metsHdr));
        }
        return true;
    }
    
    //OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
    private boolean pravidlo18(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return nastavChybu("Nenalezen žádný element <mets:agent>.", MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttributValue(node, "ROLE", "CREATOR")){
               pocitadlo++;
               ch += getMistoChyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return nastavChybu("Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", ch);
        return true;
    }
    
    //OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
    private boolean pravidlo19(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return nastavChybu("Nenalezen žádný element <mets:agent>.", MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttribut(node, "ID")){
               pocitadlo++;
               ch += getMistoChyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return nastavChybu("Element <mets:agent> neobsahuje atribut ID.", ch);
        return true;
    }
    
    //OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
    private boolean pravidlo20(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) return nastavChybu("Nenalezen žádný element <mets:agent>.", MISTO_CHYBY_NEUPRESNENO);
        int pocitadlo = 0;
        int pocitadlo2 = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:name")){
                if (!HelperString.hasContent(ValuesGetter.getXChild(node, "mets:name").getTextContent())) {
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
            return nastavChybu("Element <mets:agent> neobsahuje právě jeden dětský element <mets:name>." + h, ch);
        }
        if(pocitadlo2 != 0){
            return nastavChybu("Element <mets:agent> má nevyplněnou hodnotu u dětského elementu <mets:name>.", ch);
        }
        return true;
    }
    
    //21 volný index
    private boolean pravidlo21(){
        return true;
    }
    
    //OBSAHOVÁ č.22 Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
    private boolean pravidlo22(){
        if(metsDmdSec == null) return nastavChybu("Nenalezen element <mets:dmdSec>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasChildWithName(metsDmdSec, "mets:mdWrap")){
            return nastavChybu("Element <mets:dmdSec> neobsahuje žádný dětský element <mets:mdWrap>.", getMistoChyby(metsDmdSec));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsDmdSec, "mets:mdWrap")){
            return nastavChybu("Element <mets:dmdSec> obsahuje více než jeden dětský element <mets:mdWrap>.", getMistoChyby(metsDmdSec));
        }
        return true;
    }
    
    //OBSAHOVÁ č.23 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
    private boolean pravidlo23(){
        if(metsMdWrap == null) return nastavChybu("Nenalezen element <mets:mdWrap>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPEVERSION")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPEVERSION");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MDTYPEVERSION elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPEVERSION", "3.0")){
            return nastavChybu("Atribut MDTYPEVERSION elementu <mets:mdWrap> neobsahuje hodnotu 3.0.", getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.24 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
    private boolean pravidlo24(){
        if(metsMdWrap == null) return nastavChybu("Nenalezen element <mets:mdWrap>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "OTHERMDTYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "OTHERMDTYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut OTHERMDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "OTHERMDTYPE", "NSESSS")){
            return nastavChybu("Atribut OTHERMDTYPE elementu <mets:mdWrap> neobsahuje hodnotu NSESSS.", getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.25 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    private boolean pravidlo25(){
        if(metsMdWrap == null) return nastavChybu("Nenalezen element <mets:mdWrap>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPE", "OTHER")){
            return nastavChybu("Atribut MDTYPE elementu <mets:mdWrap> neobsahuje hodnotu OTHER.", getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.26 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    private boolean pravidlo26(){
        if(metsMdWrap == null) return nastavChybu("Nenalezen element <mets:mdWrap>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MIMETYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MIMETYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MIMETYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MIMETYPE", "text/xml")){
            return nastavChybu("Atribut MIMETYPE elementu <mets:mdWrap> neobsahuje hodnotu text/xml.", getMistoChyby(metsMdWrap));
        }
        return true;
    }
    
    //OBSAHOVÁ č.27 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    private boolean pravidlo27(){
        if(xmlData == null) return nastavChybu("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", MISTO_CHYBY_NEUPRESNENO);
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMdWrap, "mets:xmlData")){
            return nastavChybu("Element <mets:mdWrap> obsahuje více dětských elementů <mets:xmlData>.", getMistoChyby(metsMdWrap));
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
                return nastavChybu("Nenalezen element <mets:xmlData>.", MISTO_CHYBY_NEUPRESNENO);
            }
            if(xmlData.getChildNodes().getLength() == 0){
                return nastavChybu("Element <mets:xmlData> neobsahuje žádné dětské elementy.", getMistoChyby(xmlData));
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
                return nastavChybu("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", ch);
            }
            if(list.size() > 1){
                String ch = "";
                String pop = "";
                for(int i = 1; i < list.size(); i++){
                    Node node = list.get(i);
                    ch += getMistoChyby(node) + " "; 
                    pop += " " + getJmenoIdentifikator(node);
                }
                return nastavChybu("Element <mets:xmlData> obsahuje více dětských elementů." + pop, ch);
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
                return nastavChybu("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", ch);
        }
        
        for(int i = 0; i < krizove_odkazy_pevny_ano.size(); i++){
            Node krizovyOdkaz = krizove_odkazy_pevny_ano.get(i);
            Node materska_zakl_entita_eu = ValuesGetter.getXParent(krizovyOdkaz, "nsesss:Souvislosti", "nsesss:EvidencniUdaje");
            
            if(materska_zakl_entita_eu == null){
                return nastavChybu("Element <nsesss:KrizovyOdkaz> je špatně zatříděn. Nenalezeny elementy <nsesss:Souvislosti> a <nsesss:EvidencniUdaje>.", getMistoChyby(krizovyOdkaz));
            }
            Node za_ent = materska_zakl_entita_eu.getParentNode();
            Node identifikator_me = ValuesGetter.getXChild(materska_zakl_entita_eu, "nsesss:Identifikace", "nsesss:Identifikator");
            if(identifikator_me == null){
                return nastavChybu("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí element <nsesss:Identifikator>.", getMistoChyby(za_ent));
            }
            if(!ValuesGetter.hasAttribut(identifikator_me, "zdroj")){
                return nastavChybu("Základní entitě náležící k elementu <nsesss:KrizovyOdkaz> chybí atribut zdroj u elementu <nsesss:Identifikator>.", getMistoChyby(identifikator_me));
            }
            String zdroj_me = ValuesGetter.getValueOfAttribut(identifikator_me, "zdroj");
            String ident_me = identifikator_me.getTextContent();
            Node identifikator_ko = ValuesGetter.getXChild(krizovyOdkaz, "nsesss:Identifikator");
            if(identifikator_ko == null){
                return nastavChybu("Element <nsesss:KrizovyOdkaz> nemá dětský element <nsesss:Identifikator>.", getMistoChyby(krizovyOdkaz));
            }
            if(!ValuesGetter.hasAttribut(identifikator_ko, "zdroj")){
                return nastavChybu("Dětský element <nsesss:Identifikator> elementu <nsesss:KrizovyOdkaz> nemá atribut zdroj.", getMistoChyby(identifikator_ko));
            }
            String zdroj_ko = ValuesGetter.getValueOfAttribut(identifikator_ko, "zdroj");
            String ident_ko = identifikator_ko.getTextContent();
            
            if(zdroj_me.equals(zdroj_ko) && ident_me.equals(ident_ko)){
                return nastavChybu("Element <nsesss:KrizovyOdkaz> odkazuje na vlastní základní entitu.", getMistoChyby(krizovyOdkaz));
            }
            
            int pocitadlo = 0;
            String ch = "";
                for(int k = 0; k < zakladniEntity.size(); k++){
                    Node zentita = zakladniEntity.get(k);
                    Node id_ze = ValuesGetter.getXChild(zentita, "nsesss:EvidencniUdaje", "nsesss:Identifikace", "nsesss:Identifikator");
                    if(id_ze == null){
                        return nastavChybu("Nenalezen element <nsesss:Identifikator> základní entity.", getMistoChyby(zentita));
                    }
                    if(!ValuesGetter.hasAttribut(id_ze, "zdroj")){
                        return nastavChybu("Nenalezen atribut zdroj elementu <nsesss:Identifikator>.", getMistoChyby(id_ze));
                    }
                    String hodnotaZdrojMatEnt = ValuesGetter.getValueOfAttribut(id_ze, "zdroj");
                    String hodnotaIdentMatEnt = id_ze.getTextContent();
                    if(zdroj_ko.equals(hodnotaZdrojMatEnt) &&  ident_ko.equals(hodnotaIdentMatEnt)){
                        pocitadlo++;
                        ch += getMistoChyby(zentita) + " ";
                    }
                }
                if(pocitadlo == 0){
                    return nastavChybu("Nenalezena žádná základní entita, na kterou odkazuje element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + ".", getMistoChyby(krizovyOdkaz));
                }
                if(pocitadlo > 1){
                    return nastavChybu("Element <nsesss:KrizovyOdkaz> s id: " + ident_ko + " a zdrojem: " + zdroj_ko + " odkazuje na více základních entit.", ch + getMistoChyby(krizovyOdkaz));
                }
        }
        
        return true;
    }
    
    //OBSAHOVÁ č.30 Každý element <mets:amdSec> obsahuje atribut ID.",
    private boolean pravidlo30(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:amdSec>.", MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasAttribut(node, "ID")){
                return nastavChybu("Element <mets:amdSec> nemá atribut ID.", getMistoChyby(node));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.31 Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
    private boolean pravidlo31(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:amdSec>.", MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:digiprovMD")){
                return nastavChybu("Element <mets:amdSec> neobsahuje právě jeden dětský element <mets:digiprovMD>.", getMistoChyby(node));
            }
        }
        return true;
    }
    
    //32. OBSAHOVÁ 93a. Každá entia věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.
    private boolean pravidlo32(){
        NodeList vs_list = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", metsParser.getDocument());
        if(vs_list==null) {
            return nastavChybu("Věcná skupina neexistuje", getMistoChyby(metsParser.getDocument()));
        }
        for(int i = 0; i < vs_list.getLength(); i++){
            Node vs = vs_list.item(i);
            Node spl = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(spl != null){
                Node jsz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                Node pusz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");     
                if(jsz == null) return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs), getMistoChyby(vs));
                if(pusz == null) return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs), getMistoChyby(vs));
                if(!jsz.getTextContent().equals(pusz.getTextContent())){
                    return nastavChybu("Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs), getMistoChyby(jsz) + " " + getMistoChyby(pusz));
                }
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
    private boolean pravidlo33(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.", MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(digiprovMD, "mets:mdWrap");
            if(list.isEmpty()){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            if(list.size() > 1){
                return nastavChybu("Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
        }
        return true;
    }
    
    //OBSAHOVÁ č.34 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s 
    // hodnotou 1.0.",
    private boolean pravidlo34(){
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.", MISTO_CHYBY_NEUPRESNENO);
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPEVERSION")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPEVERSION");
            if(!g.equals("1.0")){
                return nastavChybu("Atribut MDTYPEVERSION neobsahuje hodnotu 1.0.", getMistoChyby(mdWrap));
            }
        }
        return true;
    }
    
    public ArrayList<Node> get_krizove_odkazy_pevny_ano() {
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

        // skip excluded checks
        if (ctx.isExcluded(idPravidla)) {
            return;
        }

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

    /**
     * Return root mets element
     * 
     * @return
     */
    public Node getMetsMets() {
    	return metsMets;
    }
    
}

