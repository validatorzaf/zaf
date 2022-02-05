package cz.zaf.sipvalidator.exceptions.codes;

public enum XmlCode implements ErrorCode {
    NEODPOVIDA_SCHEMATU("XML neodpovídá schématu");

    final private String description;

    private XmlCode(final String description) {
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
