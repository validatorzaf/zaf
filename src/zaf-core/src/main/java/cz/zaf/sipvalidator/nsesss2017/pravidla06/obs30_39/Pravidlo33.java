package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
public class Pravidlo33 extends K06PravidloBase {
	
	static final public String OBS33 = "obs33";

	public Pravidlo33(K06_Obsahova kontrola) {
		super(kontrola, OBS33,
				"Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.11. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            ArrayList<Node> list = ValuesGetter.getSpecificChildWithName(digiprovMD, "mets:mdWrap");
            if(list.isEmpty()){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            if(list.size() > 1){
                return nastavChybu("Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
        }
        return true;
	}


}
