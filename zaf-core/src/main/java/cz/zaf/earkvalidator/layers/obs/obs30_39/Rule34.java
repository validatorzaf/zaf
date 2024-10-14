package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.schema.mets_1_12_1.FileType;
import cz.zaf.schema.mets_1_12_1.FileType.FContent;
import cz.zaf.schema.mets_1_12_1.FileType.FLocat;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;
import cz.zaf.schemas.mets.MetsNS;
import cz.zaf.schemas.mets.XLinkNS;

public class Rule34 extends AipRule {
	public static final String CODE = "obs34";
	public static final String RULE_TEXT = "Správné uvedení odkazu na umístění souboru v elementu mets/fileSec/fileGrp/file/FLocat.";
	public static final String RULE_ERROR = "Chybně uveden odkaz na soubor.";
	public static final String RULE_SOURCE = "CZDAX-PMT0525, CZDAX-PMT0526, CZDAX-PMT0527, CZDAX-PMT0528";

	public Rule34() {
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
			List<FileType> files = filegrp.getFile();
			String use = filegrp.getUSE();
			for(FileType file: files) {
				// CZDAX-PMT0525
				FContent fileContent = file.getFContent();
				if(fileContent!=null) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neočekávaný element", ctx.formatMetsPosition(fileContent));
				}
				List<FileType> subfiles = file.getFile();
				if(CollectionUtils.isNotEmpty(subfiles)) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neočekávaný element", ctx.formatMetsPosition(subfiles.get(0)));
				}
				List<FLocat> flocats = file.getFLocat();
				if(flocats.size()>1) {
					throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Neočekávná vícenásobná definice flocat.", ctx.formatMetsPosition(flocats.get(1)));
				}
				for(FLocat flocat: flocats) {
					// CZDAX-PMT0526
					String loctype = flocat.getLOCTYPE();
					if(!MetsNS.LOCTYPE_URL.equals(loctype)) {
						throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/fileSec/fileGrp[@USE='"+use+"']/file/flocat/@LOCTYPE s hodnotou URL.", ctx.formatMetsPosition(flocat));
					}
					// CZDAX-PMT0527
					String xlinkType = flocat.getType();
					if(!XLinkNS.TYPE_SIMPLE.equals(xlinkType)) {
						throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/fileSec/fileGrp[@USE='"+use+"']/file/flocat/@@xlink:type s hodnotou simple.", ctx.formatMetsPosition(flocat));
					}
					
					// CZDAX-PMT0528
					String href = flocat.getHref();
					if(StringUtils.isBlank(href)) {
						throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribu mets/fileSec/fileGrp[@USE='"+use+"']/file/flocat/@href.", ctx.formatMetsPosition(flocat));
					}				
				}
			}
		}
	}


}
