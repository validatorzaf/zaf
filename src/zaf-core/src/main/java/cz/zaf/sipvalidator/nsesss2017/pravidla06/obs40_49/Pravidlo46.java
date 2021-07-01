package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.46 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
public class Pravidlo46 extends K06PravidloBase {
	
	static final public String OBS46 = "obs46";

	public Pravidlo46(K06_Obsahova kontrola) {
		super(kontrola, OBS46,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
				"Chybí popis pro ověření celistvosti komponenty (počítačového souboru) nebo je chybně uveden.",
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile== null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "CHECKSUMTYPE")){
                return nastavChybu("Element <mets:file> nemá atribut CHECKSUMTYPE.", getMistoChyby(metsFile));
            }
            String hodnota = ValuesGetter.getValueOfAttribut(metsFile, "CHECKSUMTYPE");
            if(!hodnota.equals("SHA-256") && !hodnota.equals("SHA-512")){
                return nastavChybu("Atribut CHECKSUMTYPE obsahuje nepovolenou hodnotu: " + hodnota + ".", getMistoChyby(metsFile));
            }
        }
        return true;
	}

}
