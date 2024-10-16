package cz.zaf.premisvalidator.layers.ns.ns00_09;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.premisvalidator.PremisRule;
import cz.zaf.schemas.premis.PremisNS;

public class Rule04 extends PremisRule {
	public static final String CODE = "ns04";
	public static final String RULE_TEXT = "\"Kořenový element obsahuje atribut xsi:schemaLocation s hodnotou \"http://www.loc.gov/premis/v3 http://www.loc.gov/standards/premis/premis.xsd\".";
	public static final String RULE_ERROR = "Chybí informace o umístění pro některé ze schémat.";
	public static final String RULE_SOURCE = "CZDAX-PMT0101";
	
	// Expected schema locations
	static Map<String, String> expectedSchemaLocations = new HashMap<>();
	static {
		expectedSchemaLocations.put(PremisNS.NS_PREMIS, PremisNS.NS_PREMIS_LOCATION); 
	}

	public Rule04() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}

	@Override
	protected void evalImpl() {
		Element metsRoot = ctx.getLoader().getRootElement();
		
		Attr schemaLocation = metsRoot.getAttributeNodeNS(EarkConstants.NS_XSI, EarkConstants.XSI_SCHEMA_LOCATION);
		if(schemaLocation==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut xsi:schemaLocation nebo je prázdný.");
		}
		
		String value = schemaLocation.getValue();
		if(StringUtils.isEmpty(value)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Atribut xsi:schemaLocation je prázdný.");
		}
		
		// split schema location to doubles schema, location
		String[] schemaLocations = value.split("\\s+");
		// we need even number of schema locations
		if(schemaLocations.length%2!=0) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nalezen neplatný atribut xsi:schemaLocation.");
		}

		Map<String, String> schemaLocationPairs = new HashMap<>();
		for (int i = 0; i < schemaLocations.length; i += 2) {
		    schemaLocationPairs.put(schemaLocations[i], schemaLocations[i + 1]);
		}

		// Compare if all expected schemas are present
		for (Map.Entry<String, String> entry : expectedSchemaLocations.entrySet()) {
			if(!schemaLocationPairs.containsKey(entry.getKey())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nalezena platná hodnota pro schéma: "+entry.getKey());
			}
			if(!schemaLocationPairs.get(entry.getKey()).equals(entry.getValue())) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nalezena chybná hodnota pro schéma: "+entry.getKey());
			}
		}
		
	}
}
