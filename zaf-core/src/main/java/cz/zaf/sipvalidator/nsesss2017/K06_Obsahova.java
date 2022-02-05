/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;
import cz.zaf.sipvalidator.nsesss2017.EntityId.DruhEntity;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.ChybaPravidla;


/**
 * Obsahova kontrola
 * 
 */
public class K06_Obsahova
        extends KontrolaBase
{
	
    static final public String NAME = "kontrola obsahu";

	SipInfo sipSoubor;
	private ObsahovePravidlo[] seznamPravidel;

    MetsParser metsParser;

    private List<Node> zakladniEntity;
    
    /**
     * Mapa kontrol
     */
    Map<String, ObsahovePravidlo> kontroly = new HashMap<>();
       
    public K06_Obsahova(ObsahovePravidlo[] obsahovaPravidla) {
    	this.seznamPravidel = obsahovaPravidla;
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
    
    public EntityId getEntityId(Node node) {
        String nodename = node.getNodeName();
        Node identNode;
        DruhEntity druhEntity;
        switch (nodename) {
        case NsessV3.SPISOVY_PLAN:
            identNode = ValuesGetter.getXChild(node, NsessV3.IDENTIFIKATOR);
            druhEntity = DruhEntity.SPISOVY_PLAN;
            break;
        case NsessV3.DOKUMENT:
            identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                                               "nsesss:Identifikace", NsessV3.IDENTIFIKATOR);
            druhEntity = DruhEntity.DOKUMENT;
            break;
        case NsessV3.SPIS:
            identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                                               "nsesss:Identifikace", NsessV3.IDENTIFIKATOR);
            druhEntity = DruhEntity.SPIS;
            break;
        case NsessV3.DIL:
            identNode = ValuesGetter.getXChild(node, NsessV3.EVIDENCNI_UDAJE,
                                               "nsesss:Identifikace", NsessV3.IDENTIFIKATOR);
            druhEntity = DruhEntity.DIL;
            break;
        default:
            return null;
        }
        String hodnota, zdroj;
        if (identNode == null) {
            hodnota = "nenalezeno";
            zdroj = "nenalezeno";
        } else {
            hodnota = identNode.getTextContent();
            zdroj = ValuesGetter.getValueOfAttribut(identNode, "zdroj");
        }

        return new EntityId(druhEntity, hodnota, zdroj);

    }

    public String getIdentifikatory(Node node) {
        String nodename = node.getNodeName();
        String hodnota = "nenalezeno", zdroj = "nenalezeno";
        Node identifikator;
        if(nodename.equals(NsessV3.SPISOVY_PLAN)){
            identifikator = ValuesGetter.getXChild(node, NsessV3.IDENTIFIKATOR);
            if(identifikator!= null){
                hodnota = identifikator.getTextContent();
                boolean ma = ValuesGetter.hasAttribut(identifikator, "zdroj");
                if(ma) zdroj = ValuesGetter.getValueOfAttribut(identifikator, "zdroj");
                return "(Ident. hodnota: "+ hodnota + ", zdroj: " + zdroj + ")";
            }
        }
        identifikator = ValuesGetter.getXChild(node,NsessV3.EVIDENCNI_UDAJE, 
                                               "nsesss:Identifikace", NsessV3.IDENTIFIKATOR);
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
    
    /**
     * 
     * @param idPravidla
     * @param errorCode
     *            kod chyby
     * @param textPravidla
     * @param detailChyby
     * @param obecnyPopisChyby
     * @param mistoChyby
     * @param zdroj
     */
    void pridejChybu(String idPravidla,
                     ErrorCode errorCode,
                     String textPravidla,
                     String detailChyby,
                     String obecnyPopisChyby,
                     String mistoChyby,
                     String zdroj) {
        ChybaPravidla p = new ChybaPravidla(idPravidla,
                textPravidla,
                detailChyby,
                obecnyPopisChyby,
                mistoChyby,
                zdroj,
                errorCode);
        vysledekKontroly.add(p);

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
        this.zakladniEntity = metsParser.getZakladniEntity();
		
		this.sipSoubor = ctx.getSip();

		for (int i = 0; i < seznamPravidel.length; i++) {
			ObsahovePravidlo pravidlo = seznamPravidel[i];

	        String kodPravidla = pravidlo.getKodPravidla();
	        // skip excluded checks
	        if (ctx.isExcluded(kodPravidla)) {
	            continue;
	        }

	        pravidlo.kontrolaPravidla(this);
		}
		
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

    public MetsParser getMetsParser() {
        return metsParser;
    }
    
}
