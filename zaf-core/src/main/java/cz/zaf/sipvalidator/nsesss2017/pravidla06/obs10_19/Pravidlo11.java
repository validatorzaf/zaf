package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.11 Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
public class Pravidlo11 extends K06PravidloBase {

	static final public String OBS11 = "obs11";

	public Pravidlo11() {
		super(OBS11,
				"Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
				"Chybí povinná (popisná) část struktury datového balíčku SIP.",
				"Bod 2.6. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();
        if(metsMets == null) {
        	return nastavChybu("Nenalezen kořenový element <mets:mets>.");
        }
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:dmdSec")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:dmdSec>.", metsMets);
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:dmdSec")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:dmdSec>.", metsMets);
        }        
        return true;
	}

}
