package cz.zaf.premisvalidator;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.xml.stream.Location;

import org.apache.commons.lang.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.schema.premis3.AgentComplexType;
import cz.zaf.schema.premis3.AgentIdentifierComplexType;
import cz.zaf.schema.premis3.IntellectualEntity;
import cz.zaf.schema.premis3.ObjectComplexType;
import cz.zaf.schema.premis3.ObjectIdentifierComplexType;
import cz.zaf.schema.premis3.PremisComplexType;
import jakarta.xml.bind.annotation.XmlType;

public class PremisValidationContext implements RuleEvaluationContext {
	
	private Path activeFile;
	private PremisLoader loader;
	// private Map<String, RepresentationInfo> representations = new HashMap<>();
	private Function<String, RepresentationInfo> representationReader;
	private Map<String, Map<String, AgentComplexType>> agentsByIdType = new HashMap<>();
	private Map<String, Map<String, IntellectualEntity>> intelEntsByIdType = new HashMap<>();

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

	public RepresentationInfo getRepresentation(String represnentationId) {
		if(representationReader!=null) {
			return representationReader.apply(represnentationId);
		}
		return null;
	}

	public Function<String, RepresentationInfo> getRepresentationReader() {
		return representationReader;
	}

	public void setRepresentationReader(Function<String, RepresentationInfo> representationReader) {
		this.representationReader = representationReader;
	}

	public AgentComplexType getAgentById(String identType, String linkingAgentIdentifierValue) {
		PremisComplexType premis = loader.getRootObj();
		if(premis==null) {
			throw new IllegalStateException("Premis root object not found.");
		}
		Map<String, AgentComplexType> agentsById = agentsByIdType.get(identType);
		if(agentsById==null) {
			agentsById = new HashMap<>();
			agentsByIdType.put(identType, agentsById);
			// load agents
			for(AgentComplexType agent: premis.getAgent()) {
				for(AgentIdentifierComplexType ident: agent.getAgentIdentifier()) {
					if(identType.equals(ident.getAgentIdentifierType().getValue())) {
						AgentComplexType prevValue = agentsById.put(ident.getAgentIdentifierValue(), agent);
						if(prevValue!=null) {
							throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Duplicitní element agent/agentIdentifier.", formatPosition(ident));
						}
					}
				}
			}
		}
		return agentsById.get(linkingAgentIdentifierValue);
	}

	public IntellectualEntity getIntelEntById(String identType, String identValue) {
		PremisComplexType premis = loader.getRootObj();
		if(premis==null) {
			throw new IllegalStateException("Premis root object not found.");
		}
		Map<String, IntellectualEntity> intelEntsById = intelEntsByIdType .get(identType);
		if(intelEntsById==null) {
			intelEntsById = new HashMap<>();
			intelEntsByIdType.put(identType, intelEntsById);
			// load agents
			for(ObjectComplexType obj: premis.getObject()) {
				if(obj instanceof IntellectualEntity) {
					IntellectualEntity intEnt = (IntellectualEntity) obj;
					for(ObjectIdentifierComplexType ident: intEnt.getObjectIdentifier()) {
						if(identType.equals(ident.getObjectIdentifierType().getValue())) {
							IntellectualEntity prevValue = intelEntsById.put(ident.getObjectIdentifierValue(), intEnt);
							if(prevValue!=null) {
								throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Duplicitní element object/objectIdentifier.", formatPosition(ident));
							}
						}
					}
				}
			}
		}
		return intelEntsById.get(identValue);
	}
}
