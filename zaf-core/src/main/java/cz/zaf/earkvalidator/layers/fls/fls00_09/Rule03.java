package cz.zaf.earkvalidator.layers.fls.fls00_09;

import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidationLayerContext;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.AipValidationLayer;
import cz.zaf.earkvalidator.ValidationLayers;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schema.mets_1_12_1.StructMapType;
import cz.zaf.schemas.mets.MetsNS;

public class Rule03 extends AipRule {
	public static final String CODE = "fls03";
	public static final String RULE_TEXT = "Lze zahájit kontroly správnosti souborů ve formátu PREMIS.";
	public static final String RULE_ERROR = "Nepodařilo se připravit kontrolu souborů ve formátu PREMIS.";
	public static final String RULE_SOURCE = "CZDAX-PMD0201";

	public Rule03() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		// get administration metadata based on PREMIS
		List<StructMapType> structMaps = ctx.getMets().getStructMap();
		StructMapType physicalStructMap = structMaps.stream().filter(sm -> EarkConstants.STRUCTMAP_TYPE_PHYSICAL.equals(sm.getTYPE())).findFirst().orElse(null);
		if(physicalStructMap==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap.", ctx.formatMetsPosition(ctx.getMets()));
		}
		List<DivType> divs = physicalStructMap.getDiv().getDiv();
		for(DivType div: divs) {
			String label = div.getLabelValue8();
			if(EarkConstants.STRUCTMAP_LABEL_METADATA.equals(label)) {
				div.getADMID().forEach(admId -> {
					MdSecType mdSec = (MdSecType) admId;
					MdRef mdRef = mdSec.getMdRef();
					if(MetsNS.MDTYPE_PREMIS.equals(mdRef.getMDTYPE())) {
						preparePremisValidation(mdRef.getHref());
					}
				});
			}
		}
	}

	private void preparePremisValidation(String relativePath) {
		BaseValidator<AipValidationContext> validator = (BaseValidator<AipValidationContext>) ctx.getValidator();		
		validator.addValidationLayer(new AipValidationLayer(ValidationLayers.COMMPONENT_ENCODING, relativePath));
	}

}
