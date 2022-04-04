package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.UrlJazykyParser;

public class Pravidlo62 extends K06PravidloBase {

    static UrlJazykyParser parserZUrl = new UrlJazykyParser();
    static final public String OBS62 = "obs62";

    public Pravidlo62() {
        super(OBS62,
                "Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.",
                "Uveden je chybně jazyk dokumentu.",
                "Příloha č. 2 NSESSS, ř. 132.");
    }

    //OBSAHOVÁ č.62 Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.
    @Override
    protected void kontrola() {
        List<Element> jazyky = metsParser.getNodes(NsessV3.JAZYK);
        for (Element jazyk : jazyky) {
            String hodnotaJazyk = jazyk.getTextContent();
            try {
                parserZUrl.NactiJazykyZUrl();
            } catch (IOException ex) {
                nastavChybu(BaseCode.CHYBA, "Chyba programu - nepodařilo se načíst tabulku s hodnotami pro element <nsesss:Jazyk>.");
            }
            boolean jeObsazen = parserZUrl.jeObsazenJazyk(hodnotaJazyk);
            if (!jeObsazen) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena hodnota odpovídající ISO 639-2:1998. " + getJmenoIdentifikator(jazyk), jazyk);
            }
        }
    }

}
