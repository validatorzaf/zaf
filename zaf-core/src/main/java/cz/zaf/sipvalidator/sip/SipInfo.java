/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.sip;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

/**
 * Informace o Sipu
 * 
 * Objekt obsahuje odkaz na data SIPu 
 * a výsledek provedených kontrol.
 */
public class SipInfo{
	
	public static enum LoadType {
		LT_UNKNOWN("nepovolený formát"),
		LT_DIR("složka"),
		LT_XML("xml"),
		LT_ZIP("zip");
		
		/**
		 * Jmeno typu nahrani
		 */
		final String name;
		
		private LoadType(final String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static enum SipType {
	    DOKUMENT,
	    SPIS,
	    DIL
	};
	
	final public static String METS_XML = "mets.xml"; 
    final public static String KOMPONENTY = "komponenty";

	/**
	 * ID sipu
	 * 
	 * Nacte se z mets.xml (OBJID)
	 */
    private String metsObjId;
    
    /**
     * Jmeno SIPu
     * 
     * Jméno adresáře v němž je SIP uložen
     * Jen název bez cesty.
     */
    final private String name;
    
    /**
     * Jméno ZIP souboru se SIPem
     * 
     * Jméno ZIP soouboru v němž byl SIP zabalen.
     * Jen název bez cesty.
     */
    final private String nameZipFile;
    
    /**
     * Cesta k SIPu
     * 
     * V zavislosti na typu vstupu nabyva hodnot:
     * XML - cesta k mets.xml
     * ZIP - cesta do rozbaleneho SIPu
     * DIR - cesta do adresare SIPu
     */
    final private Path sipPath;
    
    /**
     * Typ SIPu
     */
    private SipType sipType;

    /**
     * Typ zdroje SIPu
     */
    final private LoadType loadType;
    
    final protected ArrayList<VysledekKontroly> seznam_kontrol = new ArrayList<>();
    
    private String sip_kodovani;
    
    public enum LoadStatus {
        OK,
        ERR_UNZIP_FAILED,
        ERR_ZIP_INCORRECT_STRUCTURE,
        ERR_UNKNOWN
    }

    /**
     * Priznak, zda byl datovy balicek nacten
     * 
     * false - deklarovany datovy format nebyl nacten
     */
    final private LoadStatus loadStatus;
    
    /**
     * Konstruktor
     * 
     * @param name
     *            Jméno SIPu
     * @param nameZipFile
     *            Jméno zdrojového ZIP souboru, uvádí se jen pokud je zdrojem ZIP
     *            soubor
     * @param loadType
     *            Podoba zdroje SIPu
     * @param sipPath
     *            Cesta k rozbalenemu SIPu nebo primy odkaz na metsXML
     * @param loadStatus
     *            Informace o stavu nahrání SIPu
     */
    public SipInfo(final String name, final String nameZipFile, final LoadType loadType, 
                   final Path sipPath, final LoadStatus loadStatus) {
        this.name = name;
        this.nameZipFile = nameZipFile;
        this.loadType = loadType;
        this.sipPath = sipPath;
        this.loadStatus = loadStatus;
    }

	public void reset_data_Kontroly(){
        seznam_kontrol.clear();
    }
    
    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public LoadType getLoadType() {
        return loadType;
    }
    
    public Path getCesta(){
        return sipPath;
    }
    
    public Path getCestaMets() {
    	if(loadType==LoadType.LT_XML) {
    		return sipPath;
    	}
    	if(sipPath!=null) {
    		return sipPath.resolve(METS_XML);
    	}
        return null;
    }    
    
    public void setMetsObjId(String objid){
    	metsObjId = objid;
    }
    
    public void setType(SipType type){
        sipType = type;
    }

    public SipType getType(){
        return sipType;
    }
    
    public String getMetsObjId(){
        return metsObjId;
    }
    
    public String getName(){
        return name;
    }

    public String getNameZip(){
        return nameZipFile;
    }
    
    ArrayList<VysledekKontroly> getSeznamKontrol(){
        return seznam_kontrol;
    }

    public String getKodovani(){
        return sip_kodovani;
    }
    
    public void setKodovani(String kodovani){
        sip_kodovani = kodovani;
    }

    /**
     * Vrati uroven kontroly daneho typu
     * 
     * @param typUrovneKontroly
     *            typ požadované úrovně kontroly
     * @return Výsledek kontroly, případně null pokud kontrola nebyla provedena
     */
	public VysledekKontroly getUrovenKontroly(TypUrovenKontroly typUrovneKontroly) {
		for(VysledekKontroly kontrola: seznam_kontrol) {
			if(kontrola.getTypUrovneKontroly()==typUrovneKontroly) {
				return kontrola;
			}
		}
		return null;
		
	}

	public void pridejKontrolu(VysledekKontroly k) {
		// kontrola, zda jiz nebyla pridana
		VysledekKontroly urovenKontroly = getUrovenKontroly(k.getTypUrovneKontroly());
		Validate.isTrue(urovenKontroly==null);
		seznam_kontrol.add(k);
	}

	public boolean isKontrolyProvedeny() {
		if(seznam_kontrol!=null&&seznam_kontrol.size()==TypUrovenKontroly.values().length) {
			return true;
		}
		return false;
	}

	public VysledekKontroly getKontrola(int indexKontroly) {
		if(seznam_kontrol==null) {
			return null;
		}
		if(seznam_kontrol.size()>=indexKontroly) {
			return null;
		}
		return seznam_kontrol.get(indexKontroly);
	}
    
}
