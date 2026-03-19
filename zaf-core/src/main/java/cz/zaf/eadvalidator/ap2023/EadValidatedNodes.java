package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.Location;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.common.xml.PositionalXMLReader;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Tracks which DOM nodes have been validated by rules.
 *
 * Provides JAXB-to-DOM mapping using position (line:column) correlation.
 * Both JAXB (via StAX beforeUnmarshal) and DOM (via SAX startElement)
 * store element start positions, so they can be matched.
 */
public class EadValidatedNodes {

	private final Set<Node> validatedNodes = new HashSet<>();

	/**
	 * Map from line number to DOM elements starting on that line.
	 * Built lazily on first use.
	 */
	private Map<Integer, List<Element>> lineToDomMap;

	private final EadLoader eadLoader;

	public EadValidatedNodes(EadLoader eadLoader) {
		this.eadLoader = eadLoader;
	}

	// ---- JAXB-based marking (resolves JAXB object to DOM element via position) ----

	/**
	 * Mark a JAXB object's corresponding DOM element and its entire subtree
	 * (all descendant elements, their attributes, and text nodes) as validated.
	 *
	 * @param jaxbObject JAXB object to mark
	 * @return the corresponding DOM element
	 * @throws IllegalStateException if DOM element cannot be found for the JAXB object
	 */
	public Element markValidatedTreeJaxb(Object jaxbObject) {
		Element domElement = requireDomElement(jaxbObject);
		markValidatedTreeDom(domElement);
		return domElement;
	}

	/**
	 * Mark a JAXB object's corresponding DOM element and its attributes as validated,
	 * but NOT its child elements. Useful when a rule validates the element itself
	 * but child content is validated by other rules.
	 *
	 * @param jaxbObject JAXB object to mark
	 * @return the corresponding DOM element
	 * @throws IllegalStateException if DOM element cannot be found for the JAXB object
	 */
	public Element markValidatedElementJaxb(Object jaxbObject) {
		Element domElement = requireDomElement(jaxbObject);
		markValidatedElementDom(domElement);
		return domElement;
	}

	/**
	 * Mark a specific attribute on a JAXB object's corresponding DOM element as validated.
	 * In JAXB, attributes are plain Java values (e.g. persname.getLocaltype()),
	 * so there is no JAXB object for the attribute itself — we need the parent
	 * JAXB object and the attribute name.
	 *
	 * @param jaxbObject JAXB object owning the attribute
	 * @param attributeName local name of the attribute (e.g. "localtype", "level")
	 * @return the DOM Attr node
	 * @throws IllegalStateException if DOM element or attribute cannot be found
	 */
	public Attr markValidatedAttributeJaxb(Object jaxbObject, String attributeName) {
		Element domElement = requireDomElement(jaxbObject);
		validatedNodes.add(domElement);
		return requireAttributeDom(domElement, attributeName);
	}

	/**
	 * Mark only a specific attribute on a JAXB object's DOM element as validated,
	 * without marking the element itself.
	 *
	 * @param jaxbObject JAXB object owning the attribute
	 * @param attributeName local name of the attribute
	 * @return the DOM Attr node
	 * @throws IllegalStateException if DOM element or attribute cannot be found
	 */
	public Attr markValidatedAttributeOnlyJaxb(Object jaxbObject, String attributeName) {
		Element domElement = requireDomElement(jaxbObject);
		return requireAttributeDom(domElement, attributeName);
	}

	/**
	 * Mark the text/CDATA content of a JAXB object's corresponding DOM element
	 * as validated. Does not mark the element itself or its attributes.
	 *
	 * Use when a rule validates the text value (e.g. recordid.getContent())
	 * but not the element's attributes.
	 *
	 * @param jaxbObject JAXB object whose text content to mark
	 * @throws IllegalStateException if DOM element cannot be found
	 */
	public void markValidatedContentJaxb(Object jaxbObject) {
		Element domElement = requireDomElement(jaxbObject);
		markValidatedContentDom(domElement);
	}

	// ---- DOM-based marking ----

