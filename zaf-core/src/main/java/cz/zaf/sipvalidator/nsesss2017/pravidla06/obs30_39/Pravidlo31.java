package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.31 Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
public class Pravidlo31 extends K06PravidloBase {
	
	static final public String OBS31 = "obs31";

	public Pravidlo31() {
		super(OBS31,
				"Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.10. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
	    List<Node> nodeList = metsParser.getNodes(MetsElements.AMD_SEC);
        if(CollectionUtils.isEmpty(nodeList)){
            return nastavChybu("Nenalezen žádný element <mets:amdSec>.");
        }
        for(Node node: nodeList) {
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:digiprovMD")){
                return nastavChybu("Element <mets:amdSec> neobsahuje právě jeden dětský element <mets:digiprovMD>.", node);
            }
        }
        return true;
	}
}
