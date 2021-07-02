package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import java.util.ArrayList;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.28 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, 
// potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element 
// <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.
public class Pravidlo28 extends K06PravidloBase {

	static final public String OBS28 = "obs28";

	public Pravidlo28() {
		super(OBS28,
				"Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.",
				"Datový balíček SIP neobsahuje díl, spis ani dokument.",
				"Bod 2.7. přílohy č. 3 NSESSS; příloha č. 2 NSESSS, ř. 20."
				);
	}

	@Override
	protected boolean kontrolaPravidla() {
        ArrayList<Node> krizove_odkazy_pevny_ano = kontrola.get_krizove_odkazy_pevny_ano();
        if(!krizove_odkazy_pevny_ano.isEmpty()){
            return true;
        }
        else{
        	Node xmlData = this.metsParser.getMetsXmlData();
           if(xmlData== null){
                return nastavChybu("Nenalezen element <mets:xmlData>.");
            }
            if(xmlData.getChildNodes().getLength() == 0){
                return nastavChybu("Element <mets:xmlData> neobsahuje žádné dětské elementy.", getMistoChyby(xmlData));
            }
            ArrayList<Node> list = ValuesGetter.get_Node_Children(xmlData);
            if(!ValuesGetter.maRodicPouzeTytoPotomky(xmlData, "nsesss:Dil", "nsesss:Dokument", "nsesss:Spis")){
                String ch = "";
                for(int i = 0; i < list.size(); i++){
                    Node node = list.get(i);
                    String name = node.getNodeName();
                    if(!name.equals("nsesss:Dokument") || !name.equals("nsesss:Spis") || !name.equals("nsesss:Dil")){
                        ch += getMistoChyby(list.get(i)) + " "; 
                    }
                }
                return nastavChybu("Element <mets:xmlData> obsahuje nepovolené dětské elementy.", ch);
            }
            if(list.size() > 1){
                String ch = "";
                String pop = "";
                for(int i = 1; i < list.size(); i++){
                    Node node = list.get(i);
                    ch += getMistoChyby(node) + " "; 
                    pop += " " + getJmenoIdentifikator(node);
                }
                return nastavChybu("Element <mets:xmlData> obsahuje více dětských elementů." + pop, ch);
            } 
        }
        return true;
	}

}
