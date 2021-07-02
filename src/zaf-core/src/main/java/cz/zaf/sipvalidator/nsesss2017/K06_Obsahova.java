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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo22;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo23;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo24;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo25;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo26;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo27;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo28;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo29;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo30;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo31;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo33;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39.Pravidlo34;
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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99.Pravidlo93a;
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
    static final public String OBS21 = "obs21";

    static final public String MISTO_CHYBY_NEUPRESNENO = "Neupřesněno.";
    private String popisChyby = "Pravidlo nesplněno.";
    private String misto_chyby = "";
	SipInfo sipSoubor;
	private int[] seznamPravidel;

    private MetsParser metsParser;

    private Node metsMets;
    private Node metsHdr;

    private List<Node> zakladniEntity;
    private List<Node> dokumenty;
    
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
    
        pridejPravidlo(OBS21, null);
        pridejPravidlo(new Pravidlo22(this));
        pridejPravidlo(new Pravidlo23(this));
        pridejPravidlo(new Pravidlo24(this));
        pridejPravidlo(new Pravidlo25(this));
        pridejPravidlo(new Pravidlo26(this));
        pridejPravidlo(new Pravidlo27(this));
        pridejPravidlo(new Pravidlo28(this));
        pridejPravidlo(new Pravidlo29(this));
        pridejPravidlo(new Pravidlo30(this));
        pridejPravidlo(new Pravidlo31(this));
        pridejPravidlo(new Pravidlo93a(this));
        pridejPravidlo(new Pravidlo33(this));
        pridejPravidlo(new Pravidlo34(this));
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
            return Pravidlo93a.OBS93A;
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

