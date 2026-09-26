package cz.zaf.validator.ws.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Usage protocol - one CSV line per validation request.
 *
 * Records are appended to monthly files usage-yyyy-MM.csv. Protocol contains
 * only technical data (no file names or client addresses).
 */
@Service
public class UsageLogService {

	private static final Logger log = LoggerFactory.getLogger(UsageLogService.class);

	private static final String SEPARATOR = ";";

	static final List<String> COLUMNS = List.of("timestamp", "requestId", "channel", "fileExtension",
			"fileSize", "batch", "requestedType", "requestedProfile", "validationType", "validationProfile",
			"result", "packages", "invalidPackages", "errors", "waitMs", "processingMs", "appVersion",
			"errorMessage");

	private static final int MAX_MESSAGE_LENGTH = 300;

	@Value("${zaf.usage.enabled:true}")
	private boolean enabled;

	/**
	 * Folder for usage files, default: <workdir>/usage
	 */
	@Value("${zaf.usage.path:}")
	private String usagePath;

	@Value("${zaf.workdir.path:}")
	private String workingFolder;

	public enum Result {
		/**
		 * All packages are valid
		 */
		VALID,
		/**
		 * Validation finished, some package is not valid
		 */
		INVALID,
		/**
		 * Validation failed, no result
		 */
		FAILED
	}

	/**
	 * One usage record
	 */
	public static class Record {
		OffsetDateTime received;
		String requestId;
		String channel;
		String fileName;
		long fileSize;
		boolean batch;
		String requestedType;
		String requestedProfile;
		String validationType;
		String validationProfile;
		Result result;
		int packages;
		int invalidPackages;
		int errors;
		long waitMs;
		long processingMs;
		String appVersion;
		String errorMessage;
	}

	public Path getUsageFolder() {
		if (StringUtils.isNotBlank(usagePath)) {
			return Paths.get(usagePath).toAbsolutePath();
		}
		return Paths.get(workingFolder != null ? workingFolder : "").toAbsolutePath().resolve("usage");
	}

	/**
	 * Append record to the usage file
	 */
	public void write(Record rec) {
		if (!enabled) {
			return;
		}
		Path folder = getUsageFolder();
		Path file = folder.resolve("usage-" + rec.received.format(DateTimeFormatter.ofPattern("yyyy-MM")) + ".csv");
		String line = formatLine(rec);
		try {
			synchronized (this) {
				Files.createDirectories(folder);
				if (!Files.exists(file)) {
					Files.writeString(file, String.join(SEPARATOR, COLUMNS) + "\n", StandardCharsets.UTF_8,
							StandardOpenOption.CREATE_NEW);
				}
				Files.writeString(file, line + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			// usage protocol must not break validation
			log.error("Failed to write usage record, file: {}", file, e);
		}
	}

	static String formatLine(Record rec) {
		return String.join(SEPARATOR,
				rec.received.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
				escape(rec.requestId),
				escape(rec.channel),
				escape(getExtension(rec.fileName)),
				Long.toString(rec.fileSize),
				Boolean.toString(rec.batch),
				escape(rec.requestedType),
				escape(rec.requestedProfile),
				escape(rec.validationType),
				escape(rec.validationProfile),
				rec.result != null ? rec.result.name() : "",
				Integer.toString(rec.packages),
				Integer.toString(rec.invalidPackages),
				Integer.toString(rec.errors),
				Long.toString(rec.waitMs),
				Long.toString(rec.processingMs),
				escape(rec.appVersion),
				escape(StringUtils.abbreviate(rec.errorMessage, MAX_MESSAGE_LENGTH)));
	}

	static String getExtension(String fileName) {
		if (fileName == null) {
			return "";
		}
		int pos = fileName.lastIndexOf('.');
		if (pos < 0 || pos == fileName.length() - 1) {
			return "";
		}
		return fileName.substring(pos + 1).toLowerCase();
	}

	static String escape(String value) {
		if (value == null) {
			return "";
		}
		// keep one record per line
		value = value.replaceAll("[\\r\\n\\t]+", " ");
		if (value.contains(SEPARATOR) || value.contains("\"")) {
			return "\"" + value.replace("\"", "\"\"") + "\"";
		}
		return value;
	}
}
