package cz.zaf.sipvalidator.nsesss2017;

import java.nio.file.Files;
import java.nio.file.Path;
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

    public static final String KOMPONENTY_DIR = "komponenty";

    MetsParser metsParser;

    /**
     * Date when check is executed
     */
    final private LocalDate localDate;

    public KontrolaNsess2017Context(SipInfo sip, List<String> excludeChecks) {
        super(sip, excludeChecks);
        String extLocalDate = System.getProperty(ZAF_VALIDATION_DATE);
        if (StringUtils.isNotEmpty(extLocalDate)) {
            this.localDate = LocalDate.parse(extLocalDate);
        } else {
            this.localDate = LocalDate.now();
        }
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }

    public Document getMainDocument() {
        return metsParser.getDocument();
    }

    /**
     * Kontrola existence mets.xml
     * 
     * @return true pokud existuje mets.xml
     */
    public boolean maMetsXml() {
        var metsPath = sip.getCestaMets();
        return (metsPath == null) ? false : Files.isRegularFile(metsPath);
    }

    /**
     * Return current date
     * 
     * @return
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Vraci cestu do SIPu cesta/komponenty
     * 
     * @return
     */
    public Path getKomponentyPath() {
        return sip.getCesta().resolve(KOMPONENTY_DIR);
    }

    /**
     * Vraci cestu ke jedné komponentě
     * 
     * @return
     */
    public Path getKomponentaPath(String href) {
        return sip.getCesta().resolve(href);
    }

    public void setMetsParser(final MetsParser metsParser) {
        this.metsParser = metsParser;
    }
}
