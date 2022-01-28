package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ č.38 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů
 * &lt;mets:mdWrap&gt; právě jeden dětský element &lt;mets:xmlData&gt;.
 * 
 *
 */
public class Pravidlo38 extends K06PravidloBaseOld {
	
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
        List<Node> nodeList = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if(nodeList.size() == 0){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for(Node digiprovMD: nodeList) {
            Node mdWprap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWprap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }
            if(ValuesGetter.getXChild(mdWprap, "mets:xmlData") == null){
                return nastavChybu("Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", mdWprap);
            }
            if(!ValuesGetter.hasOnlyOneChild_ElementNode(mdWprap, "mets:xmlData")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje právě jeden dětský element <mets:xmlData>.", mdWprap);
            }
        }

        return true;
	}

}
