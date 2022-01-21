package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.List;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.49 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
public class Pravidlo49 extends K06PravidloBase {
	
	static final public String OBS49 = "obs49";

	public Pravidlo49() {
		super(OBS49,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
				"Chybí datum vytvoření komponenty (počítačového souboru).", 
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
	    List<Node> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);
        for (Node metsFile: nodeListMetsFile) {            
            if (!ValuesGetter.hasAttribut(metsFile, "CREATED")) {
                return nastavChybu("Elenemt <mets:file> neobsahuje atribut CREATED.", metsFile);
            }
        }   
        return true;
	}

}
