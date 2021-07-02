package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.19 Každý element <mets:agent> obsahuje atribut ID.",
public class Pravidlo19 extends K06PravidloBase {

	static final public String OBS19 = "obs19";

	public Pravidlo19(K06_Obsahova kontrola) {
		super(kontrola, OBS19,
				"Každý element <mets:agent> obsahuje atribut ID.",
				"Uveden je chybně popis původce.",
				"Bod 2.3. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) {
        	return nastavChybu("Nenalezen žádný element <mets:agent>.");
        }
        int pocitadlo = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
           if(!ValuesGetter.hasAttribut(node, "ID")){
               pocitadlo++;
               ch += getMistoChyby(node) + " ";
           }
        }
        if(pocitadlo != 0) return nastavChybu("Element <mets:agent> neobsahuje atribut ID.", ch);
        return true;
	}

}

