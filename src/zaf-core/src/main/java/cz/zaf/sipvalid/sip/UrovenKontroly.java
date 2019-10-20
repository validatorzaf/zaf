package cz.zaf.sipvalid.sip;

/**
 * Interface pro jednotlivou uroven kontroly
 *
 */
public interface UrovenKontroly {

	void provedKontrolu(KontrolaContext ctx) throws Exception;

	String getNazev();
}
