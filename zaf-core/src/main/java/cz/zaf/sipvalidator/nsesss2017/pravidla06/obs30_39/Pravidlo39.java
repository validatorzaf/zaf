package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.39 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
public class Pravidlo39 extends K06PravidloBaseOld {
	
	static final public String OBS39 = "obs39";

	public Pravidlo39() {
		super(OBS39,
			    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
				"Datový balíček SIP neobsahuje transakční protokol.",
				"Bod 2.12. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Element> nodeList = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if(nodeList.size() == 0){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for (Element digiprovMD : nodeList) {
            Element mdWr = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWr == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }
            Element xDt = ValuesGetter.getXChild(mdWr, "mets:xmlData");
            if(xDt == null){
                return nastavChybu("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", mdWr);
            }
            Element tlo = ValuesGetter.getXChild(xDt, "tp:TransakcniLogObjektu");
            if(tlo == null){
                return nastavChybu("Element <mets:xmlData> neobsahuje žádný dětský element <tp:TransakcniLogObjektu>.", xDt);
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(xDt , "tp:TransakcniLogObjektu")){
                return nastavChybu("Element <mets:xmlData> neobsahuje právě jeden dětský element <tp:TransakcniLogObjektu>.", xDt);
            }
        }

        return true;
	}


}
