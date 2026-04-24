package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;

/**
 * SIP validator dle NSESSS 2017
 *
 */
public class SipValidator extends SipValidatorBase {

    private String hrozba;

    public SipValidator(final ProfilValidace profilValidace,
                        final List<String> excludeChecks) {
    	super(pripravKontroly(profilValidace), excludeChecks);
    }

    /**
     * Pripravi seznam kontrol
     *
     * @param profilValidace
     *            validační profil
     * @return Seznam připravených úrovní kontroly
     */
    static protected List<ValidationLayer<KontrolaNsessContext>> pripravKontroly(ProfilValidace profilValidace) {
        List<ValidationLayer<KontrolaNsessContext>> kontroly = new ArrayList<>(7);

        kontroly.add(new K00_SkodlivehoKodu());

        K01_DatoveStruktury kds = new K01_DatoveStruktury();
        kontroly.add(kds);

        K02_ZnakoveSady kko = new K02_ZnakoveSady();
        kontroly.add(kko);

        K03_Spravnosti kwf = new K03_Spravnosti();
        kontroly.add(kwf);

        K04_JmennychProstoruXML kjp = new K04_JmennychProstoruXML();
        kontroly.add(kjp);

        K05_ProtiSchematu vxml = new K05_ProtiSchematu();
        kontroly.add(vxml);

        K06_Obsahova oks = new K06_Obsahova(profilValidace);
        kontroly.add(oks);

        K07_Komponent kfs = new K07_Komponent(profilValidace);
        kontroly.add(kfs);
        return kontroly;
    }

    /**
     * Nastaveni hrozby pro kontrolu skodliveho kodu
     *
     * @param hrozba
     *            textový popis hrozby, null pokud nebyla nalezena
     */
    public void setHrozba(String hrozba) {
        this.hrozba = hrozba;
    }

    @Override
    protected void prepareContext(KontrolaNsessContext ctx) {
        ctx.setHrozba(hrozba);
    }

    @Override
    public void layerValidationStarted(KontrolaNsessContext context,
                                       ValidationLayer<KontrolaNsessContext> layer) {
    }

    @Override
    public void layerValidationFinished(KontrolaNsessContext context,
                                        ValidationLayer<KontrolaNsessContext> layer) {
    }
}
