package cz.zaf.eadvalidator.ap2023.profile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.eadvalidator.ap2023.Ap2023Constants;

/**
 * Detects the {@link ProfileRevision} directly from a DOM tree.
 *
 * Reads root -> control -> localcontrol[@localtype="CZ_FINDING_AID_EAD_PROFILE"]
 * -> term -> @identifier and maps the identifier to a ProfileRevision enum value.
 *
 * JAXB is not available in early validation layers (namespace, schema), so DOM
 * traversal is used. The structural validation of the same element is performed
 * separately by obs/Rule25.
 */
public final class ProfileRevisionDetector {

	private ProfileRevisionDetector() {
	}

	/**
	 * Detect the profile revision from the given EAD root element.
	 *
	 * @param eadRoot root &lt;ead&gt; element (may be null)
	 * @return matching ProfileRevision, or null if the element is missing or
	 *         the identifier does not match any known revision
	 */
	public static ProfileRevision detect(Element eadRoot) {
		String identifier = findProfileIdentifier(eadRoot);
		if (identifier == null) {
			return null;
		}
		for (ProfileRevision value : ProfileRevision.values()) {
			if (value.name().equals(identifier)) {
				return value;
			}
		}
		return null;
	}

	private static String findProfileIdentifier(Element eadRoot) {
		if (eadRoot == null) {
			return null;
		}
		Element control = findChildElement(eadRoot, "control");
		if (control == null) {
			return null;
		}
		NodeList controlChildren = control.getChildNodes();
		for (int i = 0; i < controlChildren.getLength(); i++) {
			Node node = controlChildren.item(i);
			if (node instanceof Element element && "localcontrol".equals(element.getLocalName())) {
				String localtype = element.getAttribute("localtype");
				if (Ap2023Constants.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(localtype)) {
					Element term = findChildElement(element, "term");
					if (term != null) {
						return term.getAttribute("identifier");
					}
				}
			}
		}
		return null;
	}

	private static Element findChildElement(Element parent, String localName) {
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node instanceof Element element && localName.equals(element.getLocalName())) {
				return element;
			}
		}
		return null;
	}
}
