package cz.zaf.sipvalidator.formats;

public class MimeTypeResult {
    public enum DetectionStatus {
        OK,
        FAILED
    }

    final public DetectionStatus detectionStatus;
    public Exception exception;
    public String tikaMimetype;
    public String systemMimetype;
    public String mimeTypeConnection;

    public MimeTypeResult(DetectionStatus detectionStatus) {
        this.detectionStatus = detectionStatus;
    }

    public MimeTypeResult(Exception ex) {
        this(DetectionStatus.FAILED);
        this.exception = ex;
    }

    public MimeTypeResult(final String tika,
                          final String fileUt,
                          final String mimeTypeConnection) {
        this(DetectionStatus.OK);
        this.tikaMimetype = tika;
        this.systemMimetype = fileUt;
        this.mimeTypeConnection = mimeTypeConnection;
    }

    public MimeTypeResult(String tika, String fileUt) {
        this(DetectionStatus.OK);
        this.tikaMimetype = tika;
        this.systemMimetype = fileUt;
    }

    public MimeTypeResult(String tika) {
        this(DetectionStatus.OK);
        this.tikaMimetype = tika;
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
}