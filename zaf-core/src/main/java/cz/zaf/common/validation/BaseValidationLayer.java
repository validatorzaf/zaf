package cz.zaf.common.validation;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.exceptions.codes.ErrorCode;
import cz.zaf.common.result.EntityId;
import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;

public abstract class BaseValidationLayer<T extends ValidationLayerContext, RCtx extends RuleEvaluationContext>
        implements ValidationLayer<T> {

    static Logger log = LoggerFactory.getLogger(BaseValidationLayer.class);

    protected T ctx;

    private final ValidationLayerType validationType;

    protected ValidationLayerResult validationResult;
    
    /**
     * Name of checked inner file
     * 
     * Might be null if validation is not based on inner file.
     */
    protected final String innerFileName;

    protected BaseValidationLayer(final ValidationLayerType validationType) {
    	this(validationType, null);
    }

    protected BaseValidationLayer(final ValidationLayerType validationType, final String innerFileName) {
        this.validationType = validationType;
        this.innerFileName = innerFileName;
    }

    @Override
    public ValidationLayerType getType() {
        return validationType;
    }
    
    @Override
    public String getInnerFileName() {
    	return innerFileName;
    }

    public T getContext() {
        return ctx;
    }

    @Override
    final public void validate(T context, ValidationLayerResult result) throws Exception {
        this.ctx = context;
        this.validationResult = result;

        log.debug("Zahajena kontrola: {}", validationType.getDescription());

        long startTime = System.currentTimeMillis();
        validateImpl();

        long finishTime = System.currentTimeMillis();

        log.debug("Dokoncena kontrola: {}, doba trvani: {}ms, stav: {}", validationType.getDescription(),
                  finishTime - startTime, result.getValidationStatus());

        this.validationResult = null;
        this.ctx = null;

    }

    public void pridejChybu(Rule<RCtx> pravidlo,
                            ErrorCode errorCode,
                            String detailChyby,
                            String mistoChyby,
                            List<EntityId> entityIds) {
        RuleValidationError p = new RuleValidationError(pravidlo,
                detailChyby,
                mistoChyby,
                errorCode,
                entityIds);
        validationResult.add(p);
    }

    public void pridejChybu(RuleValidationError p) {
        validationResult.add(p);
    }
    
	public List<? extends BaseRule<RCtx> > createRules(List<Class<? extends BaseRule<RCtx> >> ruleClasses) {
    	List<BaseRule<RCtx>> rules = new ArrayList<>(ruleClasses.size());
    	for (Class<? extends BaseRule<RCtx> > ruleClass : ruleClasses) {
			try {
				Constructor<? extends BaseRule<RCtx>> constr = ruleClass.getDeclaredConstructor();
				BaseRule<RCtx> rule = constr.newInstance();
				rules.add(rule);
			} catch (Exception e) {
				throw new IllegalStateException("Nelze vytvořit třídu pravidla: " + ruleClass.getName());
			}
		}
		return rules;
	}
    
    
    /**
     * Create rules from classes.
     * @param ruleClasses
     * @return
     */
    protected List<Rule<RCtx>> createRules(Class<?>[] ruleClasses) {
    	List<Rule<RCtx>> rules = new ArrayList<>(ruleClasses.length);
    	for (Class<?> ruleClass : ruleClasses) {
			try {
				Constructor<?> constr = ruleClass.getDeclaredConstructor();
				@SuppressWarnings("unchecked")
				Rule<RCtx> rule = (Rule<RCtx>)constr.newInstance();
				rules.add(rule);
			} catch (Exception e) {
				throw new IllegalStateException("Nelze vytvořit třídu pravidla: " + ruleClass.getName());
			}
		}
		return rules;
	}


    // this method is not used and probably could be removed
    // or refactor to be usable (problem is generic collection of rules
    protected void provedKontrolu(RCtx kontrolaContext, List<? extends Rule<RCtx>> rules) {

        for (var rule : rules) {

            provedKontrolu(kontrolaContext, rule);
        }
    }

    protected void provedKontrolu(RCtx kontrolaContext, Rule<RCtx>[] rules) {
        for (var rule : rules) {

            provedKontrolu(kontrolaContext, rule);
        }
    }

    protected void provedKontrolu(RCtx kontrolaCtx, Rule<RCtx> pravidlo) {

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
            if(detailChyby==null) {
            	detailChyby = e.getMessage();
            }
        }

        pridejChybu(pravidlo,
                    errorCode,
                    detailChyby,
                    mistoChyby,
                    entityIds);

    }

    /**
     * Implementation of the validation
     */
    protected abstract void validateImpl();
}
