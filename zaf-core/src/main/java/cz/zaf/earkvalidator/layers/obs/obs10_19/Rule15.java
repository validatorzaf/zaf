package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;

public class Rule15 extends AipRule {
	public static final String CODE = "obs15";
	public static final String RULE_TEXT = "Chybně uvedená reference na soubor s popisnými metadaty v elementu mets/dmdSec.";
	public static final String RULE_ERROR = "Reference z elementu mets/dmdSec na fyzický soubor je správně zapsána v elementu mdRef.";
	public static final String RULE_SOURCE = "CZDAX-PMT0306, CZDAX-PMT0307, CZDAX-PMT0308, CZDAX-PMT0309";

	public Rule15() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		
		for(MdSecType mdSec: dmdSecs) {
			MdRef mdref = mdSec.getMdRef();
			// CZDAX-PMT0306
			if(mdref == null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/dmdSec/mdRef.", ctx.formatMetsPosition(mdSec));
			}
			// CZDAX-PMT0307
			String loctype = mdref.getLOCTYPE();
			if(!MetsNS.LOCTYPE_URL.equals(loctype)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@LOCTYPE s hodnotou URL.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0308
			String xlinkType = mdref.getType();
			if(!XLinkNS.TYPE_SIMPLE.equals(xlinkType)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@xlink:type s hodnotou simple.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0309
			String href = mdref.getHref();
			if(StringUtils.isBlank(href)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@href.", ctx.formatMetsPosition(mdref));
			}
		}
	}

}
