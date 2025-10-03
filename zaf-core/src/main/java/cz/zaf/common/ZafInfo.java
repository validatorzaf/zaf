package cz.zaf.common;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZafInfo {
	
	private static Logger logger = LoggerFactory.getLogger(ZafInfo.class);
	
	/**
	 * Name of the application
	 */
	public static final String ZAF_NAME = "ZAF";
		
	
	/**
	 * Version of the conversion utility
	 */
	private static String appVersion = "_UNKNOWN_VERSION_";
	static {
		try (InputStream input = Properties.class.getClassLoader()
				.getResourceAsStream("META-INF/maven/cz.zaf/zaf-core/pom.properties")) {
			var prop = new java.util.Properties();
			if (input == null) {
				logger.error("File not found: META-INF/maven/cz.zaf/zaf-core/pom.properties");
			}
			prop.load(input);
			appVersion = prop.getProperty("version");
		} catch (IOException ex) {
			logger.error("Failed to read pom.properties", ex);
		}
	}
	
	public static String getAppName() {
		return ZAF_NAME;
	}
	
	public static String getAppVersion() {
		return appVersion;
	}

}
