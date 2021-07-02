package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.37 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
public class Pravidlo37 extends K06PravidloBase {
	
	static final public String OBS37 = "obs37";

	public Pravidlo37() {
		super(OBS37,
				"Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
				"Uveden je chybně popis schématu XML.",
				"Bod 2.11. přílohy č. 3 NSESSS.");
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
            if(!ValuesGetter.hasAttribut(mdWrap, "MIMETYPE")){
                return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", getMistoChyby(mdWrap));
            }
            String g = ValuesGetter.getValueOfAttribut(mdWrap, "MIMETYPE");
            if(!g.equals("text/xml")){
                return nastavChybu("Atribut MIMETYPE neobsahuje hodnotu text/xml.", getMistoChyby(mdWrap));
            }
        }
        return true;
	}


}
