/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.wf00_09.Pravidlo1;

/**
 * Kontrola správnosti XML
 * 
 */
public class K03_Spravnosti
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>> {

    static final public String NAME = "správnosti XML";

    public K03_Spravnosti() {
        super(TypUrovenKontroly.SPRAVNOSTI);
    }

    @Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsessContext> wfCheckContext = new SimpleRuleContext<>(ctx);

        this.provedKontrolu(wfCheckContext, getRules());
        
    }

	public static List<? extends PravidloBase> getRules() {
		return List.of( new Pravidlo1() );
	}
}
