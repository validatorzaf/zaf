package cz.zaf.common.cz;

import java.time.LocalDateTime;

public enum UnitdateFormatType {
	C("C"), Y("Y"), YM("YM"), D("D"), DT("DT");
	
	private final String value;
	
	UnitdateFormatType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public boolean validateFrom(LocalDateTime ldt) {
		switch(this) {
		case C:
			// Check beginning of century
			if((ldt.getYear()%100)!=1) {
				return false;
			}
		case Y:
			if(ldt.getMonthValue()!=1) {
				return false;
			}
		case YM:
			// has to start at first day of month
			if(ldt.getDayOfMonth()!=1) {
				return false;
			}
		case D:
			if(ldt.getHour()!=0||
				ldt.getMinute()!=0||
				ldt.getSecond()!=0) {
				return false;
			}
			break;
		case DT:
			// can has any value
		default:
		}
		return true;
	}
	
	public boolean validateTo(LocalDateTime ldt) {
		switch(this) {
		case C:
			// Check beginning of century
			if((ldt.getYear()%100)!=0) {
				return false;
			}
		case Y:
			if(ldt.getMonthValue()!=12) {
				return false;
			}
		case YM:
			var icremented = ldt.plusDays(1);
			// has to start at first day of month
			if(icremented.getDayOfMonth()!=1) {
				return false;
			}
		case D:
			if(ldt.getHour()!=23||
				ldt.getMinute()!=59||
				ldt.getSecond()!=59) {
				return false;
			}
			break;
		case DT:
			// can has any value
		default:
		}
		return true;
	}
}