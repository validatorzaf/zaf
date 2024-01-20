package cz.zaf.sipvalidator.nsesss2017.pravidla07;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.MetsParser;
import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SipInfo;

/**
 * Kontext běžící kontroly pravidel
 */
public class K07KontrolaContext {

    private final List<Element> nodeListMetsFile;
    private final MetsParser metsParser;
    private KontrolaContext kontrolaCtx;

    public K07KontrolaContext(final MetsParser metsParser, KontrolaContext kontrolaCtx) {
        this.kontrolaCtx = kontrolaCtx;
        this.metsParser = metsParser;
        this.nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
    }

    /**
     * Vrací seznam komponent FILE v METS.xml
     * 
     * @return
     */
    public List<Element> getMetsFiles() {
        return nodeListMetsFile;
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }

    public SipInfo getSip() {
        return kontrolaCtx.getSip();
    }
}
