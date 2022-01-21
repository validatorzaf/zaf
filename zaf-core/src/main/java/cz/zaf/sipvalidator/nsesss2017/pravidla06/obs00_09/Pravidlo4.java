package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.4 Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
public class Pravidlo4 extends K06PravidloBase {

	static final public String OBS4 = "obs4";

	public Pravidlo4() {
		super(OBS4,
				"Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
				"Uvedena je chybně adresa jmenného prostoru schématu XML.",
				"Bod 2.1. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();

        if (!ValuesGetter.hasAttribut(metsMets, "xmlns:xsi")) {
            return nastavChybu("Nenalezen atribut xmlns:xsi kořenového elementu <mets:mets>.", metsMets); 
        }
        
        String hod = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xsi");
        if (StringUtils.isBlank(hod)) {
        	return nastavChybu("Atribut xmlns:xsi kořenového elementu <mets:mets> má prázdnou hodnotu.", metsMets);
        }
        
        if (!hod.equals("http://www.w3.org/2001/XMLSchema-instance")) {
        	return nastavChybu("Atribut xmlns:xsi kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                        + hod + ".",
                        metsMets);
        }
		return true;
	}

}
