package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.49 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
public class Pravidlo49 extends K06PravidloBase {
	
	static final public String OBS49 = "obs49";

	public Pravidlo49(K06_Obsahova kontrola) {
		super(kontrola, OBS49,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
				"Chybí datum vytvoření komponenty (počítačového souboru).", 
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if (!ValuesGetter.hasAttribut(metsFile, "CREATED")) {
                return nastavChybu("Elenemt <mets:file> neobsahuje atribut CREATED.", getMistoChyby(nodeListMetsFile.item(i)));
            }
        }   
        return true;
	}

}
