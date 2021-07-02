package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.26 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
public class Pravidlo26 extends K06PravidloBase {

	static final public String OBS26 = "obs26";

	public Pravidlo26() {
		super(OBS26,
				"Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
				"Uveden je chybně popis schématu XML.",
				"Bod 2.7. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMdWrap = metsParser.getMetsMdWrap();
        if(metsMdWrap == null) {
        	return nastavChybu("Nenalezen element <mets:mdWrap>.");
        }
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MIMETYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MIMETYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MIMETYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MIMETYPE", "text/xml")){
            return nastavChybu("Atribut MIMETYPE elementu <mets:mdWrap> neobsahuje hodnotu text/xml.", getMistoChyby(metsMdWrap));
        }
        return true;
	}

}
