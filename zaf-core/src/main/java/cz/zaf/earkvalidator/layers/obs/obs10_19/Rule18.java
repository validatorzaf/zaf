package cz.zaf.earkvalidator.layers.obs.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schemas.mets.MetsNS;

public class Rule18 extends AipRule {
	public static final String CODE = "obs18";
	public static final String RULE_TEXT = "Administrativní metadata jsou uvedena v elementu mets/amdSec/digiprovMD.";
	public static final String RULE_ERROR = "Administrativní metadata jsou uvedena chybně.";
	public static final String RULE_SOURCE = "CZDAX-PMT0403, CZDAX-PMT0418";

	public Rule18() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		if(CollectionUtils.isEmpty(amdSecs)) {
			return;
		}
		for(AmdSecType amdSec: amdSecs) {
			// CZDAX-PMT0418
			// musi obsahovat jen digiprovMD
			List<MdSecType> rightsMDs = amdSec.getRightsMD();
			if(CollectionUtils.isNotEmpty(rightsMDs)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element mets/amdSec/rightsMD.", ctx.formatMetsPosition(amdSec));
			}
			List<MdSecType> sourceMDs = amdSec.getSourceMD();
			if(CollectionUtils.isNotEmpty(sourceMDs)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element mets/amdSec/sourceMD.", ctx.formatMetsPosition(amdSec));
			}
			List<MdSecType> techMDs = amdSec.getTechMD();
			if(CollectionUtils.isNotEmpty(techMDs)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element mets/amdSec/techMD.", ctx.formatMetsPosition(amdSec));
			}
						
			// CZDAX-PMT0403
			List<MdSecType> digiProvs = amdSec.getDigiprovMD();
			if(CollectionUtils.isEmpty(digiProvs)) {
				continue;
			}
			for(MdSecType digiProv: digiProvs) {
				// mdType musi mit hodnotu PREMIS
				MdRef mdRef = digiProv.getMdRef();
				if(mdRef==null) {
					continue;
				}
				String mdType = mdRef.getMDTYPE();
				if(!MetsNS.MDTYPE_PREMIS.equals(mdType)) {
					throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/amdSec/digiprovMD/mdref/@MDTYPE s hodnotou PREMIS.", ctx.formatMetsPosition(digiProv));
				}
			}
		}
	}


}
