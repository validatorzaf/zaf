package cz.zaf.sipvalidator.nsesss2017;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

abstract public class KontrolaBase
        implements UrovenKontroly<KontrolaNsess2017Context> {

    static Logger log = LoggerFactory.getLogger(KontrolaBase.class);

    protected KontrolaNsess2017Context ctx;
    protected VysledekKontroly vysledekKontroly;

    @Override
    public void provedKontrolu(KontrolaNsess2017Context ctx) {
        this.ctx = ctx;

        // nejprve precteme, zda jiz byl selhany
        boolean failed = ctx.isFailed();

        vysledekKontroly = new VysledekKontroly(getUrovenKontroly(),
                getNazev());
        ctx.pridejKontrolu(vysledekKontroly);
        // po selhane kontrole se jiz nepokracuje
        if (failed) {
            return;
        }

        long startTime = System.currentTimeMillis();

        provedKontrolu();

        long finishTime = System.currentTimeMillis();

        log.debug("Dokoncena kontrola: {}, doba trvani: {}ms", this.getNazev(), finishTime - startTime);
    }

    public KontrolaNsess2017Context getContext() {
        return ctx;
    }

    abstract TypUrovenKontroly getUrovenKontroly();
    abstract void provedKontrolu();

}
