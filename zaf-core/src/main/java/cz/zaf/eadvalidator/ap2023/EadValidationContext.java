package cz.zaf.eadvalidator.ap2023;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.Location;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.schema.ead3.Ead;
import jakarta.xml.bind.annotation.XmlType;

public class EadValidationContext implements RuleEvaluationContext, ValidationLayerContext {

    private EadLoader eadLoader;
	private Element rootElement;
	private boolean useEadPrefix = true;
	private Set<String> excludeChecks = null;

    EadValidationContext(final EadLoader eadLoader, final List<String> excludeChecks) {
        this.eadLoader = eadLoader;
        if(excludeChecks!=null) {
        	this.excludeChecks = excludeChecks.stream().collect(Collectors.toSet()); 
        }
    }

    public EadLoader getLoader() {
        return eadLoader;
    }

    @Override
    public ValidationResult getValidationResult() {
        return eadLoader.getResult();
    }

    @Override
    public boolean isExcluded(String code) {
    	return (excludeChecks!=null)?
    		excludeChecks.contains(code):false;
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

	public Ead getEad() {
		return eadLoader.getEad();
	}

	public String formatEadPosition(Object object) {
		Location loc = eadLoader.getEadLocation(object);
		StringBuilder sb = new StringBuilder(); 
		if(loc!=null) {
			if(loc.getLineNumber()>0) {
				sb.append("Řádek ").append(loc.getLineNumber());
			}
			if(loc.getColumnNumber()>0) {
				sb.append(":").append(loc.getColumnNumber());
			}
			sb.append(", ");
		}
		
		Class<?> clazz = object.getClass();
		if(clazz.isAnnotationPresent(XmlType.class)) {
			XmlType xmlType = clazz.getAnnotation(XmlType.class);
			String name = xmlType.name();
			sb.append("element: <");
			if(useEadPrefix) {
				sb.append("ead:");
			}
			sb.append(name).append(">");
		} else {
			sb.append("objekt: ");
			sb.append(object.getClass().getSimpleName());
		}
		return sb.toString();
	}	
}
