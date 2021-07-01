package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/** 
 * OBSAHOVÁ 93a. Každá entia věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.
 * 
 */
public class Pravidlo93a  extends K06PravidloBase {

    static final public String OBS93A = "obs93a";

    public Pravidlo93a(K06_Obsahova kontrola) {
        super(kontrola,
                OBS93A,
                "Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, přičemž vážený součet prvních sedmi číslic má po dělení jedenácti zbytek, který po odečtení od 11 a následném vydělení 10 má zbytek roven poslední číslici.",
                "Chybně je uvedeno IČO subjektu.", 
                null);
    }

	@Override
	protected boolean kontrolaPravidla() {
        NodeList vs_list = ValuesGetter.getAllAnywhere("nsesss:VecnaSkupina", metsParser.getDocument());
        if(vs_list==null) {
            return nastavChybu("Věcná skupina neexistuje", getMistoChyby(metsParser.getDocument()));
        }
        for(int i = 0; i < vs_list.getLength(); i++){
            Node vs = vs_list.item(i);
            Node spl = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(spl != null){
                Node jsz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:JednoduchySpisovyZnak");
                if(jsz == null) {
                	return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs), getMistoChyby(vs));
                }
                Node pusz = ValuesGetter.getXChild(vs, "nsesss:EvidencniUdaje", "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");                     
                if(pusz == null) {
                	return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs), getMistoChyby(vs));
                }
                if(!jsz.getTextContent().equals(pusz.getTextContent())){
                    return nastavChybu("Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs), getMistoChyby(jsz) + " " + getMistoChyby(pusz));
                }
            }
        }
        return true;
	}

}
