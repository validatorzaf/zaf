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
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo3;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo4;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09.Pravidlo9;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo10;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo11;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo12;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo13;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo14;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo15;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo16;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo17;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo18;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19.Pravidlo19;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29.Pravidlo20;
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

    static final public String MISTO_CHYBY_NEUPRESNENO = "Neupřesněno.";
	SipInfo sipSoubor;
	private int[] seznamPravidel;

    private MetsParser metsParser;

    private Node metsMets;

    private List<Node> zakladniEntity;
    private List<Node> dokumenty;
    
    /**
     * Mapa kontrol
     */
    Map<String, Runnable> kontroly = new HashMap<>();
       
    public K06_Obsahova(int[] seznamPravidel) {
    	this.seznamPravidel = seznamPravidel;
    
        pridejPravidlo(new Pravidlo1(this));
        pridejPravidlo(new Pravidlo2(this));
        pridejPravidlo(new Pravidlo3(this));
        pridejPravidlo(new Pravidlo4(this));
        pridejPravidlo(new Pravidlo9(this));
        pridejPravidlo(new Pravidlo10(this));
        pridejPravidlo(new Pravidlo11(this));
        pridejPravidlo(new Pravidlo12(this));
        pridejPravidlo(new Pravidlo13(this));
        pridejPravidlo(new Pravidlo14(this));
        pridejPravidlo(new Pravidlo15(this));
        pridejPravidlo(new Pravidlo16(this));
        pridejPravidlo(new Pravidlo17(this));
        pridejPravidlo(new Pravidlo18(this));
        pridejPravidlo(new Pravidlo19(this));
        pridejPravidlo(new Pravidlo20(this));
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
        Validate.notNull(metodaKontroly, "Pravidlo nenalezeno, id=%s", idPravidla);
        metodaKontroly.run();
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

