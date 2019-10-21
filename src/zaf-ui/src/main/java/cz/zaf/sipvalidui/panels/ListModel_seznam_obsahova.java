/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.SipValidator;
import cz.zaf.sipvalid.sip.TypUrovenKontroly;


/**
 *
 * @author m000xz006159
 */
public class ListModel_seznam_obsahova{
    JList jList_obsahova_vse;
    JList jList_obsahova_chyby;
    DefaultListModel modelListObsahova, modelListChybObsahova;
    
    public ListModel_seznam_obsahova(SIP_MAIN sip) {
        modelListObsahova = new DefaultListModel();
        modelListChybObsahova = new DefaultListModel();
        if(sip.isKontrolyProvedeny()){
            get_model_chyby(sip);
            get_model_all(sip); 
        }
    }
    
    private void  get_model_chyby(SIP_MAIN sip){
    	SIP_MAIN_kontrola kontrola = sip.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
        for(int i = 0; i < kontrola.size(); i++){
            int index = kontrola.get(i).getIndex();
            boolean stav = kontrola.get(i).getStav();
            String id = kontrola.get(i).getId();
            modelListChybObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(index, id, stav));
        }
    }
    
    private void get_model_all(SIP_MAIN sip){
        int[] seznamIndexuPravidel = getseznam();
//        int siz = sip.getSeznamKontrol().get(6).size();
        SIP_MAIN_kontrola kontrola = sip.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
        int t = 0;        
        int size = kontrola.size() - 1;
            for(int i = 0; i < seznamIndexuPravidel.length; i++){
                int j = seznamIndexuPravidel[i];
                if(!kontrola.isEmpty()){
                   if(kontrola.get(t).getIndex() == j){
                    boolean stav = kontrola.get(t).getStav();
                    String id = kontrola.get(t).getId();
                    modelListObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(j, id, stav));
                    if(t < size) t++;
                    }
                    else{
                        String id = SIP_MAIN_helper.get_special_ida(j);
                        modelListObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(j, id, true));
                    } 
                }
                else{
                    String id = SIP_MAIN_helper.get_special_ida(j);
                    modelListObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(j, id, true));
                }      
            }
    }
    
    private int[] getseznam(){
        if(JFmain.zvoleny_typ_kontroly == 1){
            return SipValidator.seznam_Prazdny;
        }
        if(JFmain.zvoleny_typ_kontroly == 2){
            return SipValidator.seznam_Plny;
        }
        if(JFmain.zvoleny_typ_kontroly == 3){
            return SipValidator.seznam_Prejimka;
        }
        
        return SipValidator.seznamStandaPrace;
    }
    
    

    
}
