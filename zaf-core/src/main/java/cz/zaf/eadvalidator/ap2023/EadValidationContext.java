package cz.zaf.eadvalidator.ap2023;

import java.util.List;

import javax.xml.stream.Location;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.eadvalidator.ap2023.profile.DescriptionRules;
import cz.zaf.eadvalidator.ap2023.profile.FindingAidType;
import cz.zaf.eadvalidator.ap2023.profile.LocalControlDetector;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Ead;
import jakarta.xml.bind.annotation.XmlType;

public class EadValidationContext
	extends BaseValidationContext {

    private EadLoader eadLoader;
	private Element rootElement;
	private boolean useEadPrefix = true;

	/**
	 * Tracks which DOM nodes have been validated by rules.
	 * Initialized lazily on first access.
	 */
	private EadValidatedNodes validatedNodes;
	
	/**
	 * Hodnoty deklarované v elementech &lt;localcontrol&gt;.
	 *
	 * Detekují se líně z DOM při prvním čtení příslušným getterem, jakmile je
	 * k dispozici kořenový element (po vrstvě WellFormed). Detekce je záměrně
	 * nezávislá na pravidlech - pravidlo, které danou hodnotu validuje, nemusí
	 * být v aktivním profilu vůbec obsaženo, může být uživatelem vyloučeno
	 * (excludeChecks) nebo může skončit chybou dříve, než se k hodnotě dostane.
	 * Strukturu a atributy příslušných elementů validují obs/Rule23,
	 * obs/Rule24 (resp. obs/Rule24a) a obs/Rule25.
	 *
	 * Hodnota null je platný stav - znamená, že element chybí nebo obsahuje
	 * neznámý identifikátor. Příznaky *Detected zabraňují opakované detekci
	 * v takovém případě.
	 */
	private ProfileRevision profileRevision;
	private boolean profileRevisionDetected;
	private DescriptionRules descriptionRules;
	private boolean descriptionRulesDetected;
	private FindingAidType findingAidType;
	private boolean findingAidTypeDetected;
	
	// Consider moving to the BaseValidationContext
	private final ValidationSubprofile validationProfile;
	
	/**
	 * Prefix pro EAD
	 * 
	 * Prefix is set by active profile
	 */
	private String prefixNsEad;

    public EadValidationContext(final EadLoader eadLoader, final List<String> excludeChecks, 
    		final String prefixNsEad, final ValidationSubprofile validationProfile) {
    	super(excludeChecks);
        this.eadLoader = eadLoader;
        this.prefixNsEad = prefixNsEad;
        this.validationProfile = validationProfile;
    }
    
    public ValidationSubprofile getValidationProfile() {
    	return validationProfile;
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
		this.profileRevisionDetected = true;
	}

	public ProfileRevision getProfileRevision() {
		if (!profileRevisionDetected && rootElement != null) {
			profileRevision = LocalControlDetector.detect(rootElement,
					Ap2023Constants.LOCALTYPE_FINDING_AID_EAD_PROFILE, ProfileRevision.class);
			profileRevisionDetected = true;
		}
		return profileRevision;
	}

	public void setDescriptionRules(final DescriptionRules descRules) {
		this.descriptionRules = descRules;
		this.descriptionRulesDetected = true;
	}

	public DescriptionRules getDescriptionRules() {
		if (!descriptionRulesDetected && rootElement != null) {
			descriptionRules = LocalControlDetector.detect(rootElement,
					Ap2023Constants.LOCALTYPE_RULES, DescriptionRules.class);
			descriptionRulesDetected = true;
		}
		return descriptionRules;
	}

	public FindingAidType getFindingAidType() {
		if (!findingAidTypeDetected && rootElement != null) {
			findingAidType = LocalControlDetector.detect(rootElement,
					Ap2023Constants.LOCALTYPE_FINDING_AID_TYPE, FindingAidType.class);
			findingAidTypeDetected = true;
		}
		return findingAidType;
	}

	public void setFindingAidType(final FindingAidType findingAidType) {
		this.findingAidType = findingAidType;
		this.findingAidTypeDetected = true;
	}

	public String getPrefixNsEad() {
		return this.prefixNsEad;
	}

	/**
	 * Get the validated nodes tracker. Creates it on first access.
	 */
	public EadValidatedNodes getValidatedNodes() {
		if (validatedNodes == null) {
			validatedNodes = new EadValidatedNodes(eadLoader);
		}
		return validatedNodes;
	}

	// ---- Convenience methods for marking JAXB objects as validated ----

	/**
	 * Mark a JAXB object's entire DOM subtree as validated
	 * (element + all descendants + all attributes + text nodes).
	 *
	 * @param jaxbObject JAXB object to mark
	 * @return the corresponding DOM element, or null if mapping failed
	 */
	public Element markValidatedTree(Object jaxbObject) {
		return getValidatedNodes().markValidatedTreeJaxb(jaxbObject);
	}

	/**
	 * Mark a JAXB object's DOM element as validated (only the element node).
	 * Attributes and child nodes are NOT marked — they must be marked
	 * explicitly by the rule that validates them.
	 *
	 * @param jaxbObject JAXB object to mark
	 * @return the corresponding DOM element
	 * @throws IllegalStateException if DOM element cannot be found
	 */
	public Element markValidatedElement(Object jaxbObject) {
		return getValidatedNodes().markValidatedElementJaxb(jaxbObject);
	}

	/**
	 * Mark a specific attribute and its owning element as validated.
	 * This is the common case — most rules that check an attribute
	 * also implicitly validate the element's presence.
	 *
	 * @param jaxbObject JAXB object owning the attribute
	 * @param attributeName local name of the attribute (e.g. "localtype")
	 * @return the DOM Attr node
	 * @throws IllegalStateException if DOM element or attribute cannot be found
	 */
	public Attr markValidatedAttribute(Object jaxbObject, String attributeName) {
		return getValidatedNodes().markValidatedAttributeJaxb(jaxbObject, attributeName);
	}

	/**
	 * Mark only a specific attribute as validated, without marking the element.
	 * Use when the element is validated by a different rule.
	 *
	 * @param jaxbObject JAXB object owning the attribute
	 * @param attributeName local name of the attribute
	 * @return the DOM Attr node
	 * @throws IllegalStateException if DOM element or attribute cannot be found
	 */
	public Attr markValidatedAttributeOnly(Object jaxbObject, String attributeName) {
		return getValidatedNodes().markValidatedAttributeOnlyJaxb(jaxbObject, attributeName);
	}

	/**
	 * Mark the text content of a JAXB object's DOM element as validated.
	 * Use when a rule validates the text value (e.g. recordid.getContent())
	 * but not the element's attributes.
	 *
	 * @param jaxbObject JAXB object whose text content to mark
	 * @throws IllegalStateException if DOM element cannot be found
	 */
	public void markValidatedContent(Object jaxbObject) {
		getValidatedNodes().markValidatedContentJaxb(jaxbObject);
	}

	// ---- Convenience methods for marking DOM nodes as validated ----

	/**
	 * Mark a DOM element and its entire subtree as validated.
	 */
	public void markValidatedTreeDom(Node node) {
		getValidatedNodes().markValidatedTreeDom(node);
	}

	/**
	 * Mark a DOM element as validated (only the element node, not attributes or children).
	 */
	public void markValidatedElementDom(Element element) {
		getValidatedNodes().markValidatedElementDom(element);
	}
	
	public void markValidatedAttributeDom(Element element, String attrName) {
		getValidatedNodes().markValidatedAttributeOnlyDom(element, attrName);
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

	/**
	 * Return source Element for given JAXB object
	 * @param jaxbObject
	 * @return
	 */
	public Element getDOMElement(Object jaxbObject) {
		return getValidatedNodes().requireDomElement(jaxbObject);
	}
}
