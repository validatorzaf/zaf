package cz.zaf.common.validation;

import cz.zaf.common.result.ValidationResult;

/**
 * Validator for inner files inside another validator.
 * 
 * Caller has to prepare validation context and all other required information.
 */
public interface InnerFileValidator<T extends ValidationLayerContext> {

	/**
	 * Call inner validation
	 * @param innerFileName
	 * @param result
	 * @throws Exception
	 */
	public void validate(T context, String innerFileName, ValidationResult result) throws Exception;
}
