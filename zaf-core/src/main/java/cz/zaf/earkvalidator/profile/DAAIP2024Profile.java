package cz.zaf.earkvalidator.profile;

import cz.zaf.common.validation.ValidationSubprofile;

public enum DAAIP2024Profile implements ValidationSubprofile {
	AIP {

		@Override
		public String getName() {
			return "AIP";
		}
		
	},
	DIP_METADATA {
		
		@Override
		public String getName() {
			return "metadatový DIP";
		}		
	},
	DIP_CONTENT {
		
		@Override
		public String getName() {
			return "úplný DIP";
		}		
	};
}
