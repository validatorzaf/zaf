package cz.zaf.common.xml;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * Load and cache Schema as resource
 */
public class SchemaResourceLoader {
	static Map<String, Schema> schemaCache = new HashMap<>();
	
	// schema factory can be used only SingleThreaded!!!
	// can be used only from loadSchema method
	static SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	
	/**
	 * Function load schema and return it
	 * @param resource
	 * @return
	 */
	private synchronized static Schema loadSchema(String resource) {
		URL schemaFile = SchemaResourceLoader.class.getResource(resource);
		if(schemaFile==null) {
			throw new IllegalStateException("Cannot resource: "+resource);
		}        
		try {
			return schemaFactory.newSchema(schemaFile);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot load schema: "+resource, e);
		}
	}

	/**
	 * Return schema
	 * 
	 * If schema is not loaded try to load shared resource
	 * @param resource
	 * @return
	 * @throws Throw IllegalStateException if cannot load schema
	 */
	public static Schema get(String resource) {
		return schemaCache.computeIfAbsent(resource, SchemaResourceLoader::loadSchema);
	}

	/**
	 * Return combined schema
	 * @param params
	 * @return
	 */
	public static Schema getCombined(String...params) {
		// aggregate all schemas
		StringBuilder sb = new StringBuilder();
		for(var param: params) {
			if(sb.length()>0) {
				sb.append(":");
			}
			sb.append(param);
		}
		String key = sb.toString();
		return schemaCache.computeIfAbsent(key, k -> {
			Source schemas [] = new Source[params.length]; 			
			
			try {
				int pos = 0;
				for (var resource : params) {
					URL schemaFile = SchemaResourceLoader.class.getResource(resource);
					Source source = new StreamSource(schemaFile.openStream());
					schemas[pos] = source;
					pos++;
				}
				return schemaFactory.newSchema(schemas);
			} catch(Exception e) {
				throw new IllegalStateException("Cannot load schemas", e);
			}
		});
	}
	
}
