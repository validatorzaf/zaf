/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.sipvalidator.nsesss2017.pravidla02.kod00_09.Pravidlo1;

/**
 * Kontroluje kódování SIP souboru.
 * Potřebuje dvě knihovny a to: ucu4j-56.jar
 * lib commons-io-2.4
 * 
 */
public class K02_ZnakoveSady
        extends KontrolaBase<KontrolaNsessContext> {

    static final public String NAME = "znakové sady";
    
    public K02_ZnakoveSady() {
        super(TypUrovenKontroly.ZNAKOVE_SADY);
    }

    @Override
    public void validateImpl() {

        this.provedKontrolu(ctx, getRules());

    }

	public static List<? extends PravidloBase> getRules() {
		return List.of(new Pravidlo1());
	}
}
