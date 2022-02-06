package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
public class Pravidlo33 extends K06PravidloBaseOld {
	
	static final public String OBS33 = "obs33";

	public Pravidlo33() {
		super(OBS33,
				"Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.11. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
	    List<Node> nodeList = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if(nodeList.size() == 0){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for(Node digiprovMD: nodeList) {
            List<Node> list = ValuesGetter.getChildNodes(digiprovMD, "mets:mdWrap");
            if(list.isEmpty()){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }
            if(list.size() > 1){
                return nastavChybu("Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", digiprovMD);
            }
        }
        return true;
	}


}
