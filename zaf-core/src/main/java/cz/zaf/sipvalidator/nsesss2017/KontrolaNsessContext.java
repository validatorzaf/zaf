package cz.zaf.sipvalidator.nsesss2017;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.w3c.dom.Document;

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;

public class KontrolaNsessContext extends KontrolaContext {

    public static final String KOMPONENTY_DIR = "komponenty";

    MetsParser metsParser;

    public KontrolaNsessContext(SipInfo sip, List<String> excludeChecks) {
        super(sip, excludeChecks);
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
