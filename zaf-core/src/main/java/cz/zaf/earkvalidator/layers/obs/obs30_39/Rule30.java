package cz.zaf.earkvalidator.layers.obs.obs30_39;

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

public class Rule30 extends AipRule {
	public static final String CODE = "obs30";
	public static final String RULE_TEXT = "Uvedení odkazů na reprezentace v elementu mets/fileSec/fileGrp[@USE=[starts-with('Representations')]]";
	public static final String RULE_ERROR = "Chybně odkazované soubory reprezentací.";
	public static final String RULE_SOURCE = "CZDAX-PMT0509, CZDAX-PMT0513";

	public Rule30() {
		super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
	}
	
	@Override
	public void evalImpl() {
		FileSec filesec = ctx.getMets().getFileSec();
		if(filesec==null) {
			return;
		}
		List<FileGrp> filegroups = filesec.getFileGrp();

		for(FileGrp filegrp: filegroups) {
			// check each filegrp, if it contains USE=Representations/ then
			// its files have to be in the folder representations/
			String use = filegrp.getUSE();
			if(!use.startsWith(EarkConstants.USE_REPRESENTATIONS+"/")) {
				continue;
			}
			
			// check all pathes are schemas/...
			List<FileType> files = filegrp.getFile();
			if(CollectionUtils.isEmpty(files)) {
				throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Předaná reprezentact bez souborů v mets/fileSec/fileGrp[@USE='"+use+"'].", ctx.formatMetsPosition(filesec));
			}
			for(FileType file: files) {
				List<FLocat> flocats = file.getFLocat();
				// kontrola spravnosti cest
				for(FLocat flocat: flocats) {
					String href = flocat.getHref();
					if(href==null) {
						throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribu mets/fileSec/fileGrp[@USE='"+use+"']/file/flocat/@href.", ctx.formatMetsPosition(flocat));
					}
					if(!href.startsWith(EarkConstants.REPRESENTATIONS_DIR_NAME+"/")) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Předaná schemata jsou bez souborů v mets/fileSec/fileGrp[@USE='"+use+"']/file/flocat/@href: "+href, ctx.formatMetsPosition(flocat));
					}
					
					// nazev slozky a reprezentace musí být shodný s nazvem odkazu na reprezentací
					String dirName = href.substring(EarkConstants.REPRESENTATIONS_DIR_NAME.length()+1, href.length());
					String repName = use.substring(EarkConstants.USE_REPRESENTATIONS.length()+1, use.length());
					if(!dirName.startsWith(repName)) {
						throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
								"Název složky musí být shodný s názvem reprezentace, href: "+href, 
								ctx.formatMetsPosition(flocat));
					}
				}
			}
			
		}	
	}
}
