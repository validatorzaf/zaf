package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.15 Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
public class Pravidlo15 extends K06PravidloBase {

	static final public String OBS15 = "obs15";

	public Pravidlo15(K06_Obsahova kontrola) {
		super(kontrola, OBS15,
				"Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
				"Chybí datum vytvoření datového balíčku SIP.",
				"Bod 2.2. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsHdr = metsParser.getMetsHdr();
        if(metsHdr == null) {
        	return nastavChybu("Nenalezen element <mets:metsHdr>.");
        }
        if(!ValuesGetter.hasAttribut(metsHdr, "CREATEDATE")){
            return nastavChybu("Element <mets:metsHdr> nemá atribut CREATEDATE.", getMistoChyby(metsHdr));
        }
        return true;
	}
}
