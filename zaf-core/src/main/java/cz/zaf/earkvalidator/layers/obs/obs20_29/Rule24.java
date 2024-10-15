package cz.zaf.earkvalidator.layers.obs.obs20_29;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schemas.mets.MetsNS;

public class Rule24 extends AipRule {
	public static final String CODE = "obs24";
	public static final String RULE_TEXT = "Element mets/amdSec/digiprovMD/mdRef má uvedeny všechny povinné atributy.";
	public static final String RULE_ERROR = "Element mets/amdSec/digiprovMD/mdRef není správně uveden.";
	public static final String RULE_SOURCE = "CZDAX-PMT0411, CZDAX-PMT0412, CZDAX-PMT0413, CZDAX-PMT0414, CZDAX-PMT0415, CZDAX-PMT0416";

	public Rule24() {
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
				// CZDAX-PMT0411
				String mdtype = mdref.getMDTYPE();
				if(!MetsNS.MDTYPE_PREMIS.equals(mdtype)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@MDTYPE s hodnotou EAD.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0412
				String mimetype = mdref.getMIMETYPE();
				if(StringUtils.isBlank(mimetype)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@MIMETYPE.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0413
				Long fileSize = mdref.getSIZE();
				if(fileSize==null) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@SIZE.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0414
				XMLGregorianCalendar created = mdref.getCREATED();
				if(created==null) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@CREATED.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0415
				String checksum = mdref.getCHECKSUM();
				if(checksum==null) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@CHECKSUM.", ctx.formatMetsPosition(mdref));
				}
				// CZDAX-PMT0416
				String checksumType = mdref.getCHECKSUMTYPE();
				if(!MetsNS.CHECKSUMTYPE_SHA_512.equals(checksumType)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdRef/@CHECKSUMTYPE s hodnotou SHA-512.", ctx.formatMetsPosition(mdref));
				}
			}
		}
	}


}
