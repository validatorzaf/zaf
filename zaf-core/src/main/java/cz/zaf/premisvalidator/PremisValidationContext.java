package cz.zaf.premisvalidator;

import java.nio.file.Path;

import cz.zaf.common.validation.RuleEvaluationContext;

public class PremisValidationContext implements RuleEvaluationContext {
	
	private Path activeFile;

	public PremisValidationContext(Path activeFile) {
		this.activeFile = activeFile;
	}

	public Path getActiveFile() {
		return activeFile;
	}

}
