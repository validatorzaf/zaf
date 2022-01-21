/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.analysis;

import java.util.ArrayList;
import java.util.Optional;

import cz.zaf.sipvalidator.sip.VysledekPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;
import cz.zaf.sipvalidui.panels.NahraneSoubory;

/**
 *
 * @author m000xz006159
 */
public final class Analys_main {
    Analys_main_control skodlivehokodu, datovestruktury, znakovesady, spravnostixml, jmennychprostoru, protischematu, obsahu;
    ArrayList<Integer> list_validnich, list_nevalidnich;
    static ArrayList<Analys_rule> analys_obsahova;
    boolean pomocnybol = true;
    NahraneSoubory nahraneSoubory;

    public Analys_main(NahraneSoubory nahraneSoubory) {
        this.nahraneSoubory = nahraneSoubory;
        list_validnich = new ArrayList();
        list_nevalidnich = new ArrayList();
        analys_obsahova = new ArrayList();
        skodlivehokodu = new Analys_main_control("Kontrola škodlivého kódu", 0, nahraneSoubory.size());
        datovestruktury = new Analys_main_control("Kontrola datové struktury", 1, nahraneSoubory.size());
        znakovesady = new Analys_main_control("Kontrola znakové sady", 2, nahraneSoubory.size());
        spravnostixml = new Analys_main_control("Kontrola správnosti xml", 3, nahraneSoubory.size());
        jmennychprostoru = new Analys_main_control("Kontrola jmenných prostorů", 4, nahraneSoubory.size());
        protischematu = new Analys_main_control("Kontrola proti schématu", 5, nahraneSoubory.size());
        obsahu = new Analys_main_control("Kontrola obsahu", 6, nahraneSoubory.size());

        for (int i = 0; i < nahraneSoubory.size(); i++) {
            SipInfo sip = nahraneSoubory.get(i).getSip();
        	
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.SKODLIVY_KOD), skodlivehokodu);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.DATOVE_STRUKTURY), datovestruktury);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.ZNAKOVE_SADY), znakovesady);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.SPRAVNOSTI), spravnostixml);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.JMENNE_PROSTORY_XML), jmennychprostoru);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.PROTI_SCHEMATU), protischematu);
        	zapis_kontrolu(i, sip.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA), obsahu);

            analys_obsahova_set(i);
            
            if(pomocnybol) list_validnich.add(i);
            else list_nevalidnich.add(i);
            pomocnybol = true;
        }
        
        skodlivehokodu.vyhodnot_kontrolu();
        datovestruktury.vyhodnot_kontrolu();
        znakovesady.vyhodnot_kontrolu();
        spravnostixml.vyhodnot_kontrolu();
        jmennychprostoru.vyhodnot_kontrolu();
        protischematu.vyhodnot_kontrolu();
        obsahu.vyhodnot_kontrolu();
        analys_obsahova = Analys_helper.sort_reverse(analys_obsahova);
        
    }
    
    private void zapis_kontrolu(int indexSip, VysledekKontroly kontrola, Analys_main_control vyslednySeznam) {

    	zapis_kontrolu(indexSip, kontrola.getStavKontroly(), vyslednySeznam);
	}

	void zapis_kontrolu(int index_sip, StavKontroly stavKontroly, Analys_main_control vyslednySeznam){
		switch(stavKontroly)
		{
		case NESPUSTENA:
			vyslednySeznam.list_neprobehlych.add(index_sip);
			break;
		case OK:
    		vyslednySeznam.list_probehlych.add(index_sip);
    		vyslednySeznam.list_bezchybnych.add(index_sip);
    		break;
		case CHYBA:
    		vyslednySeznam.list_probehlych.add(index_sip);
        	vyslednySeznam.list_chybnych.add(index_sip);
            pomocnybol = false;
            break;
		}
    }
    
    private void analys_obsahova_set(int index){
        VysledekKontroly kontrola = nahraneSoubory.get(index).getSip().getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);

        for(int i = 0; i < kontrola.size(); i++){
            VysledekPravidla pravidlo = kontrola.get(i);
            // zjisteni zda jiz existuje
            Optional<Analys_rule> found = analys_obsahova.stream().filter(ao -> ao.id.equals(pravidlo.getId()))
                    .findFirst();
            if (found.isPresent()) {
                found.get().pocet_chybnych++;
                found.get().seznam_chybnych.add(index);
            } else {
                // pridani
                Analys_rule arule = new Analys_rule();
                arule.id = pravidlo.getId();
                arule.pocet_chybnych = 1;
                arule.seznam_chybnych = new ArrayList<>();
                arule.seznam_chybnych.add(index);
                analys_obsahova.add(arule);
            }

        }
    }
    
    
    
}
