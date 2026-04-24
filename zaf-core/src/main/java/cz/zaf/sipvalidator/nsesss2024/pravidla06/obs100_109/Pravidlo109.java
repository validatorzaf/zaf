package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;

public class Pravidlo109 extends K06PravidloBase {

    public static final String OBS109 = "obs109";
    private final Set<String> mimeTypes = Set.of(
            "application/zip",
            "application/zip-compressed",
            "application/x-zip-compressed",
            "multipart/x-zip",
            "application/vnd.etsi.asic-e+zip",
            "application/vnd.etsi.asic-s+zip"
    );

    public Pravidlo109() {
        super(OBS109,
                """
                Pokud existuje jakýkoli element <mets:file>, který obsahuje atribut MIMETYPE s jednou z uvedených hodnot:
                application/zip
                application/zip-compressed
                application/x-zip-compressed
                multipart/x-zip,
                application/vnd.etsi.asic-e+zip, 
                application/vnd.etsi.asic-s+zip
                potom jakýkoli element  <nsesss:Komponenta>, který obsahuje atribut ID s hodnotou uvedenou v atributu DMDID jakéhokoli elementu <mets:file> příslušné komponenty, obsahuje atribut forma_uchovani s hodnotou kontejner.
                """,
                "Uveden je chybně kontejner.",
                "Požadavek 2.4.3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> listMetsFile = metsParser.getNodes(MetsElements.FILE);
        if (!CollectionUtils.isEmpty(listMetsFile)) {
            List<Element> listKomponenta = metsParser.getNodes(NsesssV4.KOMPONENTA);
            if (CollectionUtils.isEmpty(listKomponenta)) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Komponenta>.");
            }
            for (Element elMetsFile : listMetsFile) {
                String mimetipe = elMetsFile.getAttribute("MIMETYPE");
                if (mimetipe != null) {
                    if (mimeTypes.contains(mimetipe)) {
                        String metsFileDmdid = elMetsFile.getAttribute("DMDID");
                        if (metsFileDmdid == null) {
                            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut DMDID elementu <mets:file>.", getMistoChyby(elMetsFile));
                        }
                        Element elKomponenta = getKomponenta(metsFileDmdid, listKomponenta);
                        if (elKomponenta != null) {
                            String atrFormaUchovani = elKomponenta.getAttribute("forma_uchovani");
                            if (StringUtils.isBlank(atrFormaUchovani)) {
                                nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Atribut forma_uchovani elementu <nsesss:Komponenta> není vyplněn.", getMistoChyby(elKomponenta), getEntityId(elKomponenta));
                            }
                            if (!StringUtils.equals(atrFormaUchovani, "kontejner")) {
                                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut forma_uchovani elementu <nsesss:Komponenta> obsahuje nepovolenou hodnotu: " + atrFormaUchovani + ".", getMistoChyby(elKomponenta), getEntityId(elKomponenta));
                            }
                        }
                    }
                }
            }
        }

    }

    private Element getKomponenta(String dmdid, List<Element> listKomponenta) {
        for (Element elKomponenta : listKomponenta) {
            String atrID = elKomponenta.getAttribute("ID");
            if (StringUtils.equals(atrID, dmdid)) {
                return elKomponenta;
            }
        }
        return null;
    }

}
