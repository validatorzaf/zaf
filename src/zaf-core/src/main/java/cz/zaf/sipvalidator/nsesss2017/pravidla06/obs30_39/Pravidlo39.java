package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.39 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
public class Pravidlo39 extends K06PravidloBase {
	
	static final public String OBS39 = "obs39";

	public Pravidlo39() {
		super(OBS39,
			    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
				"Datový balíček SIP neobsahuje transakční protokol.",
				"Bod 2.12. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:digiprovMD", metsParser.getDocument());
        if(nodeList == null){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for(int i = 0; i < nodeList.getLength(); i++){
            Node digiprovMD = nodeList.item(i);
            Node mdWr = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWr == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            Node xDt = ValuesGetter.getXChild(mdWr, "mets:xmlData");
            if(xDt == null){
                return nastavChybu("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", getMistoChyby(mdWr));
            }
            Node tlo = ValuesGetter.getXChild(xDt, "tp:TransakcniLogObjektu");
            if(tlo == null){
                return nastavChybu("Element <mets:xmlData> neobsahuje žádný dětský element <tp:TransakcniLogObjektu>.", getMistoChyby(xDt));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(xDt , "tp:TransakcniLogObjektu")){
                return nastavChybu("Element <mets:xmlData> neobsahuje právě jeden dětský element <tp:TransakcniLogObjektu>.", getMistoChyby(xDt));
            }
        }

        return true;
	}


}
