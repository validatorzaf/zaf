package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.10 Element <mets:mets> obsahuje právě jeden dětský element <mets:metsHdr>.",
public class Pravidlo10 extends K06PravidloBaseOld {

	static final public String OBS10 = "obs10";

	public Pravidlo10() {
		super(OBS10,
				"Element <mets:mets> obsahuje dětský element <mets:metsHdr>.",
				"Chybí povinná část (záhlaví) struktury datového balíčku SIP.",
				"Bod 2.2. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();
        if(metsMets == null) {
        	return nastavChybu("Nenalezen kořenový element <mets:mets>.");
        }
        if(!ValuesGetter.hasChildWithName(metsMets, "mets:metsHdr")){
            return nastavChybu("Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", metsMets);
        }
        if(!ValuesGetter.hasOnlyOneChild_ElementNode(metsMets, "mets:metsHdr")){
            return nastavChybu("Kořenový element <mets:mets> má více než jeden dětský element <mets:metsHdr>.", metsMets);
        }
        return true;
	}

}
