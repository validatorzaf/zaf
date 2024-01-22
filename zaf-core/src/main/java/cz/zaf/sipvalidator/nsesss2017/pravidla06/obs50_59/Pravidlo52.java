package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

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
            ArrayList<String> seznam_z_xml = new ArrayList<>();
            for (Element elFlocat : nodeListFlocat) {
                if (!ValuesGetter.hasAttribut(elFlocat, "xlink:href")) {
                    nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:FLocat> neobsahuje atribut xlink:href.", elFlocat);
                }
                String href = ValuesGetter.getValueOfAttribut(elFlocat, "xlink:href");
                if (!href.startsWith("komponenty")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Špatně uvedená relativní cesta ke komponentě.", elFlocat);
                }
                href = HelperString.replaceSeparators(href);

                String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(kontrola.getContext().getSip())
                        .replaceFirst("komponenty",
                        "")
                        + href;
                File file = new File(cestaKeKomponente);
                if (!file.exists()) {
                    if (href.contains(File.separator)) {
                        int s = href.lastIndexOf(File.separator);
                        String g = href.substring(s + 1);
                        href = g;
                    }
                    nastavChybu(BaseCode.CHYBI_KOMPONENTA, "Komponenta " + href + " nenalezena.", elFlocat);
                }
                seznam_z_xml.add(file.getName());
            }
            File[] listKomponent = new File(SIP_MAIN_helper.getCesta_komponenty(kontrola.getContext().getSip()))
                    .listFiles();
            for (File listKomponent1 : listKomponent) {
                String name = listKomponent1.getName();
                boolean jeVSeznamu = false;
                for (int y = 0; y < seznam_z_xml.size(); y++) {
                    if (seznam_z_xml.get(y).equals(name)) {
                        jeVSeznamu = true;
                    }
                }
                if (!jeVSeznamu) {
                    nastavChybu(BaseCode.CHYBNA_KOMPONENTA, "Komponenta " + name
                            + " ve složce komponenty nemá na sebe žádný odkaz z elementu <mets:FLocat>.", "komponenty"
                            + File.separator + name);
                }
            }
        }
    }
}
