package cz.zaf.sipvalidator.pdfa;

public class ValidationResult {

    private String errorMsg;
    private boolean compliant = false;

    ValidationResult() {
    }

    public void setError(final String errorMsg) {
        this.errorMsg = errorMsg;
        this.compliant = false;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getErrorMessage() {
        return errorMsg;
    }

    public void setCompliant(final boolean b) {
        compliant = b;
    }

}
