package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.20 Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
public class Pravidlo20 extends K06PravidloBase {

	static final public String OBS20 = "obs20";

	public Pravidlo20() {
		super(OBS20,
				"Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
				"Chybí informace o původci.",
				"Bod 2.4. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> nodes = metsParser.getNodes(MetsElements.AGENT);
        if(CollectionUtils.isEmpty(nodes)){
            return nastavChybu("Nenalezen element <mets:agent>.");
        }
        
        int pocitadlo = 0;
        int pocitadlo2 = 0;
        String ch = "";
        for(Node node: nodes){
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
                h = "Dětský element <mets:name> má prázdnou hodnotu.";
            }
            return nastavChybu("Element <mets:agent> neobsahuje právě jeden dětský element <mets:name>." + h, ch);
        }
        if(pocitadlo2 != 0){
            return nastavChybu("Element <mets:agent> má nevyplněnou hodnotu u dětského elementu <mets:name>.", ch);
        }
        return true;
	}

}
