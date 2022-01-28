package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.14 Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
public class Pravidlo14 extends K06PravidloBaseOld {

	static final public String OBS14 = "obs14";

	public Pravidlo14() {
		super(OBS14,
				"Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
				"Chybí datum poslední úpravy datového balíčku SIP.",
				"Bod 2.2. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsHdr = metsParser.getMetsHdr();
        if(metsHdr == null) {
        	return nastavChybu("Nenalezen element <mets:metsHdr>.");
        }
        if(!ValuesGetter.hasAttribut(metsHdr, "LASTMODDATE")){
            return nastavChybu("Element <mets:metsHdr> nemá atribut LASTMODDATE.", getMistoChyby(metsHdr));
        }
        return true;
	}
}
