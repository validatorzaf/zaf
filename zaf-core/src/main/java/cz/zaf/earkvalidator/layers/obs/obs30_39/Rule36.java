package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.StructMapType;

public class Rule36 extends AipRule {
	public static final String CODE = "obs36";
	public static final String RULE_TEXT = "Balíček obsahuje fyzickou strukturální mapu.";
	public static final String RULE_ERROR = "Není uvedena fyzická strukturální mapa nebo je chybně popsána.";
	public static final String RULE_SOURCE = "CZDAX-PMT0603, CZDAX-PMT0604, CZDAX-PMT0605";

	public Rule36() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<StructMapType> structMaps = ctx.getMets().getStructMap();
		if(CollectionUtils.isEmpty(structMaps)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap.", ctx.formatMetsPosition(ctx.getMets()));
		}
		StructMapType physicalStructMap = null;
		for(StructMapType structMap: structMaps) {
			// CZDAX-PMT0601
			if(structMap.getTYPE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/@TYPE.", ctx.formatMetsPosition(structMap));
			}
			// CZDAX-PMT0602, CZDAX-PMT0603
			if(EarkConstants.STRUCTMAP_TYPE_PHYSICAL.equals(structMap.getTYPE())) {
				if(physicalStructMap!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nelze uvést dvě fyzické strukturální mapy.", ctx.formatMetsPosition(structMap));
				}
				physicalStructMap = structMap;
				continue;
			}
		}
		if(physicalStructMap==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí fyzická strukturální mapa.", ctx.formatMetsPosition(ctx.getMets()));
		}
		
		// CZDAX-PMT0604
		String label = physicalStructMap.getLabelValue2();
		if(StringUtils.isBlank(label)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/@LABEL.", ctx.formatMetsPosition(physicalStructMap));
		}
		if(!EarkConstants.STRUCTMAP_LABEL_CSIP.equals(label)) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut má chybní hodnotu: "+ label, ctx.formatMetsPosition(physicalStructMap));
		}
		
		// CZDAX-PMT0605
		String id = physicalStructMap.getID();
		if(StringUtils.isBlank(id)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/@ID.", ctx.formatMetsPosition(physicalStructMap));
		}
		if(!ValidatorId.checkFormatId(id)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/@ID, value: "+id, ctx.formatMetsPosition(physicalStructMap));
		}
	}


}
