package cz.zaf.earkvalidator.layers.obs.obs30_39;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.earkvalidator.AipRule;
import cz.zaf.earkvalidator.eark.EarkConstants;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec;
import cz.zaf.schema.mets_1_12_1.MetsType.FileSec.FileGrp;
import cz.zaf.schemas.eark.CSIPExtensionMETS_NS;

public class Rule31 extends AipRule {
	public static final String CODE = "obs31";
	public static final String RULE_TEXT = "Správný zápis elementu reprezentace.";
	public static final String RULE_ERROR = "Chybně uveden typ reprezentace.";
	public static final String RULE_SOURCE = "CZDAX-PMT0511, CZDAX-PMT0512";

	public Rule31() {
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
			if(!use.startsWith(EarkConstants.USE_REPRESENTATIONS)) {
				continue;
			}

			Map<QName, String> otherattrs = filegrp.getOtherAttributes();
			if(otherattrs==null) {
				return;
			}
			
			// CZDAX-PMT0511
			// check if contains @csip:CONTENTINFORMATIONTYPE
			if(otherattrs.containsKey(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.CONTENTINFORMATIONTYPE))) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "V mets/fileSec/fileGrp[@USE='"+use+"'] je chybně uveden atribut csip:CONTENTINFORMATIONTYPE.", ctx.formatMetsPosition(filegrp));
			}
			
			// CZDAX-PMT0512
			// check if contains @csip:OTHERCONTENTINFORMATIONTYPE
			if(otherattrs.containsKey(new QName(CSIPExtensionMETS_NS.NS_CSIP, CSIPExtensionMETS_NS.OTHERCONTENTINFORMATIONTYPE))) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "V mets/fileSec/fileGrp[@USE='"+use+"'] je chybně uveden atribut csip:OTHERCONTENTINFORMATIONTYPE.", ctx.formatMetsPosition(filegrp));
			}
		}
	}

}
