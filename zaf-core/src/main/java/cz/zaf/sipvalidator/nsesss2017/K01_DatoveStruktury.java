/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;


import java.util.List;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo3;


/**
 * Kontrola datové struktury
 *
 */
public class K01_DatoveStruktury
        extends KontrolaBase<KontrolaNsessContext>
{

    static final public String NAME = "datové struktury";

    private static final Class<?>[] ruleClasses = {
        Pravidlo1.class,
        Pravidlo2.class,
        Pravidlo3.class
    };

    public K01_DatoveStruktury() {
        super(TypUrovenKontroly.DATOVE_STRUKTURY);
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
