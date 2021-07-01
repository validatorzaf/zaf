package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/** 
 * OBSAHOVÁ č.34 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> 
 * atribut MDTYPEVERSION s hodnotou 1.0. 
 *
 */
public class Pravidlo34 extends K06PravidloBase {
	
	static final public String OBS34 = "obs34";

	public Pravidlo34(K06_Obsahova kontrola) {
		super(kontrola, OBS34,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 1.0.",
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
            if(!ValuesGetter.hasAttribut(mdWrap, "MDTYPEVERSION")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPEVERSION");
            if(!g.equals("1.0")){
                return nastavChybu("Atribut MDTYPEVERSION neobsahuje hodnotu 1.0.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}

}
