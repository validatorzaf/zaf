/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;


/**
 *
 * @author standa
 */
public class K00_SkodlivehoKodu
        implements UrovenKontroly<KontrolaNsess2017Context>
{
	
	static public final String VIR1 = "vir1";
	static public final String NAME = "kontrola škodlivého kódu";

    private boolean kontrolaOk = true;
	private String errorDescr;

	// Kontrola je provedena jiz pred volanim validatoru
	// V konstruktoru je jen predna vysledek kontroly
	/**
	 * 
	 * @param stav
	 * @param error
	 */
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
    public void provedKontrolu(KontrolaNsess2017Context ctx) {
		VysledekKontroly k = new VysledekKontroly(TypUrovenKontroly.SKODLIVY_KOD,
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

		PravidloKontroly p = new PravidloKontroly(0, VIR1, kontrolaOk, descr, 0, "");
		k.add(p);		
	}

	@Override
	public String getNazev() {
		return NAME;
	}
}
