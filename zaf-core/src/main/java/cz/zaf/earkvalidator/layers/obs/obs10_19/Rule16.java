package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schemas.mets.MetsNS;

public class Rule16 extends AipRule {
	public static final String CODE = "obs16";
	public static final String RULE_TEXT = "Reference z elementu mets/dmdSec/mdRef na fyzický soubor má správně uvedeny všechny atributy.";
	public static final String RULE_ERROR = "CZDAX-PMT0310, CZDAX-PMT0311, CZDAX-PMT0312, CZDAX-PMT0313, CZDAX-PMT0314, CZDAX-PMT0315";
	public static final String RULE_SOURCE = "Neúplná reference na soubor s popisnými metadaty v elementu mets/dmdSec/mdRef.";

	public Rule16() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		if(CollectionUtils.isEmpty(dmdSecs)) {
			return;
		}
		
		for(MdSecType dmdSec: dmdSecs) {
			MdRef mdref = dmdSec.getMdRef();
			// CZDAX-PMT0306
			if(mdref == null) {
				throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/dmdSec/mdRef.", ctx.formatMetsPosition(dmdSec));
			}
			// CZDAX-PMT0310
			String mdtype = mdref.getMDTYPE();
			if(!MetsNS.MDTYPE_EAD.equals(mdtype)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@MDTYPE s hodnotou EAD.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0311
			String mimetype = mdref.getMIMETYPE();
			if(StringUtils.isBlank(mimetype)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@MIMETYPE.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0312
			Long fileSize = mdref.getSIZE();
			if(fileSize==null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@SIZE.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0313
			XMLGregorianCalendar created = mdref.getCREATED();
			if(created==null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@CREATED.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0314
			String checksum = mdref.getCHECKSUM();
			if(checksum==null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@CHECKSUM.", ctx.formatMetsPosition(mdref));
			}
			// CZDAX-PMT0315
			String checksumType = mdref.getCHECKSUMTYPE();
			if(!MetsNS.CHECKSUMTYPE_SHA_512.equals(checksumType)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/dmdSec/mdRef/@CHECKSUMTYPE s hodnotou SHA-512.", ctx.formatMetsPosition(mdref));
			}
		}
	}


}
