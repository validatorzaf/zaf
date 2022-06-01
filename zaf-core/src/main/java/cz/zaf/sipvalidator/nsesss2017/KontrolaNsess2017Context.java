package cz.zaf.sipvalidator.nsesss2017;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;

public class KontrolaNsess2017Context extends KontrolaContext {

    /**
     * Property to set date of validation as external parameter
     * 
     * Format of date is yyyy-mm-dd
     */
    public static final String ZAF_VALIDATION_DATE = "zaf.validation.date";

    final MetsParser metsParser;

    /**
     * Date when check is executed
     */
    final private LocalDate localDate;

    public KontrolaNsess2017Context(MetsParser mp, SipInfo sip, List<String> excludeChecks) {
        super(sip, excludeChecks);
        this.metsParser = mp;
        String extLocalDate = System.getProperty(ZAF_VALIDATION_DATE);
        if (StringUtils.isNotEmpty(extLocalDate)) {
            this.localDate = LocalDate.parse(extLocalDate);
        } else {
            this.localDate = LocalDate.now();
        }
    }

    MetsParser getMetsParser() {
        return metsParser;
    }

    Document getMainDocument() {
        return metsParser.getDocument();
    }

    /**
     * Kontrola existence mets.xml
     * 
     * @return true pokud existuje mets.xml
     */
    public boolean maMetsXml() {
        File m = sip.getCestaMets().toFile();
        return m.exists();
    }

    /**
     * Return current date
     * 
     * @return
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

}
