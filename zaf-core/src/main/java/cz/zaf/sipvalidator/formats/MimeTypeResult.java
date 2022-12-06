package cz.zaf.sipvalidator.formats;

public class MimeTypeResult {
    public enum DetectionStatus {
        OK,
        FAILED
    }

    final public DetectionStatus detectionStatus;
    public Exception exception;
    public String detectedMimetype;

    public MimeTypeResult(DetectionStatus detectionStatus) {
        this.detectionStatus = detectionStatus;
    }

    public MimeTypeResult(Exception ex) {
        this(DetectionStatus.FAILED);
        this.exception = ex;
    }

    public MimeTypeResult(final String detectedMimetype) {
        this(DetectionStatus.OK);
        this.detectedMimetype = detectedMimetype;
    }

    public boolean isMimetype(String extType) {
        if (compareType(detectedMimetype, extType)) {
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

    public DetectionStatus getDetectionStatus() {
        return detectionStatus;
    }

    public Exception getException() {
        return exception;
    }

    public String getDetectedMimetype() {
        return detectedMimetype;
    }
}