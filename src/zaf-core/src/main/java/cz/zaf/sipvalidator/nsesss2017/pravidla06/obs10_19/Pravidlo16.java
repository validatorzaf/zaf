package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.ArrayList;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
public class Pravidlo16 extends K06PravidloBase {

	static final public String OBS16 = "obs16";

	public Pravidlo16(K06_Obsahova kontrola) {
		super(kontrola, OBS16,
				"Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
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
            if(ValuesGetter.hasAttributValue(nodeList.get(i), "TYPE", "ORGANIZATION")) pocitadlo++;
        }
        if(pocitadlo == 0){
            return nastavChybu("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", getMistoChyby(metsHdr));
        }
        if(pocitadlo > 1){
            return nastavChybu("Element <mets:metsHdr> obsahuje více elementů <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.", getMistoChyby(metsHdr));
        }
        return true;
	}
}
