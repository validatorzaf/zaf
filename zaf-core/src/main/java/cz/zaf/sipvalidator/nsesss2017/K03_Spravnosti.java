/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.sipvalidator.nsesss2017.pravidla03.wf00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.WfCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla03.WfCheckRule;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola správnosti XML
 * 
 */
public class K03_Spravnosti
        extends KontrolaBase<WfCheckContext> {

    static final public String NAME = "správnosti XML";

    public K03_Spravnosti() {
    }

    @Override
    public void provedKontrolu() {

 	   WfCheckContext wfCheckContext = new WfCheckContext(ctx);

 	   WfCheckRule rules[] = {
                new Pravidlo1(),
        };

        this.provedKontrolu(wfCheckContext, rules);
        
    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.SPRAVNOSTI;
    }

}
