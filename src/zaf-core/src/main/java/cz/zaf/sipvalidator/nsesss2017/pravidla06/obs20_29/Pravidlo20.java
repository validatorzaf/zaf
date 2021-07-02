package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
public class Pravidlo20 extends K06PravidloBase {

	static final public String OBS20 = "obs20";

	public Pravidlo20(K06_Obsahova kontrola) {
		super(kontrola, OBS20,
				"Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
				"Chybí informace o původci.",
				"Bod 2.4. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
        if(nodeList == null) {
        	return nastavChybu("Nenalezen žádný element <mets:agent>.");
        }
        int pocitadlo = 0;
        int pocitadlo2 = 0;
        String ch = "";
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(ValuesGetter.hasOnlyOneChild_ElementNode(node, "mets:name")){
                if (!HelperString.hasContent(ValuesGetter.getXChild(node, "mets:name").getTextContent())) {
                    pocitadlo2++;
                    ch += getMistoChyby(node) + " ";
                }
            } 
            else{
               pocitadlo ++; 
               ch += getMistoChyby(node) + " ";
            } 
        }
        if(pocitadlo != 0){
            String h = "";
            if(pocitadlo2 != 0){
                h = "Ďetský element <mets:name> má prázdnou hodnotu.";
            }
            return nastavChybu("Element <mets:agent> neobsahuje právě jeden dětský element <mets:name>." + h, ch);
        }
        if(pocitadlo2 != 0){
            return nastavChybu("Element <mets:agent> má nevyplněnou hodnotu u dětského elementu <mets:name>.", ch);
        }
        return true;
	}

}
