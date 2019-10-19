/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.sip;
import java.util.ArrayList;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN{

    private String id = " - ", name = " - ", name_zip = " - ";
    // sip_type 0 dok, 1 spi, 2 dil
    private int sip_type = -1, load_type = -1;
    private long lenght = 0;
    
    private ArrayList<SIP_MAIN_kontrola> seznam_kontrol;
    
    private String sip_skznak = " - ", sip_sklhuta = " - ", sip_kodovani;
    private final String sip_relativni_cesta;
    private String xsi_schemaLocation;
    private boolean load_good = true;
    // load sip_type: 0 dir, 1 xml, 2 zip, -1 nepovolený formát
    public SIP_MAIN(String name, String name_zip, int load_type, long lenght, String cesta) {
        this.name = name;
        if(!name_zip.equals("")){
            this.name_zip = name_zip;
        }
        this.lenght = lenght;
        seznam_kontrol = new ArrayList<>();
        this.sip_relativni_cesta = cesta;
        this.load_type = load_type;
    }
    
    public void reset_data_Kontroly(){
        seznam_kontrol = new ArrayList<>();
    }
    
    public boolean getLoadGood(){
        return load_good;
    }
    
    public void setLoadGood(boolean bol){
        load_good = bol;
    }
    
    public int getLoadType(){
        return load_type;
    }
    
    public void setLoadType(int type){
        load_type = type;
    }
    
    public String getCesta(){
        return sip_relativni_cesta;
    }
    
    public void setID(String objid){
        id = objid;
    }
    
    public void setType(int typ){
        sip_type = typ;
    }
    
    public String getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public String getNameZip(){
        return name_zip;
    }

    public int getType(){
        return sip_type;
    }

    public long getLenght(){
        return lenght;
    }
    
    public ArrayList<SIP_MAIN_kontrola> getSeznamKontrol(){
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
    
}
