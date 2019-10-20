/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.sipvalid.sip.KontrolaContext;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola;
import cz.zaf.sipvalid.sip.SIP_MAIN_kontrola_pravidlo;
import cz.zaf.sipvalid.sip.TypUrovenKontroly;
import cz.zaf.sipvalid.sip.UrovenKontroly;


/**
 *
 * @author standa
 */
public class K00_SkodlivehoKodu
	implements UrovenKontroly
{
	
	static public final String VIR1 = "vir1";
	static public final String NAME = "kontrola škodlivého kódu";
	private boolean kontrolaOk;
	private String errorDescr;

	// Kontrola je provedena jiz pred volanim validatoru
	// V konstruktoru je jen predna vysledek kontroly
	/**
	 * 
	 * @param stav
	 * @param error
	 */
	public K00_SkodlivehoKodu(final boolean kontrolaOk, String error) {
		this.kontrolaOk = kontrolaOk;
		if(kontrolaOk) {
			Validate.isTrue(StringUtils.isEmpty(error), "Informace o skodlivem kodu neni prazdna, %s", error);
		}
		this.errorDescr = error;
	}

	@Override
	public void provedKontrolu(KontrolaContext ctx) {
		SIP_MAIN_kontrola k = new SIP_MAIN_kontrola(TypUrovenKontroly.SKODLIVY_KOD,
				NAME);
		ctx.pridejKontrolu(k);
		
		String descr;
		if (kontrolaOk) {
			descr = "Datový balíček SIP neobsahuje hrozbu.";
		} else {
			if (errorDescr == null) {
				descr = "Chybové hlášení nebylo předáno.";
			} else {
				descr = errorDescr;
			}
		}

		SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, VIR1, kontrolaOk, descr, 0, "");
		k.add(p);		
		k.setProvedena(true);		
		k.setStav(kontrolaOk);		
	}

	@Override
	public String getNazev() {
		return NAME;
	}
}
