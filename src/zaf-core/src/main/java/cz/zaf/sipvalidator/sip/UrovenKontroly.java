package cz.zaf.sipvalidator.sip;

/**
 * Interface pro jednotlivou uroven kontroly
 *
 */
public interface UrovenKontroly<T extends KontrolaContext> {

    void provedKontrolu(T ctx) throws Exception;

	String getNazev();
}
