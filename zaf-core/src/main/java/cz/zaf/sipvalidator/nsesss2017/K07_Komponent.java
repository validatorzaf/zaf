package cz.zaf.sipvalidator.nsesss2017;

import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07KontrolaContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07PravidloBase;

/**
 * Kontrola komponent
 */
public class K07_Komponent extends KontrolaBase<K07KontrolaContext> {

    static final public String NAME = "kontrola komponent";

    private K07PravidloBase[] pravidla;

    public K07_Komponent(final K07PravidloBase[] pravidla) {
        this.pravidla = pravidla;
    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.KOMPONENT;
    }

    @Override
    void provedKontrolu() {
        K07KontrolaContext kontrolaCtx = new K07KontrolaContext(ctx.getMetsParser(), ctx);

        provedKontrolu(kontrolaCtx, pravidla);
    }

}
