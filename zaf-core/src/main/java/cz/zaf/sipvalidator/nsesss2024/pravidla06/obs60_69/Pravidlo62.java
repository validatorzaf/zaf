package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.io.IOException;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.UrlJazykyParser;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.ArrayList;

//
// OBSAHOVÁ č.62 Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje
// pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL:
// http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.
//
public class Pravidlo62 extends K06PravidloBase {

    static UrlJazykyParser parserZUrl = new UrlJazykyParser();
    static {
        try {
            parserZUrl.nactiJazykyZUrl();
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Chyba programu - nepodařilo se načíst tabulku s hodnotami pro element <nsesss:Jazyk>.");
        }
    }
    static final public String OBS62 = "obs62";

    public Pravidlo62() {
        super(OBS62,
                "Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.",
                "Uveden je chybně jazyk dokumentu.",
                "Příloha č. 2 NSESSS, ř. 132.");
    }

    @Override
    protected void kontrola() {
        List<Element> jazyky = metsParser.getNodes(NsesssV3.JAZYK);
        for (Element jazyk : jazyky) {
            String hodnotaJazyk = jazyk.getTextContent();
            boolean jeObsazen = parserZUrl.jeObsazenJazyk(hodnotaJazyk);
            if (!jeObsazen) {
                Element elEntita = kontrola.getEntity(jazyk);
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nenalezena hodnota odpovídající ISO 639-2:1998.", jazyk, kontrola.getEntityId(elEntita));
            }
        }
    }

}