	/**
	 * Mark a DOM element and its entire subtree (all descendant elements,
	 * their attributes, and text nodes) as validated.
	 */
	public void markValidatedTreeDom(Node node) {
		validatedNodes.add(node);

		// Mark attributes
		if (node instanceof Element element) {
			NamedNodeMap attrs = element.getAttributes();
			if (attrs != null) {
				for (int i = 0; i < attrs.getLength(); i++) {
					validatedNodes.add(attrs.item(i));
				}
			}
		}

		// Mark children recursively
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			markValidatedTreeDom(children.item(i));
		}
	}

	/**
	 * Mark a DOM element as validated (only the element node itself).
	 * Attributes and child nodes are NOT marked — they must be marked
	 * explicitly by the rule that validates them.
	 */
	public void markValidatedElementDom(Element element) {
		validatedNodes.add(element);
	}

	/**
	 * Mark the text/CDATA child nodes of a DOM element as validated.
	 * Does not mark the element itself or its attributes.
	 */
	public void markValidatedContentDom(Element element) {
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.TEXT_NODE
					|| child.getNodeType() == Node.CDATA_SECTION_NODE) {
				validatedNodes.add(child);
			}
		}
	}

	/**
	 * Mark a specific attribute and its owning element as validated.
	 *
	 * @param element DOM element owning the attribute
	 * @param attributeName local name of the attribute
	 * @return the DOM Attr node
	 * @throws IllegalStateException if the attribute does not exist on the element
	 */
	public Attr markValidatedAttributeDom(Element element, String attributeName) {
		validatedNodes.add(element);
		return requireAttributeDom(element, attributeName);
	}

	/**
	 * Mark only a specific attribute on a DOM element as validated,
	 * without marking the element itself.
	 */
	public Attr markValidatedAttributeOnlyDom(Element element, String attributeName) {
		return requireAttributeDom(element, attributeName);
	}

	/**
	 * Mark a single DOM node as validated (without descendants or attributes).
	 */
	public void markValidatedNode(Node node) {
		validatedNodes.add(node);
	}

	// ---- Resolution helpers (throw on missing) ----

	/**
	 * Find the DOM element for a JAXB object, throwing if not found.
	 */
	public Element requireDomElement(Object jaxbObject) {
		Element domElement = findDomElement(jaxbObject);
		if (domElement == null) {
			throw new IllegalStateException(
					"Cannot find DOM element for JAXB object: " + jaxbObject.getClass().getSimpleName()
							+ ". Position mapping failed.");
		}
		return domElement;
	}

	/**
	 * Find a DOM attribute by name, mark it as validated, and throw if not found.
	 */
	private Attr requireAttributeDom(Element element, String attributeName) {
		Attr attr = element.getAttributeNode(attributeName);
		if (attr == null) {
			throw new IllegalStateException(
					"Attribute '" + attributeName + "' not found on element <"
							+ element.getNodeName() + ">.");
		}
		validatedNodes.add(attr);
		return attr;
	}

	// ---- Query methods ----

	/**
	 * Check if a node has been validated.
	 */
	public boolean isValidated(Node node) {
		return validatedNodes.contains(node);
	}

	/**
	 * Find the DOM element corresponding to a JAXB object using position
	 * and element name matching.
	 *
	 * Uses line number from both JAXB (StAX) and DOM (SAX) positions,
	 * and verifies the element local name matches the JAXB @XmlType name
	 * to disambiguate when multiple elements share the same line.
	 */
	public Element findDomElement(Object jaxbObject) {
		Location loc = eadLoader.getEadLocation(jaxbObject);
		if (loc == null) {
			return null;
		}

		ensureLineMap();

		String expectedName = getExpectedElementName(jaxbObject);
		int line = loc.getLineNumber();

		List<Element> candidates = lineToDomMap.get(line);
		if (candidates == null || candidates.isEmpty()) {
			return null;
		}

		// If we know the expected name, filter by it
		if (expectedName != null) {
			for (Element candidate : candidates) {
				if (expectedName.equals(candidate.getLocalName())) {
					return candidate;
				}
			}
		}

		// Fallback: return first element on the line
		return candidates.get(0);
	}

	/**
	 * Get the expected DOM element local name from a JAXB object's @XmlType annotation.
	 */
	private String getExpectedElementName(Object jaxbObject) {
		XmlType xmlType = jaxbObject.getClass().getAnnotation(XmlType.class);
		if (xmlType != null) {
			String name = xmlType.name();
			if (name != null && !name.isEmpty() && !"##default".equals(name)) {
				return name;
			}
		}
		return null;
	}

	/**
	 * Find all unvalidated EAD elements in the document.
	 * Only checks elements in the given namespace.
	 * Skips non-EAD elements (e.g. CAM elements inside objectxmlwrap).
	 */
	public List<Node> findUnvalidatedElements(String eadNamespaceUri) {
		List<Node> unvalidated = new ArrayList<>();
		Document doc = eadLoader.getDocument();
		if (doc == null) {
			return unvalidated;
		}
		collectUnvalidatedElements(doc.getDocumentElement(), eadNamespaceUri, unvalidated);
		return unvalidated;
	}

	private void collectUnvalidatedElements(Element element, String eadNamespaceUri,
			List<Node> unvalidated) {
		// Only check EAD namespace elements
		if (!eadNamespaceUri.equals(element.getNamespaceURI())) {
			return;
		}

		if (!isValidated(element)) {
			unvalidated.add(element);
		} else {
			// check all attributes of the element (if itself is validated)
			var attrNodes = element.getAttributes();
			if (attrNodes != null) {
				for (int i = 0; i < attrNodes.getLength(); i++) {
					var attrNode = attrNodes.item(i);
					if (attrNode instanceof org.w3c.dom.Attr attr) {
						if (!isValidated(attr)) {
							unvalidated.add(attrNode);
						}
					}

				}
			}
		}		

		// Check child elements recursively
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element childElement) {
				collectUnvalidatedElements(childElement, eadNamespaceUri, unvalidated);
			}
		}
	}

	// ---- Internal: position map ----

	private void ensureLineMap() {
		if (lineToDomMap != null) {
			return;
		}
		lineToDomMap = new HashMap<>();
		Document doc = eadLoader.getDocument();
		if (doc != null && doc.getDocumentElement() != null) {
			buildLineMap(doc.getDocumentElement());
		}
	}

	private void buildLineMap(Element element) {
		Object lineNumber = element.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME);
		if (lineNumber instanceof Integer line) {
			lineToDomMap.computeIfAbsent(line, k -> new ArrayList<>()).add(element);
		}

		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element childElement) {
				buildLineMap(childElement);
			}
		}
	}
}
