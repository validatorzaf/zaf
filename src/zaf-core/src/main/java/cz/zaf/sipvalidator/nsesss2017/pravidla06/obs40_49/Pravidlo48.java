package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.io.File;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.helper.HelperString;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;

//OBSAHOVÁ č.48 Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
public class Pravidlo48 extends K06PravidloBase {
	
	static final public String OBS48 = "obs48";

	public Pravidlo48() {
		
		super(OBS48, 
			    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
			    "Chybí velikost komponenty (počítačového souboru) nebo je uvedena chybně.",			    
				"Bod 2.15. přílohy č. 3 NSESSS.");
	}

	@Override
	protected boolean kontrolaPravidla() {
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        for (int i = 0; i < nodeListMetsFile.getLength(); i++)
        {
            Node metsFile = nodeListMetsFile.item(i);
            if(!kontrolaSouboru(metsFile)) {
            	return false;
            }
        }
        return true;
	}

	private boolean kontrolaSouboru(Node metsFile) {
        if(!ValuesGetter.hasAttribut(metsFile, "SIZE")){
            return nastavChybu("Element <mets:file> neobsahuje atribut SIZE.", getMistoChyby(metsFile));
        }
        String hodnotaVelikosti = ValuesGetter.getValueOfAttribut(metsFile, "SIZE");            
        Node flocat = ValuesGetter.getXChild(metsFile, "mets:FLocat");
        if(flocat == null){
            return nastavChybu("Nenalezen dětský element <mets:FLocat> elementu <mets:file>.", getMistoChyby(metsFile));
        }
        if(!ValuesGetter.hasAttribut(flocat, "xlink:href")){
            return nastavChybu("Element <mets:FLocat> neobsahuje atribut xlink:href.", getMistoChyby(flocat));
        }
        String xlinkHref = ValuesGetter.getValueOfAttribut(flocat, "xlink:href"); // komponenty/jmenosouboru
        xlinkHref = HelperString.replaceSeparators(xlinkHref);
        String cestaKeKomponente = SIP_MAIN_helper.getCesta_komponenty(context.getSip());
        File file = new File(cestaKeKomponente);
        file = new File(file.getParentFile().getAbsolutePath() + File.separator + xlinkHref);
        if(!file.exists()){
        	return nastavChybu("Nenalezena příslušná komponenta v složce komponenty.", getMistoChyby(flocat) + " Soubor: " + xlinkHref + ".");
        }
        String velikost = String.valueOf(file.length());
        if(!velikost.equals(hodnotaVelikosti)){
            return nastavChybu("Velikost komponenty není totožná s metadaty.", getMistoChyby(metsFile) + " Komponenta: " + file.getName() + ".");
        }
        return true;
	}

}
