package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.ArrayList;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
public class Pravidlo17 extends K06PravidloBase {

	static final public String OBS17 = "obs17";

	public Pravidlo17(K06_Obsahova kontrola) {
		super(kontrola, OBS17,
				"Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
				"Uveden je chybně popis původce.",
				"Bod 2.3. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsHdr = metsParser.getMetsHdr();
        if(metsHdr == null) {
        	return nastavChybu("Nenalezen element <mets:metsHdr>.");
        }
        ArrayList<Node> nodeList = ValuesGetter.getChildList(metsHdr, "mets:agent");
        if(nodeList == null || nodeList.isEmpty()){
            return nastavChybu("Nenalezen element <mets:agent>.", getMistoChyby(metsHdr));
        }
        int pocitadlo = 0;
        for(int i = 0; i < nodeList.size(); i++){
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "INDIVIDUAL")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return nastavChybu("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", getMistoChyby(metsHdr));
        }
        return true;
	}

}
