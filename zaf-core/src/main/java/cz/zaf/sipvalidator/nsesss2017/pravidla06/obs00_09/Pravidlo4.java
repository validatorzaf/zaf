package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

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
    protected void kontrola() {
		Node metsMets = metsParser.getMetsRootNode();

        String hod = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xsi");
        if (hod == null) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut xmlns:xsi kořenového elementu <mets:mets>.",
                        metsMets);
        }

        if (StringUtils.isBlank(hod)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                        "Atribut xmlns:xsi kořenového elementu <mets:mets> má prázdnou hodnotu.", metsMets);
        }
        
        if (!hod.equals("http://www.w3.org/2001/XMLSchema-instance")) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut xmlns:xsi kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                                + hod + ".",
                        metsMets);
        }
	}

}
