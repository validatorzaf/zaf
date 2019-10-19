/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.SIP_MAIN_seznam;

/**
 *
 * @author standa
 */
public class EventListenerJpanelValidaceListObsahovaNa implements ListSelectionListener {
    JList<EventListenerJFmainTableSeSipSoubory_object> jListOBSAHOVANA;
    JTextArea jTextAreaPopisPravidel;
    JTable jTable1;
    SIP_MAIN sf;

    public EventListenerJpanelValidaceListObsahovaNa(JTextArea jTextAreaPopisPravidel, JList jListOBSAHOVANA){
        this.jListOBSAHOVANA = jListOBSAHOVANA;
        this.jTextAreaPopisPravidel = jTextAreaPopisPravidel;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        EventListenerJFmainTableSeSipSoubory_object selectedPravidlo = jListOBSAHOVANA.getSelectedValue();
        if(JFmain.jTable1.getSelectedRow() != -1){
            sf = JFmain.seznamNahranychSouboru.get(JFmain.jTable1.getSelectedRow());
            if(selectedPravidlo != null){
                jTextAreaPopisPravidel.setText(nastavTextPopisuPravidla(selectedPravidlo));
                jTextAreaPopisPravidel.setCaretPosition(0);
            }
            
        }
//        sf = JFmain.seznamNahranychSouboru.get(JFmain.jTable1.getSelectedRow());
//        if(selectedPravidlo != null && (selectedPravidlo.splneno == false)){
//            jTextAreaPopisPravidel.setText(nastavTextPopisuPravidla(selectedPravidlo));
//        }
//        if(selectedPravidlo != null && (selectedPravidlo.splneno == true)){
//            jTextAreaPopisPravidel.setText(nastavTextPopisuPravidla(selectedPravidlo));
//        }
//        jTextAreaPopisPravidel.setText(nastavTextPopisuPravidla(selectedPravidlo));
//        jTextAreaPopisPravidel.setCaretPosition(0);
    }

    private String nastavTextPopisuPravidla(EventListenerJFmainTableSeSipSoubory_object selectedPravidlo){
        String vraceny =    "Znění pravidla: " + SIP_MAIN_seznam.get_text_Obsahova(selectedPravidlo.index) + "\n" + "\n" +
                            "Číslo pravidla: " + selectedPravidlo.id + ". Index pravidla: " + selectedPravidlo.index + ". Stav: " + selectedPravidlo.splneno;
        if(!selectedPravidlo.splneno){
            vraceny += "\n" + "\n" + "Popis chyby: " + SIP_MAIN_helper.get_vypisChyby_obsahova(sf, selectedPravidlo.index);
            vraceny += "\n" + "\n" + "Popis obecný: " + SIP_MAIN_seznam.get_popis_Obsahova(selectedPravidlo.index);
            vraceny += "\n" + "\n" + "Místo chyby: " + SIP_MAIN_helper.get_mistoChyby_obsahova(sf, selectedPravidlo.index);
            vraceny += "\n" + "\n" + "Zdroj: " + SIP_MAIN_seznam.get_zdroje_Obsahova(selectedPravidlo.index);
        }
        else{
            vraceny += "\n" + "\n" + "Zdroj: " + SIP_MAIN_seznam.get_zdroje_Obsahova(selectedPravidlo.index);
        }
        return vraceny;
    }
    
}
