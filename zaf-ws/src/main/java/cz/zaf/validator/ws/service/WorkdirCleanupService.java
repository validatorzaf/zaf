package cz.zaf.validator.ws.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Periodically removes old requests from the working folder.
 *
 * Requests are stored in subfolders yyyy/MM/dd/requestId. Whole day folders
 * older than configured retention are removed. Other content of the working
 * folder is not touched.
 */
@Service
public class WorkdirCleanupService {

	private static final Logger log = LoggerFactory.getLogger(WorkdirCleanupService.class);

	private static final Pattern YEAR_PATTERN = Pattern.compile("\\d{4}");
	private static final Pattern MONTH_DAY_PATTERN = Pattern.compile("\\d{2}");

	/**
	 * Number of days to keep received requests, 0 = keep forever
	 */
	@Value("${zaf.workdir.retention-days:0}")
	private int retentionDays;

	private final ValidationService validationService;

	public WorkdirCleanupService(final ValidationService validationService) {
		this.validationService = validationService;
	}

	@Scheduled(cron = "${zaf.workdir.cleanup-cron:0 0 3 * * *}")
	public void scheduledCleanup() {
		if (retentionDays <= 0) {
			return;
		}
		cleanup(LocalDate.now().minusDays(retentionDays));
	}

	/**
	 * Remove requests received before given date
	 * @param keepFrom first day which is kept
	 */
	public void cleanup(LocalDate keepFrom) {
		Path root = validationService.getWorkdirRoot();
		log.info("Removing requests received before {}, working folder: {}", keepFrom, root);

		int removedJobs = validationService.removeJobsReceivedBefore(keepFrom);
		if (removedJobs > 0) {
			log.debug("Removed {} finished jobs", removedJobs);
		}

		if (!Files.isDirectory(root)) {
			return;
		}
		YearMonth keepFromMonth = YearMonth.from(keepFrom);
		for (Path yearDir : listSubdirs(root, YEAR_PATTERN)) {
			int year = Integer.parseInt(yearDir.getFileName().toString());
			for (Path monthDir : listSubdirs(yearDir, MONTH_DAY_PATTERN)) {
				int month = Integer.parseInt(monthDir.getFileName().toString());
				for (Path dayDir : listSubdirs(monthDir, MONTH_DAY_PATTERN)) {
					LocalDate date;
					try {
						date = LocalDate.of(year, month, Integer.parseInt(dayDir.getFileName().toString()));
					} catch (DateTimeException e) {
						// not a request folder
						continue;
					}
					if (date.isBefore(keepFrom)) {
						delete(dayDir);
					}
				}
				// remove only empty folders of the past months (current month might be in use)
				if (isPastMonth(year, month, keepFromMonth)) {
					deleteIfEmpty(monthDir);
				}
			}
			if (year < keepFrom.getYear()) {
				deleteIfEmpty(yearDir);
			}
		}
	}

	private static boolean isPastMonth(int year, int month, YearMonth keepFromMonth) {
		try {
			return YearMonth.of(year, month).isBefore(keepFromMonth);
		} catch (DateTimeException e) {
			return false;
		}
	}

	private List<Path> listSubdirs(Path dir, Pattern namePattern) {
		try (Stream<Path> stream = Files.list(dir)) {
			return stream.filter(p -> Files.isDirectory(p) && namePattern.matcher(p.getFileName().toString()).matches())
					.sorted()
					.toList();
		} catch (IOException e) {
			log.error("Failed to read folder: {}", dir, e);
			return List.of();
		}
	}

	private void delete(Path dir) {
		log.info("Removing old requests: {}", dir);
		try {
			FileUtils.deleteDirectory(dir.toFile());
		} catch (IOException e) {
			log.error("Failed to remove folder: {}", dir, e);
		}
	}

	private void deleteIfEmpty(Path dir) {
		try (Stream<Path> stream = Files.list(dir)) {
			if (stream.findAny().isEmpty()) {
				Files.delete(dir);
			}
		} catch (IOException e) {
			log.error("Failed to remove folder: {}", dir, e);
		}
	}
}
