package cz.zaf.earkvalidator;

import java.nio.file.Path;
import java.util.List;

import javax.xml.stream.Location;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.result.ValidationResult;
import cz.zaf.common.validation.BaseValidationContext;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.schema.mets_1_12_1.Mets;
import jakarta.xml.bind.annotation.XmlType;

public class AipValidationContext extends BaseValidationContext implements RuleEvaluationContext {
	
	/**
	 * AIP loader
	 */
	private AipLoader aipLoader;
	
	private Element metsRootElement = null;

	/**
	 * Active file for checks
	 */
	private Path activeFile;
	
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
	 * Return active path for validation
	 * @return
	 */
	public Path getActiveFile() {
		return activeFile;
	}
	
	void setActiveFile(Path activeFile) {
		this.activeFile = activeFile;
	}
}
