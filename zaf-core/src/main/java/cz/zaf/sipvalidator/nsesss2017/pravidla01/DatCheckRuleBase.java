package cz.zaf.sipvalidator.nsesss2017.pravidla01;

public abstract class DatCheckRuleBase implements DatCheckRule {

    final protected String kodPravidla;
    final protected String textPravidla;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    protected DatCheckContext ctx;

    public DatCheckRuleBase(final String kodPravidla,
                            final String textPravidla,
                            final String obecnyPopisChyby,
                            final String zdrojChyby) {
        this.kodPravidla = kodPravidla;
        this.textPravidla = textPravidla;
        this.obecnyPopisChyby = obecnyPopisChyby;
        this.zdrojChyby = zdrojChyby;
    }

    @Override
    public String getCode() {
        return kodPravidla;
    }

    @Override
    public String getDescription() {
        return textPravidla;
    }

    @Override
    public String getGenericError() {
        return obecnyPopisChyby;
    }

    @Override
    public String getRuleSource() {
        return zdrojChyby;
    }

    @Override
    public void eval(DatCheckContext ctx) {
        this.ctx = ctx;
        kontrola();
        this.ctx = null;
    }

    abstract protected void kontrola();
}
