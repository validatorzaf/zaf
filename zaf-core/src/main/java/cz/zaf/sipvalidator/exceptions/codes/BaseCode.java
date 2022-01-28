package cz.zaf.sipvalidator.exceptions.codes;

public enum BaseCode implements ErrorCode {

    UNKNOWN_ERROR("Jiná výjimka"),

    /**
     * Standardní chybový kód.
     * 
     * Pooužije se pokud není k dispozici jiná specializace
     */
    ERROR("Obecná chyba"),

    /**
     * TODO: Dopsat pravidla užití kódů
     */
    MISSING_ELEMENT("Chybí element"),

    MISSING_ATTRIBUTE("Chybí atribut elementu v XML, který je povinný"),

    INVALID_VALUE("Chybná hodnota atributu");

    final private String description;

    private BaseCode(final String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getErrorCode() {
        return name();
    }
}
