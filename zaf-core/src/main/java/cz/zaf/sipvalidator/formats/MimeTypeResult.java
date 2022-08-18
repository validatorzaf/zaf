package cz.zaf.sipvalidator.formats;

import cz.zaf.sipvalidator.formats.MimetypeDetector.MimeTypeResult.DetectionStatus;

public class MimeTypeResult {
    public DetectionStatus detectionStatus;
    public Exception exception;
    public String tikaMimetype;
    public String systemMimetype;
    public String mimeTypeConnection;

    public MimeTypeResult(DetectionStatus detectionStatus) {
        this.detectionStatus = detectionStatus;
    }
}