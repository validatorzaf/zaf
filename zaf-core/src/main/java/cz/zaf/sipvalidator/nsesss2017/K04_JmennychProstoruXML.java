/*
 * To change this license header, cho'ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09.Pravidlo2;

/**
 * Kontrola jmennych prostoru
 *
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "jmenných prostorů";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class,
        Pravidlo2.class
    };

    public K04_JmennychProstoruXML() {
        super(TypUrovenKontroly.JMENNE_PROSTORY_XML);
    }


	@Override
    public void validateImpl() {

        this.provedKontrolu(ctx, createRules());
	}

    public static Class<?>[] getRuleClasses() {
        return ruleClasses;
    }

	public List<? extends Rule<KontrolaNsessContext>> createRules() {
		return createRules(ruleClasses);
	}
}
