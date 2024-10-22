package cz.zaf.premisvalidator.layers.obs.obs00_09;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schema.premis3.EventComplexType;
import cz.zaf.schema.premis3.PremisComplexType;

public class Rule09 extends PremisRule {

	public static final String CODE = "obs09";
	public static final String RULE_TEXT = "Datace je uvedena správně.";
	public static final String RULE_ERROR = "Chybný způsob zápisu datace.";
	public static final String RULE_SOURCE = "CZDAX-PMS0301, CZDAX-PMS0302, CZDAX-PMS0304";


	public Rule09() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	public void evalImpl() {
		PremisComplexType premis = ctx.getLoader().getRootObj();
		premis.getEvent().forEach(evnt -> {
			String eventDateTime = evnt.getEventDateTime();
			checkDateFormat(evnt, eventDateTime);
		});
	}

	private void checkDateFormat(EventComplexType evnt, String eventDateTime) {
		if(StringUtils.isBlank(eventDateTime)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezena data", ctx.formatPosition(evnt));
		}
		// CZDAX-PMS0304 check special value NA
		if(EarkCz.EVENT_DATETIME_NA.equals(eventDateTime)) {
			return;
		}
		// check if interval
		String[] parts = eventDateTime.split("/");
		if(parts.length < 1 || parts.length > 2) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nesprávný interval", ctx.formatPosition(evnt));
		}
		if(parts.length == 1) {
			convertSingleDate(evnt, parts[0]);
		} else {
			LocalDateTime from = convertSingleDate(evnt, parts[0]);
			LocalDateTime to = convertSingleDate(evnt, parts[1]);
			if(from.isAfter(to)) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nesprávný interval, od je po do.", ctx.formatPosition(evnt));
			}
			if(from.isEqual(to)) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nesprávný interval, od a do jsou shodné hodnoty.", ctx.formatPosition(evnt));
			}
		}
	}

	private LocalDateTime convertSingleDate(EventComplexType evnt, String dateTime) {
		// CZDAX-PMS0301
		// parse ISO 8601 date/time format to LocalDateTime with timezone
		try {
			if(dateTime.contains("T")) {
				if(dateTime.contains("+") || dateTime.contains("-")) {
			        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, formatter);
			        return zonedDateTime.toLocalDateTime();			        
				}
				return LocalDateTime.parse(dateTime);
			} else {
				return LocalDateTime.parse(dateTime + "T00:00:00");
			}
		} catch (Exception e) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nesprávný formát data: " + dateTime + ".", ctx.formatPosition(evnt), e);
		}
	}
}
