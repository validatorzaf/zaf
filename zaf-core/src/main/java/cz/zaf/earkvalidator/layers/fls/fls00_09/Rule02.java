package cz.zaf.earkvalidator.layers.fls.fls00_09;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.DivType;
import cz.zaf.schema.mets_1_12_1.MdSecType;
import cz.zaf.schema.mets_1_12_1.StructMapType;
import cz.zaf.schema.mets_1_12_1.DivType.Fptr;
import cz.zaf.schema.mets_1_12_1.FileType;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;

public class Rule02 extends AipRule {
	public static final String CODE = "fls02";
	public static final String RULE_TEXT = "V balíčku neexistují soubory, které nejsou uvedené v METS.xml.";
	public static final String RULE_ERROR = "V balíčku jsou nedeklarované soubory.";
	public static final String RULE_SOURCE = "CZDAX-PPR0307";

	public Rule02() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		// create list of expected files
		Set<String> expectedFiles = getExpectedFiles();
		
		// create list of files	from the package
		Path aipPath = ctx.getLoader().getAipPath();
		// depth first search
		try {
			Files.walkFileTree(aipPath, new SimpleFileVisitor<Path>(){

			    @Override
			    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			    	Path relativePath = aipPath.relativize(file);			    	
			    	String filePath = relativePath.toString().replace('\\', '/');
			    	if(!expectedFiles.contains(filePath)) {
				    	throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Nalezen nedeklarovaný soubor: "+filePath, ctx.formatMetsPosition(ctx.getMets()));
			    	}
			    	return FileVisitResult.CONTINUE;
			    }
			});
		} catch (IOException e) {
			throw new ZafException(BaseCode.CHYBA, "Chyba IO, cesta: "+aipPath, e);
		}		
	}

	private Set<String> getExpectedFiles() {
		Set<String> result = new HashSet<>();
		result.add(EarkConstants.METS_FILE_NAME);
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
					FileGrp fileGrp = (FileGrp) fptr.getFILEID();
					for(FileType fileElem: fileGrp.getFile()) {
						String href = fileElem.getFLocat().get(0).getHref();
						result.add(href);
					}
				}
			} else
			if(EarkConstants.STRUCTMAP_LABEL_METADATA.equals(label)) {
				// check metadata files
				div.getDMDID().forEach(dmdId -> {
					MdSecType mdSec = (MdSecType) dmdId;
					result.add(mdSec.getMdRef().getHref());
				});
				div.getADMID().forEach(admId -> {
					MdSecType mdSec = (MdSecType) admId;
					result.add(mdSec.getMdRef().getHref());
				});
			} else {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neočekávaný type elementu mets/structMap/div/div, label: "+label+".", ctx.formatMetsPosition(div));
			}
		}
		return result;
	}


}
