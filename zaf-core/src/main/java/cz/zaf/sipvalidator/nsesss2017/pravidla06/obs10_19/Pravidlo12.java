package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.12 Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
public class Pravidlo12 extends K06PravidloBaseOld {

	static final public String OBS12 = "obs12";

	public Pravidlo12() {
		super(OBS12,
				"Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.9. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();
        if(metsMets == null) {
        	return nastavChybu("Nenalezen kořenový element <mets:mets>.");
        }
        if(!ValuesGetter.hasChildWithName(metsMets, MetsElements.AMD_SEC)){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:amdSec>.", metsMets);
        }       
        return true;
	}
}
