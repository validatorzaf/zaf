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
    
    DUPLICITA("Nalezena nepovolená duplicita entity, elementu, hodnoty..."),
    
    CHYBA_KRIZVEHO_ODKAZU("Křížový odkaz neplní správně svou funkci"),
    
    CHYBI_ELEMENT("Chybí element"),

    CHYBI_HODNOTA_ELEMENTU("Nevyplněná hodnota elementu"),
    
    CHYBI_HODNOTA_ATRIBUTU("Nevyplněná hodnota atributu"),
    
    NEPOVOLENY_ELEMENT("Výskyt nepovoleného elementu"),
    
    CHYBI_ATRIBUT("Chybí atribut elementu v XML, který je povinný"),

    CHYBNA_HODNOTA_ATRIBUTU("Chybná hodnota atributu"),

    CHYBNA_HODNOTA_ELEMENTU("Chybná hodnota elementu"),
    
    CHYBNY_ELEMENT("Chybný element"),
    
    CHYBI_KOMPONENTA("Chybí soubor odpovídající komponentě"),

    CHYBNA_KOMPONENTA("Chyba v komponentě");

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
