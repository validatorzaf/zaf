package cz.zaf.earkvalidator.layers.obs.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.FileType;
import cz.zaf.schema.mets_1_12_1.FileType.FLocat;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;

public class Rule28 extends AipRule {
	public static final String CODE = "obs28";
	public static final String RULE_TEXT = "Předaná dokumentace je uvedena v elementu mets/fileSec/fileGrp[@USE='Documentation'].";
	public static final String RULE_ERROR = "Chybně odkazované soubory dokumentace.";
	public static final String RULE_SOURCE = "CZDAX-PMT0507, CZDAX-PMT0513";

	public Rule28() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		List<FileGrp> filegroups = filesec.getFileGrp();
		FileGrp filegrpDocumentation = null;
		for(FileGrp filegrp: filegroups) {
			// check each filegrp, if it contains USE=Documentation then
			// its files have to be in the folder documentation
			String use = filegrp.getUSE();
			if(!use.equals(EarkConstants.USE_DOCUMENTATION)) {
				continue;
			}
			// check if onnly one exists
			if(filegrpDocumentation!=null) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Předaná dokumentace je uvedena ve více elementech mets/fileSec/fileGrp[@USE='Documentation'].", ctx.formatMetsPosition(filesec));
			}
			filegrpDocumentation = filegrp;
			// check all pathes are documentation/...
			List<FileType> files = filegrp.getFile();
			if(CollectionUtils.isEmpty(files)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Předaná dokumentace je uvedena bez souborů v mets/fileSec/fileGrp[@USE='Documentation'].", ctx.formatMetsPosition(filesec));
			}
			for(FileType file: files) {
				List<FLocat> flocats = file.getFLocat();
				// kontrola spravnosti cest
				for(FLocat flocat: flocats) {
					String href = flocat.getHref();
					if(!href.startsWith(EarkConstants.DOCUMENTATION_DIR_NAME+"/")) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Předaná dokumentace je uvedena bez souborů v mets/fileSec/fileGrp[@USE='Documentation']/file/flocat/@href: "+href, ctx.formatMetsPosition(flocat));
					}
				}
			}			
		}
	}


}
