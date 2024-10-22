package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkCz;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.DivType.Fptr;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;
import cz.zaf.schema.mets_1_12_1.StructMapType;

public class Rule38 extends AipRule {
	public static final String CODE = "obs38";
	public static final String RULE_TEXT = "Správná podoba logické strukturální mapy.";
	public static final String RULE_ERROR = "Logická strukturální mapa obsahuje chybné záznamy.";
	public static final String RULE_SOURCE = "CZDAX-PMT0704, CZDAX-PMT0705, CZDAX-PMT0706, CZDAX-PMT0707, CZDAX-PMT0708, CZDAX-PMT0709, CZDAX-PMT0710, CZDAX-PMT0711, CZDAX-PMT0712";
	
	
	private String contentType;
	// create stack to store last level type
	private LinkedList<String> levelTypes = new LinkedList<>();

	public Rule38() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		List<StructMapType> structMaps = ctx.getMets().getStructMap();
		if(CollectionUtils.isEmpty(structMaps)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap.", ctx.formatMetsPosition(ctx.getMets()));
		}
		StructMapType logicalStructMap = null;
		for(StructMapType structMap: structMaps) {
			// CZDAX-PMT0601
			if(structMap.getTYPE() == null) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/@TYPE.", ctx.formatMetsPosition(structMap));
			}
			// CZDAX-PMT0704
			if(EarkCz.STRUCTMAP_TYPE_LOGICAL.equals(structMap.getTYPE())) {
				if(logicalStructMap!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nelze uvést dvě logické strukturální mapy.", ctx.formatMetsPosition(structMap));
				}
				logicalStructMap = structMap;
				continue;
			}
		}
		// logical map is not mandatory
		if(logicalStructMap==null) {
			return;
		}
		
		// get content type
		Map<QName, String> otherAttrs = ctx.getMets().getOtherAttributes();
		if(otherAttrs==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut csip:OTHERCONTENTINFORMATIONTYPE", ctx.formatMetsPosition(ctx.getMets()));
		}
		this.contentType = otherAttrs.get(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.OTHERCONTENTINFORMATIONTYPE));
		
		// CZDAX-PMT0705
		String name = logicalStructMap.getLabelValue2();
		if(!EarkCz.STRUCTMAP_LOGICAL_NAME.equals(name)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybné jméno logické strukturální mapy, mets/structMap/div/@LABEL, hodnota: "+name+", očekávaná hodnota: "+EarkCz.STRUCTMAP_LOGICAL_NAME+".", ctx.formatMetsPosition(logicalStructMap));
		}

		// ověření správnosti logické strukturální mapy
		// existuje ze schematu
		DivType mainDiv = logicalStructMap.getDiv();
		// CZDAX-PMT0706
		String mainId = mainDiv.getID();
		if(StringUtils.isBlank(mainId)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/@ID.", ctx.formatMetsPosition(mainDiv));
		}
		if(!ValidatorId.checkFormatId(mainId)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/div/@ID.", ctx.formatMetsPosition(mainDiv));
		}		
		// 		
		checkDiv(mainDiv);
	}

	private void checkDiv(DivType div) {
			// CZDAX-PMT0708
			String id = div.getID();
			if(StringUtils.isBlank(id)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/@ID.", ctx.formatMetsPosition(div));
			}
			if(!ValidatorId.checkFormatId(id)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/div/div/@ID.", ctx.formatMetsPosition(div));
			}
			// CZDAX-PMT0709
			String label = div.getLabelValue8();
			if(StringUtils.isBlank(label)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/@LABEL.", ctx.formatMetsPosition(div));
			}
			
			// CZDAX-PMT0710
			String type = div.getTYPE();
			if(StringUtils.isBlank(type)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/@TYPE.", ctx.formatMetsPosition(div));
			}
			validateLevelType(div, type);

			// CZDAX-PMT0711 - fptr
			if(CollectionUtils.isNotEmpty(div.getMptr())) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div/mptr.", ctx.formatMetsPosition(div.getMptr().get(0)));
			}
			List<Fptr> fptrs = div.getFptr();
			if(!CollectionUtils.isEmpty(fptrs)) {
				for(Fptr fptr:fptrs) {
					checkFptrWithId(fptr);
				}		
			}
			
			levelTypes.push(type);
			// check inner divs			
			for(DivType innerDiv: div.getDiv()) {
				checkDiv(innerDiv);
			}
			levelTypes.pop();
	}

	private void validateLevelType(DivType div, String type) {
		if(EarkCz.CONTENT_TYPE_NSESSS.equals(contentType)) {
			// CZDAX-SLM0101, CZDAX-SLM0102
			validateLevelNsesss(div, type);
		} else
		if(EarkCz.CONTENT_TYPE_VOLNE_SOUBORY.equals(contentType)) {
			// CZDAX-VSL0101
			validateLevelVolneSoubory(div, type);
		}		
	}

	private void validateLevelVolneSoubory(DivType div, String type) {
		switch(type) {
		case EarkCz.VOLNE_SOUBORY_LEVEL_BALICEK:
			if(levelTypes.peek()==null) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Kořenem logické strukt. mapy musí být balicek.", ctx.formatMetsPosition(div));
		case EarkCz.VOLNE_SOUBORY_LEVEL_SLOZKA:
			if(EarkCz.VOLNE_SOUBORY_LEVEL_SLOZKA.equals(levelTypes.peek())||EarkCz.VOLNE_SOUBORY_LEVEL_BALICEK.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem složky musí být složka nebo balíček.", ctx.formatMetsPosition(div));
		case EarkCz.VOLNE_SOUBORY_LEVEL_KOMPONENTA:
			if(EarkCz.VOLNE_SOUBORY_LEVEL_SLOZKA.equals(levelTypes.peek())||EarkCz.VOLNE_SOUBORY_LEVEL_BALICEK.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem souboru musí být složka nebo balíček.", ctx.formatMetsPosition(div));
		}
		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neočekávaný typ úrovně: "+type+".", ctx.formatMetsPosition(div));
	}

	private void validateLevelNsesss(DivType div, String type) {
		switch(type) {
		case EarkCz.NSESSS_LEVEL_SPISPLAN:
			if(levelTypes.peek()==null) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Kořenem logické strukt. mapy musí být spisový plán.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_VECNASKP:
			if(EarkCz.NSESSS_LEVEL_VECNASKP.equals(levelTypes.peek())||EarkCz.NSESSS_LEVEL_SPISPLAN.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem věcné skupiny musí být věcná skupina nebo spisový plán.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_DOKUMENT:
			if(EarkCz.NSESSS_LEVEL_VECNASKP.equals(levelTypes.peek())||
					EarkCz.NSESSS_LEVEL_SPIS.equals(levelTypes.peek())||
					EarkCz.NSESSS_LEVEL_DIL.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem dokumentu musí být věcná skupina nebo spis nebo díl.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_SPIS:
			if(EarkCz.NSESSS_LEVEL_VECNASKP.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem spisu musí být věcná skupina.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_KOMPONENTA:
			if(EarkCz.NSESSS_LEVEL_DOKUMENT.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem komponenty musí být dokument.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_VERKOMPONENTY:
			if(EarkCz.NSESSS_LEVEL_DOKUMENT.equals(levelTypes.peek())||EarkCz.NSESSS_LEVEL_KOMPONENTA.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem verze komponenty musí být dokument nebo komponenta.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_TYPSPIS:
			if(EarkCz.NSESSS_LEVEL_VECNASKP.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem typového spisu musí být věcná skupina.", ctx.formatMetsPosition(div));
		case EarkCz.NSESSS_LEVEL_DIL:
			if(EarkCz.NSESSS_LEVEL_TYPSPIS.equals(levelTypes.peek())) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Rodičem dílu typového spisu musí být typový spis.", ctx.formatMetsPosition(div));
		}
		
		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neočekávaný typ úrovně: "+type+".", ctx.formatMetsPosition(div));
	}

	private void checkFptrWithId(Fptr fptr) {
		Object fileId = fptr.getFILEID();
		if(fileId==null) {
			throw new ZafException (BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/fptr/@FILEID.", ctx.formatMetsPosition(fptr));
		}
	}
}
