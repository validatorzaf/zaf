package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs00_09;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

// OBSAHOVÁ č.2 Element <mets:mets> obsahuje atribut LABEL s hodnotou
// Datový balíček pro provedení skartačního řízení nebo
// Datový balíček pro předávání dokumentů a jejich metadat do archivu.
public class Pravidlo2 extends K06PravidloBase {

	static final public String OBS2 = "obs2";

	public Pravidlo2() {
		super(OBS2,
				"Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
				"Uveden je chybně popis datového balíčku SIP.", 
                "Požadavek 9.2.1 NSESSS; bod 1.1 přílohy č. 2 NSESSS.");
	}

	@Override
    protected void kontrola() {
		Node metsMets = metsParser.getMetsRootNode();

        String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
        if (hodLab == null) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT, "Nenalezen atribut LABEL kořenového elementu <mets:mets>.", metsMets);
		}

		if (StringUtils.isBlank(hodLab)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
					"Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.",
					metsMets);
		}
		if (!hodLab.equals("Datový balíček pro provedení skartačního řízení")
				&& !hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                                + hodLab + ".", metsMets);
		}
	}

}
