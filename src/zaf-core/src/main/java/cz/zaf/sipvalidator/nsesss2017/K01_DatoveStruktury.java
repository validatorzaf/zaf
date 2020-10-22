/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;


import java.io.File;
import java.nio.file.Path;

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;


/**
 *
 * @author standa
 */
public class K01_DatoveStruktury
        extends KontrolaBase
{
	
	static final public String NAME = "kontrola datové struktury"; 
    static final public String DATA1 = "data1";
    static final public String DATA2 = "data2";
    static final public String DATA3 = "data3";
    
    static String[] idPravidel =
    {
        DATA1,
        DATA2,
        DATA3
    };

    static String[] zdrojePravidla = {
            //1 zdroj d1
            "Požadavek 11.2.9 a 11.2.1 NSESSS.",    
            //2 zdroj d2
            "Požadavek 11.2.9 NSESSS.",    
            //3 zdroj d3
            "Požadavek 11.2.2, 11.2.3 a 11.2.8 NSESSS.",    
    };
    static String[] textPravidla =
    {
            //1 data1
            "Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.",
            //2 data2
            "Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.",
            //3 data3
            "Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.",
    };

    static String[] obecnaChyba = {
            "Datový balíček SIP je chybně strukturován.",
            "Uvedeno je chybně označení datového balíčku SIP.",
            "Uvedena jsou chybně metadata a komponenty (počítačové soubory) v datovém balíčku SIP."
    };

    public K01_DatoveStruktury() {
    }
    
    // Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.
    private void pravidlo1(SipInfo file) {

        LoadType loadtype = file.getLoadType();

        boolean stav = false;
        String vypisChyby;
        switch(loadtype){
        case LT_XML:
            vypisChyby = "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu xml.";
            break;
        case LT_DIR:
            stav = true;
            vypisChyby = "SIP balíček byl nahrán jako složka.";
            break;
        case LT_ZIP:
            switch (file.getLoadStatus()) {
            case ERR_ZIP_INCORRECT_STRUCTURE:
                stav = true;
                vypisChyby = "SIP balíček rozbalen, chybná struktura v ZIP souboru.";
                break;
            case OK:
                stav = true;
                vypisChyby = "SIP balíček nahrán ze souboru typu zip.";
                break;
            case ERR_UNKNOWN:
            case ERR_UNZIP_FAILED:
            default:
                vypisChyby = "Datový balíček SIP není soubor v datovém formátu ZIP (jeho MIME Content-type není application/zip).";
                break;
            }
            break;
        case LT_UNKNOWN:
        default:
            vypisChyby = "Datový balíček SIP není soubor v povoleném datovém formátu.";
            break;    
        }
        pridejPravidlo(0, stav, vypisChyby);

    }

    private void pridejPravidlo(int index, boolean stav, String vypisChyby) {
        String popisChybyObecny = null;
        if (!stav) {
            popisChybyObecny = obecnaChyba[index];
        }
        PravidloKontroly p = new PravidloKontroly(idPravidel[index], stav,
                textPravidla[index],
                vypisChyby, popisChybyObecny,
                null,
                zdrojePravidla[index]);
        vysledekKontroly.add(p);
        
    }

    // Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, 
    // po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.
    private void pravidlo2(SipInfo file) {
        LoadType loadtype = file.getLoadType();
        boolean stav = false;
        String vypisChyby;
        if(loadtype == LoadType.LT_ZIP){ //byl to zip
            String bezKoncovky = file.getNameZip().substring(0, file.getNameZip().length() - 4);
            if (file.getName().equals(bezKoncovky)) {
                stav = true;
                vypisChyby = "Nahraný soubor typu zip obsahoval očekávaný SIP balíček.";
            }
            else{
                vypisChyby = "Nahraný soubor typu zip neobsahoval očekávaný SIP balíček, očekávaná hodnota: "
                        + bezKoncovky + ", zjištěná hodnota:" + file.getName();
            }
        }
        else{
            stav = true;
            vypisChyby = "Nejedná se o SIP balíček, ale nahrán jako samostatný soubor typu " + loadtype.getName() + ".";
        }
        pridejPravidlo(1, stav, vypisChyby);
    }
    
    public static boolean ma_pouze_povolene_soubory(SipInfo sip) {

        Path cesta = sip.getCesta();
        if (cesta == null) {
            // cesta neni definovana -> nelze kontrolovat
            return false;
        }
        File[] f = cesta.toFile().listFiles();
        if (f != null) {
            for (File s : f) {
                if (!(s.getName().toLowerCase().equals(SipInfo.KOMPONENTY) || s.getName().toLowerCase().equals(
                                                                                                               SipInfo.METS_XML))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.
    private void pravidlo3(SipInfo file) {
        boolean stav = false;
        String vypisChyby;

        if (ma_pouze_povolene_soubory(file)) {
            if (ctx.maMetsXml()) {
                StringBuilder sb = new StringBuilder();
                sb.append("SIP balíček obsahuje právě jeden soubor \"mets.xml\"");
                if (SIP_MAIN_helper.maSlozku_komponenty(file)) {
                    sb.append(" a složku komponenty.");
                } else {
                    sb.append(".");
                }
                vypisChyby = sb.toString();
                stav = true;
            }
            else{
                vypisChyby = "SIP balíček neobsahuje právě jeden soubor \"mets.xml\".";
            }
        }
        else{
            vypisChyby = "SIP balíček obsahuje nepovolené soubory.";
        }

        pridejPravidlo(2, stav, vypisChyby);
    }

	@Override
    public void provedKontrolu() {

        SipInfo file = ctx.getSip();
		
        pravidlo1(file);
        pravidlo2(file);
        pravidlo3(file);
	}

	@Override
	public String getNazev() {
		return NAME;
	}
    
    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.DATOVE_STRUKTURY;
    }
}
