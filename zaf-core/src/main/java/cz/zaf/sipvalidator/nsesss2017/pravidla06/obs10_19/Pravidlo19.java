package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
public class Pravidlo19 extends K06PravidloBaseOld {

	static final public String OBS19 = "obs19";

	public Pravidlo19() {
		super(OBS19,
				"Každý element <mets:agent> obsahuje atribut ID.",
				"Uveden je chybně popis původce.",
				"Bod 2.3. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> nodes = metsParser.getNodes(MetsElements.AGENT);
        if(CollectionUtils.isEmpty(nodes)){
            return nastavChybu("Nenalezen element <mets:agent>.");
        }
        List<Node> errorList = new ArrayList<>(0);

        for(Node node: nodes){
           if(!ValuesGetter.hasAttribut(node, "ID")){
               errorList.add(node);
           }
        }
        if(errorList.size()>0) {
            return nastavChybu("Element <mets:agent> neobsahuje atribut ID.", errorList);
        }
        return true;
	}

}

