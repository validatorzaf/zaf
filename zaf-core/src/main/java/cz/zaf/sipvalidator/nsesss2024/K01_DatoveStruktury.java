/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2024;


import java.util.List;

import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2024.KontrolaBase;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.KontrolaNsessContext;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo1a;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2024.pravidla01.dat00_09.Pravidlo3;


/**
 * Kontrola datové struktury
 * 
 */
public class K01_DatoveStruktury
        extends KontrolaBase<SimpleRuleContext<KontrolaNsessContext>>
{
	
    static final public String NAME = "datové struktury";

    public K01_DatoveStruktury() {
        super(TypUrovenKontroly.DATOVE_STRUKTURY);
    }


	@Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsessContext> datCheckContext = new SimpleRuleContext<>(ctx);
        this.provedKontrolu(datCheckContext, getRules());
	}


	public static List<? extends PravidloBase> getRules() {
		return List.of(
                new Pravidlo1(),
                new Pravidlo1a(),
                new Pravidlo2(),
                new Pravidlo3()
				);
	}
}
