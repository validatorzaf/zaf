/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;


/**
 *
 * @author m000xz006159
 */
public class ListModel_seznam_obsahova{
    JList jList_obsahova_vse;
    JList jList_obsahova_chyby;
    DefaultListModel modelListObsahova, modelListChybObsahova;
    
    int[] seznamIndexuPravidel;

    public ListModel_seznam_obsahova(SipInfo sip, ProfilValidace profilValidace) {
        modelListObsahova = new DefaultListModel();
        modelListChybObsahova = new DefaultListModel();
        this.seznamIndexuPravidel = profilValidace.getObsahoveKontroly();
        if (sip.isKontrolyProvedeny()) {
            get_model_chyby(sip);
            get_model_all(sip); 
        }
    }
    
    private void  get_model_chyby(SipInfo sip){
    	VysledekKontroly kontrola = sip.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
        for (PravidloKontroly pravidlo : kontrola.getPravidla()) {
            if (!pravidlo.getStav()) {
                modelListChybObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(pravidlo));
            }
        }
    }
    
    private void get_model_all(SipInfo sip) {
        VysledekKontroly kontrola = sip.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
        for (PravidloKontroly pravidlo : kontrola.getPravidla()) {
            modelListObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(pravidlo));
        }
    }
}
