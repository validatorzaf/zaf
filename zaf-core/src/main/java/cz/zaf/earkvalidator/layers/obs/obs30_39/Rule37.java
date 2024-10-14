package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.AmdSecType;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.DivType.Fptr;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;
import cz.zaf.schema.mets_1_12_1.StructMapType;

public class Rule37 extends AipRule {
	public static final String CODE = "obs37";
	public static final String RULE_TEXT = "Správná podoba fyzické strukturální mapy.";
	public static final String RULE_ERROR = "Fyzická strukturální mapa obsahuje chybné záznamy.";
	public static final String RULE_SOURCE = "CZDAX-PMT0607, CZDAX-PMT0608, CZDAX-PMT0609, CZDAX-PMT0610, CZDAX-PMT0611, CZDAX-PMT0612, CZDAX-PMT0613, CZDAX-PMT0614, CZDAX-PMT0615, CZDAX-PMT0616, CZDAX-PMT0617, CZDAX-PMT0618, CZDAX-PMT0619, CZDAX-PMT0620, CZDAX-PMT0621, CZDAX-PMT0622, CZDAX-PMT0623, CZDAX-PMT0624, CZDAX-PMT0625, CZDAX-PMT0626, CZDAX-PMT0627";

	public Rule37() {
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
		
		// ověření správnosti fyzické strukturální mapy
		// existuje ze schematu
		DivType mainDiv = physicalStructMap.getDiv();
		// CZDAX-PMT0607
		String mainId = mainDiv.getID();
		if(StringUtils.isBlank(mainId)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/@ID.", ctx.formatMetsPosition(mainDiv));
		}
		if(!ValidatorId.checkFormatId(mainId)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/div/@ID.", ctx.formatMetsPosition(mainDiv));
		}
		// check all subdivs
		List<DivType> subDivs = mainDiv.getDiv();
		if(CollectionUtils.isEmpty(subDivs)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div.", ctx.formatMetsPosition(mainDiv));
		}
		
		checkDivs(subDivs);
	}

	private void checkDivs(List<DivType> divs) {
		DivType metadataDiv = null;
		DivType documentationDiv = null;
		DivType schemasDiv = null;
		DivType representationsDiv = null;
		
		for(DivType div: divs) {
			// CZDAX-PMT0609, CZDAX-PMT0614, CZDAX-PMT0619, CZDAX-PMT0624
			String id = div.getID();
			if(StringUtils.isBlank(id)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/@ID.", ctx.formatMetsPosition(div));
			}
			if(!ValidatorId.checkFormatId(id)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/div/div/@ID.", ctx.formatMetsPosition(div));
			}
			
			String label = div.getLabelValue8();
			if(StringUtils.isBlank(label)) {
				throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/@LABEL.", ctx.formatMetsPosition(div));
			}

			// CZDAX-PMT0608
			if(EarkConstants.STRUCTMAP_LABEL_METADATA.equals(label)) {
				if(metadataDiv!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Element se shodným LABEL již existuje.", ctx.formatMetsPosition(div));
				}
				metadataDiv = div;
			}
			// CZDAX-PMT0613
			else if(EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION.equals(label)) {
				if(documentationDiv!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Element se shodným LABEL již existuje.", ctx.formatMetsPosition(div));
				}
				documentationDiv = div;
			}
			// CZDAX-PMT0618
			else if(EarkConstants.STRUCTMAP_LABEL_SCHEMAS.equals(label)) {
				if(schemasDiv!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Element se shodným LABEL již existuje.", ctx.formatMetsPosition(div));
				}
				schemasDiv = div;
			}
			// CZDAX-PMT0623
			else if(EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS.equals(label)) {
				if(representationsDiv!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Element se shodným LABEL již existuje.", ctx.formatMetsPosition(div));
				}
				representationsDiv = div;
			}
			else {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/structMap/div/div/@LABEL: " + label, ctx.formatMetsPosition(div));
			}
		}
		
		checkMetadataDiv(metadataDiv);
		checkDocumentationDiv(documentationDiv);
		checkSchemasDiv(schemasDiv);
		checkRepresentationsDiv(representationsDiv);
		
	}

