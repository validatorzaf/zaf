package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.25 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
public class Pravidlo25 extends K06PravidloBaseOld {

	static final public String OBS25 = "obs25";

	public Pravidlo25() {
		super(OBS25,
				"Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
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
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPE")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPE.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPE");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MDTYPE elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPE", "OTHER")){
            return nastavChybu("Atribut MDTYPE elementu <mets:mdWrap> neobsahuje hodnotu OTHER.", getMistoChyby(metsMdWrap));
        }
        return true;
	}

}
