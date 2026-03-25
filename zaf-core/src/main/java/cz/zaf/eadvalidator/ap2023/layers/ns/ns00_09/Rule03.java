package cz.zaf.eadvalidator.ap2023.layers.ns.ns00_09;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.PositionalXMLReader;
import cz.zaf.eadvalidator.ap2023.Ap2023Constants;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.eadvalidator.ap2023.profile.ProfileRevision;
import cz.zaf.schemas.cam.CamNS;
import cz.zaf.schemas.cam.CamNSv2;

public class Rule03 extends EadRule {
	static final public String CODE = "ns3";

	public Rule03() {
        super(CODE,
                "Element <ead:ead> má atribut \"xmlns:cam\" o hodnotě \"http://cam.tacr.cz/2019\" nebo \"http://cam.tacr.cz/2025\" (profily od května 2026).",
                "Umístění schématu systému CAM je popsáno chybně.",
                "Část 1.4 profilu EAD3 MV ČR");
	}

	@Override
	protected void evalImpl() {
		Element eadRoot = ctx.getRootElement();

		@SuppressWarnings("unchecked")
		Map<String, String> nsMapping = (Map<String, String>) eadRoot.getUserData(PositionalXMLReader.NS_MAPPING);
		String value = (nsMapping!=null)?nsMapping.get("cam"):null;
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen platný atribut xmlns:cam");
		}
		
		ctx.markValidatedAttributeDom(eadRoot, "xmlns:cam");

		// For profile CZ_EAD3_PROFILE_20260501 and newer, allow both CAM v1 and v2
		String profileIdentifier = findProfileIdentifier(eadRoot);
		if(ProfileRevision.CZ_EAD3_PROFILE_20260501.name().equals(profileIdentifier)) {
			if(!CamNS.NS_CAM.equals(value) && !CamNSv2.NS_CAM.equals(value)) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:cam: "+value);
			}
			return;
		}

		if(!CamNS.NS_CAM.equals(value)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybná hodnota xmlns:cam: "+value);
		}
	}

	/**
	 * Find profile identifier directly from DOM.
	 *
	 * Traverses: root -> control -> localcontrol[@localtype="CZ_FINDING_AID_EAD_PROFILE"] -> term -> @identifier
	 *
	 * JAXB is not available at this stage of validation, so DOM traversal is used.
	 *
	 * @return profile identifier string or null if not found
	 */
	private String findProfileIdentifier(Element eadRoot) {
		Element control = findChildElement(eadRoot, "control");
		if(control==null) {
			return null;
		}

		NodeList controlChildren = control.getChildNodes();
		for(int i = 0; i < controlChildren.getLength(); i++) {
			Node node = controlChildren.item(i);
			if(node instanceof Element element && "localcontrol".equals(element.getLocalName())) {
				String localtype = element.getAttribute("localtype");
				if(Ap2023Constants.LOCALTYPE_FINDING_AID_EAD_PROFILE.equals(localtype)) {
					Element term = findChildElement(element, "term");
					if(term!=null) {
						return term.getAttribute("identifier");
					}
				}
			}
		}
		return null;
	}

	private Element findChildElement(Element parent, String localName) {
		NodeList children = parent.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node instanceof Element element && localName.equals(element.getLocalName())) {
				return element;
			}
		}
		return null;
	}
}
