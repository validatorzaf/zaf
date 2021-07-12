package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.40 Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <mets:mets> právě jeden dětský element <mets:fileSec>.",
public class Pravidlo40  extends K06PravidloBase {
	
	static final public String OBS40 = "obs40";

	public Pravidlo40() {
		super(OBS40,
				"Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <mets:mets> právě jeden dětský element <mets:fileSec>.",
				"Chybí připojení komponent (počítačových souborů).",
				"Bod 2.13. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
		List<Node> dokumenty = metsParser.getDokumenty();
		
        if(CollectionUtils.isEmpty(dokumenty) ) {
        	return true;
        }
        
        for(Node dokument: dokumenty){
            Node analogovyDokument = ValuesGetter.getXChild(dokument, "nsesss:EvidencniUdaje", "nsesss:Manipulace", "nsesss:AnalogovyDokument");
            if(analogovyDokument == null){
                return nastavChybu("Element <nsesss:Dokument> " + kontrola.getIdentifikatory(dokument) +" neobsahuje element <nsesss:AnalogovyDokument>.", dokument);
            }
            boolean maHodnotuNe = analogovyDokument.getTextContent().toLowerCase().equals("ne");
            if(maHodnotuNe){
            	Node metsMets = metsParser.getMetsRootNode();
                if(ValuesGetter.getXChild(metsMets, "mets:fileSec") == null){
                    return nastavChybu("Element <mets:mets> neobsahuje žádný element <mets:fileSec>.", metsMets);
                }
                if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:fileSec")){
                    return nastavChybu("Element <mets:mets> neobsahuje právě jeden dětský element <mets:fileSec>.", metsMets);
                }
                else{
                    return true;
                }
            }
        }
        return true;
	}


}
