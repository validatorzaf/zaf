package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.schema.mets_1_12_1.StructMapType;

public class Rule35 extends AipRule {
	public static final String CODE = "obs35";
	public static final String RULE_TEXT = "Balíček obsahuje fyzickou nebo logickou strukturální mapu.";
	public static final String RULE_ERROR = "Uvedena strukturální mapa neznámého typu.";
	public static final String RULE_SOURCE = "CZDAX-PMT0601, CZDAX-PMT0602, CZDAX-PMT0704";

	public Rule35() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<StructMapType> structMaps = ctx.getMets().getStructMap();
		if(CollectionUtils.isEmpty(structMaps)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap.", ctx.formatMetsPosition(ctx.getMets()));
		}
		StructMapType physicalStructMap = null;
		StructMapType logicalStructMap = null;
		for(StructMapType structMap: structMaps) {
			// CZDAX-PMT0601
			if(structMap.getTYPE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/@TYPE.", ctx.formatMetsPosition(structMap));
			}
			// CZDAX-PMT0602
			if(EarkConstants.STRUCTMAP_TYPE_PHYSICAL.equals(structMap.getTYPE())) {
				if(physicalStructMap!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nelze uvést dvě fyzické strukturální mapy.", ctx.formatMetsPosition(structMap));
				}
				physicalStructMap = structMap;
				continue;
			}
			
			// CZDAX-PMT0704
			if(EarkCz.STRUCTMAP_TYPE_LOGICAL.equals(structMap.getTYPE())) {
				if(logicalStructMap!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nelze uvést dvě logické strukturální mapy.", ctx.formatMetsPosition(structMap));
				}
				logicalStructMap = structMap;
				continue;
			}
			
			// jiny typ strukt. mapy
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepodporovaný typ strukt. mapy. Type: "+structMap.getTYPE(), ctx.formatMetsPosition(structMap));
		}
		if(physicalStructMap==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí fyzická strukturální mapa.", ctx.formatMetsPosition(ctx.getMets()));
		}			
	}


}
