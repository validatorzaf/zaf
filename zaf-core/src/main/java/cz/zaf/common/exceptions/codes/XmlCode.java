package cz.zaf.common.exceptions.codes;

public enum XmlCode implements ErrorCode {
    FAILED_TO_PARSE("XML se nepodailo načíst"),

    NEODPOVIDA_SCHEMATU("XML neodpovídá schématu"),

    NEOCEKAVANY_ELEMENT("V XML je uveden neočekávaný element");

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
