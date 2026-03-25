package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.ead.EadNS;

public class Rule98 extends EadRule {

    static final public String CODE = "obs98";
    static final public String RULE_TEXT = "Pokud element <unitdatestructured>, <origination> v elementu <did>, <language>, <container>, <altformavail> nebo <relation> má atribut \"altrender\", jeho hodnota je \"inherited\". V nadřazené jednotce popisu musí existovat element se shodnou strukturou jako má element s atributem \"altrender\", tj. se shodnými atributy včetně všech podřízených elementů, jejich atributů a hodnot. Nadřazený element nemusí mít uveden atribut \"altrender\".";
    static final public String RULE_ERROR = "Element <unitdatestructured>, <origination> v elementu <did>, <language>, <container>, <altformavail> nebo <relation> má atribut \"altrender\" neobsahující hodnotu \"inherited\". Nebo v nadřazené jednotce popisu neexistuje element se shodnou strukturou jako má element s atributem \"altrender\", tj. se shodnými atributy včetně všech podřízených elementů, jejich atributů a hodnot.";
    static final public String RULE_SOURCE = "Část 5.2 profilu EAD3 MV ČR";

    private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";

    /**
     * Element types that support altrender="inherited" inheritance checking.
     */
    private static final Set<String> INHERITABLE_ELEMENTS = Set.of(
            "unitdatestructured", "origination", "language", "container", "altformavail", "relation");

    public Rule98() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Document doc = ctx.getDocument();
        var ead = ctx.getEad();
        
        List<Element> rootInheritedElements = findInheritableElementsWithAltrender(doc.getDocumentElement());
        if(CollectionUtils.isNotEmpty(rootInheritedElements)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Na kořeni archivního popisu nemohou být elementy s nastaveným atributem altrender.",
                    formatDomPosition(rootInheritedElements.get(0)));        	
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Element cDom = ctx.getDOMElement(c);

            // Find elements with altrender attribute in this C (not descending into nested C elements)
            List<Element> inheritedElements = findInheritableElementsWithAltrender(cDom);
            if(CollectionUtils.isEmpty(inheritedElements)) {
            	return;
            }

            Element parentDom;
            if (parent != null) {
            	parentDom = ctx.getDOMElement(parent);
            } else {
                parentDom = ctx.getDOMElement(ead.getArchdesc());
            }

            for (Element elem : inheritedElements) {
                String altrender = elem.getAttribute("altrender");
                if (!"inherited".equals(altrender)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                            "Atribut altrender obsahuje nepovolenou hodnotu: " + altrender + ".",
                            formatDomPosition(elem));
                }
                // mark attribute as validated
                ctx.markValidatedAttributeDom(elem, "altrender");

                // Find candidate elements with the same local name in the parent
                List<Element> candidates = findInheritableElements(parentDom, elem.getLocalName());

                boolean found = false;
                for (Element candidate : candidates) {
                    if (elementsMatchIgnoringAltrender(elem, candidate)) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT,
                            "V nadřazené jednotce popisu neexistuje element <"
                                    + ctx.getEadElementName(elem.getLocalName())
                                    + "> se shodnou strukturou jako element s atributem altrender=\"inherited\".",
                            formatDomPosition(elem));
                }
            }
        });
    }

    /**
     * Find elements of inheritable types that have the altrender attribute,
     * within the given C/archdesc element. Does not descend into nested C elements.
     */
    private List<Element> findInheritableElementsWithAltrender(Element root) {
        List<Element> result = new ArrayList<>();
        collectInheritableElementsWithAltrender(root, result);
        return result;
    }

    private void collectInheritableElementsWithAltrender(Element element, List<Element> result) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (!(child instanceof Element childElem)) {
                continue;
            }
            // Do not descend into nested C elements
            if ("c".equals(childElem.getLocalName()) && EadNS.NS_EADS.equals(childElem.getNamespaceURI())) {
                continue;
            }
            if (INHERITABLE_ELEMENTS.contains(childElem.getLocalName())
                    && EadNS.NS_EADS.equals(childElem.getNamespaceURI())
                    && childElem.hasAttribute("altrender")) {
                result.add(childElem);
            }
            collectInheritableElementsWithAltrender(childElem, result);
        }
    }

    /**
     * Find all elements with the given local name (in EAD namespace) within the
     * given C/archdesc element. Does not descend into nested C elements.
     */
    private List<Element> findInheritableElements(Element root, String localName) {
        List<Element> result = new ArrayList<>();
        collectInheritableElements(root, localName, result);
        return result;
    }

    private void collectInheritableElements(Element element, String localName, List<Element> result) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (!(child instanceof Element childElem)) {
                continue;
            }
            // Do not descend into nested C elements
            if ("c".equals(childElem.getLocalName()) && EadNS.NS_EADS.equals(childElem.getNamespaceURI())) {
                continue;
            }
            if (localName.equals(childElem.getLocalName()) && EadNS.NS_EADS.equals(childElem.getNamespaceURI())) {
                result.add(childElem);
            }
            collectInheritableElements(childElem, localName, result);
        }
    }

    /**
     * Compare two elements ignoring the altrender attribute, whitespace-only
     * text nodes, and XML comments. Uses deep DOM comparison via isEqualNode().
     */
    private boolean elementsMatchIgnoringAltrender(Element inherited, Element candidate) {
        Element inheritedClone = (Element) inherited.cloneNode(true);
        Element candidateClone = (Element) candidate.cloneNode(true);

        // Remove altrender attribute from both clones
        inheritedClone.removeAttribute("altrender");
        candidateClone.removeAttribute("altrender");

        // Remove namespace declarations (may differ based on tree position)
        removeNamespaceDeclarations(inheritedClone);
        removeNamespaceDeclarations(candidateClone);

        // Strip whitespace-only text nodes and comments (formatting differs between levels)
        stripWhitespaceAndComments(inheritedClone);
        stripWhitespaceAndComments(candidateClone);

        return inheritedClone.isEqualNode(candidateClone);
    }

    /**
     * Remove all xmlns:* namespace declaration attributes from an element and its descendants.
     */
    private void removeNamespaceDeclarations(Element element) {
        NamedNodeMap attrs = element.getAttributes();
        List<String> toRemove = new ArrayList<>();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node attr = attrs.item(i);
            if (XMLNS_URI.equals(attr.getNamespaceURI())) {
                toRemove.add(attr.getNodeName());
            }
        }
        for (String name : toRemove) {
            element.removeAttribute(name);
        }
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element childElem) {
                removeNamespaceDeclarations(childElem);
            }
        }
    }

    /**
     * Remove whitespace-only text nodes and XML comments from a node and its descendants.
     */
    private void stripWhitespaceAndComments(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().isEmpty()) {
                node.removeChild(child);
            } else if (child.getNodeType() == Node.COMMENT_NODE) {
                node.removeChild(child);
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                stripWhitespaceAndComments(child);
            }
        }
    }

    /**
     * Format a DOM element position for error messages.
     */
    private String formatDomPosition(Element element) {
        StringBuilder sb = new StringBuilder();
        Object lineNumber = element.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME);
        Object colNumber = element.getUserData(PositionalXMLReader.COLUMN_NUMBER);
        if (lineNumber != null) {
            sb.append("Řádek ").append(lineNumber);
            if (colNumber != null) {
                sb.append(":").append(colNumber);
            }
            sb.append(", ");
        }
        sb.append("element: <").append(ctx.getEadElementName(element.getLocalName())).append(">");
        return sb.toString();
    }
}
