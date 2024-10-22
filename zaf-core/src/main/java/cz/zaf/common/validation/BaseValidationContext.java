package cz.zaf.common.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseValidationContext implements ValidationLayerContext {
	
	private Set<String> excludeChecks = null;
	
	public BaseValidationContext(Collection<String> excludeChecks) {
		if(excludeChecks!=null) {
			this.excludeChecks = new HashSet<>(excludeChecks);
		} else {
			this.excludeChecks = Collections.emptySet();
		}
	}

	@Override
	public boolean isExcluded(String code) {
    	return excludeChecks.contains(code);
	}
}
