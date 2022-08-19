/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.formats;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.tika.Tika;

/**
 * Mimetype detector
 * 
 */
public class MimetypeDetector {
    
    static private Tika tikaInstance = new Tika();

    public static MimeTypeResult getMimeType(Path file) {
        
        String siegFriedPath = System.getProperty("zaf.siegfried.path");

        try {
            String tika = tikaInstance.detect(file);
            /*
            String fileUt = Files.probeContentType(file.toPath());
            */

            /*
            String mimeTypes = null;
            URLConnection urlConnection = file.toURL().openConnection();
            try {
                mimeTypes = urlConnection.getContentType();
            } finally {
                if (urlConnection != null) {
                    urlConnection.getInputStream().close();
                }
            }
            */
            return new MimeTypeResult(tika);
        } catch (IOException ex) {
            return new MimeTypeResult(ex);
        }
    }
    
}
