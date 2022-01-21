package cz.zaf.sipvalidator.sip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Kontext provadene kontroly
 *
 */
public class KontrolaContext {
	
	/**
	 * SIP nad nimz je provadena kontrola
	 */
	final protected SipInfo sip;
	
    /**
     * Seznam ignorovanych kontrol
     */
    final Set<String> excludeChecks = new HashSet<>();

    public KontrolaContext(final SipInfo sip, final List<String> excludeCheckList) {
		this.sip = sip;
        this.excludeChecks.addAll(excludeCheckList);
	}

	public void pridejKontrolu(VysledekKontroly k) {

		sip.pridejKontrolu(k);		
		
    }

    public boolean isExcluded(String checkId) {
        return excludeChecks.contains(checkId);
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
