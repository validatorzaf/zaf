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


/**
 * Kontrola skodliveho kodu
 * 
 */
public class K00_SkodlivehoKodu
        extends KontrolaBase
{
	
	static public final String VIR1 = "vir1";
	static public final String NAME = "kontrola škodlivého kódu";

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
		String descr;
        String textPravidla;
		if (kontrolaOk) {
			descr = "Datový balíček SIP neobsahuje hrozbu.";
            textPravidla = "Datový balíček SIP neobsahuje hrozbu.";
		} else {
            textPravidla = "Datový balíček SIP obsahuje hrozbu.";
			if (errorDescr == null) {
				descr = "Chybové hlášení nebylo předáno.";
			} else {
				descr = errorDescr;
			}
		}

        String zdroj = "§ 21 odst. 6 vyhlášky č. 259/2012 Sb.";
        PravidloKontroly p = new PravidloKontroly(VIR1, kontrolaOk,
                textPravidla,
                descr, null, null,
                zdroj);
        vysledekKontroly.add(p);
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