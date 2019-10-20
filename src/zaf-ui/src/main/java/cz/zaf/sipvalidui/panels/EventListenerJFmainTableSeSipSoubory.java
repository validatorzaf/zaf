/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import static cz.zaf.sipvalidui.panels.JPanel_Kontrola_analyza.jTextArea_kontrola;
import static cz.zaf.sipvalid.validator.SeznamPravidelSpolecna2018.seznam_pravidel_Datova_struktura;

import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.TypUrovenKontroly;




/**
 *
 * @author m000xz006159
 */
public class EventListenerJFmainTableSeSipSoubory implements ListSelectionListener{

    JTextArea jTextAreaPredKontrola;
    JTable jTable1;
    JList jListObsahovaNA;
    JCheckBox chboxObsahovaNa;
    JTextArea jTextAreaPopisPravidel;
    
    
    public EventListenerJFmainTableSeSipSoubory(JTextArea jTextAreaPredKontrola, JTextArea jTextAreaPopisPravidel, JTable jTable1, JList jListObsahovaNA, JCheckBox chboxObsahovaNa) {
        this.jTextAreaPredKontrola = jTextAreaPredKontrola;
        this.jTextAreaPopisPravidel = jTextAreaPopisPravidel;
        this.jTable1 = jTable1;
        this.jListObsahovaNA = jListObsahovaNA;
        this.chboxObsahovaNa = chboxObsahovaNa;
        chboxObsahovaNa.addActionListener((ActionEvent e) -> { selectSipAction();
        });
        
    }
    

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        selectSipAction();
        
    }
    
    public void selectSipAction(){
        jTextAreaPopisPravidel.setText("");
        int selectedRow = jTable1.getSelectedRow();
        if(selectedRow != -1){  
            SIP_MAIN sf = JFmain.seznamNahranychSouboru.get(selectedRow);
            SIP_MAIN_kontrola obsahova = sf.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
            if(obsahova!=null && obsahova.isProvedena()){ 
                chboxObsahovaNa.setEnabled(true);
                boolean zakrtnutoObsahovaNA = chboxObsahovaNa.isSelected();
                // tady zmena
                ListModel_seznam_obsahova ls = new ListModel_seznam_obsahova(sf);
                if(zakrtnutoObsahovaNA) jListObsahovaNA.setModel(ls.modelListChybObsahova);
                if(!zakrtnutoObsahovaNA) jListObsahovaNA.setModel(ls.modelListObsahova);
            }
            else{
                chboxObsahovaNa.setEnabled(false);
                jListObsahovaNA.setModel(new DefaultListModel());
                jTextAreaPredKontrola.setText("");
            }
            vypis(sf); // chybejici node resit pak  
            jTextAreaPredKontrola.setCaretPosition(0);
            jTextArea_kontrola.setCaretPosition(0);    
        }
        if(selectedRow == -1){
           jListObsahovaNA.setModel(new DefaultListModel());
           jTextAreaPredKontrola.setText("");
        }
    }
    
    private void vypis(SIP_MAIN sf){
        String vypis = "";
        if(sf.isKontrolyProvedeny()){
            vypis = vypisKontrolaSkodlivehoKodu(sf)+ // vypis škodlivý kód
            vypisKontrolaDatoveStruktury(sf) + // vypis datovou
            vypisKodovani(sf) + // znaková sada     
            vypisSpravnosti(sf) +
            vypisKOntrolaJmennychProstoru(sf) + // jmennych prostoru
            vypisValidaceXML(sf) +  // proti schématu 
            vypisKontrolaObsahova(sf); // obsahova
        }
        jTextAreaPredKontrola.setText(
            "* CESTA K METS XML: " + SIP_MAIN_helper.getCesta_mets(sf) + "\n" + "\n"+
            "* CESTA K XML PRO WEB: " +  JFmain.cesta_xml_web +    
            vypis
        );
        
    }
    
    private String vypisSpravnosti(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrSpravnosti = sf.getUrovenKontroly(TypUrovenKontroly.SPRAVNOSTI);
        boolean b = kontrSpravnosti.isProvedena();
        boolean bb = kontrSpravnosti.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.ZNAKOVE_SADY).getStav()){
            String vypis = "\n" + "\n" + "* KONTROLA SPRÁVNOSTI XML:" + " ";
            if(!b) return vypis + "Kontrola ještě neproběhla";
            else{
                if(bb){
                    vypis += "Soubor je Well Formed!";
                    return vypis;
                }
                if(!bb){
                    vypis += "Soubor není Well Formed! " + kontrSpravnosti.get(0).getVypis_chyby();
                    return vypis;
                }
            }
            return vypis;
        }
        return "";
    }
    
    private String vypisKodovani(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.ZNAKOVE_SADY);
        boolean b = kontrola.isProvedena();
        boolean bb = kontrola.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.DATOVE_STRUKTURY).getStav()){
            String vypis = "\n" + "\n" + "* KONTROLA ZNAKOVÉ SADY: ";
            if(!b){
                vypis += "Kontrola ještě neproběhla";
                return vypis;
            }
            else{
                if(bb){
                    vypis += "Proběhla v pořádku. " + "Kódování: " + sf.getKodovani();
                    return vypis;
                }
                else{
                    vypis += "Nalezena chyba. " + kontrola.get(0).getVypis_chyby();
                    return vypis;
                }
            } 
        }
        return "\n" + "\n" + "* KONTROLA ZNAKOVÉ SADY: NEPROVEDENA.";
    }
    
    private String vypisValidaceXML(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.PROTI_SCHEMATU);
        boolean b = kontrola.isProvedena();
        boolean bb = kontrola.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.JMENNE_PROSTORY_XML).getStav()){
            String vypis ="\n" + "* KONTROLA PROTI SCHÉMATU XSD: ";
            String r = "Kontola ještě neproběhla.";
            if(!b) return vypis + r;
            else{
                if(bb){
                    return vypis += "Proběhla v pořádku." + "\n";
                }
                else{
                    vypis += "Nalezeny chyby souboru!";
                    for(int i = 0; i < kontrola.size(); i++){
                        vypis += "\n" + "   " + "Místo chyby: " + kontrola.get(i).getMisto_chyby() + "\n";
                        if(i != kontrola.size()-1){
                            vypis += "   " + "Popis chyby: " + kontrola.get(i).getVypis_chyby() + "\n";
                        }
                        else{
                            vypis += "   " + "Popis chyby: " + kontrola.get(i).getVypis_chyby();
                        }
                    }

                    return vypis;
                }
            }
        }
        return "\n" + "\n" + "* KONTROLA PROTI SCHÉMATU XSD: NEPROVEDENA.";
    }

    private String vypisKontrolaObsahova(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.OBSAHOVA);
        boolean b = kontrola.isProvedena();
        boolean bb = kontrola.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.PROTI_SCHEMATU).getStav()){
            String vypis ="\n" + "* KONTROLA OBSAHU: ";
            String r = "Kontola ještě neproběhla.";
            if(!b) return vypis + r;
            else{
                if(bb){
                    return vypis += "Proběhla v pořádku.";
                }
                else{
                    vypis += "Nalezeny chyby!";
                    return vypis;
                }
            }
        }
        return "\n" + "\n" + "* KONTROLA OBSAHU: NEPROVEDENA.";
    }
    private String vypisKontrolaDatoveStruktury(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.DATOVE_STRUKTURY);
        boolean b = kontrola.isProvedena();
        boolean bb = kontrola.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.SKODLIVY_KOD).getStav()){
            String vypis = "\n" + "\n" + "* KONTROLA DATOVÉ STRUKTURY: ";
            String r = "Kontrola ještě neproběhla";
            if(!b) return vypis + r;
            else{
                if(!bb) r = "Nalezeny chyby!";
                else r = "Proběhla v pořádku";
                vypis += r;
                for(int i = 0; i < 3; i++){
                     boolean bol = kontrola.get(i).getStav();
                    String s = kontrola.get(i).getVypis_chyby();
                    if(bol){
                        vypis += "\n" + "   " + "Pravidlo " + (i+1) + ": " + seznam_pravidel_Datova_struktura[i+1] + "\n" + "\t" +
                               "  SPLNĚNO: " + s;
                    }
                    else{
                        vypis += "\n" + "   " + "Pravidlo " + (i+1) + ": " + seznam_pravidel_Datova_struktura[i+1] + "\n" + "\t" +
                               "  NESPLNĚNO: " + s; 
                    }
                }
            }
            return vypis;
        }
        return "\n" + "\n" + "* KONTROLA DATOVÉ STRUKTURY: NEPROVEDENA.";        
    }
    
    private String vypisKontrolaSkodlivehoKodu(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.SKODLIVY_KOD);
        boolean b = kontrola.isProvedena();
        boolean bb = kontrola.getStav();
        String vypis = "\n" + "\n" + "* KONTROLA ŠKODLIVÉHO KÓDU: ";
        if(!b){
            vypis += "Kontrola ještě neproběhla";
            return vypis;
        }
        if(b){
            if(bb){
                vypis += "Proběhla v pořádku";
                return vypis;
            }
            if(!bb){
                vypis +=  "Nalezeny hrozby!" + "\n" + "   " + kontrola.get(0).getVypis_chyby();
                return vypis;
            }
        }
        return vypis;
    }
    
    private String vypisKOntrolaJmennychProstoru(SIP_MAIN sf){
    	SIP_MAIN_kontrola kontrola = sf.getUrovenKontroly(TypUrovenKontroly.JMENNE_PROSTORY_XML);
        boolean provedena = kontrola.isProvedena();
        boolean jeKontrolaBezChyby = kontrola.getStav();
        if(sf.getUrovenKontroly(TypUrovenKontroly.SPRAVNOSTI).getStav()){
            String vypis = "\n" + "\n" + "* KONTROLA JMENNÝCH PROSTORŮ XML: ";
            if(!provedena){
                vypis += "Kontrola ještě neproběhla";
                return vypis;
            }
            else{
                if(!jeKontrolaBezChyby){
                    vypis += " Neproběhla v pořádku. Nalezena chyba!" + "\n";
                    if(!kontrola.get(0).getStav()){
                        vypis += "   " + "Pravidlo 1: " + "Soubor obsahuje právě jeden kořenový element <mets:mets>." + "\n";
                        vypis += "\t" + "  NESPLNĚNO: " + kontrola.get(0).getVypis_chyby() + "\n"; 
                    }
                    if(!kontrola.get(1).getStav()){
                        vypis += "   " + "Pravidlo 2: " + "Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd, nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd." + "\n";
                        vypis += "\t" + "  NESPLNĚNO: " + kontrola.get(1).getVypis_chyby() + "\n"; 
                    }
                    return vypis;
                }
                else{
                    vypis += " Proběhla v pořádku" + "\n";
                    return vypis;
                }
            }
        }
        return "\n" + "\n" + "* KONTROLA JMENNÝCH PROSTORŮ XML: NEPROVEDENA.";
    }
}
