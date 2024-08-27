package cz.zaf.eadvalidator.ap2023;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerContext;

public class EadValidationContext implements RuleEvaluationContext, ValidationLayerContext {

    private EadLoader eadLoader;
	private Element rootElement;

    EadValidationContext(final EadLoader eadLoader) {
        this.eadLoader = eadLoader;
    }

    public EadLoader getLoader() {
        return eadLoader;
    }

    @Override
    public ValidationResult getValidationResult() {
        return eadLoader;
    }

    @Override
    public boolean isExcluded(String code) {
        return false;
    }

    /**
     * Return active document
     * 
     * Document is not null after WellFormed check
     * @return
     */
	public Document getDocument() {
		return eadLoader.getDocument();		
	}
	
    /**
     * Return root element
     * 
     * Element is not null after WellFormed check
     * @return
     */
	public Element getRootElement() {
		return rootElement;
	}

	public void setRootNode(final Element documentRoot) {
		this.rootElement = documentRoot;
	}
}
