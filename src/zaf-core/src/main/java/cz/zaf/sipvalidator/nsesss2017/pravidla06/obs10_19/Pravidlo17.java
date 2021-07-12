package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.17 Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
public class Pravidlo17 extends K06PravidloBase {

	static final public String OBS17 = "obs17";

	public Pravidlo17() {
		super(OBS17,
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
        List<Node> nodes = metsParser.getNodes(MetsElements.AGENT);
        if(CollectionUtils.isEmpty(nodes)){
            return nastavChybu("Nenalezen element <mets:agent>.", metsHdr);
        }
        int pocitadlo = 0;
        for(Node node: nodes){
            if(ValuesGetter.hasAttributValue(node, "TYPE", "INDIVIDUAL")) {
                pocitadlo++;
            }
        }
        if(pocitadlo == 0){
            return nastavChybu("Element <mets:metsHdr> neobsahuje žádný element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.", metsHdr);
        }
        return true;
	}

}
