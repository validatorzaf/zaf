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

public class Rule22 extends AipRule {
	public static final String CODE = "obs22";
	public static final String RULE_TEXT = "Reference z elementu mets/amdSec/digiprovMD na fyzický soubor je správně zapsána v elementu mdRef.";
	public static final String RULE_ERROR = "Chybně zapsaná reference na fyzický soubor s administrativními metadaty.";
	public static final String RULE_SOURCE = "CZDAX-PMT0407, CZDAX-PMT0408, CZDAX-PMT0409, CZDAX-PMT0410";

	public Rule22() {
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
				// CZDAX-PMT0408
				String loctype = mdref.getLOCTYPE();
				if(!MetsNS.LOCTYPE_URL.equals(loctype)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@LOCTYPE s hodnotou URL.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0409
				String xlinkType = mdref.getType();
				if(!XLinkNS.TYPE_SIMPLE.equals(xlinkType)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@xlink:type s hodnotou simple.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0410
				String href = mdref.getHref();
				if(StringUtils.isBlank(href)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@href.", ctx.formatMetsPosition(mdref));
				}
			}
		}
	}


}
