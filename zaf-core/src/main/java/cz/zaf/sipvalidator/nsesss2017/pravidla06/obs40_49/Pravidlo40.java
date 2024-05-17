package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.40 Pokud jakýkoli element <nsesss:Dokument> obsahuje
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace>
// element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element
// <mets:mets> právě jeden dětský element <mets:fileSec>.
public class Pravidlo40 extends K06PravidloBase {
	
	static final public String OBS40 = "obs40";

	public Pravidlo40() {
		super(OBS40,
				"Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <mets:mets> právě jeden dětský element <mets:fileSec>.",
				"Chybí připojení komponent (počítačových souborů).",
				"Bod 2.13. přílohy č. 3 NSESSS.");
	}

	@Override
    protected void kontrola() {
        List<Element> dokumenty = metsParser.getDokumenty();
		
        if(CollectionUtils.isEmpty(dokumenty) ) {
            return;
        }
        
        for (Element dokument : dokumenty) {
            Element analogovyDokument = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE,
                                                            "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            // TODO: Je toto nutne? mozna musi existovat jiz ze souladu se schematem
            if (analogovyDokument == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                            "Element <nsesss:Dokument> " + K06_Obsahova.getIdentifikatory(dokument)
                                    + " neobsahuje element <nsesss:AnalogovyDokument>.",
                            dokument,
                            K06_Obsahova.getEntityId(dokument));
            }
            boolean maHodnotuNe = analogovyDokument.getTextContent().toLowerCase().equals("ne");
            if(maHodnotuNe){
                Element metsMets = metsParser.getMetsRootNode();
                List<Element> fileSecNodes = ValuesGetter.getChildNodes(metsMets, "mets:fileSec");
                if (CollectionUtils.isEmpty(fileSecNodes)) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT,
                                "Element <mets:mets> neobsahuje žádný element <mets:fileSec>.",
                                metsMets);
                }
                if (fileSecNodes.size() > 1) {
                    nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                                "Element <mets:mets> obsahuje více dětský element <mets:fileSec>.",
                                metsMets);
                } else {
                    return;
                }
            }
        }
	}

}
