package cz.zaf.premisvalidator;

public class RepresentationInfo {
	
	private String id;
	
	private String name;
	
	private Object object;
	
	public RepresentationInfo(final String id,
			final String name, final Object object) {
		this.id = id;
		this.name = name;
		this.object = object;
	}
	
	public Object getObject() {
		return object;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
