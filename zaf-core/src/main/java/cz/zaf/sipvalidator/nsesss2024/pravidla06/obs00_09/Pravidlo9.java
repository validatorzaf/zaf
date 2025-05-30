package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.9 Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.
public class Pravidlo9 extends K06PravidloBase {

	static final public String OBS9 = "obs9";

	public Pravidlo9() {
		super(OBS9,
				"Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.",
				"Uvedena je chybně adresa jmenného prostoru schématu XML.",
				"Bod 1.1 přílohy č. 2 NSESSS."
				);
	}

	@Override
    protected void kontrola() {
		Node metsMets = metsParser.getMetsRootNode();

        String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xmlns:xlink");
        if (hodnota == null) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut xmlns:xlink kořenového elementu <mets:mets>.",
                        metsMets);
        }

        if (!hodnota.equals("http://www.w3.org/1999/xlink")) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut xmlns:xlink kořenového elementu <mets:mets> neobsahuje hodnotu http://www.w3.org/1999/xlink.",
                        metsMets);
        }
	}

}