	private void checkDocumentationDiv(DivType div) {
		FileGrp fileGrp = null;
		// read documentation from fileSec
		FileSec fileSec = ctx.getMets().getFileSec();
		if(fileSec!=null) {
			for(FileGrp grp: fileSec.getFileGrp()) {
				if(EarkConstants.USE_DOCUMENTATION.equals(grp.getUSE())) {
					fileGrp = grp;
					break;
				}
			}			
		}
		if(fileGrp==null) {
			if(div==null) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/fileSec/fileGrp[@USE='"+EarkConstants.USE_DOCUMENTATION+"']", ctx.formatMetsPosition(div));
		}
		if(div==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION+"']", ctx.formatMetsPosition(fileGrp));
		}
		// CZDAX-PMT0616
		if(CollectionUtils.isNotEmpty(div.getDiv())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION+"']/div.", ctx.formatMetsPosition(div.getDiv().get(0)));
		}
		if(CollectionUtils.isNotEmpty(div.getMptr())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION+"']/mptr.", ctx.formatMetsPosition(div.getMptr().get(0)));
		}
		List<Fptr> fptrs = div.getFptr();
		if(CollectionUtils.isEmpty(fptrs)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION+"']/fptr.", ctx.formatMetsPosition(div));
		}
		if(fptrs.size()>1) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION+"']/fptr.", ctx.formatMetsPosition(fptrs.get(1)));
		}
		Fptr fptr = fptrs.get(0);
		checkFptrWithId(fptr, fileGrp, fileGrp.getID());
	}

	private void checkFptrWithId(Fptr fptr, Object fileIdLink, String expectedFileId) {
		Object fileId = fptr.getFILEID();
		if(fileId==null) {
			throw new ZafException (BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/structMap/div/div/fptr/@FILEID.", ctx.formatMetsPosition(fptr));
		}
		// both elements should have same id
		if(fileId!=fileIdLink) {
			throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Nenalezen platný odkaz. Očekávaná hodnota @FILEID: "+expectedFileId, ctx.formatMetsPosition(fptr));
		}
	}

	private void checkRepresentationsDiv(DivType div) {
		FileSec fileSec = ctx.getMets().getFileSec();
		
		// get all filesec groups
		if(div==null) {
			if(fileSec==null) {
				return;
			}
			List<FileGrp> fileGrps = fileSec.getFileGrp();
			if(CollectionUtils.isEmpty(fileGrps)) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/fileSec/fileGrp[@USE='"+EarkConstants.USE_REPRESENTATIONS+"']", ctx.formatMetsPosition(div));
		}
		Set<FileGrp> fileGrps = new HashSet<>();
		for(FileGrp grp: fileSec.getFileGrp()) {
			String use = grp.getUSE();
			if(use!=null && use.startsWith(EarkConstants.USE_REPRESENTATIONS+"/")) {
				fileGrps.add(grp);
			}
		}			
		
		if(CollectionUtils.isNotEmpty(div.getADMID())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatný atribut mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS+"']/@ADMID.", ctx.formatMetsPosition(div));
		}
		if(CollectionUtils.isNotEmpty(div.getDMDID())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatný atribut mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS+"']/@DMDID.", ctx.formatMetsPosition(div));
		}
		if(CollectionUtils.isNotEmpty(div.getDiv())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatný element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS+"']/div.", ctx.formatMetsPosition(div));
		}
		if(CollectionUtils.isNotEmpty(div.getMptr())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatný element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS+"']/mptr.", ctx.formatMetsPosition(div));
		}
		if(CollectionUtils.isNotEmpty(div.getCONTENTIDS())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neplatný atribut mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS+"']/@CONTENTIDS.", ctx.formatMetsPosition(div));
		}
		
		// get all fptrs
		List<Fptr> fptrs = div.getFptr();
		for(Fptr fptr: fptrs) {
			Object fileRef = fptr.getFILEID();
			if(!fileGrps.remove(fileRef)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neočekávaná hodnota @FILEID: "+fileRef, ctx.formatMetsPosition(fptr));
			}			
		}
		// Kontrola z druhe strany
		if(fileGrps.size()>0) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Neodkazovan8 reprezentace v elementu mets/fileSec/fileGrp[@USE='"+EarkConstants.USE_REPRESENTATIONS+"'], ID: "+fileGrps.iterator().next().getID(), ctx.formatMetsPosition(div));
		}
		
	}

	private void checkSchemasDiv(DivType div) {
		FileGrp fileGrp = null;
		// read documentation from fileSec
		FileSec fileSec = ctx.getMets().getFileSec();
		if(fileSec!=null) {
			for(FileGrp grp: fileSec.getFileGrp()) { 
				if(EarkConstants.USE_SCHEMAS.equals(grp.getUSE())) {
					fileGrp = grp;
					break;
				}
			}			
		}
		if(fileGrp==null) {
			if(div==null) {
				return;
			}
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/fileSec/fileGrp[@USE='"+EarkConstants.USE_SCHEMAS+"']", ctx.formatMetsPosition(div));
		}
		if(div==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_SCHEMAS+"']", ctx.formatMetsPosition(fileGrp));
		}
		// CZDAX-PMT0616
		if(CollectionUtils.isNotEmpty(div.getDiv())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_SCHEMAS+"']/div.", ctx.formatMetsPosition(div.getDiv().get(0)));
		}
		if(CollectionUtils.isNotEmpty(div.getMptr())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_SCHEMAS+"']/mptr.", ctx.formatMetsPosition(div.getMptr().get(0)));
		}
		List<Fptr> fptrs = div.getFptr();
		if(CollectionUtils.isEmpty(fptrs)) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_SCHEMAS+"']/fptr.", ctx.formatMetsPosition(div));
		}
		if(fptrs.size()>1) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_SCHEMAS+"']/fptr.", ctx.formatMetsPosition(fptrs.get(1)));
		}
		Fptr fptr = fptrs.get(0);
		checkFptrWithId(fptr, fileGrp, fileGrp.getID());		
	}

	private void checkMetadataDiv(DivType div) {
		// some metadata have to exists
		if(div==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_METADATA+"']", ctx.formatMetsPosition(ctx.getMets().getStructMap().get(0)));
		}
		
		// CZDAX-PMT0616
		if(CollectionUtils.isNotEmpty(div.getDiv())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_METADATA+"']/div.", ctx.formatMetsPosition(div.getDiv().get(0)));
		}
		if(CollectionUtils.isNotEmpty(div.getMptr())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_METADATA+"']/mptr.", ctx.formatMetsPosition(div.getMptr().get(0)));
		}
		if(CollectionUtils.isNotEmpty(div.getFptr())) {
			throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Vnořený element mets/structMap/div/div[@LABEL='"+EarkConstants.STRUCTMAP_LABEL_METADATA+"']/fptr.", ctx.formatMetsPosition(div.getFptr().get(0)));
		}
		// check DMDSec
		List<MdSecType> dmdSecs = ctx.getMets().getDmdSec();
		Set<Object> dmdRefs = div.getDMDID().stream().collect(Collectors.toSet());
		for(MdSecType dmdSec: dmdSecs) {
			if(!dmdRefs.contains(dmdSec)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen atribut DMDID v mets/structMap/div/div odkazujici na:"+dmdSec.getID(), ctx.formatMetsPosition(div));
			}
		}
		if(dmdRefs.size()!=dmdSecs.size()) {
			// dohledani druheho smeru
			for(Object obj: dmdRefs) {
				if(!dmdSecs.contains(obj)) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "V atributy DMDID v mets/structMap/div/div je neplatný odkaz odkazujici na:"+obj, ctx.formatMetsPosition(div));
				}
			}
		}

		// check AMDSec
		List<AmdSecType> amdSecs = ctx.getMets().getAmdSec();
		Set<MdSecType> adms = new HashSet<>();
		// Collect all administrative metadata
		for(AmdSecType amdSec: amdSecs) {
			adms.addAll(amdSec.getDigiprovMD());
			adms.addAll(amdSec.getRightsMD());
			adms.addAll(amdSec.getSourceMD());
			adms.addAll(amdSec.getTechMD());
		}
		Set<Object> amdRefs = div.getADMID().stream().collect(Collectors.toSet());
		for(MdSecType amdSec: adms) {
			if(!amdRefs.contains(amdSec)) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezen atribut AMDID v mets/structMap/div/div odkazujici na:"+amdSec.getID(), ctx.formatMetsPosition(div));
			}
		}
		if(amdRefs.size()!=adms.size()) {
			// dohledani druheho smeru
			for(Object obj: amdRefs) {
				if(!adms.contains(obj)) {
					throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "V atributy AMDID v mets/structMap/div/div je neplatný odkaz odkazujici na:"+obj, ctx.formatMetsPosition(div));
				}
			}
		}
		
	}


}
