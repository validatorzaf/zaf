package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.30 Každý element <mets:amdSec> obsahuje atribut ID.",
public class Pravidlo30 extends K06PravidloBase {
	
	static final public String OBS30 = "obs30";

	public Pravidlo30() {
		super(OBS30,
				"Každý element <mets:amdSec> obsahuje atribut ID.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.9. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> nodeList = metsParser.getNodes(MetsElements.AMD_SEC);
        if(CollectionUtils.isEmpty(nodeList)){
            return nastavChybu("Nenalezen žádný element <mets:amdSec>.");
        }
        for(Node node: nodeList) {
            if(!ValuesGetter.hasAttribut(node, "ID")){
                return nastavChybu("Element <mets:amdSec> nemá atribut ID.", node);
            }
        }
        return true;
	}


}
