/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.openFiles;

import java.io.File;
import java.io.FilenameFilter;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author m000xz006159
 */
public class SIP_JFCooser_Filter extends FileFilter implements FilenameFilter {

    @Override
    public boolean accept(File file) {
        boolean bol = file_filter(file);
        return bol;
    }

    @Override
    public String getDescription() {
        return "sip-package";
    }

    @Override
    public boolean accept(File file, String string) {
        boolean bol = file_filter(file);
        return bol;
    }
    
    boolean file_filter (File file)
    {
        boolean bol = (file.isDirectory() || file.getAbsolutePath().endsWith(".zip"));
        return bol;
    }
}
