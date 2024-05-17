package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.52 Pokud existuje jakýkoli element <mets:FLocat>,
// každý obsahuje atribut xlink:href s hodnotou, která odpovídá relativní
// cestě odkazu jakékoli komponenty uložené ve složce komponenty.
public class Pravidlo52 extends K06PravidloBase {

    static final public String OBS52 = "obs52";

    public Pravidlo52() {
        super(OBS52,
                "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:href s hodnotou, která odpovídá relativní cestě odkazu jakékoli komponenty uložené ve složce komponenty, přičemž právě jedna hodnota atributu xlink:href odpovídá relativní cestě odkazu právě jedné komponenty.",
                "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
                "Bod 2.16. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {

        List<Element> nodeListFlocat = metsParser.getNodes(MetsElements.FLOCAT);
        if (!nodeListFlocat.isEmpty()) {
            Path sipPath = kontrola.getContext().getSip().getCesta().toAbsolutePath();
            String prefix = sipPath.toAbsolutePath().toString();

            Set<String> souboryZXML = new HashSet<>();
            for (Element elFlocat : nodeListFlocat) {
                if (!ValuesGetter.hasAttribut(elFlocat, "xlink:href")) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:FLocat> neobsahuje atribut xlink:href.", elFlocat);
                }
                String href = ValuesGetter.getValueOfAttribut(elFlocat, "xlink:href");
                if (!href.startsWith("komponenty")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Špatně uvedená relativní cesta ke komponentě.", elFlocat);
                }
                href = HelperString.replaceSeparators(href);

                Path kompPath = kontrola.getContext().getKomponentaPath(href);
                if (!Files.isRegularFile(kompPath)) {
                    if (href.contains(File.separator)) {
                        int s = href.lastIndexOf(File.separator);
                        String g = href.substring(s + 1);
                        href = g;
                    }
                    nastavChybu(BaseCode.CHYBI_KOMPONENTA, "Komponenta " + href + " nenalezena.", elFlocat);
                }
                souboryZXML.add(href);
            }
            Path sourceDir = this.kontrola.getContext().getKomponentyPath().toAbsolutePath();
            try (Stream<Path> stream = Files.walk(sourceDir)) {
                stream.filter(Files::isRegularFile)
                        .forEach(file -> {
                            // kontrola
                            String relativePath = sipPath.relativize(file).toString();
                            if (!souboryZXML.contains(relativePath)) {
                                nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Komponenta " + relativePath
                                        + " ve složce komponenty nemá na sebe žádný odkaz z elementu <mets:FLocat>.",
                                            relativePath);
                            }
                        });
            } catch (IOException e) {
                throw new ZafException(BaseCode.CHYBNA_KOMPONENTA, "Nelze prochazet slozku " + sourceDir.toString()
                        + ".", e);
            }
        }
    }
}
