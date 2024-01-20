package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.UrovenKontroly;

/**
 * SIP validator dle NSESSS 2017
 *
 */
public class SipValidator {

    static private Logger log = LoggerFactory.getLogger(SipValidator.class);
    
    private MetsParser metsParser;

    K00_SkodlivehoKodu ksk;

    final List<UrovenKontroly<KontrolaNsess2017Context>> kontroly;
    private KontrolaNsess2017Context ctx;

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
    private List<UrovenKontroly<KontrolaNsess2017Context>> pripravKontroly(ProfilValidace profilValidace) {
        ArrayList<UrovenKontroly<KontrolaNsess2017Context>> kontroly = new ArrayList<>(7);

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
        
        metsParser = new MetsParser();
        metsParser.parse(sipLoader);

        SipInfo sip = sipLoader.getSip();

        UrovenKontroly<KontrolaNsess2017Context> aktivniKontrola = null;
        try {
            // provedeni kontrol            
            ctx = new KontrolaNsess2017Context(metsParser, sip, excludeChecks);
            for (UrovenKontroly<KontrolaNsess2017Context> kontrola : kontroly) {
                aktivniKontrola = kontrola;
                kontrola.provedKontrolu(ctx);
            }
            aktivniKontrola = null;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Uncatched exception");
            if (aktivniKontrola != null) {
                sb.append(", aktivniKontrola: ").append(aktivniKontrola.getNazev());
            }
            log.error(sb.toString() + ", detail: " + e.toString(), e);
            throw new IllegalStateException(sb.toString(), e);
        }
    }
}
