/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.sip;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

/**
 * Informace o Sipu
 * 
 */
public class SIP_MAIN{
	
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
	
	final public static String METS_XML = "mets.xml"; 

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
     */
    final private String name;
    
    /**
     * Jméno ZIP souboru se SIPem
     * 
     * Jméno ZIP soouboru v němž byl SIP zabalen
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
    
    // sip_type 0 dok, 1 spi, 2 dil
    private int sip_type = -1;

    /**
     * Typ zdroje SIPu
     */
    final private LoadType loadType;
    private long lenght = 0;
    
    final protected ArrayList<SIP_MAIN_kontrola> seznam_kontrol = new ArrayList<>();
    
    private String sip_skznak = " - ", sip_sklhuta = " - ", sip_kodovani;    
    private String xsi_schemaLocation;
    
    /**
     * Priznak, zda byl datovy balicek nacten
     * 
     * false - deklarovany datovy format nebyl nacten
     */
    private boolean loadGood = true;
    
    public SIP_MAIN(final String name, final String nameZipFile, final LoadType loadType, 
    		long lenght, final Path sipPath) {
        this.name = name;
        this.nameZipFile = nameZipFile;
        this.loadType = loadType;
        this.sipPath = sipPath;

        this.lenght = lenght;
    }
    
    public void reset_data_Kontroly(){
        seznam_kontrol.clear();
    }
    
    public boolean getLoadGood(){
        return loadGood;
    }
    
    public void setLoadGood(boolean bol){
        loadGood = bol;
    }
    
    public LoadType getLoadType(){
        return loadType;
    }
    
    public Path getCesta(){
        return sipPath;
    }
    
    public Path getCesta_mets(){
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
    
    public void setType(int typ){
        sip_type = typ;
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

    public int getType(){
        return sip_type;
    }

    public long getLenght(){
        return lenght;
    }
    
    ArrayList<SIP_MAIN_kontrola> getSeznamKontrol(){
        return seznam_kontrol;
    }

    public void setSKznak(String znak){
        sip_skznak = znak;
    }
    
    public void setSKLhuta(String lhuta){
        sip_sklhuta = lhuta;
    }
    
    public String getSKznak(){
        return sip_skznak;
    }
    
    public String getSKlhuta(){
        return sip_sklhuta;
    }
    
    public String getKodovani(){
        return sip_kodovani;
    }
    
    public void setKodovani(String kodovani){
        sip_kodovani = kodovani;
    }
    
	public String get_xsi_schemaLocation(){
        return xsi_schemaLocation;
    }
    
    public void set_xsi_schemaLocation(String hodnota){
        xsi_schemaLocation = hodnota;
    }

    /**
     * Vrati uroven kontroly daneho typu
     * @param typUrovneKontroly
     * @return
     */
	public SIP_MAIN_kontrola getUrovenKontroly(TypUrovenKontroly typUrovneKontroly) {
		for(SIP_MAIN_kontrola kontrola: seznam_kontrol) {
			if(kontrola.getTypUrovneKontroly()==typUrovneKontroly) {
				return kontrola;
			}
		}
		return null;
		
	}

	public void pridejKontrolu(SIP_MAIN_kontrola k) {
		// kontrola, zda jiz nebyla pridana
		SIP_MAIN_kontrola urovenKontroly = getUrovenKontroly(k.getTypUrovneKontroly());
		Validate.isTrue(urovenKontroly==null);
		seznam_kontrol.add(k);
	}

	public boolean isKontrolyProvedeny() {
		if(seznam_kontrol!=null&&seznam_kontrol.size()==TypUrovenKontroly.values().length) {
			return true;
		}
		return false;
	}
    
}
