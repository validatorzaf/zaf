package cz.zaf.earkvalidator;

import java.nio.file.Path;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;

/**
 * Load AIP from filesystem
 */
public class AipLoader implements AutoCloseable {
	
	ValidationResult validationResult; 

	public AipLoader(Path path) {
		validationResult = new ValidationResultImpl(null, path.getFileName().toString());
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public ValidationResult getResult() {
		return validationResult;
	}

}
