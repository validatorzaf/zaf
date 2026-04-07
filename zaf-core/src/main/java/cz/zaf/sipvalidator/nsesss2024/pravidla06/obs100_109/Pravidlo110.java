package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs100_109;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;

public class Pravidlo110 extends K06PravidloBase {

    public static final String OBS110 = "obs110";

    private final Set<String> mimeTypes = Set.of(
            "application/vnd.etsi.asic-e+zip",
            "application/vnd.etsi.asic-s+zip",
            "application/vnd.software602.filler.form+xml",
            "application/vnd.software602.filler.form-xml-zip",
            "message/rfc822",
            "application/vnd.isac.fcs.isdocx",
            "application/zip",
            "application/zip-compressed",
            "application/x-zip-compressed",
            "multipart/x-zip",
            "application/pdf"
    );

    public Pravidlo110() {
        super(OBS110,
                """
                Pokud existuje jakýkoli element <nsesss:Komponenta>, který obsahuje atribut forma_uchovani s hodnotou kontejner, potom jakýkoli element <mets:file>, který obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <mets:Komponenta> příslušné komponenty, obsahuje atribut MIMETYPE s jednou z uvedených hodnot: application/vnd.etsi.asic-e+zip, application/vnd.etsi.asic-s+zip, application/vnd.software602.filler.form+xml, application/vnd.software602.filler.form-xml-zip, message/rfc822, application/vnd.isac.fcs.isdocx, application/zip, application/zip-compressed, application/x-zip-compressed, multipart/x-zip, application/pdf.
                """,
                "Uveden je chybně kontejner.",
                "Požadavek 2.4.1 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> listKomponenty = metsParser.getElements(NsesssV4.KOMPONENTA);
        if (!CollectionUtils.isEmpty(listKomponenty)) {
            List<Element> listMetsFile = metsParser.getNodes(MetsElements.FILE);
            for (Element elKomponenta : listKomponenty) {
                String atrFormaUchovani = elKomponenta.getAttribute("forma_uchovani");
                if (StringUtils.equals(atrFormaUchovani, "kontejner")) {
                    if (CollectionUtils.isEmpty(listMetsFile)) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <mets:file> na který odkazuje element <nsesss:Komponenta>.", getMistoChyby(elKomponenta), getEntityId(elKomponenta));
                    }
                    String atrID = elKomponenta.getAttribute("ID");
                    Element elMetsFile = getMetsFile(atrID, listMetsFile);
                    if (elMetsFile == null) {
                        new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <mets:file> na který odkazuje element <nsesss:Komponenta> s ID: " + atrID + ".", 
                            getMistoChyby(elKomponenta)).addEntity(getEntityId(elKomponenta));
                    }
                    String mimetype = elMetsFile.getAttribute("MIMETYPE");
                    if (StringUtils.isBlank(mimetype)) {
                        nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> nemá vyplněn atribut MIMETYPE.", getMistoChyby(elMetsFile));
                    }
                    if (!mimeTypes.contains(mimetype)) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Element <mets:file> obsahuje v atributu MIMETYPE nepovolenou hodnotu: " + mimetype + ".", getMistoChyby(elMetsFile));
                    }
                }
            }
        }

    }

    private Element getMetsFile(String atrID, List<Element> listMetsFile) {
        for (Element elMetsFile : listMetsFile) {
            String atrDmdid = elMetsFile.getAttribute("DMDID");
            if (StringUtils.equals(atrDmdid, atrID)) {
                return elMetsFile;
            }
        }
        return null;
    }

}
