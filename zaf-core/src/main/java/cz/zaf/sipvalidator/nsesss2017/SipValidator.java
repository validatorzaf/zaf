package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;

/**
 * SIP validator dle NSESSS 2017
 *
 */
public class SipValidator implements ValidatorListener<KontrolaNsess2017Context> {

    K00_SkodlivehoKodu ksk;

    final List<ValidationLayer<KontrolaNsess2017Context>> kontroly;

    /**
     * Seznam kontrol k vynechani
     */
    private List<String> excludeChecks;

    public SipValidator(final ProfilValidace profilValidace,
                        final List<String> excludeChecks) {
        this.kontroly = pripravKontroly(profilValidace);
        this.excludeChecks = excludeChecks;
    }

    /**
     * Pripravi seznam kontrol
     * 
     * @param profilValidace
     *            validační profil
     * @return Seznam připravených úrovní kontroly
     */
    private List<ValidationLayer<KontrolaNsess2017Context>> pripravKontroly(ProfilValidace profilValidace) {
        List<ValidationLayer<KontrolaNsess2017Context>> kontroly = new ArrayList<>(7);

        ksk = new K00_SkodlivehoKodu();
        kontroly.add(ksk);
        
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

        K06_Obsahova oks = new K06_Obsahova(profilValidace.createObsahovaPravidla());
        kontroly.add(oks);
        
        K07_Komponent kfs = new K07_Komponent(profilValidace.createFormatovaPravidla());
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
        ksk.setHrozba(hrozba);
    }

    public void validate(SipLoader sipLoader) {

        SipInfo sip = sipLoader.getSip();
        
        // provedeni kontrol
        KontrolaNsess2017Context ctx = new KontrolaNsess2017Context(sip, excludeChecks);
        BaseValidator<KontrolaNsess2017Context> validator = new BaseValidator<>(kontroly);
        validator.registerListener(this);
        validator.validate(ctx);
    }

    @Override
    public void layerValidationStarted(KontrolaNsess2017Context context,
                                       ValidationLayer<KontrolaNsess2017Context> layer) {
        if (layer.getClass() == K03_Spravnosti.class) {
            // nacteni dokumentu pokud zaciname kontrolovat jeho spravnost a jedna se o XML
            MetsParser metsParser = new MetsParser();
            metsParser.parse(context.getSip());

            context.setMetsParser(metsParser);
        }
    }

    @Override
    public void layerValidationFinished(KontrolaNsess2017Context context,
                                        ValidationLayer<KontrolaNsess2017Context> layer) {
    }
}
