package cz.zaf.common.validation;

import java.util.List;

/**
 * Basic info about validator and its rules
 */
public interface ValidatorInfo {
	
	/***
	 * Return list of validation layers
	 * @return
	 */
	List<? extends ValidationLayerType> getValidationLayers();
	
	/**
	 * Return list of available subprofiles
	 * @return
	 */
	List<? extends ValidationSubprofile> getValidationSubprofiles();
	
	/**
	 * Return complete set of rules for specified validation layer
	 * @param validationLayerType
	 * @return
	 */
	List< Rule<? extends RuleEvaluationContext> > getRules(ValidationLayerType layerType, ValidationSubprofile subProfile);
}
