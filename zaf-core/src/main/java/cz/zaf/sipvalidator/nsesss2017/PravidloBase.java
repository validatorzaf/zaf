package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.SimpleRuleContext;

/**
 * Vychozi trida pro jednoducha pravidla
 */
public abstract class PravidloBase implements Rule<SimpleRuleContext<KontrolaNsess2017Context>> {

    final protected String kodPravidla;
    final protected String textPravidla;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    protected SimpleRuleContext<KontrolaNsess2017Context> ctx;

    public PravidloBase(final String kodPravidla,
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
    public void eval(SimpleRuleContext<KontrolaNsess2017Context> ctx) {
        this.ctx = ctx;
        kontrola();
        this.ctx = null;
    }

    abstract protected void kontrola();
}
