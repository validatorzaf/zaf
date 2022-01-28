package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.24 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
public class Pravidlo24 extends K06PravidloBaseOld {

	static final public String OBS24 = "obs24";

	public Pravidlo24() {
		super(OBS24,
				"Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
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
        if(!ValuesGetter.hasAttribut(metsMdWrap, "OTHERMDTYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "OTHERMDTYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut OTHERMDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "OTHERMDTYPE", "NSESSS")){
            return nastavChybu("Atribut OTHERMDTYPE elementu <mets:mdWrap> neobsahuje hodnotu NSESSS.", getMistoChyby(metsMdWrap));
        }
        return true;
	}

}
