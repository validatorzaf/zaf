package cz.zaf.eadvalidator.ap2023;

import java.util.List;

import javax.xml.stream.Location;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.eadvalidator.ap2023.profile.DescriptionRules;
import cz.zaf.eadvalidator.ap2023.profile.FindingAidType;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.Ead;
import jakarta.xml.bind.annotation.XmlType;

public class EadValidationContext
	extends BaseValidationContext
	implements RuleEvaluationContext {

    private EadLoader eadLoader;
	private Element rootElement;
	private boolean useEadPrefix = true;
	
	/**
	 * Aktualni verze profilu
	 * 
	 * Vyplňuje se při zpracování pravidla ob25
	 */
	private ProfileRevision profileRevision;
	private DescriptionRules descriptionRules;
	private FindingAidType findingAidType;
	
	/**
	 * Prefix pro EAD
	 * 
	 * Prefix is set by active profile
	 */
	private String prefixNsEad;

    public EadValidationContext(final EadLoader eadLoader, final List<String> excludeChecks, final String prefixNsEad) {
    	super(excludeChecks);
        this.eadLoader = eadLoader;
        this.prefixNsEad = prefixNsEad;
    }

    public EadLoader getLoader() {
        return eadLoader;
    }

    @Override
    public ValidationResult getValidationResult() {
        return eadLoader.getResult();
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

	public EadLevelIterator getEadLevelIterator() {		
		return new EadLevelIterator(eadLoader.getEad());
	}

	public void setProfileRevision(final ProfileRevision profileRevision) {
		this.profileRevision = profileRevision;		
	}
	
	ProfileRevision getProfileRevision() {
		return profileRevision;
	}

	public void setDescriptionRules(final DescriptionRules descRules) {
		this.descriptionRules = descRules;		
	}
	
	public DescriptionRules getDescriptionRules() {
		return descriptionRules;
	}

	public FindingAidType getFindingAidType() {
		return findingAidType;		
	}

	public void setFindingAidType(final FindingAidType findingAidType) {
		this.findingAidType = findingAidType;		
	}

	public String getPrefixNsEad() {
		return this.prefixNsEad;
	}
	
	/**
	 * Return EAD element name with prefix (if defined)
	 * @param elementName
	 * @return
	 */
	public String getEadElementName(String elementName) {
		if(prefixNsEad==null || prefixNsEad.length()==0) {
			return elementName;
		}
		return prefixNsEad+":"+elementName;
	}
}
