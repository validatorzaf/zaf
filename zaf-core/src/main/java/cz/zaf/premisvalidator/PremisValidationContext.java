package cz.zaf.premisvalidator;

import java.nio.file.Path;

import javax.xml.stream.Location;

import org.apache.commons.lang.StringUtils;

import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.schema.premis3.EventComplexType;
import jakarta.xml.bind.annotation.XmlType;

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

	public String formatPosition(Object object) {
		Location loc = loader.getXmlLocation(object);
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

}
