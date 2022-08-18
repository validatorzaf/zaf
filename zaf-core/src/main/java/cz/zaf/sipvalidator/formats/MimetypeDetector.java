/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.formats;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.tika.Tika;

import cz.zaf.sipvalidator.formats.MimetypeDetector.MimeTypeResult.DetectionStatus;

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

        MimeTypeResult data = new MimeTypeResult(DetectionStatus.FAILED);

        public MimeTypeResult(Exception ex) {
            data.detectionStatus = DetectionStatus.FAILED;
            this.data.exception = ex;
        }

        public MimeTypeResult(final String tika,
                              final String fileUt,
                              final String mimeTypeConnection) {
            data.detectionStatus = DetectionStatus.OK;
            this.data.tikaMimetype = tika;
            this.data.systemMimetype = fileUt;
            this.data.mimeTypeConnection = mimeTypeConnection;
        }

        public MimeTypeResult(String tika, String fileUt) {
            data.detectionStatus = DetectionStatus.OK;
            this.data.tikaMimetype = tika;
            this.data.systemMimetype = fileUt;
        }

        public MimeTypeResult(String tika) {
            data.detectionStatus = DetectionStatus.OK;
            this.data.tikaMimetype = tika;
        }

        public DetectionStatus getDetectionStatus() {
            return data.detectionStatus;
        }

        public Exception getException() {
            return data.exception;
        }

        public String getTikaMimetype() {
            return data.tikaMimetype;
        }

        public String getSystemMimetype() {
            return data.systemMimetype;
        }

        public String getMimeTypeConnection() {
            return data.mimeTypeConnection;
        }

        public boolean isMimetype(String extType) {
            if (compareType(data.tikaMimetype, extType)) {
                return true;
            }
            if (compareType(data.systemMimetype, extType)) {
                return true;
            }
            if (compareType(data.mimeTypeConnection, extType)) {
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
