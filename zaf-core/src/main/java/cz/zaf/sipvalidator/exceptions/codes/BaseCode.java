package cz.zaf.sipvalidator.exceptions.codes;

public enum BaseCode implements ErrorCode {

    NEZNAMA_CHYBA("Jiná výjimka"),

    /**
     * Standardní chybový kód.
     * 
     * Pooužije se pokud není k dispozici jiná specializace
     */
    CHYBA("Obecná chyba"),

    /**
     * TODO: Dopsat pravidla užití kódů
     */
    CHYBI_ELEMENT("Chybí element"),

    CHYBI_ATRIBUT("Chybí atribut elementu v XML, který je povinný"),

    CHYBNA_HODNOTA_ATRIBUTU("Chybná hodnota atributu");

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
