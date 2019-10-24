package cz.zaf.sipvalidator.nsesss2017;

import java.io.File;

import org.w3c.dom.Document;

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;

public class KontrolaNsess2017Context extends KontrolaContext {

    final MetsParser metsParser;

    public KontrolaNsess2017Context(MetsParser mp, SipInfo sip) {
        super(sip);
        this.metsParser = mp;
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
     * @return
     */
    public boolean maMetsXml() {
        File m = sip.getCestaMets().toFile();
        return m.exists();
    }

}
