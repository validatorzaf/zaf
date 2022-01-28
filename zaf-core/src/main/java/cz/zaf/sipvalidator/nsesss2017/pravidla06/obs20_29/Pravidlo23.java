package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.23 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
public class Pravidlo23 extends K06PravidloBaseOld {

	static final public String OBS23 = "obs23";

	public Pravidlo23() {
		super(OBS23,
				"Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
				"Uveden je chybně popis schématu XML.", // Uveden je chybně popis původce.
				"Bod 2.7. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMdWrap = metsParser.getMetsMdWrap();
        if(metsMdWrap == null) {
        	return nastavChybu("Nenalezen element <mets:mdWrap>.");
        }
        if(!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPEVERSION")){
            return nastavChybu("Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", getMistoChyby(metsMdWrap));
        }
        String g = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPEVERSION");
        if (StringUtils.isBlank(g)) {
            return nastavChybu("Atribut MDTYPEVERSION elementu <mets:mdWrap> má prázdnou hodnotu.", getMistoChyby(metsMdWrap));
        }
        if(!ValuesGetter.hasAttributValue(metsMdWrap, "MDTYPEVERSION", "3.0")){
            return nastavChybu("Atribut MDTYPEVERSION elementu <mets:mdWrap> neobsahuje hodnotu 3.0.", getMistoChyby(metsMdWrap));
        }
        return true;
	}

}
