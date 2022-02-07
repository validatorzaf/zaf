package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.16 Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
public class Pravidlo16 extends K06PravidloBaseOld {

	static final public String OBS16 = "obs16";

	public Pravidlo16() {
		super(OBS16,
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
        List<Element> nodes = metsParser.getNodes(MetsElements.AGENT);
        if(CollectionUtils.isEmpty(nodes)){
            return nastavChybu("Nenalezen element <mets:agent>.", metsHdr);
        }
        int pocitadlo = 0;
        for(Node node: nodes){
            if(ValuesGetter.hasAttributValue(node, "TYPE", "ORGANIZATION")) {
                pocitadlo++;
            }
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
