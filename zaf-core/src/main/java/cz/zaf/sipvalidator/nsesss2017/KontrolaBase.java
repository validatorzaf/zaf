package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerInfo;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        implements ValidationLayer<KontrolaNsess2017Context> {

    static Logger log = LoggerFactory.getLogger(KontrolaBase.class);

    protected KontrolaNsess2017Context ctx;

    private ValidationLayerInfo validationType;

    protected ValidationLayerResult validationResult;

    KontrolaBase(final ValidationLayerInfo validationType) {
        this.validationType = validationType;
    }

    @Override
    public ValidationLayerInfo getType() {
        return validationType;
    }

    @Override
    public void validate(KontrolaNsess2017Context context, ValidationLayerResult result) throws Exception
    {
        this.ctx = context;
        this.validationResult = result;

        log.debug("Zahajena kontrola: {}", validationType.getDescription());

        long startTime = System.currentTimeMillis();
        provedKontrolu();

        long finishTime = System.currentTimeMillis();

        log.debug("Dokoncena kontrola: {}, doba trvani: {}ms, stav: {}", validationType.getDescription(),
                  finishTime - startTime, result.getValidationStatus());

        this.validationResult = null;
        this.ctx = null;
    }

    public void pridejChybu(Rule<? extends KontrolaContext> pravidlo,
                            ErrorCode errorCode,
                            String detailChyby,
                            String mistoChyby,
                            List<EntityId> entityIds) {
        RuleValidationError p = new ChybaPravidlaNsesss2017(pravidlo,
                detailChyby,
                mistoChyby,
                errorCode,
                entityIds);
        validationResult.add(p);
    }

    public void pridejChybu(RuleValidationError p) {
        validationResult.add(p);
    }

    protected void provedKontrolu(KontrolaContext kontrolaContext, List<Rule<KontrolaContext>> rules) {

        for (var rule : rules) {

            provedKontrolu(kontrolaContext, rule);
        }
    }

    protected void provedKontrolu(KontrolaContext kontrolaContext, Rule<KontrolaContext>[] rules) {
        for (var rule : rules) {

            provedKontrolu(kontrolaContext, rule);
        }
    }

    protected void provedKontrolu(KontrolaContext kontrolaCtx, Rule<KontrolaContext> pravidlo) {

        // skip excluded checks
        if (ctx.isExcluded(pravidlo.getCode())) {
            return;
        }

        // reset promennych pred spustenim
        String mistoChyby = null;
        String detailChyby = null;
        ErrorCode errorCode = null;
        List<EntityId> entityIds = null;

        try {
            pravidlo.eval(kontrolaCtx);
            // vse ok
            return;
        } catch (ZafException e) {
            errorCode = e.getErrorCode();
            detailChyby = e.getMessage();
            mistoChyby = e.getMistoChyby();

            entityIds = e.getEntityIds();
        } catch (Exception e) {
            errorCode = BaseCode.NEZNAMA_CHYBA;
            detailChyby = e.getLocalizedMessage();
        }

        pridejChybu(pravidlo,
                    errorCode,
                    detailChyby,
                    mistoChyby,
                    entityIds);

    }

    public KontrolaNsess2017Context getContext() {
        return ctx;
    }

    /**
     * Provedeni kontroly jednotlivych pravidel
     */
    abstract void provedKontrolu();
}
