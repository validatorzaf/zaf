package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.UrlJazykyParser;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
    protected boolean kontrolaPravidla() {
        NodeList jazyky = ValuesGetter.getAllAnywhere("nsesss:Jazyk", metsParser.getDocument());
        if (jazyky == null)
            return true;
        String hodnotaJazyk;
        for (int i = 0; i < jazyky.getLength(); i++) {
            Node jazyk = jazyky.item(i);
            hodnotaJazyk = jazyk.getTextContent();
            try {
                parserZUrl.NactiJazykyZUrl();
            } catch (IOException ex) {
                return nastavChybu("Chyba programu - nepodařilo se načíst tabulku s hodnotami pro element <nsesss:Jazyk>.");
            }
            boolean jeObsazen = parserZUrl.jeObsazenJazyk(hodnotaJazyk);
            if (!jeObsazen)
                return nastavChybu("Nenalezena hodnota odpovídající ISO 639-2:1998. " + getJmenoIdentifikator(jazyk),
                                   jazyk);
        }
        return true;
    }

}
