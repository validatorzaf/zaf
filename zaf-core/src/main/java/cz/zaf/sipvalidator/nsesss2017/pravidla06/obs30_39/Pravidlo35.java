package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ č.35 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů
 * &lt;mets:mdWrap&gt; atribut OTHERMDTYPE s hodnotou TP.
 *
 */
public class Pravidlo35 extends K06PravidloBase {
	
	static final public String OBS35 = "obs35";

	public Pravidlo35() {
		super(OBS35,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
				"Uveden je chybně popis schématu XML.",
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
            Node mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "OTHERMDTYPE");
            if(StringUtils.isEmpty(g)) {
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", getMistoChyby(mdWrap));
            }
            
            if(!g.equals("TP")){
                return nastavChybu("Atribut OTHERMDTYPE neobsahuje hodnotu TP.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}

}