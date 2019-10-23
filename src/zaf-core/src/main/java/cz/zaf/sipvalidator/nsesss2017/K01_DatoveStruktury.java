/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;


import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;


/**
 *
 * @author standa
 */
public class K01_DatoveStruktury
        implements UrovenKontroly<KontrolaNsess2017Context>
{
	
	static final public String NAME = "kontrola datové struktury"; 
    
    public K01_DatoveStruktury() {
    }
    
    // Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.
    private void pravidlo1(VysledekKontroly k, SipInfo file){
    	LoadType loadtype = file.getLoadType(); // load type: 0 dir, 1 xml, 2 zip, -1 nepovolený formát
        switch(loadtype){
            case LT_UNKNOWN:
                PravidloKontroly p = new PravidloKontroly(0, "data1", false, "Datový balíček SIP není soubor v povoleném datovém formátu.", 4, "");
                k.add(p);
            break;    
            case LT_XML:
                PravidloKontroly p1 = new PravidloKontroly(0, "data1", false, "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu xml.", 4, "");
                k.add(p1);
            break;
            case LT_DIR:
                PravidloKontroly p2 = new PravidloKontroly(0, "data1", true, "SIP balíček byl nahrán jako složka.", 0, "");
                k.add(p2);
            break;
            case LT_ZIP:
                if(!file.getLoadGood()){
                    PravidloKontroly p3 = new PravidloKontroly(0, "data1", false, "Datový balíček SIP není soubor v datovém formátu ZIP (jeho MIME Content-type není application/zip).", 4, "");
                    k.add(p3);
                }
                else{
                    PravidloKontroly p4 = new PravidloKontroly(0, "data1", true, "SIP balíček nahrán ze souboru typu zip.", 0, "");
                    k.add(p4);
                }
            break;    
        }
        
    }
    
    // Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, 
    // po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.
    private void pravidlo2(VysledekKontroly k, SipInfo file){
    	LoadType loadtype = file.getLoadType();        
        if(loadtype == LoadType.LT_ZIP){ //byl to zip
            if(file.getName().equals(file.getNameZip().replaceAll(".zip", ""))){
                PravidloKontroly p = new PravidloKontroly(1, "data2", true, "Nahraný soubor typu zip obsahoval očekávaný SIP balíček.", 0, "");
                k.add(p);
            }
            else{
                PravidloKontroly p1 = new PravidloKontroly(1, "data2", false, "Nahraný soubor typu zip neobsahoval očekávaný SIP balíček.", 5, "");
                k.add(p1);
            }
        }
        else{
            PravidloKontroly p2 = new PravidloKontroly(1, "data2", true, "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu " + loadtype.getName() + ".", 0, "");
            k.add(p2); 
        }
    }
    
    // Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.
    private void pravidlo3(VysledekKontroly k, SipInfo file){
        if(SIP_MAIN_helper.ma_pouze_povolene_soubory(file)){
            if(SIP_MAIN_helper.ma_metsxml(file)){
                String s = "SIP balíček obsahuje právě jeden soubor \"mets.xml\"";
                if(SIP_MAIN_helper.maSlozku_komponenty(file)) s += " a složku komponenty."; else s += ".";
                PravidloKontroly p = new PravidloKontroly(2, "data3", true, s, 0, "");
                k.add(p);
            }
            else{
                PravidloKontroly p1 = new PravidloKontroly(2, "data3", false, "SIP balíček neobsahuje právě jeden soubor \"mets.xml\".", 9, "");
                k.add(p1);
            }
        }
        else{
            PravidloKontroly p2 = new PravidloKontroly(2, "data3", false, "SIP balíček obsahuje nepovolené soubory.", 9, "");
            k.add(p2);
        }
    }

	@Override
    public void provedKontrolu(KontrolaNsess2017Context ctx) {
        VysledekKontroly k = new VysledekKontroly(TypUrovenKontroly.DATOVE_STRUKTURY,
        		NAME);
        boolean failed = ctx.isFailed();
        ctx.pridejKontrolu(k);
		if(failed) {
			return;
		}
        
        SipInfo file = ctx.getSip();
		
        pravidlo1(k, file);
        pravidlo2(k, file);
        pravidlo3(k, file);
	}

	@Override
	public String getNazev() {
		return NAME;
	}
    
}
