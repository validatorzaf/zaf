/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.openFiles;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author m000xz006159
 */
public class SIP_JFChooser extends JFileChooser{
    
    public SIP_JFChooser() {
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setFileFilter(new SIP_JFCooser_Filter());
        setAcceptAllFileFilterUsed(false);
        setMultiSelectionEnabled(true); 
    }
    
    public File[] getOpenFiles(){
        int returnVal;
        if (SIP_Opener.theOutString != null){
            this.setCurrentDirectory(new File(SIP_Opener.theOutString));
            returnVal = showDialog(this, null);
        }
        else{
            returnVal = showDialog(this, null);
        }
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File[] jfch_choosen_files = getSelectedFiles();
            SIP_Opener.theOutString = jfch_choosen_files[0].getAbsolutePath();
            return jfch_choosen_files;
        }
        
        return null;
    }
    
}
