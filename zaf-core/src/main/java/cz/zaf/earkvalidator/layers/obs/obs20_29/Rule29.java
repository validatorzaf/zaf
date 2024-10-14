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

public class Rule29 extends AipRule {
	public static final String CODE = "obs29";
	public static final String RULE_TEXT = "Předaná schémata jsou uvedena v elementu mets/fileSec/fileGrp[@USE='Schemas'].";
	public static final String RULE_ERROR = "Chybně odkazované soubory schémat.";
	public static final String RULE_SOURCE = "CZDAX-PMT0508, CZDAX-PMT0513";

	public Rule29() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		List<FileGrp> filegroups = filesec.getFileGrp();
		FileGrp filegrpSchemas = null;
		for(FileGrp filegrp: filegroups) {
			// check each filegrp, if it contains USE=Schemas then
			// its files have to be in the folder schemas
			String use = filegrp.getUSE();
			if(!EarkConstants.USE_SCHEMAS.equals(use)) {
				continue;
			}
			// check if onnly one exists
			if(filegrpSchemas!=null) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Předaná schemata jsou uvedena ve více elementech mets/fileSec/fileGrp[@USE='Schemas'].", ctx.formatMetsPosition(filesec));
			}
			filegrpSchemas = filegrp;
			// check all pathes are schemas/...
			List<FileType> files = filegrp.getFile();
			if(CollectionUtils.isEmpty(files)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Předaná schemata jsou uvedena bez souborů v mets/fileSec/fileGrp[@USE='Schemas'].", ctx.formatMetsPosition(filesec));
			}
			for(FileType file: files) {
				List<FLocat> flocats = file.getFLocat();
				// kontrola spravnosti cest
				for(FLocat flocat: flocats) {
					String href = flocat.getHref();
					if(href==null) {
						throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribu mets/fileSec/fileGrp[@USE='Schemas']/file/flocat/@href.", ctx.formatMetsPosition(flocat));
					}
					if(!href.startsWith(EarkConstants.SCHEMAS_DIR_NAME+"/")) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Předaná schemata jsou bez souborů v mets/fileSec/fileGrp[@USE='Schemas']/file/flocat/@href: "+href, ctx.formatMetsPosition(flocat));
					}
				}
			}			
		}
	}


}
