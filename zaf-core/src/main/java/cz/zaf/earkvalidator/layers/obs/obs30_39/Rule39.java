package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;

public class Rule39 extends AipRule {
	public static final String CODE = "obs39";
	public static final String RULE_TEXT = "Existuje soubor se PACKAGE-INFO.xml se souhrnnými informacemi o balíčku.";
	public static final String RULE_ERROR = "Soubor PACKAGE-INFO.xml neexistuje.";
	public static final String RULE_SOURCE = "CZDAX-PKG0001";

	public Rule39() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		MdRef packageInfo = null;
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		if(CollectionUtils.isNotEmpty(amdSecs)) {
			for(AmdSecType amdSec: amdSecs) {
				packageInfo =findPackageInfo(amdSec);
				if(packageInfo != null) {
					break;
				}
			}
		}
		if(packageInfo==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/amdSec/digiprovMD/mdRef[@xlink:href='metadata/preservation/PACKAGE-INFO.xml'].", ctx.formatMetsPosition(ctx.getMets()));
		}
	}

	private MdRef findPackageInfo(AmdSecType amdSec) {
		List<MdSecType> digiProvs = amdSec.getDigiprovMD();
		if(CollectionUtils.isNotEmpty(digiProvs)) {
			for(MdSecType mdSec: digiProvs) {
				MdRef mdref = mdSec.getMdRef();
				// CZDAX-PMT0407
				if(mdref == null) {
					throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/amdSec/digiprovMD/mdRef.", ctx.formatMetsPosition(mdSec));
				}
				// CZDAX-PMT0406
				if(EarkCz.GROUPID_PRESERVATION.equals(mdSec.getGROUPID())) {
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
					if(href.equals("metadata/preservation/"+EarkCz.PACKAGE_INFO_FILENAME)) {
						return mdref;
					}
				}				
				
			}
		}
		return null;
	}
}
