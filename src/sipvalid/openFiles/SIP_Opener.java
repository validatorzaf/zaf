/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.openFiles;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import sipvalid.sip.SIP_MAIN;
import sipvalid.sip.SIP_MAIN_helper;
import sipvalid.frames.panels.JFmain;
import sipvalid.helper.HelperTime;

/**
 *
 * @author m000xz006159
 */
public class SIP_Opener {
    public static int sip_opener_index = 0, sip_opener_pocet = 0;
    public static String sip_opener_index_fileName = "";
    public static String sip_opener_actualdatestring= "";
    // load sip_type: 0 dir, 1 xml, 2 zip, -1 nepovolený formát
    static int load_like = -1;
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
                    
                    File file = zpracuj_soubor(vybrane_soubory[i], null);
                    // zip xml dir
                    if(file != null){
                        if(!is_in_list(file, JFmain.seznamNahranychSouboru)){
                            long l = SIP_MAIN_helper.get_sip_lenght(file);
                            SIP_MAIN new_sip = new SIP_MAIN(file.getName(), zip_name, load_like, l, file.getAbsolutePath());
                            if(!zip_good){
                                new_sip.setLoadGood(false);
                                zip_good = true; // nastavit na true pro další soubor
                            }
                            JFmain.seznamNahranychSouboru.add(new_sip);
                            pridejRadekDoTabulky(new_sip);  
                        }
                        
                    }
                    // nahrán jako něco jiného než zip dir nebo xml
                    else{
                        if(!is_in_list(vybrane_soubory[i], JFmain.seznamNahranychSouboru)){
                            long l = SIP_MAIN_helper.get_sip_lenght(vybrane_soubory[i]);
                            SIP_MAIN new_sip = new SIP_MAIN(vybrane_soubory[i].getName(), zip_name, load_like, l, vybrane_soubory[i].getAbsolutePath());
                            JFmain.seznamNahranychSouboru.add(new_sip);
                            pridejRadekDoTabulky(new_sip);  
                        }  
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
    
    public static void getOpenFiles_web(File nahrany_soubor, String sip_or_directoryWithSips, String path_for_unzip){   
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
                        SIP_MAIN new_sip = new SIP_MAIN(file.getName(), zip_name, load_like, l, file.getAbsolutePath());
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
                        SIP_MAIN new_sip = new SIP_MAIN(vybrane_soubory1.getName(), zip_name, load_like, l, vybrane_soubory1.getAbsolutePath());
                        JFmain.seznamNahranychSouboru.add(new_sip);
                    }else{

                    }
                    
                } 
            }
        }
    }
     
    public static void getOpenFiles_web_vypis(File nahrany_soubor, String sip_or_directoryWithSips, String path_for_unzip){   
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
                        SIP_MAIN new_sip = new SIP_MAIN(file.getName(), zip_name, load_like, l, file.getAbsolutePath());
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
                        SIP_MAIN new_sip = new SIP_MAIN(vybrane_soubory1.getName(), zip_name, load_like, l, vybrane_soubory1.getAbsolutePath());
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
    
    private static File zpracuj_soubor(File file, String path_for_unzip){
        if(file.isDirectory()){
            load_like = 0;
            zip_name = "";
            return file;
        }
        else{
            if(file.getAbsolutePath().endsWith(".zip")){
                zip_name = file.getName();
                load_like = 2;
                //bere pouze directories
                return get_unzipped_file(file, path_for_unzip);
            
            }
            if(file.getAbsolutePath().endsWith(".xml")){
                zip_name = "";
                load_like = 1;
                return file;
            
            }
            zip_name = "";
            load_like = -1;
            return file;  
        }
    }
    
    private static boolean is_in_list(File file, ArrayList<SIP_MAIN> seznamSIP){
        for (SIP_MAIN sip : seznamSIP) {
            if(file.getName().equals(sip.getName())) return true;
        }
        return false;
    }
    
    private static File get_unzipped_file(File zip, String path_for_unzip){
        String path;
        if(path_for_unzip == null || path_for_unzip.equals("undefined") || path_for_unzip.equals("null")){
            try {
                path = new File(".").getCanonicalPath() + File.separator + "rozbaleno" + File.separator + sip_opener_actualdatestring;
            } catch (IOException ex) {
                Logger.getLogger(SIP_Opener.class.getName()).log(Level.SEVERE, null, ex);
                path = path_for_unzip + File.separator + sip_opener_actualdatestring;
            }
        }
        else{
            path = path_for_unzip + File.separator + sip_opener_actualdatestring;
        }
        
        new File(path).mkdir();
        File rozbaleny;
        try {
            ZipFile zipFile = new ZipFile(zip);
            zipFile.setFileNameCharset("IBM852"); // extrakce českých znaků
            boolean isvalidZipFile = zipFile.isValidZipFile();
            if(isvalidZipFile){
                boolean bol = canBeUnziped(zipFile);
                if(bol){
                    String n = zipFile.getFile().getName().substring(0, zipFile.getFile().getName().length()-4);
                    zipFile.setRunInThread(false);
                    zipFile.extractAll(path);
                    rozbaleny = new File(path + File.separator + n);
                    while(zipFile.getProgressMonitor().getPercentDone() == 100){
                        return rozbaleny;
                    }     
                }
                else{
                    zip_good = false; 
                    return zip;
                }
            }
            else{
                zip_good = false; 
                return zip;
            }
            
        } catch (ZipException ex) {
            zip_good = false;
            return zip;
        }
        return rozbaleny;
    }
    
    private static boolean canBeUnziped(ZipFile zipFile){
        if(!zipFile.isValidZipFile()) return false;
        String ocekavanejmeno = zipFile.getFile().getName().replaceFirst(".zip", "");
        try {
            ArrayList<FileHeader> list = (ArrayList<FileHeader>) zipFile.getFileHeaders();
            for(int i = 0; i < list.size(); i++){
                FileHeader fh = list.get(i);   
                String s = fh.getFileName();
                if(!(s.contains(ocekavanejmeno + File.separator) || s.contains(ocekavanejmeno + "/"))){
                    return false;
                }
            }
        } catch (ZipException ex) {
            return false;
        }

        return true;
    }

    private static Object[] pridejRadekDoTabulky(SIP_MAIN souborSIP){
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
