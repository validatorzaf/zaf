package cz.zaf.eadvalidator.ap2023.layers.val.val00_09;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.xml.SchemaResourceLoader;
import cz.zaf.common.xml.ValidationRuleErrorHandler;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schemas.cam.CamNS;
import cz.zaf.schemas.cam.CamNSv2;
import cz.zaf.schemas.ead.EadNS;

public class Rule01 extends EadRule {

	static final public String CODE = "val1";

	public Rule01() {
		super(CODE,
				"Soubor je validní proti schématu ead3.xsd a cam.",
				"Soubor není validní proti příslušným schématům.",
				"Část 1.1.3 profilu EAD3 MV ČR");
	}

	@Override
	protected void evalImpl() {
		var schema = SchemaResourceLoader.getCombined(EadNS.SCHEMA_RESOURCE,
				CamNS.SCHEMA_RESOURCE, CamNSv2.SCHEMA_RESOURCE);
		var validator = schema.newValidator();
		validator.setErrorHandler(new ValidationRuleErrorHandler());

		try(InputStream is = Files.newInputStream(ctx.getLoader().getFilePath())) {
			Source source = new StreamSource(is);
			validator.validate(source);
		} catch (SAXException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba SAX", e);
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba IO", e);
		}

		// EAD schema uses processContents="lax" for objectxmlwrap content,
		// so unknown CAM elements are silently skipped. We need to validate
		// objectxmlwrap content separately against the correct CAM schema.
		validateObjectXmlWrapContent();

		try {
			// Parse to JAXB
			ctx.getLoader().loadJaxb();
		} catch(Exception e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba JAXB", e);
		}

		if(ctx.getLoader().getEad()==null) {
			// toto by nemelo nastat
			throw new ZafException(BaseCode.NEZNAMA_CHYBA, "Po načtení pomocí JAXB chybí kořenový objekt");
		}

	}

	/**
	 * Validate content of objectxmlwrap elements directly against the CAM schema.
	 *
	 * The EAD schema uses processContents="lax" for objectxmlwrap, which means
	 * unknown elements in a known namespace are silently skipped instead of rejected.
	 * To properly validate CAM content, we extract each objectxmlwrap's child element
	 * and validate it as a root element against the appropriate CAM schema.
	 */
	private void validateObjectXmlWrapContent() {
		Element root = ctx.getRootElement();
		if(root==null) {
			return;
		}

		NodeList objectXmlWraps = root.getElementsByTagNameNS(EadNS.NS_EADS, "objectxmlwrap");
		for(int i = 0; i < objectXmlWraps.getLength(); i++) {
			Element objectXmlWrap = (Element) objectXmlWraps.item(i);
			Element contentElement = getFirstChildElement(objectXmlWrap);
			if(contentElement==null) {
				continue;
			}

			String nsUri = contentElement.getNamespaceURI();
			Schema camSchema = getCamSchema(nsUri);
			if(camSchema==null) {
				// Not a CAM namespace, skip
				continue;
			}

			var camValidator = camSchema.newValidator();
			camValidator.setErrorHandler(new ValidationRuleErrorHandler());
			try {
				camValidator.validate(new DOMSource(contentElement));
			} catch (SAXException e) {
				throw new ZafException(BaseCode.CHYBA,
						"Obsah elementu objectxmlwrap není validní proti schématu CAM: " + e.getMessage(),
						e);
			} catch (IOException e) {
				throw new ZafException(BaseCode.CHYBA, "Chyba IO při validaci objectxmlwrap", e);
			}
		}
	}

	private Schema getCamSchema(String nsUri) {
		if(CamNS.NS_CAM.equals(nsUri)) {
			return SchemaResourceLoader.get(CamNS.SCHEMA_RESOURCE);
		}
		if(CamNSv2.NS_CAM.equals(nsUri)) {
			return SchemaResourceLoader.get(CamNSv2.SCHEMA_RESOURCE);
		}
		return null;
	}

	private Element getFirstChildElement(Element parent) {
		NodeList children = parent.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if(node instanceof Element element) {
				return element;
			}
		}
		return null;
	}

}
