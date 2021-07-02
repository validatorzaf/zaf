package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.18 Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
public class Pravidlo18 extends K06PravidloBase {

	static final public String OBS18 = "obs18";

	public Pravidlo18() {
		super(OBS18, "Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
				"Uveden je chybně popis původce.", 
				"Bod 2.3. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
		NodeList nodeList = ValuesGetter.getAllAnywhere("mets:agent", metsParser.getDocument());
		if (nodeList == null) {
			return nastavChybu("Nenalezen žádný element <mets:agent>.");
		}
		int pocitadlo = 0;
		String ch = "";
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (!ValuesGetter.hasAttributValue(node, "ROLE", "CREATOR")) {
				pocitadlo++;
				ch += getMistoChyby(node) + " ";
			}
		}
		if (pocitadlo != 0)
			return nastavChybu("Element <mets:agent> neobsahuje atribut ROLE s hodnotou CREATOR.", ch);
		return true;
	}
}
