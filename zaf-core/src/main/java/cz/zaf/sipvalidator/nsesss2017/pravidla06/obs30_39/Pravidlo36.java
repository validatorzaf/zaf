package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ č.36 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů
 * &lt;mets:mdWrap&gt; atribut MDTYPE s hodnotou OTHER.
 *
 */
public class Pravidlo36 extends K06PravidloBaseOld {
	
	static final public String OBS36 = "obs36";

	public Pravidlo36() {
		super(OBS36,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
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
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPE");
            if(StringUtils.isEmpty(g)) {
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", getMistoChyby(mdWrap));
            }
            if(!g.equals("OTHER")){
                return nastavChybu("Atribut MDTYPE neobsahuje hodnotu OTHER.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}

}
