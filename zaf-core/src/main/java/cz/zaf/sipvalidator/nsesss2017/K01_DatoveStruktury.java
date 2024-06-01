/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;


import cz.zaf.common.validation.SimpleRuleContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo1;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo2;
import cz.zaf.sipvalidator.nsesss2017.pravidla01.dat00_09.Pravidlo3;


/**
 * Kontrola datové struktury
 * 
 */
public class K01_DatoveStruktury
        extends KontrolaBase<SimpleRuleContext<KontrolaNsess2017Context>>
{
	
    static final public String NAME = "datové struktury";

    public K01_DatoveStruktury() {
        super(TypUrovenKontroly.DATOVE_STRUKTURY);
    }


	@Override
    public void validateImpl() {

        SimpleRuleContext<KontrolaNsess2017Context> datCheckContext = new SimpleRuleContext<>(ctx);

        PravidloBase rules[] = {
                new Pravidlo1(),
                new Pravidlo2(),
                new Pravidlo3(),
        };

        this.provedKontrolu(datCheckContext, rules);
	}
}
