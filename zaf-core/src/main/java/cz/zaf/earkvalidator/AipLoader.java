package cz.zaf.earkvalidator;

import java.nio.file.Path;
import java.util.Objects;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.result.ValidationResultImpl;

/**
 * Load AIP from filesystem
 */
public class AipLoader implements AutoCloseable {
	
	public enum AipSrcType {
		DIRECTORY,
		FILE
	}
	
	ValidationResult validationResult;
	
	private AipSrcType aipSrcType;
	
	/**
	 * Original path to the AIP
	 */
	private Path aipSrcPath; 

	public AipLoader(Path aipSrcPath) {
		Objects.requireNonNull(aipSrcPath);
		
		this.aipSrcPath = aipSrcPath;
		validationResult = new ValidationResultImpl(null, aipSrcPath.getFileName().toString());
	}
	
	/**
	 * Load AIP
	 */
	public void init() {
		if (aipSrcPath.toFile().isDirectory()) {
			aipSrcType = AipSrcType.DIRECTORY;
		} else {
			aipSrcType = AipSrcType.FILE;
		}		
	}
	
	public AipSrcType getAipSrcType() {
		return aipSrcType;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public ValidationResult getResult() {
		return validationResult;
	}

	public Path getAipSrcPath() {
		return aipSrcPath;
	}

}
