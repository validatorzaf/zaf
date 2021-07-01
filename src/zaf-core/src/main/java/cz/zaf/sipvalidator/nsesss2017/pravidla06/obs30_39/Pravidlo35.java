package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ č.35 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> 
 * atribut OTHERMDTYPE s hodnotou TP.",
 *
 */
public class Pravidlo35 extends K06PravidloBase {
	
	static final public String OBS35 = "obs35";

	public Pravidlo35(K06_Obsahova kontrola) {
		super(kontrola, OBS35,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
				"Uveden je chybně popis schématu XML.",
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
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            if(!ValuesGetter.hasAttribut(mdWrap, "OTHERMDTYPE")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "OTHERMDTYPE");
            if(!g.equals("TP")){
                return nastavChybu("Atribut OTHERMDTYPE neobsahuje hodnotu TP.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}

}
