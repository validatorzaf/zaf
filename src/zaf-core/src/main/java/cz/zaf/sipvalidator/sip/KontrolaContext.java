package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;

/**
 * Kontext provadene kontroly
 *
 */
public class KontrolaContext {
	
	/**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SipInfo sip;
	
	public KontrolaContext(final SipInfo sip) {
		this.sip = sip;
	}

	public void pridejKontrolu(VysledekKontroly k) {

		sip.pridejKontrolu(k);		
		
    }

	/**
	 * Kontrola, zda doslo k selhani
	 * 
	 * @return
	 */
	public boolean isFailed() {
		ArrayList<VysledekKontroly> kontroly = sip.getSeznamKontrol();
		if(kontroly==null||kontroly.size()==0) {
			return false;
		}
		// dle stavu posledni kontroly se rozhodneme
		VysledekKontroly posledniKontrola = kontroly.get(kontroly.size()-1);
		StavKontroly stav = posledniKontrola.getStavKontroly();
		if(stav!=StavKontroly.OK) {
			// neni provedena -> selhani jiz nekde drive
			return true;
		}
		return false;
	}

	public SipInfo getSip() {
		return sip;
	}
}
