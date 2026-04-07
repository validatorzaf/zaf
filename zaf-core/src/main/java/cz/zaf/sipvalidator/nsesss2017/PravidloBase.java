package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.BaseRule;

/**
 * Vychozi trida pro jednoducha pravidla
 */
public abstract class PravidloBase extends BaseRule<KontrolaNsessContext> {

    protected KontrolaNsessContext ctx;

    public PravidloBase(final String kodPravidla,
                            final String textPravidla,
                            final String obecnyPopisChyby,
                            final String zdrojChyby) {
        super(kodPravidla, textPravidla, obecnyPopisChyby, zdrojChyby);
    }

    @Override
    public void eval(KontrolaNsessContext ctx) {
        this.ctx = ctx;
        kontrola();
        this.ctx = null;
    }

    abstract protected void kontrola();
}
