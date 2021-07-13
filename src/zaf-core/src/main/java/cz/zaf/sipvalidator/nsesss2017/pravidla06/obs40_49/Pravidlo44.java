package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.JmenaElementu;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.44 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>.",
public class Pravidlo44 extends K06PravidloBase {
	
	static final public String OBS44 = "obs44";

	public Pravidlo44() {
		super(OBS44,
				"Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>, přičemž právě jedna hodnota atributu DMDID odpovídá právě jedné hodnotě atributu ID.",
				"Chybí provázání komponenty (počítačového souboru) s popisnou částí.",
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
	    // cteni mets:file
        List<Node> nodeListMetsFile = metsParser.getNodes(MetsElements.FILE);        
        List<Node> nodeListKomponenty = metsParser.getNodes(JmenaElementu.KOMPONENTA);
        if(nodeListKomponenty.size() != nodeListMetsFile.size()){
            return nastavChybu("Nenalezen shodný počet <nsesss:Komponenta> a <mets:file>.");
        }
        if(nodeListKomponenty.size()==0) {
            return true;
        }

        Map<String, Node> filesIdMap = new HashMap<>();
        for(Node metsFile: nodeListMetsFile){
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            if(StringUtils.isEmpty(dmdid)) {
                return nastavChybu("Element <mets:file> nemá atribut DMDID.",  metsFile);
            }
            Node prevNode = filesIdMap.put(dmdid, metsFile);
            if(prevNode!=null) {
                return nastavChybu("Více elementů <mets:file> má shodnou hodnotu DMDID.",  metsFile);
            }
        }

        // cteni nsesss:komponenta
        Map<String, Node> komponentyIdMap = new HashMap<>();
        for(Node komponenta: nodeListKomponenty) {
            String id = ValuesGetter.getValueOfAttribut(komponenta, "ID");
            if(StringUtils.isEmpty(id)){
                return nastavChybu("Element <nsesss:Komponenta> nemá atribut ID. " + getJmenoIdentifikator(komponenta), komponenta);
            }
            
            // Check if exists in fileIdMap            
            Node divNode = filesIdMap.get(id);
            if(divNode==null) {
                return nastavChybu("Element <nsesss:Komponenta> nemá odpovídající záznam ve strukturální mapě. " + getJmenoIdentifikator(komponenta), komponenta);
            }
            
            komponentyIdMap.put(id, komponenta);
        }
        
        // Kontrola odkazů ze strukturální mapy
        for(Node metsFile: nodeListMetsFile) {
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            Node dmdNode = komponentyIdMap.get(dmdid);
            if(dmdNode==null) {
                return nastavChybu("Element <mets:file> neodkazuje na odpovídající <nsesss:Komponenta>",
                                   metsFile);
            }
        }
        return true;
	}

}
