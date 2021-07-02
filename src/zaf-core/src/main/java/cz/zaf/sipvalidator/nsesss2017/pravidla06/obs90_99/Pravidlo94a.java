package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.94a. Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), 
// která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), 
// obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s 
// hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem.
public class Pravidlo94a extends K06PravidloBase {

    static final public String OBS94A = "obs94a";

    public Pravidlo94a() {
    	super(OBS94A,
    		    "Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem.",
    			"Chybně jsou uvedeny spisové znaky.",
    			"Požadavek 3.1.30 NSESSS.");
    }

	@Override
	protected boolean kontrolaPravidla() {
		if(!kontrolaVecneSkupiny()) {
			return false;
		}

		if(!kontrolaSoucasti()) {
			return false;
		}
		
// TODO? Kontrola pro typove spisy?
//      if(typoveSpisy!= null){
//      for(int i = 0; i < typoveSpisy.getLength(); i++){
//          Node plneUrceny_node = ValuesGetter.getXChild(typoveSpisy.item(i), "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
//          if(plneUrceny_node == null){
//              return add_popisy("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>.", false, get_misto_chyby(typoveSpisy.item(i)));
//          }
//          String hodnota = plneUrceny_node.getTextContent();
//          if(!sz_ma_oddelovac_vsobe(hodnota)){
//              return add_popisy("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač.", false, get_misto_chyby(plneUrceny_node));
//          } 
//      }
//  }

		return true;
	}

	private boolean kontrolaSoucasti() {
		
		NodeList soucasti = ValuesGetter.getAllAnywhere("nsesss:Soucast", metsParser.getDocument());
		if (soucasti == null) {
			return true;
		}
		
		for (int i = 0; i < soucasti.getLength(); i++) {
			Node soucast = soucasti.item(i);
			Node plneUrceny_node = ValuesGetter.getXChild(soucast, "nsesss:EvidencniUdaje", "nsesss:Trideni",
					"nsesss:PlneUrcenySpisovyZnak");
			if (plneUrceny_node == null) {
				return nastavChybu(
						"Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(soucast),
						getMistoChyby(soucast));
			}
			String hodnota = plneUrceny_node.getTextContent();
			if (!K06_Obsahova.spisZnakObsahujeOddelovac(hodnota)) {
				return nastavChybu("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. "
						+ getJmenoIdentifikator(soucast), getMistoChyby(plneUrceny_node));
			}
		}

		return true;
	}

	private boolean kontrolaVecneSkupiny() {
		NodeList vecneSkupiny = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", metsParser.getDocument());

		if (vecneSkupiny == null) {
			return true;
		}

		
		for (int i = 0; i < vecneSkupiny.getLength(); i++) {
			Node vecnaSkupina = vecneSkupiny.item(i);
			boolean dite = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni",
					"nsesss:MaterskaEntita", "nsesss:VecnaSkupina") != null;
			if (dite) {
				Node plneUrceny_node = ValuesGetter.getXChild(vecnaSkupina, "nsesss:EvidencniUdaje", "nsesss:Trideni",
						"nsesss:PlneUrcenySpisovyZnak");
				if (plneUrceny_node == null) {
					return nastavChybu(
							"Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vecnaSkupina),
							getMistoChyby(vecnaSkupina));
				}
				String hodnota = plneUrceny_node.getTextContent();
				if (!K06_Obsahova.spisZnakObsahujeOddelovac(hodnota)) {
					return nastavChybu("Hodnota elementu <nsesss:PlneUrcenySpisovyZnak> v sobě neobsahuje oddělovač. "
							+ getJmenoIdentifikator(vecnaSkupina), getMistoChyby(plneUrceny_node));
				}
			}
		}
		return true;
	}

}
