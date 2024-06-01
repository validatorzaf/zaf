package cz.zaf.sipvalidator.nsesss2017.pravidla06;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.result.EntityId;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.KontrolaNsess2017Context;
import cz.zaf.sipvalidator.nsesss2017.MetsParser;

public class K06KontrolaContext implements RuleEvaluationContext {
    private final MetsParser metsParser;
    private KontrolaNsess2017Context kontrolaCtx;

    public K06KontrolaContext(final MetsParser metsParser, KontrolaNsess2017Context kontrolaCtx) {
        this.kontrolaCtx = kontrolaCtx;
        this.metsParser = metsParser;
    }

    public MetsParser getMetsParser() {
        return metsParser;
    }

    public KontrolaNsess2017Context getContext() {
        return kontrolaCtx;
    }

    public List<Element> getZakladniEnity() {
        return metsParser.getZakladniEntity();
    }

    public EntityId getEntityId(Element dokument) {
        return K06_Obsahova.getEntityId(dokument);
    }

    public String getIdentifikatory(Element dokument) {
        return K06_Obsahova.getIdentifikatory(dokument);
    }

    public Element getEntity(Element jazyk) {
        return K06_Obsahova.getEntity(jazyk);
    }

    public List<EntityId> getEntityId(List<Element> listElementu) {
        return K06_Obsahova.getEntityId(listElementu);
    }

    public String getJmenoIdentifikator(Element zakladniEntita) {
        return K06_Obsahova.getJmenoIdentifikator(zakladniEntita);
    }

}
