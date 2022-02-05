package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

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
		List<Node> dokumenty = metsParser.getDokumenty();
		
        if(CollectionUtils.isEmpty(dokumenty) ) {
            return;
        }
        
        for(Node dokument: dokumenty){
            Node analogovyDokument = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE,
                                                            "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            // TODO: Je toto nutne? mozna musi existovat jiz ze souladu se schematem
            if (analogovyDokument == null) {
                nastavChybu(BaseCode.CHYBA,
                            "Element <nsesss:Dokument> " + kontrola.getIdentifikatory(dokument)
                                    + " neobsahuje element <nsesss:AnalogovyDokument>.",
                            dokument,
                            kontrola.getEntityId(dokument));
            }
            boolean maHodnotuNe = analogovyDokument.getTextContent().toLowerCase().equals("ne");
            if(maHodnotuNe){
            	Node metsMets = metsParser.getMetsRootNode();
                List<Node> fileSecNodes = ValuesGetter.getChildList(metsMets, "mets:fileSec");
                if (CollectionUtils.isEmpty(fileSecNodes)) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT,
                                "Element <mets:mets> neobsahuje žádný element <mets:fileSec>.",
                                metsMets);
                }
                if (fileSecNodes.size() > 1) {
                    nastavChybu(BaseCode.CHYBA,
                                "Element <mets:mets> obsahuje více dětský element <mets:fileSec>.",
                                metsMets);
                } else {
                    return;
                }
            }
        }
	}

}
