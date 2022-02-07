package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.37 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
public class Pravidlo37 extends K06PravidloBaseOld {
	
	static final public String OBS37 = "obs37";

	public Pravidlo37() {
		super(OBS37,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
				"Uveden je chybně popis schématu XML.",
				"Bod 2.11. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        List<Element> nodeList = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if(nodeList.size() == 0){
            return nastavChybu("Nenalezen žádný element <mets:digiprovMD>.");
        }
        for (Element digiprovMD : nodeList) {
            Element mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if(mdWrap == null){
                return nastavChybu("Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", getMistoChyby(digiprovMD));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MIMETYPE");
            if(StringUtils.isEmpty(g)) {
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", getMistoChyby(mdWrap));
            }
            if(!g.equals("text/xml")) {
                return nastavChybu("Atribut MIMETYPE neobsahuje hodnotu text/xml.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}


}
