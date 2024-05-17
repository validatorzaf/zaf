package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.StavKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

abstract public class KontrolaBase<KontrolaContext extends RuleEvaluationContext>
        implements UrovenKontroly<KontrolaNsess2017Context> {

    static Logger log = LoggerFactory.getLogger(KontrolaBase.class);

    protected KontrolaNsess2017Context ctx;
    protected VysledekKontroly vysledekKontroly;

    @Override
    public void provedKontrolu(KontrolaNsess2017Context ctx) {
        this.ctx = ctx;

        // nejprve precteme, zda jiz byl selhany
        boolean failed = ctx.isFailed();

        vysledekKontroly = new VysledekKontroly(getUrovenKontroly(),
                getNazev());
        ctx.pridejKontrolu(vysledekKontroly);
        // po selhane kontrole se jiz nepokracuje
        if (failed) {
            return;
        }
        
        log.debug("Zahajena kontrola: {}", this.getNazev());

        long startTime = System.currentTimeMillis();
        // vychozi stav je ok
        vysledekKontroly.setStav(StavKontroly.OK);
        provedKontrolu();

        long finishTime = System.currentTimeMillis();

        log.debug("Dokoncena kontrola: {}, doba trvani: {}ms, stav: {}", this.getNazev(),
                  finishTime - startTime, vysledekKontroly.getStavKontroly());
    }

    public void pridejChybu(Rule<KontrolaContext> pravidlo,
                            ErrorCode errorCode,
                            String detailChyby,
                            String mistoChyby,
                            List<EntityId> entityIds) {
        ChybaPravidla p = new ChybaPravidlaNsesss2017(pravidlo,
                detailChyby,
                mistoChyby,
                errorCode,
                entityIds);
        vysledekKontroly.add(p);
    }

    protected void provedKontrolu(KontrolaContext kontrolaContext, List<Rule<KontrolaContext>> rules) {

        for (Rule<KontrolaContext> rule : rules) {

            provedKontrolu(kontrolaContext, rule);
        }
    }

    protected void provedKontrolu(KontrolaContext kontrolaContext, Rule<KontrolaContext>[] rules) {
        for (Rule<KontrolaContext> rule : rules) {

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

    abstract TypUrovenKontroly getUrovenKontroly();

    /**
     * Provedeni kontroly jednotlivych pravidel
     */
    abstract void provedKontrolu();
}
