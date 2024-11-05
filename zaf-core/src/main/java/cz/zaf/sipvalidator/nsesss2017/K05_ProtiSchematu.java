/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla05.val00_09.Pravidlo1;

/**
 * Kontrola souladu se schématem XSD
 * 
 */
public class K05_ProtiSchematu
        extends KontrolaBase<SimpleRuleContext<KontrolaNsess2017Context>> {
    static final public String NAME = "proti schématu";

    public K05_ProtiSchematu() {
        super(TypUrovenKontroly.PROTI_SCHEMATU);
    }

    @Override
    public void validateImpl() {
    	
    	SimpleRuleContext<KontrolaNsess2017Context> schemaCheckContext = new SimpleRuleContext<>(ctx);
    	
    	this.provedKontrolu(schemaCheckContext, getRules());
    }

	public static List<? extends PravidloBase> getRules() {
		return List.of(new Pravidlo1());
	}
}
