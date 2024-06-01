package cz.zaf.common.validation;

import java.util.Objects;

public abstract class BaseRule<Ctx extends RuleEvaluationContext> implements Rule<Ctx> {

    final protected String kodPravidla;
    final protected String textPravidla;
    final protected String obecnyPopisChyby;
    final protected String zdrojChyby;

    public BaseRule(final String kodPravidla,
                    final String textPravidla,
                    final String obecnyPopisChyby,
                    final String zdrojChyby) {
        Objects.requireNonNull(kodPravidla);
        Objects.requireNonNull(textPravidla);

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
    public String getRuleSource() {
        return zdrojChyby;
    }

    @Override
    public String getDescription() {
        return textPravidla;
    }

    @Override
    public String getGenericError() {
        return obecnyPopisChyby;
    }

}
