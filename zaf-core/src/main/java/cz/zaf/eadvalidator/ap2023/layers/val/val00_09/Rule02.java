package cz.zaf.eadvalidator.ap2023.layers.val.val00_09;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.cam.CamNS;
import cz.zaf.schemas.cam.CamNSv2;
import cz.zaf.schemas.ead.EadNS;

public class Rule02 extends EadRule {

	static final public String CODE = "val2";

	public Rule02() {
		super(CODE,
				"Každý element <objectxmlwrap>, který je uveden v elementu <source>, má právě jeden podřízený element, a to buď <cam:ent> (dle schématu CAMv1) nebo <cam:entity> (dle schématu CAMv2).",
				"Element <objectxmlwrap>, který je uveden v elementu <source>, neobsahuje nebo naopak obsahuje více než jeden příslušný element schématu CAM.",
				"Část 8.3 profilu EAD3 MV ČR");
	}

	@Override
	protected void evalImpl() {
		Element root = ctx.getRootElement();
		if (root == null) {
			return;
		}

		NodeList sources = root.getElementsByTagNameNS(EadNS.NS_EADS, "source");
		for (int i = 0; i < sources.getLength(); i++) {
			Element source = (Element) sources.item(i);
			NodeList objectXmlWraps = source.getElementsByTagNameNS(EadNS.NS_EADS, "objectxmlwrap");
			for (int j = 0; j < objectXmlWraps.getLength(); j++) {
				Element objectXmlWrap = (Element) objectXmlWraps.item(j);
				validateObjectXmlWrap(objectXmlWrap);
			}
		}
	}

	private void validateObjectXmlWrap(Element objectXmlWrap) {
		List<Element> childElements = getChildElements(objectXmlWrap);

		if (childElements.size() != 1) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT,
					"Element <" + ctx.getEadElementName("objectxmlwrap")
							+ "> musí obsahovat právě jeden podřízený element CAM, nalezeno: "
							+ childElements.size() + ".",
					formatDomPosition(objectXmlWrap));
		}

		Element child = childElements.get(0);
		String nsUri = child.getNamespaceURI();
		String localName = child.getLocalName();

		boolean isCamV1 = CamNS.NS_CAM.equals(nsUri) && "ent".equals(localName);
		boolean isCamV2 = CamNSv2.NS_CAM.equals(nsUri) && "entity".equals(localName);

		if (!isCamV1 && !isCamV2) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
					"Element <" + ctx.getEadElementName("objectxmlwrap")
							+ "> obsahuje neočekávaný element <" + child.getNodeName()
							+ ">, očekáván <cam:ent> nebo <cam:entity>.",
					formatDomPosition(child));
		}
	}

	private List<Element> getChildElements(Element parent) {
		List<Element> elements = new ArrayList<>();
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node instanceof Element element) {
				elements.add(element);
			}
		}
		return elements;
	}

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
		sb.append("element: <").append(element.getNodeName()).append(">");
		return sb.toString();
	}
}
