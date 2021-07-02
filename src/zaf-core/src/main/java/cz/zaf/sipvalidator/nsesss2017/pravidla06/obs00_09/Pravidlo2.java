package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs00_09;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.2 Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.
public class Pravidlo2 extends K06PravidloBase {

	static final public String OBS2 = "obs2";

	public Pravidlo2(K06_Obsahova kontrola) {
		super(kontrola, OBS2,
				"Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
				"Uveden je chybně popis datového balíčku SIP.",
				"Bod 2.1. přílohy č. 3 NSESSS."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
		Node metsMets = metsParser.getMetsRootNode();

        if(!ValuesGetter.hasAttribut(metsMets, "LABEL")){
            return nastavChybu("Nenalezen atribut LABEL kořenového elementu <mets:mets>.", metsMets);
        }
        
        String hodLab = ValuesGetter.getValueOfAttribut(metsMets, "LABEL");
        if (StringUtils.isBlank(hodLab)) {
        	return nastavChybu("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je prázdná.", metsMets);
        }
        if (!hodLab.equals("Datový balíček pro provedení skartačního řízení") &&
        		!hodLab.equals("Datový balíček pro předávání dokumentů a jejich metadat do archivu")) {
        	return nastavChybu("Atribut LABEL kořenového elementu <mets:mets> nemá správnou hodnotu. Jeho hodnota je: "
                        + hodLab + ".", metsMets);
        }
        return true;
	}

}
