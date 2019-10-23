/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.openFiles;

import static java.lang.Math.round;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import cz.zaf.sipvalidator.helper.HelperTime;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.LoadType;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidui.panels.JFmain;

/**
 *
 * @author m000xz006159
 */
public class SIP_Opener {
    public static int sip_opener_index = 0, sip_opener_pocet = 0;
    public static String sip_opener_index_fileName = "";
    public static String sip_opener_actualdatestring= "";
    // zpusob nahrani
    static LoadType loadLike = LoadType.LT_UNKNOWN;
    static String zip_name = "";
    static boolean zip_good = true;
    static String theOutString;
    
    public static void getOpenFiles(JFrame main_frame){
        File[] vybrane_soubory = new SIP_JFChooser().getOpenFiles();
        if(vybrane_soubory != null){
            Thread t1 = new Thread(() -> {
                SIP_Opener_Frame frame = new SIP_Opener_Frame();
                frame.setVisible(true);
                main_frame.setEnabled(false);
                SIP_Opener_ProgressWorker worker = new SIP_Opener_ProgressWorker(frame.jProgressBar1, frame.jLabel1); 
                
                sip_opener_actualdatestring = HelperTime.getActualDateString();
                sip_opener_pocet = vybrane_soubory.length;
                for(int i = 0; i < vybrane_soubory.length; i++){
                    sip_opener_index = i;
                    sip_opener_index_fileName = vybrane_soubory[i].getName();
                    try {
                        worker.doInBackground();
                    } catch (Exception ex) {
                        Logger.getLogger(SIP_Opener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Path srcPath = vybrane_soubory[i].toPath();
                    String srcPath = vybrane_soubory[i].getAbsolutePath();
                    
                    SipLoader sipLoader = new SipLoader(srcPath, null);
                    SipInfo sip = sipLoader.getSip();
                    String sipName = sip.getName();
                    /*
                    Path finalSipPath = zpracuj_soubor(srcPath, null);
                    boolean isBroken = false;
                    if(finalSipPath==null) {
                    	// broken
                    	// pouzije se predchozi cesta
                    	finalSipPath = srcPath;
                    	isBroken = true;
                    }
                    long l = SipLoader.getSipLenght(finalSipPath);                    
                    SipInfo sip = new SipInfo(sipName, zip_name, loadLike, l, finalSipPath);
                    if(isBroken) {
                    	sip.setLoadGood(false);
                    }*/
                    
                    if (!is_in_list(sipName, JFmain.seznamNahranychSouboru)) {
                        JFmain.seznamNahranychSouboru.add(sip);
                        pridejRadekDoTabulky(sip);
                    }
                }
                
            worker.done();
            worker.execute();
            frame.setVisible(false);
            main_frame.setEnabled(true);
            
            // zaměří zpět jframe
            main_frame.toFront();
            main_frame.requestFocus();
            });  
            t1.start();
        }
    }
    
    /*
    public static void getOpenFiles_web(File nahrany_soubor, String sip_or_directoryWithSips, Path path_for_unzip){   
        File[] vybrane_soubory = null;
        if(sip_or_directoryWithSips.equals("true")){
            vybrane_soubory = new File[]{nahrany_soubor};
        }
        if(sip_or_directoryWithSips.equals("false")){
            if(nahrany_soubor.isDirectory()){
                vybrane_soubory = nahrany_soubor.listFiles();
            }
            else vybrane_soubory = new File[]{nahrany_soubor};
        }
        
        if(vybrane_soubory != null){          
            sip_opener_actualdatestring = HelperTime.getActualDateString();
            for (File vybrane_soubory1 : vybrane_soubory) {
                File file = zpracuj_soubor(vybrane_soubory1, path_for_unzip);
                // zip xml dir
                if (file != null) {
                    if(!is_in_list(file, JFmain.seznamNahranychSouboru)){
                        long l = SIP_MAIN_helper.get_sip_lenght(file);
                        SIP_MAIN new_sip = new SIP_MAIN(file.getName(), zip_name, loadLike, l, file.getAbsolutePath());
                        if(!zip_good){
                            new_sip.setLoadGood(false);
                            zip_good = true; // nastavit na true pro další soubor
                        }
                        JFmain.seznamNahranychSouboru.add(new_sip);
                    }else{
                    }
                } 
                else {
                    if (!is_in_list(vybrane_soubory1, JFmain.seznamNahranychSouboru)) {
                        long l = SIP_MAIN_helper.get_sip_lenght(vybrane_soubory1);
                        SIP_MAIN new_sip = new SIP_MAIN(vybrane_soubory1.getName(), zip_name, loadLike, l, vybrane_soubory1.getAbsolutePath());
                        JFmain.seznamNahranychSouboru.add(new_sip);
                    }else{

                    }
                    
                } 
            }
        }
    }
     
    public static void getOpenFiles_web_vypis(File nahrany_soubor, String sip_or_directoryWithSips, Path path_for_unzip){   
        File[] vybrane_soubory = null;
        if(sip_or_directoryWithSips.equals("true")){
            vybrane_soubory = new File[]{nahrany_soubor};
        }
        if(sip_or_directoryWithSips.equals("false")){
            if(nahrany_soubor.isDirectory()){
                vybrane_soubory = nahrany_soubor.listFiles();
            }
            else vybrane_soubory = new File[]{nahrany_soubor};
        }
        
        if(vybrane_soubory != null){
            System.out.println(HelperTime.getHodiny(System.currentTimeMillis()) + " - VYBRÁNO " + vybrane_soubory.length + " SOUBORŮ. ZAČÍNÁM ZPRACOVÁVAT...");
            
            sip_opener_actualdatestring = HelperTime.getActualDateString();
            for (File vybrane_soubory1 : vybrane_soubory) {
                System.out.println("*" + HelperTime.getHodiny(System.currentTimeMillis()) + " - nahrávám soubor " + vybrane_soubory1.getName());
                File file = zpracuj_soubor(vybrane_soubory1, path_for_unzip);
                // zip xml dir
                if (file != null) {
                    if(!is_in_list(file, JFmain.seznamNahranychSouboru)){
                        long l = SIP_MAIN_helper.get_sip_lenght(file);
                        SIP_MAIN new_sip = new SIP_MAIN(file.getName(), zip_name, loadLike, l, file.getAbsolutePath());
                        if(!zip_good){
                            new_sip.setLoadGood(false);
                            System.out.println("   zpracován - problém při rozbalování");
                            zip_good = true; // nastavit na true pro další soubor
                        }
                        JFmain.seznamNahranychSouboru.add(new_sip);
                        System.out.println("   zpracován v pořádku");
                    }else{
                        System.out.println("   DUPLICITNÍ - nepřidán do seznamu souborů - v seznamu již takový soubor existoval.");
                    }
                } 
                else {
                    System.out.println("   CHYBASIP - nezpracován - nesplňuje podmínky sip balíčku");
                    if (!is_in_list(vybrane_soubory1, JFmain.seznamNahranychSouboru)) {
                        long l = SIP_MAIN_helper.get_sip_lenght(vybrane_soubory1);
                        SIP_MAIN new_sip = new SIP_MAIN(vybrane_soubory1.getName(), zip_name, loadLike, l, vybrane_soubory1.getAbsolutePath());
                        JFmain.seznamNahranychSouboru.add(new_sip);
                        System.out.println("   přidán do seznamu souborů");
                    }else{
                        System.out.println("   DUPLICITNÍ - nepřidán do seznamu souborů - v seznamu již takový soubor existoval.");
                    }
                    
                } 
            }
            System.out.println(HelperTime.getHodiny(System.currentTimeMillis()) + " - SOUBORY ZPRACOVÁNY. NAHRÁNO: " + vybrane_soubory.length + " ZPRACOVÁNO: " + JFmain.seznamNahranychSouboru.size());
            System.out.println("");
        }
    }    
     */
    
    private static boolean is_in_list(String sipName, ArrayList<SipInfo> seznamSIP){
        for (SipInfo sip : seznamSIP) {
            if(sipName.equals(sip.getName())) {
            	return true;
            }
        }
        return false;
    }


    private static Object[] pridejRadekDoTabulky(SipInfo souborSIP){
        String g = souborSIP.getName();
        int index = JFmain.tabulkaSIPsouboru.getRowCount();
        Object[] row = new Object[]{" " + (index+1) + ".", "???",  "?" + "????", " " + souborSIP.getName(), getVelikostSipuProTabulku(souborSIP.getLenght())};
        JFmain.tabulkaSIPsouboru.addRow(row);
        return row;
    }
    
    static String getVelikostSipuProTabulku(long sipSizeLong){
        int sipSizeInt = (int) sipSizeLong;
        int sipSizeLenght = Integer.toString(sipSizeInt).length();
        String vysledek;
        
        if(sipSizeLenght < 4){
            vysledek = " " + Integer.toString(sipSizeInt);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci vrátit pouze první zaokrouhlenou číslici (takže mě zajímají jen první dvě.
        if(sipSizeLenght == 4 || sipSizeLenght == 7 || sipSizeLenght == 10){
            String s = Integer.toString(sipSizeInt).substring(0, 2);
            int v = round(Integer.parseInt(s) / 10);            
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci 2 (např 20 kB)
        if(sipSizeLenght == 5 || sipSizeLenght == 8){
            String s = Integer.toString(sipSizeInt).substring(0, 3);
            int v = round(Integer.parseInt(s) / 10);
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci např 200 kB
        if(sipSizeLenght == 6 || sipSizeLenght == 9){
            String s = Integer.toString(sipSizeInt).substring(0, 4);
            int v = round(Integer.parseInt(s) / 10);
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        } 
        return "nezjistěno";
        
    }
    
    private static String pridejJednotkyProVelikostSipSouboru(long sipSizeLong){
        String b = " B";
        String kb = " kB";
        String mb = " MB";
        String gb = " GB";
        if(sipSizeLong < 4) return b;
        if(sipSizeLong == 4 || sipSizeLong == 5 | sipSizeLong == 6) return kb;
        if(sipSizeLong > 6 && sipSizeLong != 10) return mb;    
        return gb;
    }
    
}
