/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.sipvalidator.nsesss2017.pravidla00.VirCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.VirCheckRule;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.vir00_09.Pravidlo1;


/**
 * Kontrola skodliveho kodu
 * 
 */
public class K00_SkodlivehoKodu
        extends KontrolaBase<VirCheckContext>
{
	
	static public final String NAME = "škodlivého kódu";

    private boolean kontrolaOk = true;
	private String errorDescr;

    public K00_SkodlivehoKodu() {
        super(TypUrovenKontroly.SKODLIVY_KOD);
    }

    /**
     * Nastavi, zda hrozba byla nalezena a jeji popis
     * 
     * @param popisHrozby
     */
    void setHrozba(String popisHrozby) {
        if (StringUtils.isEmpty(popisHrozby)) {
            // hrozba nenalezena
            kontrolaOk = true;
            errorDescr = null;
        } else {
            kontrolaOk = false;
            Validate.isTrue(StringUtils.isNotBlank(popisHrozby),
                            "Informace o skodlivem kodu je prazdna");
            errorDescr = popisHrozby;
        }

	}

	@Override
    public void provedKontrolu() {
        VirCheckContext virtCheckContext = new VirCheckContext(kontrolaOk, errorDescr);
        
        VirCheckRule rules[] = {
                new Pravidlo1()
        };

        provedKontrolu(virtCheckContext, rules);
	}
}
