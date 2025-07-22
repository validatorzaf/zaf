package cz.zaf.earkvalidator.layers.fls.fls00_09;

import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadLoader;
import cz.zaf.eadvalidator.ap2023.EadValidationContext;
import cz.zaf.eadvalidator.ap2023.ValidatorEadInner;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.AipValidationContext;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.premisvalidator.PremisValidationContext;
import cz.zaf.premisvalidator.RepresentationInfo;
import cz.zaf.premisvalidator.ValidatorPremisInner;
import cz.zaf.premisvalidator.profile.PremisProfile;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;
import cz.zaf.schema.mets_1_12_1.StructMapType;
import cz.zaf.schemas.mets.MetsNS;

public class Rule05 extends AipRule {
	public static final String CODE = "fls05";
	public static final String RULE_TEXT = "Lze zahájit kontroly správnosti souborů ve formátu EAD.";
	public static final String RULE_ERROR = "Nepodařilo se připravit kontrolu souborů ve formátu EAD.";
	public static final String RULE_SOURCE = "CZDAX-PMD0202";

	public Rule05() {
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
				div.getDMDID().forEach(dmdId -> {
					MdSecType mdSec = (MdSecType) dmdId;
					MdRef mdRef = mdSec.getMdRef();
					if(MetsNS.MDTYPE_EAD.equals(mdRef.getMDTYPE())) {
						prepareEadValidation(mdRef.getHref());
					}
				});
			}
		}
	}

	private void prepareEadValidation(String relativePath) {
		Path aipPath = ctx.getLoader().getAipPath();
		Path eadPath = aipPath.resolve(relativePath);
		
		// boolean isPackageInfo = relativePath.equals	("metadata/preservation/PACKAGE-INFO.xml");
		// TODO: distinguish between inherent and contextual EAD
				
		EadLoader eadLoader = new EadLoader(eadPath, ctx.getValidationResult());
		
		// EadValidationContext eadCtx = new EadValidationContext(eadLoader, null);
		// context will be deleted after finishing this rule
		/*
		final var localCtx = ctx;
		ctx.setRepresentationReader(repId -> {
			var fileSec = localCtx.getMets().getFileSec();
			if(fileSec!=null) {	
				for (FileGrp grp : fileSec.getFileGrp()) {
					if (grp.getID().equals(repId)) {
						if (grp.getUSE().startsWith(EarkConstants.USE_REPRESENTATIONS)) {
							String name = grp.getUSE().substring(EarkConstants.USE_REPRESENTATIONS.length());
							return new RepresentationInfo(repId, name, grp);
						}
					}
				}
			}
			return null;
		})*/;
		ValidatorEadInner<AipValidationContext> vpi = new ValidatorEadInner<>(relativePath, eadLoader, AP2023Profile.EARK_INHERENT_DESC); //TODO: add profile, profile);
		this.ctx.addInnerFileValidation(vpi, relativePath);
	}

}
