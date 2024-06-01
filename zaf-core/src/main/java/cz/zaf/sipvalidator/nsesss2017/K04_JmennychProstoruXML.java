/*
 * To change this license header, cho'ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.NsCheckRule;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo2;

/**
 * Kontrola jmennych prostoru
 * 
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase<SimpleRuleContext<KontrolaNsess2017Context>> {
            
    static final public String NAME = "jmenných prostorů";

    public K04_JmennychProstoruXML() {
        super(TypUrovenKontroly.JMENNE_PROSTORY_XML);
    }


	@Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsess2017Context> namespCheckContext = new SimpleRuleContext<>(ctx);

        NsCheckRule rules[] = {
                new Pravidlo1(),
                new Pravidlo2(),
        };

        this.provedKontrolu(namespCheckContext, rules);
	}
}
