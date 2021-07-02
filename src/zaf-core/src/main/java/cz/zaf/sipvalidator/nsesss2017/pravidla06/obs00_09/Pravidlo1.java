package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.1 Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
public class Pravidlo1 extends K06PravidloBase {

	static final public String OBS1 = "obs1";

	public Pravidlo1(K06_Obsahova kontrola) {
		super(kontrola, OBS1, 
				"Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
				"Chybí identifikátor datového balíčku SIP.", 
				"Bod 2.1. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();

		if (!ValuesGetter.hasAttribut(metsMets, "OBJID")) {
			return nastavChybu("Nenalezen atribut OBJID kořenového elementu <mets:mets>.", metsMets);
		}
		if (!HelperString.hasContent(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"))) {
			return nastavChybu("Atribut OBJID kořenového elementu <mets:mets> není vyplněn.", metsMets);
		}
		return true;
	}

}
