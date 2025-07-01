package cz.zaf.common.validation;

/**
 * Validator for inner files inside another validator.
 * 
 * Caller has to prepare validation context and all other required information.
 */
public interface InnerFileValidator<T extends ValidationLayerContext> {

	/**
	 * Call inner validation
	 * @param innerFileName Name of inner file. This is used to display information for user 
	 * and not to load file from disk.
	 * @param result
	 * @throws Exception
	 */
	public void validate(T context, String innerFileName) throws Exception;
}
