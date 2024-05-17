/*
 * To change this license header, cho'ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.NsCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.NsCheckRule;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo2;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola jmennych prostoru
 * 
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase<NsCheckContext> {
            
    static final public String NAME = "jmenných prostorů";

    public K04_JmennychProstoruXML() {
    }


	@Override
    public void provedKontrolu() {

        NsCheckContext namespCheckContext = new NsCheckContext(ctx);

        NsCheckRule rules[] = {
                new Pravidlo1(),
                new Pravidlo2(),
        };

        this.provedKontrolu(namespCheckContext, rules);
	}

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.JMENNE_PROSTORY_XML;
    }
}
