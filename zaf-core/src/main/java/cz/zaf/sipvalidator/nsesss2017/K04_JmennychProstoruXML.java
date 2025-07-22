/*
 * To change this license header, cho'ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo2;

/**
 * Kontrola jmennych prostoru
 * 
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {
            
    static final public String NAME = "jmenných prostorů";

    public K04_JmennychProstoruXML() {
        super(TypUrovenKontroly.JMENNE_PROSTORY_XML);
    }


	@Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsessContext> namespCheckContext = new SimpleRuleContext<>(ctx);

        this.provedKontrolu(namespCheckContext, getRules());
	}


	public static List<? extends PravidloBase> getRules() {		
		return List.of(new Pravidlo1(), 
                new Pravidlo2()
				);
	}
}
