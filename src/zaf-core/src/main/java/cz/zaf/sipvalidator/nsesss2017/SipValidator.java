package cz.zaf.sipvalidator.nsesss2017;

import java.util.ArrayList;
import java.util.List;

import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.XmlReportBuilder;

/**
 * SIP validator dle NSESSS 2017
 *
 */
public class SipValidator {

    private SipLoader sipLoader;
    private ProfilValidace profilValidace;
    
    private MetsParser metsParser;

    K00_SkodlivehoKodu ksk;

    final List<UrovenKontroly<KontrolaNsess2017Context>> kontroly;

    public SipValidator(final ProfilValidace profilValidace) {
        this.profilValidace = profilValidace;
        this.kontroly = pripravKontroly(profilValidace.getObsahoveKontroly());
    }

    /**
     * Pripravi seznam kontrol
     * @param seznamObsKontrol 
     * @param skodlivyKodError 
     * @param skodlivyKodOk 
     * @return
     */
    private List<UrovenKontroly<KontrolaNsess2017Context>> pripravKontroly(int[] seznamObsKontrol) {
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

        K06_Obsahova oks = new K06_Obsahova(seznamObsKontrol);
        kontroly.add(oks);
        
        return kontroly;
    }

    /**
     * Nastaveni hrozby pro kontrolu skodliveho kodu
     * 
     * @param hrozba
     */
    public void setHrozba(String hrozba) {
        ksk.setHrozba(hrozba);
    }

    public void validate(SipLoader sipLoader) {
        this.sipLoader = sipLoader;
        
        metsParser = new MetsParser();
        metsParser.parse(sipLoader);

        SipInfo sip = sipLoader.getSip();

        try {
            // provedeni kontrol
            KontrolaNsess2017Context ctx = new KontrolaNsess2017Context(metsParser, sip);
            for (UrovenKontroly<KontrolaNsess2017Context> kontrola : kontroly) {
                kontrola.provedKontrolu(ctx);
            }
        } catch (Exception e) {
            //TODO: odstranit..
            e.printStackTrace();
        }
    }

    /**
     * Zapsani aktualniho SIPu do vystupu
     * 
     * @param xmlBuilder
     */
    public void writeResult(XmlReportBuilder xmlBuilder) {
        SipInfo sipInfo = sipLoader.getSip();
        xmlBuilder.addSipNode(sipInfo);

    }
}
