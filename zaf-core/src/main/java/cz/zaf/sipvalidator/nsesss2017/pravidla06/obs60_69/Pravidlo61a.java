package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// OBSAHOVÁ č.61a Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a 
// současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.
public class Pravidlo61a extends K06PravidloBase {
	
	static final public String OBS61A = "obs61a";

	public Pravidlo61a() {
		super(OBS61A, 
			    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.",
			    "Chybí množství vlastního dokumentu v analogové podobě.", 
			    "Příloha č. 2 NSESSS, ř. 1208.");
	}

	@Override
	protected boolean kontrolaPravidla() {	    
        List<Node> dokuments = metsParser.getDokumenty();
        if(dokuments == null){
            return nastavChybu("Nenalezen žádný element <nsesss:Dokument>.");
        }
        for(Node dokument: dokuments)
        {   
            Node ad = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Manipulace",
                                             "nsesss:AnalogovyDokument");
            if(ad == null){
                return nastavChybu("Nenalezen element <nsesss:AnalogovyDokument>. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".", dokument);
            }
            String hodnotaAnalogovyDokument = ad.getTextContent();
            if (hodnotaAnalogovyDokument.equals("ano") && ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE,
                                                                                 "nsesss:Puvod",
                                                                                 "nsesss:VlastniDokument") != null) {
                Node mnozstvi = ValuesGetter.getXChild(dokument, NsessV3.EVIDENCNI_UDAJE, "nsesss:Puvod",
                                                       "nsesss:VlastniDokument", "nsesss:VytvoreneMnozstvi");
                if(mnozstvi == null){
                    return nastavChybu("Nenalezen povinný element <nsesss:VytvoreneMnozstvi>. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".", dokument);
                }
                else{
                    String s = mnozstvi.getTextContent();
                    if (!HelperString.hasContent(s)) {
                        return nastavChybu("Element <nsesss:VytvoreneMnozstvi> obsahuje prázdnou hodnotu. Dokumentu " + kontrola.getIdentifikatory(dokument) + ".", mnozstvi);
                    }
                }
            }
        }    
        return true;
	}

}
