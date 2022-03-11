/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.tika.Tika;

/**
 * Mimetype detector
 * 
 */
public class MimetypeDetector {
    
    static private Tika tikaInstance = new Tika();
    
    static public class MimeTypeResult {

        public enum DetectionStatus {
            OK,
            FAILED
        }

        DetectionStatus detectionStatus = DetectionStatus.FAILED;
        private Exception exception;
        private String tikaMimetype;
        private String systemMimetype;
        private String mimeTypeConnection;

        public MimeTypeResult(Exception ex) {
            detectionStatus = DetectionStatus.FAILED;
            this.exception = ex;
        }

        public MimeTypeResult(final String tika,
                              final String fileUt,
                              final String mimeTypeConnection) {
            detectionStatus = DetectionStatus.OK;
            this.tikaMimetype = tika;
            this.systemMimetype = fileUt;
            this.mimeTypeConnection = mimeTypeConnection;
        }

        public MimeTypeResult(String tika, String fileUt) {
            detectionStatus = DetectionStatus.OK;
            this.tikaMimetype = tika;
            this.systemMimetype = fileUt;
        }

        public MimeTypeResult(String tika) {
            detectionStatus = DetectionStatus.OK;
            this.tikaMimetype = tika;
        }

        public DetectionStatus getDetectionStatus() {
            return detectionStatus;
        }

        public Exception getException() {
            return exception;
        }

        public String getTikaMimetype() {
            return tikaMimetype;
        }

        public String getSystemMimetype() {
            return systemMimetype;
        }

        public String getMimeTypeConnection() {
            return mimeTypeConnection;
        }

        public boolean isMimetype(String extType) {
            if (compareType(tikaMimetype, extType)) {
                return true;
            }
            if (compareType(systemMimetype, extType)) {
                return true;
            }
            if (compareType(mimeTypeConnection, extType)) {
                return true;
            }
            return false;
        }

        static private boolean compareType(String detectedType, String extType) {
            if (detectedType == null) {
                return false;
            }
            if (detectedType.equals(extType)) {
                return true;
            }
            String modified = detectedType.replaceFirst("application", "text");
            if (modified.equals(extType)) {
                return true;
            }

            return false;
        }
    };

    public static MimeTypeResult getMimeType(Path file) {
        
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
