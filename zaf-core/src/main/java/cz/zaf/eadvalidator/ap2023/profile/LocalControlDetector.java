package cz.zaf.eadvalidator.ap2023.profile;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Detects values declared in &lt;localcontrol&gt; elements directly from a DOM tree.
 *
 * Reads root -&gt; control -&gt; localcontrol[@localtype=...] -&gt; term -&gt; @identifier
 * and maps the identifier to a constant of the requested enum. The enum constant
 * names have to match the identifiers used in the EAD profile
 * ({@link ProfileRevision}, {@link DescriptionRules}, {@link FindingAidType}).
 *
 * JAXB is not available in early validation layers (namespace, schema), so DOM
 * traversal is used. The structural validation of the same elements is performed
 * separately by the obs rules (obs23, obs24/obs24a, obs25) - the detector is
 * deliberately tolerant and returns null whenever the value cannot be determined.
 */
public final class LocalControlDetector {

	private LocalControlDetector() {
	}

	/**
	 * Detect the value declared in &lt;localcontrol&gt; with the given localtype.
	 *
	 * @param eadRoot   root &lt;ead&gt; element (may be null)
	 * @param localtype value of the localtype attribute to look for
	 * @param enumType  enum the identifier is mapped to
	 * @return matching enum constant, or null if the element is missing or the
	 *         identifier does not match any constant
	 */
	public static <E extends Enum<E>> E detect(final Element eadRoot, final String localtype,
			final Class<E> enumType) {
		String identifier = findIdentifier(eadRoot, localtype);
		if (identifier == null) {
			return null;
		}
		for (E value : enumType.getEnumConstants()) {
			if (value.name().equals(identifier)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Return the identifier of the first &lt;localcontrol&gt; with the given localtype.
	 *
	 * Duplicate elements are not reported here, they are detected by the obs rules.
	 */
	private static String findIdentifier(final Element eadRoot, final String localtype) {
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
				if (localtype.equals(element.getAttribute("localtype"))) {
					Element term = findChildElement(element, "term");
					if (term != null) {
						return term.getAttribute("identifier");
					}
				}
			}
		}
		return null;
	}

	private static Element findChildElement(final Element parent, final String localName) {
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
