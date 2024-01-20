package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.K07KontrolaContext;
import cz.zaf.sipvalidator.nsesss2017.pravidla07.KomponentovePravidlo;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola komponent
 */
public class K07_Komponent extends KontrolaBase {

    static final public String NAME = "kontrola komponent";

    private KomponentovePravidlo[] pravidla;

    public K07_Komponent(final KomponentovePravidlo[] pravidla) {
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

        for (int i = 0; i < pravidla.length; i++) {
            KomponentovePravidlo pravidlo = pravidla[i];

            provedKontrolu(kontrolaCtx, pravidlo);
        }
    }

    public void pridejChybu(KomponentovePravidlo pravidlo,
                            ErrorCode errorCode, 
                            String detailChyby,
                            String mistoChyby,
                            List<EntityId> entityIds) {
        ChybaPravidla p = new ChybaPravidla(pravidlo,
                detailChyby,
                mistoChyby,
                errorCode,
                entityIds);
        vysledekKontroly.add(p);
    }

    private void provedKontrolu(K07KontrolaContext kontrolaCtx, KomponentovePravidlo pravidlo) {

        // skip excluded checks
        if (ctx.isExcluded(pravidlo.getCode())) {
            return;
        }

        // reset promennych pred spustenim
        String mistoChyby = null;
        String detailChyby = null;
        ErrorCode errorCode = null;
        List<EntityId> entityIds = null;

        try {
            pravidlo.kontrolaPravidla(kontrolaCtx);
            // vse ok
            return;
        } catch (ZafException e) {
            errorCode = e.getErrorCode();
            detailChyby = e.getMessage();
            mistoChyby = e.getMistoChyby();

            entityIds = e.getEntityIds();
        } catch (Exception e) {
            errorCode = BaseCode.NEZNAMA_CHYBA;
            detailChyby = e.getLocalizedMessage();
        }

        pridejChybu(pravidlo,
                    errorCode,
                    detailChyby,
                    mistoChyby,
                    entityIds);

    }

}
