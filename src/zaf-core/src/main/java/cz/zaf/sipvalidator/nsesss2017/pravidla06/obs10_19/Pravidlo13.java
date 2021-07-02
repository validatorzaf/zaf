package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.13 Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
public class Pravidlo13 extends K06PravidloBase {

	static final public String OBS13 = "obs13";

	public Pravidlo13(K06_Obsahova kontrola) {
		super(kontrola, OBS13,
				"Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
				"Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
				"Bod 2.2.17. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();
        if(metsMets == null) {
        	return nastavChybu("Nenalezen kořenový element <mets:mets>.");
        }
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:structMap")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:structMap>.", getMistoChyby(metsMets));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:structMap")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:structMap>.", getMistoChyby(metsMets));
        }        
        return true;
	}

}
