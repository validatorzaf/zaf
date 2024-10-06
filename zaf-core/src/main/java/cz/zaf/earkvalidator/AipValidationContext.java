package cz.zaf.earkvalidator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerContext;

public class AipValidationContext implements RuleEvaluationContext, ValidationLayerContext{
	
	/**
	 * AIP loader
	 */
	private AipLoader aipLoader;
	
	private Set<String> excludeChecks = null;	
	
	public AipValidationContext(final AipLoader aipLoader, 
			final List<String> excludeChecks) {
		this.aipLoader = aipLoader;
        if(excludeChecks!=null) {
        	this.excludeChecks = excludeChecks.stream().collect(Collectors.toSet()); 
        }		
	}

	@Override
	public ValidationResult getValidationResult() {
		return aipLoader.getResult();
	}

	@Override
	public boolean isExcluded(String code) {
    	return (excludeChecks!=null)?
        		excludeChecks.contains(code):false;
	}

	public AipLoader getLoader() {
		return aipLoader;
	}

}
