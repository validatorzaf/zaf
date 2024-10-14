package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeType;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.ValidatorId;
import cz.zaf.schema.mets_1_12_1.FileType;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;

public class Rule33 extends AipRule {
	public static final String CODE = "obs33";
	public static final String RULE_TEXT = "Správně uveden odkaz na soubor v elementu mets/fileSec/fileGrp/file.";
	public static final String RULE_ERROR = "Odkaz na soubor v elementu mets/fileSec/fileGrp/file není úplný a správně vyplněný.";
	public static final String RULE_SOURCE = "CZDAX-PMT0515, CZDAX-PMT0516, CZDAX-PMT0517, CZDAX-PMT0518, CZDAX-PMT0519, CZDAX-PMT0520, CZDAX-PMT0521, CZDAX-PMT0522, CZDAX-PMT0524";

	public Rule33() {
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
			for(FileType file: files) {
				// check all file
				checkFileElement(file);
			}
		}
	}

	private void checkFileElement(FileType file) {
		// CZDAX-PMT0516
		// mets/fileSec/fileGrp/file/@ID
		String id = file.getID();
		if(StringUtils.isBlank(id)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Skupina souborů nemá uveden identifikátor.", ctx.formatMetsPosition(file));
		}
		// check ID format
		if(!ValidatorId.checkFormatId(id)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybná hodnota atributu mets/fileSec/fileGrp/file/@ID, value: "+id+".", ctx.formatMetsPosition(file));
		}
		
		// CZDAX-PMT0517
		// mets/fileSec/fileGrp/file/@MIMETYPE
		String mimetype = file.getMIMETYPE();
		if(StringUtils.isBlank(mimetype)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí atribut MIMETYPE", ctx.formatMetsPosition(file));
		}
		// check correct format of mimetype
		if(!MimeType.isValid(mimetype)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Chybna hodnota atribut MIMETYPE: "+mimetype, ctx.formatMetsPosition(file));
		}
		// CZDAX-PMT0518
		Long fileSize = file.getSIZE();
		if(fileSize==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/filesec/fileGrp/file/@SIZE.", ctx.formatMetsPosition(file));
		}
		//CZDAX-PMT0519
		XMLGregorianCalendar created = file.getCREATED();
		if(created==null) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/filesec/fileGrp/file/@CREATED.", ctx.formatMetsPosition(file));
		}
		//CZDAX-PMT0520
		String checksum = file.getCHECKSUM();
		if(StringUtils.isEmpty(checksum)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/filesec/fileGrp/file/@CHECKSUM.", ctx.formatMetsPosition(file));			
		}
		//CZDAX-PMT0521
		String checksumType = file.getCHECKSUMTYPE();
		if(StringUtils.isEmpty(checksumType)) {
			throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut mets/filesec/fileGrp/file/@CHECKSUMTYPE.", ctx.formatMetsPosition(file));						
		}
		//CZDAX-PMT0522
		String ownerid = file.getOWNERID();
		if(StringUtils.isNotEmpty(ownerid)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nalezen zakázaný atribut mets/filesec/fileGrp/file/@OWNERID.", ctx.formatMetsPosition(file));
		}
		//CZDAX-PMT0524
		List<Object> dmdids = file.getDMDID();
		if(!CollectionUtils.isEmpty(dmdids)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nalezen zakázaný atribut mets/filesec/fileGrp/file/@DMDID.", ctx.formatMetsPosition(file));
		}
	}

}
