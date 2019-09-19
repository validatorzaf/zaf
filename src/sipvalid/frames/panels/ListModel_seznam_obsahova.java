/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.frames.panels;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import sipvalid.sip.SIP_MAIN;
import sipvalid.sip.SIP_MAIN_helper;


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
        if(!sip.getSeznamKontrol().isEmpty()){
            get_model_chyby(sip);
            get_model_all(sip); 
        }
    }
    
    private void  get_model_chyby(SIP_MAIN sip){
        for(int i = 0; i < sip.getSeznamKontrol().get(6).size(); i++){
            int index = sip.getSeznamKontrol().get(6).get(i).getIndex();
            boolean stav = sip.getSeznamKontrol().get(6).get(i).getStav();
            String id = sip.getSeznamKontrol().get(6).get(i).getId();
            modelListChybObsahova.addElement(new EventListenerJFmainTableSeSipSoubory_object(index, id, stav));
        }
    }
    
    private void get_model_all(SIP_MAIN sip){
        int[] seznamIndexuPravidel = getseznam();
//        int siz = sip.getSeznamKontrol().get(6).size();
        int t = 0;
        int size = sip.getSeznamKontrol().get(6).size() - 1;
            for(int i = 0; i < seznamIndexuPravidel.length; i++){
                int j = seznamIndexuPravidel[i];
                if(!sip.getSeznamKontrol().get(6).isEmpty()){
                   if(sip.getSeznamKontrol().get(6).get(t).getIndex() == j){
                    boolean stav = sip.getSeznamKontrol().get(6).get(t).getStav();
                    String id = sip.getSeznamKontrol().get(6).get(t).getId();
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
            return JFmain.seznam_Prazdny;
        }
        if(JFmain.zvoleny_typ_kontroly == 2){
            return JFmain.seznam_Plny;
        }
        if(JFmain.zvoleny_typ_kontroly == 3){
            return JFmain.seznam_Prejimka;
        }
        
        return JFmain.seznamStandaPrace;
    }
    
    

    
}
