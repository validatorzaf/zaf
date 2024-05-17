package cz.zaf.sipvalidator.nsesss2017.pravidla07;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.KontrolaNsess2017Context;
import cz.zaf.sipvalidator.nsesss2017.MetsParser;
import cz.zaf.sipvalidator.sip.SipInfo;

/**
 * Kontext běžící kontroly pravidel
 */
public class K07KontrolaContext implements RuleEvaluationContext {

    private final List<Element> nodeListMetsFile;
    private final MetsParser metsParser;
    private KontrolaNsess2017Context kontrolaCtx;

    public K07KontrolaContext(final MetsParser metsParser, KontrolaNsess2017Context kontrolaCtx) {
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

    public KontrolaNsess2017Context getContext() {
        return kontrolaCtx;
    }
}
