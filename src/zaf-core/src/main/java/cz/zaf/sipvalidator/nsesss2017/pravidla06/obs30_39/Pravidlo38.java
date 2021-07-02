package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/** 
 * OBSAHOVÁ č.38 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů 
 * <mets:mdWrap> právě jeden dětský element <mets:xmlData>."
 * 
 *
 */
public class Pravidlo38 extends K06PravidloBase {
	
	static final public String OBS38 = "obs38";

	public Pravidlo38() {
		super(OBS38,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
				"Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
				"Bod 2.12. přílohy č. 3 NSESSS."
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
            Node mdWprap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWprap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            if(ValuesGetter.getXChild(mdWprap, "mets:xmlData") == null){
                return nastavChybu("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", getMistoChyby(mdWprap));
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(mdWprap, "mets:xmlData")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje právě jeden dětský element <mets:xmlData>.", getMistoChyby(mdWprap));
            }
        }

        return true;
	}

}
