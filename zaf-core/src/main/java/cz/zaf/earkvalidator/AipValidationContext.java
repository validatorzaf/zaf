package cz.zaf.earkvalidator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.Location;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;
import cz.zaf.common.validation.InnerFileValidator;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.schema.mets_1_12_1.Mets;
import jakarta.xml.bind.annotation.XmlType;

public class AipValidationContext extends BaseValidationContext implements RuleEvaluationContext {
	
	/**
	 * AIP loader
	 */
	private AipLoader aipLoader;
	
	private Element metsRootElement = null;
	
	private List<InnerValidatorRequest> innerValidators = new ArrayList<>();
	
	public static class InnerValidatorRequest {
		final InnerFileValidator<AipValidationContext> innerValidator;
		final String innerFilePath;
		
		public InnerValidatorRequest(InnerFileValidator<AipValidationContext> innerValidator, String innerFilePath) {
			this.innerValidator = innerValidator;
			this.innerFilePath = innerFilePath;
		}

		public InnerFileValidator<AipValidationContext> getInnerValidator() {
			return innerValidator;
		}

		public String getInnerFilePath() {
			return innerFilePath;
		}
		
	};

	public AipValidationContext(final AipLoader aipLoader, 
			final List<String> excludeChecks) {
		super(excludeChecks);
		this.aipLoader = aipLoader;
	}

	@Override
	public ValidationResult getValidationResult() {
		return aipLoader.getResult();
	}

	public AipLoader getLoader() {
		return aipLoader;
	}

	public Element getMetsRootElement() {
		return metsRootElement;
	}
	public void setMetsRootElement(Element metsRootElement) {
		this.metsRootElement = metsRootElement;
	}

	public Mets getMets() {
		return aipLoader.getMets();
	}

	public String formatMetsPosition(Object object) {
		Location loc = aipLoader.getMetsLocation(object);
		StringBuilder sb = new StringBuilder(); 
		if(loc!=null) {
			if(loc.getLineNumber()>0) {
				sb.append("Řádek ").append(loc.getLineNumber());
			}
			if(loc.getColumnNumber()>0) {
				sb.append(":").append(loc.getColumnNumber());
			}			
		}

		// vypis elementu
		boolean elementFound = false;
		Class<?> clazz = object.getClass();
		if(clazz.isAnnotationPresent(XmlType.class)) {			
			XmlType xmlType = clazz.getAnnotation(XmlType.class);
			String name = xmlType.name();
			if(StringUtils.isNotBlank(name)) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append("element: <").append(name).append(">");
				elementFound = true;
			}
		}
		if(!elementFound) {
			if(sb.length()>0) {
				sb.append(", ");
			}			
			sb.append("objekt: ");
			sb.append(object.getClass().getSimpleName());
		}
		return sb.toString();
	}

    /**
     * Allow to add next validator
     * @param innerFileValidator
     * @param innerFilePath
     */
	public void addInnerFileValidation(InnerFileValidator<AipValidationContext> innerFileValidator, String innerFilePath) {
		innerValidators.add(new InnerValidatorRequest(innerFileValidator, innerFilePath));
	}

	/**
	 * @return the innerValidators
	 */
	public List<InnerValidatorRequest > getInnerValidators() {
		return innerValidators;
	}
}
