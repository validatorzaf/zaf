package cz.zaf.sipvalid.sip;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

/**
 * Kontext provadene kontroly
 *
 */
public class KontrolaContext {
	
	/**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SIP_MAIN sip;
	
	public KontrolaContext(final SIP_MAIN sip) {
		this.sip = sip;
	}

	public void pridejKontrolu(SIP_MAIN_kontrola k) {

		sip.pridejKontrolu(k);		
		
	}

	public boolean isFailed() {
		ArrayList<SIP_MAIN_kontrola> kontroly = sip.getSeznamKontrol();
		if(kontroly.size()==0) {
			return false;
		}
		// dle stavu posledni kontroly se rozhodneme
		SIP_MAIN_kontrola posledniKontrola = kontroly.get(kontroly.size()-1);
		if(!posledniKontrola.isProvedena()) {
			// neni provedena -> selhani jiz nekde drive
			return true;
		}
		if(posledniKontrola.isFailed()) {
			return true;
		}
		return false;
	}

	public SIP_MAIN getSip() {
		return sip;
	}
}
