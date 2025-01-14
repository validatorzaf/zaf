package cz.zaf.premisvalidator.profile;

import cz.zaf.common.validation.ValidationSubprofile;

public enum PremisProfile implements ValidationSubprofile {
	PACKAGE_INFO("Balíček"),
	PACKAGE_INFO_CHANGE("Změnový balíček"),
	METADATA("Metadata");
	
	private final String name;

	private PremisProfile(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
