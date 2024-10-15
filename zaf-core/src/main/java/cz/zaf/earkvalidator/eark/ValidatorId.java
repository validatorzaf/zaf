package cz.zaf.earkvalidator.eark;

import java.util.regex.Pattern;

public class ValidatorId {
	static private  Pattern ID_PATTERN = Pattern.compile("^uuid-[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	/**
	 * Format of ID, expected format: uuid-<UUID>
	 * @param id
	 * @return
	 */
	public static boolean checkFormatId(String id) {
		if(!ID_PATTERN.matcher(id).matches()) {
			return false;
		}
		return true;
	}

}
