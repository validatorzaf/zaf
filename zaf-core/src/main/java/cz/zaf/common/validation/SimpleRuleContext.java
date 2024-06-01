package cz.zaf.common.validation;

public class SimpleRuleContext<Ctx extends ValidationLayerContext> implements RuleEvaluationContext {

    private final Ctx context;

    public SimpleRuleContext(final Ctx context) {
        this.context = context;
    }

    public Ctx getContext() {
        return context;
    }
}
