/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.sipvalidator.nsesss2017.pravidla02.KodCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla02.KodCheckRule;
import cz.zaf.sipvalidator.nsesss2017.pravidla02.kod00_09.Pravidlo1;

/**
 * Kontroluje kódování SIP souboru.
 * Potřebuje dvě knihovny a to: ucu4j-56.jar
 * lib commons-io-2.4
 * 
 */
public class K02_ZnakoveSady
        extends KontrolaBase<KodCheckContext> {
    static final public String NAME = "znakové sady";
    

    @Override
    public void provedKontrolu() {

    	   KodCheckContext kodCheckContext = new KodCheckContext(ctx);

    	   KodCheckRule rules[] = {
                   new Pravidlo1(),
           };

           this.provedKontrolu(kodCheckContext, rules);

        }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.ZNAKOVE_SADY;
    }

}
