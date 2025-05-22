package cz.zaf.sipvalidator.nsesss2024;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.sipvalidator.nsesss2024.SipValidatorBase;
import cz.zaf.sipvalidator.nsesss2024.KontrolaNsessContext;
import cz.zaf.sipvalidator.nsesss2024.MetsParser;
import cz.zaf.sipvalidator.nsesss2024.profily.ProfilValidace;

/**
 * SIP validator dle NSESSS 2024
 *
 */
public class SipValidator extends SipValidatorBase {

    K00_SkodlivehoKodu ksk;

    public SipValidator(final ProfilValidace profilValidace,
                        final List<String> excludeChecks) {
    	super(pripravKontroly(profilValidace), excludeChecks);
    	ksk = (K00_SkodlivehoKodu)kontroly.get(0);
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

    @Override
    public void layerValidationStarted(KontrolaNsessContext context,
                                       ValidationLayer<KontrolaNsessContext> layer) {
        if (layer.getClass() == K03_Spravnosti.class) {
            // nacteni dokumentu pokud zaciname kontrolovat jeho spravnost a jedna se o XML
            MetsParser metsParser = new MetsParser();
            metsParser.parse(context.getSip());

            context.setMetsParser(metsParser);
        }
    }

    @Override
    public void layerValidationFinished(KontrolaNsessContext context,
                                        ValidationLayer<KontrolaNsessContext> layer) {
    }
}
