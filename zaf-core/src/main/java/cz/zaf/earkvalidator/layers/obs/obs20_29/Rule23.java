package cz.zaf.earkvalidator.layers.obs.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;

public class Rule23 extends AipRule {
	public static final String CODE = "obs23";
	public static final String RULE_TEXT = "Metadata popisující uchovávání (preservation) MUSÍ být uložena v podsložce preservation.";
	public static final String RULE_ERROR = "Metadata popisující uchování jsou v neočekávaném místě.";
	public static final String RULE_SOURCE = "CZDAX-PSP0106";

	public Rule23() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		if(CollectionUtils.isEmpty(amdSecs)) {
			return;
		}
		for(AmdSecType amdSec: amdSecs) {
			// CZDAX-PMT0403
			List<MdSecType> digiProvs = amdSec.getDigiprovMD();
			if(CollectionUtils.isEmpty(digiProvs)) {
				continue;
			}
			for(MdSecType mdSec: digiProvs) {
				MdRef mdref = mdSec.getMdRef();
				// CZDAX-PMT0407
				if(mdref == null) {
					throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/amdSec/digiprovMD/mdRef.", ctx.formatMetsPosition(mdSec));
				}
				// CZDAX-PMT0410
				String href = mdref.getHref();
				if(StringUtils.isBlank(href)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@href.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PSP0106
				// check if href start with metadata/preservation/
				if(!href.startsWith("metadata/preservation/")) {
					throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@href s hodnotou začínající metadata/preservation/.", ctx.formatMetsPosition(mdref));
				}
			}
		}

	}


}
