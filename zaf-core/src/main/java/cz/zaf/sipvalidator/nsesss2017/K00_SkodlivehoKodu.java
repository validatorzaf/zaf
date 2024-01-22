/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.common.validation.Rule;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.VirCheckContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla00.vir00_09.Pravidlo1;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;


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

        List<Rule<VirCheckContext>> rules = new ArrayList<>();
        rules.add(new Pravidlo1());

        provedKontrolu(virtCheckContext, rules);
	}

    @Override
	public String getNazev() {
		return NAME;
	}

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.SKODLIVY_KOD;
    }
}
