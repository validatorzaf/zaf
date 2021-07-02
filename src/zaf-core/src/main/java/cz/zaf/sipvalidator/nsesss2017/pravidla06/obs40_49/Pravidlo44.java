package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs40_49;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.Comparator;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.Obj_Node_String;
import cz.zaf.sipvalidator.nsesss2017.PositionalXMLReader;
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
        NodeList nodeListMetsFile = ValuesGetter.getAllAnywhere("mets:file", metsParser.getDocument());
        NodeList nodeListKomponenty = ValuesGetter.getAllAnywhere("nsesss:Komponenta", metsParser.getDocument());
        if(nodeListMetsFile == null) return true;
        if(nodeListKomponenty == null || nodeListKomponenty.getLength() != nodeListMetsFile.getLength()){
            return nastavChybu("Nenalezen žádný element <nsesss:Komponenta>.");
        }
        ArrayList<Obj_Node_String> files = new ArrayList<>(), komponents = new ArrayList<>();
        for(int i = 0; i < nodeListMetsFile.getLength(); i++){
            Node metsFile = nodeListMetsFile.item(i);
            Node komponenta = nodeListKomponenty.item(i);
            if(!ValuesGetter.hasAttribut(metsFile, "DMDID")){
                return nastavChybu("Element <mets:file> nemá atribut DMDID.", getMistoChyby(metsFile));
            }
            String dmdid = ValuesGetter.getValueOfAttribut(metsFile, "DMDID");
            files.add(new Obj_Node_String(metsFile, dmdid));
            if(!ValuesGetter.hasAttribut(komponenta, "ID")){
                return nastavChybu("Element <nsesss:Komponenta> nemá atribut ID. " + getJmenoIdentifikator(komponenta), getMistoChyby(komponenta));
            }
            String id = ValuesGetter.getValueOfAttribut(komponenta, "ID");
            komponents.add(new Obj_Node_String(komponenta, id));
        }
        String ch = "";
        String misto_ch = "";
        boolean vysledek = true;
        for(int f = 0; f < files.size(); f++){
            Obj_Node_String file = files.get(f);
            ArrayList<Obj_Node_String>  f_list = Comparator.obj_Node_String_howmany_in_list(file, komponents);
            if(f_list.size() != 1){
                vysledek = false;
                String mch = file.get_node().getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME).toString();
                if(f_list.isEmpty()){
                    ch += "Element <mets:file> na řádku: " + mch +". neodkazuje na žádnou komponentu. ";
                    misto_ch += getMistoChyby(file.get_node()) + " ";
                }
                else{
                    String komp = "";
                    for(int i = 0; i < f_list.size(); i++){
                        Obj_Node_String file2 = f_list.get(i);
                        if(i != f_list.size()-1){
                            komp += getMistoChyby(file2.get_node()) + " ";

                        }
                        else{
                            komp += getMistoChyby(file2.get_node()) + " ";
                        }
                    }
                    ch += "Element <mets:file> na řádku: " + mch +". odkazuje na více komponent: " + komp;
                    misto_ch += komp + " ";
                }
            }  
        }
        for(int k = 0; k < komponents.size(); k++){
            Obj_Node_String komponenta = komponents.get(k);
            ArrayList<Obj_Node_String>  k_list = Comparator.obj_Node_String_howmany_in_list(komponenta, files);
            if(k_list.size() != 1){
                Node komp = komponenta.get_node();
                vysledek = false;
                String mch = komp.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME).toString();
                if(k_list.isEmpty()){
                    ch += "Element <nsesss:Komponenta> na řádku: " + mch +". neodkazuje na žádný element <mets:file> " + getJmenoIdentifikator(komponenta.get_node());
                    misto_ch += getMistoChyby(komp);
                }
                else{
                    String fil = "";
                    for(int i = 0; i < k_list.size(); i++){
                        Obj_Node_String komponenta2 = k_list.get(i);
                        Node kom = komponenta2.get_node();
                        if(i != k_list.size()-1){
                            fil += getMistoChyby(kom) + " ";

                        }
                        else{
                            fil += getMistoChyby(kom) + " ";
                        }
                    }
                    ch += "Element <nsesss:Komponenta> na řádku: " + mch +". odkazuje na více elementů <mets:file>: " + fil + " " + getJmenoIdentifikator(komp);
                    misto_ch += fil + " ";
                }
            }  
        }
        if(!vysledek){
            return nastavChybu(ch, misto_ch);
        }
        
        
        return vysledek;
	}

}
