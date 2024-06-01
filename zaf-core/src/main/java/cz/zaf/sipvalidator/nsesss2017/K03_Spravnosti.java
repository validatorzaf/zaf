/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.wf00_09.Pravidlo1;

/**
 * Kontrola správnosti XML
 * 
 */
public class K03_Spravnosti
        extends KontrolaBase<SimpleRuleContext<KontrolaNsess2017Context>> {

    static final public String NAME = "správnosti XML";

    public K03_Spravnosti() {
        super(TypUrovenKontroly.SPRAVNOSTI);
    }

    @Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsess2017Context> wfCheckContext = new SimpleRuleContext<>(ctx);

        PravidloBase rules[] = {
                new Pravidlo1(),
        };

        this.provedKontrolu(wfCheckContext, rules);
        
    }
}
