package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs40_49;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//
// OBSAHOVÁ č.44
//
// Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s
// hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>.
//
public class Pravidlo44 extends K06PravidloBase {

    static final public String OBS44 = "obs44";

    public Pravidlo44() {
        super(OBS44,
                "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>, přičemž právě jedna hodnota atributu DMDID odpovídá právě jedné hodnotě atributu ID.",
                "Chybí provázání komponenty (počítačového souboru) s popisnou částí.",
                "Bod 2.15. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        // cteni mets:file
        List<Element> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        if (CollectionUtils.isEmpty(nodeListMetsFile)) {
            // dalsi kontrola je jen pokud existuje File
            return;
        }
        List<Element> nodeListKomponenty = metsParser.getNodes(NsesssV3.KOMPONENTA);
        if (nodeListKomponenty.size() != nodeListMetsFile.size()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Nenalezen shodný počet <nsesss:Komponenta> a <mets:file>." + "Komponenty ("
                                + nodeListKomponenty.size() + ") vs File (" + nodeListMetsFile.size() + ")");
        }

        Map<String, Element> filesIdMap = new HashMap<>();
        for (Element metsFile : nodeListMetsFile) {
            String dmdid = metsFile.getAttribute("DMDID");
            if (StringUtils.isEmpty(dmdid)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT, "Element <mets:file> nemá atribut DMDID.", metsFile);
            }
            Element prevNode = filesIdMap.put(dmdid, metsFile);
            if (prevNode != null) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Více elementů <mets:file> má shodnou hodnotu DMDID.",
                        metsFile);
            }
        }

        // cteni nsesss:komponenta
        Map<String, Element> komponentyIdMap = new HashMap<>();
        for (Element komponenta : nodeListKomponenty) {
            String id = komponenta.getAttribute("ID");
            if (StringUtils.isEmpty(id)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <nsesss:Komponenta> nemá atribut ID. " + getJmenoIdentifikator(komponenta),
                        komponenta);
            }

            // Check if exists in fileIdMap            
            Element divNode = filesIdMap.get(id);
            if (divNode == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <nsesss:Komponenta> nemá odpovídající záznam ve strukturální mapě. "
                        + getJmenoIdentifikator(komponenta),
                        komponenta);
            }

            komponentyIdMap.put(id, komponenta);
        }

        // Kontrola odkazů ze strukturální mapy
        for (Element metsFile : nodeListMetsFile) {
            String dmdid = metsFile.getAttribute("DMDID");
            Element dmdNode = komponentyIdMap.get(dmdid);
            if (dmdNode == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:file> neodkazuje na odpovídající <nsesss:Komponenta>",
                        metsFile);
            }
        }
    }

}
