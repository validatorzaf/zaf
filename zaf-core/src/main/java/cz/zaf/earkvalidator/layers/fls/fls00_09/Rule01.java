package cz.zaf.earkvalidator.layers.fls.fls00_09;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.DivType.Fptr;
import cz.zaf.schema.mets_1_12_1.FileType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.MdSecType.MdRef;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;
import cz.zaf.schema.mets_1_12_1.StructMapType;

public class Rule01 extends AipRule {
	public static final String CODE = "fls01";
	public static final String RULE_TEXT = "V balíčku jsou k dispozici všechny soubory uvedené v METS.xml.";
	public static final String RULE_ERROR = "Chybí deklarovaný soubor.";
	public static final String RULE_SOURCE = "CZDAX-PPR0307";

	public Rule01() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		// check existence of files from the package
		// iterate PHYSICAL structure map and read all linked files
		List<StructMapType> structMaps = ctx.getMets().getStructMap();
		StructMapType physicalStructMap = structMaps.stream().filter(sm -> EarkConstants.STRUCTMAP_TYPE_PHYSICAL.equals(sm.getTYPE())).findFirst().orElse(null);
		if(physicalStructMap==null) {
			throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element mets/structMap.", ctx.formatMetsPosition(ctx.getMets()));
		}
		List<DivType> divs = physicalStructMap.getDiv().getDiv();
		for(DivType div: divs) {
			String label = div.getLabelValue8();
			if(EarkConstants.STRUCTMAP_LABEL_SCHEMAS.equals(label) ||
					EarkConstants.STRUCTMAP_LABEL_DOCUMENTATION.equals(label)||
					EarkConstants.STRUCTMAP_LABEL_REPRESENTATIONS.equals(label)) {
				List<Fptr> fptrs = div.getFptr();
				for(Fptr fptr: fptrs) {
					checkFptr(fptr);
				}
			} else
			if(EarkConstants.STRUCTMAP_LABEL_METADATA.equals(label)) {
				// check metadata files
				div.getDMDID().forEach(dmdId -> {
					MdSecType mdSec = (MdSecType) dmdId;
					checkMetadata(mdSec);
				});
				div.getADMID().forEach(admId -> {
					MdSecType mdSec = (MdSecType) admId;
					checkMetadata(mdSec);					
				});
			} else {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neočekávaný type elementu mets/structMap/div/div, label: "+label+".", ctx.formatMetsPosition(div));
			}
		}
	}

	private void checkMetadata(MdSecType mdSec) {
		MdRef mdRef = mdSec.getMdRef();
		checkFile(mdRef, mdRef.getHref());
	}

	private void checkFile(Object srcObj, String href) {
		Path aipPath = ctx.getLoader().getAipPath();
		Path filePath = aipPath.resolve(href);
		// check existence of the file
		if(!Files.isRegularFile(filePath)) {
			throw new ZafException(BaseCode.CHYBI_KOMPONENTA, "Nenalezen soubor: "+filePath, 
					ctx.formatMetsPosition(srcObj));
		}
		// TODO: check file size and checksum		
	}

	private void checkFptr(Fptr fptr) {	
		Object fileId = fptr.getFILEID();
		FileGrp fileGrp = (FileGrp) fileId;
		for(FileType fileElem: fileGrp.getFile()) {
			String href = fileElem.getFLocat().get(0).getHref();
			checkFile(fileElem, href);
		}
	}

}
