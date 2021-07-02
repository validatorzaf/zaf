package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:amdSec>.");
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:digiprovMD")){
                return nastavChybu("Element <mets:amdSec> neobsahuje právě jeden dětský element <mets:digiprovMD>.", getMistoChyby(node));
            }
        }
        return true;
	}
}
