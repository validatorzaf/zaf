/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla05.val00_09.Pravidlo1;

/**
 * Kontrola souladu se schématem XSD
 *
 */
public class K05_ProtiSchematu
        extends KontrolaBase<KontrolaNsessContext> {
    static final public String NAME = "proti schématu";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class
    };

    public K05_ProtiSchematu() {
        super(TypUrovenKontroly.PROTI_SCHEMATU);
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
