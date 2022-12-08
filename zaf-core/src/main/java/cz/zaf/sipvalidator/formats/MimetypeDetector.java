/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.formats;

import java.nio.file.Path;

/**
 * Mimetype detector
 * 
 */
public interface MimetypeDetector {
    
    public MimeTypeResult getMimeType(Path file);

    public void cleanUp();
    
}
