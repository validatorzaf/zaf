package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
public class Pravidlo18 extends K06PravidloBaseOld {

	static final public String OBS18 = "obs18";

	public Pravidlo18() {
		super(OBS18, "Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
				"Uveden je chybně popis původce.", 
				"Bod 2.3. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Element> nodes = metsParser.getNodes(MetsElements.AGENT);
        if(CollectionUtils.isEmpty(nodes)){
            return nastavChybu("Nenalezen element <mets:agent>.");
        }
		List<Node> errorList = new ArrayList<>(0);
		for (Node node: nodes) {
			if (!ValuesGetter.hasAttributValue(node, "ROLE", "CREATOR")) {
				errorList.add(node);
			}
		}
		if (errorList.size() > 0) {
			return nastavChybu("Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", errorList);
		}
		return true;
	}
}
