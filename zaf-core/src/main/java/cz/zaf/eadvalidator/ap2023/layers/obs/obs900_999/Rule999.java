package cz.zaf.eadvalidator.ap2023.layers.obs.obs900_999;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.EadValidatedNodes;
import cz.zaf.schemas.ead.EadNS;

/**
 * Rule obs999: Checks that the EAD document does not contain any elements
 * not validated by other rules (i.e., unsupported by the Czech profile).
 *
 * This rule should be the LAST rule executed. It walks the DOM tree
 * and reports any EAD elements that were not marked as validated
 * by preceding rules.
 */
public class Rule999 extends EadRule {

	static final public String CODE = "obs999";

	private static final int MAX_REPORTED_ELEMENTS = 10;

	public Rule999() {
		super(CODE,
				"EAD dokument může obsahovat jen elementy povolené českou specifikací. Nepovolené elementy nejsou přípustné.",
				"Nalezen nepovolený element českou specifikací",
				"Část 1.1. profilu EAD3 MV ČR");
	}

	@Override
	protected void evalImpl() {
		EadValidatedNodes validatedNodes = ctx.getValidatedNodes();

		List<Node> unvalidated = validatedNodes.findUnvalidatedElements(EadNS.NS_EADS);

		if (!unvalidated.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			int count = Math.min(unvalidated.size(), MAX_REPORTED_ELEMENTS);
			for (int i = 0; i < count; i++) {
				if (i > 0) {
					sb.append("\n");
				}				
				sb.append(i + 1).append(") ");
				Node node = unvalidated.get(i);
				if(node instanceof Element elem) {				
					sb.append(formatElementWithAttributes(elem));
					sb.append(" - ");
					sb.append(formatDomPosition(elem));
				} else 
				if(node instanceof Attr attr) {
					sb.append(formatElementWithAttributes(attr.getOwnerElement()));
					sb.append(" - ");
					sb.append(formatDomPosition(attr));					
				}
			}
			if (unvalidated.size() > MAX_REPORTED_ELEMENTS) {
				sb.append("\n... a dalších ").append(unvalidated.size() - MAX_REPORTED_ELEMENTS);
			}

			Node first = unvalidated.get(0);
			throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT,
					"Celkem neověřených elementů: " + unvalidated.size() + ".\n" + sb,
					formatDomPosition(first));
		}
	}

	private String formatElementWithAttributes(Element element) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(element.getNodeName());
		NamedNodeMap attrs = element.getAttributes();
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				Node attr = attrs.item(i);
				String nsUri = attr.getNamespaceURI();
				if ("http://www.w3.org/2000/xmlns/".equals(nsUri)) {
					continue;
				}
				sb.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
			}
		}
		sb.append(">");
		return sb.toString();
	}

	private String formatDomPosition(Node node) {
		StringBuilder sb = new StringBuilder();
		Object lineNumber = node.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME);
		Object colNumber = node.getUserData(PositionalXMLReader.COLUMN_NUMBER);
		if (lineNumber != null) {
			sb.append("Řádek ").append(lineNumber);
			if (colNumber != null) {
				sb.append(":").append(colNumber);
			}
			sb.append(", ");
		}
		if(node instanceof Element element) {
			sb.append("element: <").append(element.getNodeName()).append(">");
		} else 
		if(node instanceof Attr attr) {
			sb.append("attribute: ").append(attr.getName())
				.append("=\"").append(attr.getValue()).append("\", ")
				.append(formatDomPosition(attr.getOwnerElement()));
		}
		return sb.toString();
	}
}
