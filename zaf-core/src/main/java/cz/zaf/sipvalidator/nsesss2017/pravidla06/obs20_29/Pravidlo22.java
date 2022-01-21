package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.22 Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
public class Pravidlo22 extends K06PravidloBase {

	static final public String OBS22 = "obs22";

	public Pravidlo22() {
		super(OBS22,
				"Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
				"Chybí povinná (popisná) část struktury datového balíčku SIP.",
				"Bod 2.7. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsDmdSec = metsParser.getMetsDmdSec();
        if(metsDmdSec == null) {
        	return nastavChybu("Nenalezen element <mets:dmdSec>.");
        }
        if(!ValuesGetter.hasChildWithName(metsDmdSec, "mets:mdWrap")){
            return nastavChybu("Element <mets:dmdSec> neobsahuje žádný dětský element <mets:mdWrap>.", getMistoChyby(metsDmdSec));
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsDmdSec, "mets:mdWrap")){
            return nastavChybu("Element <mets:dmdSec> obsahuje více než jeden dětský element <mets:mdWrap>.", getMistoChyby(metsDmdSec));
        }
        return true;
	}

}
