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

import org.apache.commons.lang3.StringUtils;

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.SipInfo;

/**
 *
 * @author standa
 */
public class EventListenerJpanelValidaceListObsahovaNa implements ListSelectionListener {
    JList<EventListenerJFmainTableSeSipSoubory_object> jListOBSAHOVANA;
    JTextArea jTextAreaPopisPravidel;
    JTable jTable1;
    SipInfo sf;
    private NahraneSoubory nahraneSoubory;

    public EventListenerJpanelValidaceListObsahovaNa(JTextArea jTextAreaPopisPravidel, JList jListOBSAHOVANA,
                                                     NahraneSoubory nahraneSoubory) {
        this.nahraneSoubory = nahraneSoubory;
        this.jListOBSAHOVANA = jListOBSAHOVANA;
        this.jTextAreaPopisPravidel = jTextAreaPopisPravidel;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        EventListenerJFmainTableSeSipSoubory_object selectedPravidlo = jListOBSAHOVANA.getSelectedValue();
        if (JFmain.jTable1.getSelectedRow() >= 0) {
            sf = nahraneSoubory.get(JFmain.jTable1.getSelectedRow()).getSip();
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
        PravidloKontroly pravidlo = selectedPravidlo.getPravidlo();
        StringBuilder sb = new StringBuilder();

        sb.append("Znění pravidla: ").append(pravidlo.getTextPravidla()).append("\n\n")
                .append("Číslo pravidla: ").append(pravidlo.getId()).append(". Stav: ").append(pravidlo.getStav())
                .append("\n\n");

        if(!pravidlo.getStav()){
            String vypisChyby = pravidlo.getVypis_chyby();
            if(StringUtils.isNotEmpty(vypisChyby)) {
                sb.append("Popis chyby: ").append(vypisChyby).append("\n\n");
            }

            String popisChyby = pravidlo.getPopisChybyObecny();
            if (StringUtils.isNotEmpty(popisChyby)) {
                sb.append("Popis obecný: ").append(popisChyby).append("\n\n");
            }

            String mistoChyby = pravidlo.getMisto_chyby();
            if (StringUtils.isNotEmpty(mistoChyby)) {
                sb.append("Místo chyby: ").append(mistoChyby).append("\n\n");
            }
        }
        String zdroj = pravidlo.getZdroj();
        if (StringUtils.isNotEmpty(zdroj)) {
            sb.append("Zdroj: ").append(zdroj);
        }
        return sb.toString();
    }
    
}
