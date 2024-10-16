package cz.zaf.premisvalidator;

import java.nio.file.Path;

import cz.zaf.common.validation.RuleEvaluationContext;

public class PremisValidationContext implements RuleEvaluationContext {
	
	private Path activeFile;
	private PremisLoader loader;

	public PremisValidationContext(Path activeFile) {
		this.activeFile = activeFile;
		this.loader = new PremisLoader(activeFile);
	}

	public Path getActiveFile() {
		return activeFile;
	}

	public PremisLoader getLoader() {
		return loader;
	}

}
