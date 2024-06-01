package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.BaseRule;
import cz.zaf.common.validation.SimpleRuleContext;

/**
 * Vychozi trida pro jednoducha pravidla
 */
public abstract class PravidloBase extends BaseRule<SimpleRuleContext<KontrolaNsess2017Context>> {

    protected SimpleRuleContext<KontrolaNsess2017Context> ctx;

    public PravidloBase(final String kodPravidla,
                            final String textPravidla,
                            final String obecnyPopisChyby,
                            final String zdrojChyby) {
        super(kodPravidla, textPravidla, obecnyPopisChyby, zdrojChyby);
    }

    @Override
    public void eval(SimpleRuleContext<KontrolaNsess2017Context> ctx) {
        this.ctx = ctx;
        kontrola();
        this.ctx = null;
    }

    abstract protected void kontrola();
}
