package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs50_59;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Metods;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_amdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_dmdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_metsdiv;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_amdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_dmdSec;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_Obj_metsdiv;
import cz.zaf.sipvalidator.nsesss2017.structmap.StructMap_Obj_return_bol_AL_node;

// OBSAHOVÁ č.54a Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, 
// potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou 
// příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci 
// amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, 
// <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> 
// odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), 
// přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena 
// v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).
public class Pravidlo54a  extends K06PravidloBase {

    static final public String OBS54A = "obs54a";

    public Pravidlo54a() {
    	super(OBS54A,
    			"Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).",
    			"Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol nebo je struktura uvedena chybně s ohledem na existenci pevného křížového odkazu.",    			
    			"Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3)."
    			);
    }

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> krizoveodkazy = metsParser.getKrizoveOdkazyPevnyAno();
        if(krizoveodkazy.isEmpty()) {
            return true;
        }
        // TEST mets:amdSec
        NodeList amdSec_nodelist = ValuesGetter.getAllAnywhere("mets:amdSec", metsParser.getDocument());
        if(amdSec_nodelist == null){
            return nastavChybu("Nenalezen element <mets:amdSec>.");
        }
        StructMap_Obj_return_bol_AL_Obj_amdSec ret_amd = StructMap_Metods.get_amdSec_list(amdSec_nodelist);
        if (!ret_amd.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < ret_amd.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_amd.getList().get(i).getNode()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:amdSec>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:amdSec>.";
            return nastavChybu(hlaska, mistoCh);
        }
        List<StructMap_Obj_amdSec> amdSec_list = ret_amd.getList();
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_amdSec = StructMap_Metods.test_amdSec_uniqueness(amdSec_list);
        if (!jedinecnost_amdSec.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < jedinecnost_amdSec.getList().size(); i++) {
                mistoCh += getMistoChyby(jedinecnost_amdSec.getList().get(i)) + " ";
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:amdSec>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby duplicity u elementů <mets:amdSec>.";
            return nastavChybu(hlaska, mistoCh);
        }
        // KONEC TESTU mets:amdSec
        
        // TEST mets_div
        NodeList metsdiv_nodelist = ValuesGetter.getAllAnywhere("mets:div", metsParser.getDocument());
        if(metsdiv_nodelist == null){
            return nastavChybu("Nenalezen element <mets:div>.");
        }
        StructMap_Obj_return_bol_AL_Obj_metsdiv ret_metsdiv = StructMap_Metods.get_metsdiv_list(metsdiv_nodelist);
        if(!ret_metsdiv.getBol()){
            String mistoCh = "";
            for (int i = 0; i < ret_metsdiv.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_metsdiv.getList().get(i).getMetsDiv()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if (ret_amd.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return nastavChybu(hlaska, mistoCh);
        }
        List<StructMap_Obj_metsdiv> metsdiv_list = ret_metsdiv.getList();
            //jedinečnost
        StructMap_Obj_return_bol_AL_node jedinecnost_metsdiv = StructMap_Metods.test_metsdiv_uniqueness(metsdiv_list);
        if(!jedinecnost_metsdiv.getBol()){
            String mistoCh = "";
            for(int i = 0; i < jedinecnost_metsdiv.getList().size(); i++){
                mistoCh += getMistoChyby(jedinecnost_metsdiv.getList().get(i)) + " ";  
            }
            String hlaska = "Nalezena chyba duplicity u elementu <mets:div>.";
            if (ret_metsdiv.getList().size() > 1)
                hlaska = "Nalezeny chyby duplicity u elementů <mets:div>.";
            return nastavChybu(hlaska, mistoCh);
        }
        // KONEC TESTU mets:div
        
        // TEST dmdSec
        StructMap_Obj_return_bol_AL_Obj_dmdSec ret_dmdsec = StructMap_Metods.get_dmdsec_list(metsParser.getDocument());
        if (!ret_dmdsec.getBol()) {
            String mistoCh = "";
            for (int i = 0; i < ret_dmdsec.getList().size(); i++) {
                mistoCh += getMistoChyby(ret_dmdsec.getList().get(i).getNode()) + " ";
            }
            String hlaska = "Nalezena chyba u elementu <mets:div>.";
            if (ret_dmdsec.getList().size() > 1)
                hlaska = "Nalezeny chyby u elementů <mets:div>.";
            return nastavChybu(hlaska, mistoCh);
        }
        List<StructMap_Obj_dmdSec> metsdmdSec_list = ret_dmdsec.getList();
        // KONEC TESTU dmdSec
        
        // TEST amdSec to metsdiv
        StructMap_Obj_return_bol_AL_node test_amd_to_div = StructMap_Metods.compare_amdSec_with_metsDiv(amdSec_list, metsdiv_list);
        if(!test_amd_to_div.getBol()){
            if (test_amd_to_div.getList().size() == 1) {
                return nastavChybu("Element <mets:amdSec> neodkazuje na žádný element <mets:div>.", getMistoChyby(test_amd_to_div.getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_amd_to_div.getList().get(0));
                for (int i = 1; i < test_amd_to_div.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_amd_to_div.getList().get(i));
                }
                return nastavChybu("Element <mets:amdSec> odkazuje na více elementů <mets:div>.", ch); 
            }
        }
        // TEST metsdiv to amd 
        StructMap_Obj_return_bol_AL_node test_div_toamd = StructMap_Metods.compare_metsDiv_with_amdSec(amdSec_list, metsdiv_list);
        if(!test_div_toamd.getBol()){
            if (test_div_toamd.getList().size() == 1) {
                return nastavChybu("Element <mets:div> neodkazuje na žádný element <mets:amdSec>.", getMistoChyby(test_div_toamd.getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_div_toamd.getList().get(0));
                for (int i = 1; i < test_div_toamd.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_div_toamd.getList().get(i));
                }
                return nastavChybu("Element <mets:div> odkazuje na více elementů <mets:amdScec>.", ch); 
            }
        }
        // KONEC TESTU amdSec + metsdiv
        
        //TEST AMD TO DMDSEC
        StructMap_Obj_return_bol_AL_node test_amd_to_dmd = StructMap_Metods.compare_amdSec_dmdSec(amdSec_list, metsdmdSec_list);
        if(!test_amd_to_dmd.getBol()){
            if (test_amd_to_dmd.getList().size() == 1) {
                String name = test_amd_to_dmd.getList().get(0).getNodeName();
                if(name.equals("mets:amdSec")){
                    return nastavChybu("Element <mets:amdSec> neodkazuje na žádný element v <mets:dmdSec>.", getMistoChyby(test_amd_to_dmd.getList().get(0)));
                }
                else{
                    return nastavChybu("Element <" + name + "> neodkazuje na žádný element <mets:amdSec>.", getMistoChyby(test_amd_to_dmd.getList().get(0)));
                }       
            }
            else{
                String ch = getMistoChyby(test_amd_to_dmd.getList().get(0));
                for (int i = 1; i < test_amd_to_dmd.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_amd_to_dmd.getList().get(i));
                }
                return nastavChybu("Elementy v <mets:dmdSec> neodkazují na žádný element v <mets:amdSec>.", ch); 
            }       
        }
        //KONEC TESTU AMD TO DMDSEC
        
        //TEST DIV TO DMDSEC
//        StructMap_Obj_return_bol_AL_node test_div_to_dmd = StructMap_Metods.compare_metsDiv_with_dmdSec(metsdmdSec_list, metsdiv_list);
//        if(!test_div_to_dmd.bol){
//            if(test_div_to_dmd.node_list.size() == 1){
//                String name = test_div_to_dmd.node_list.get(0).getNodeName();
//                if(name.equals("mets:div")){
//                    return add_popisy("Element <mets:div> neodkazuje na žádný element v <mets:dmdSec>.", chyba_vygenerovan, false, get_misto_chyby(test_div_to_dmd.node_list.get(0)));
//                }
//                else{
//                    return add_popisy("Element <" + name + "> neodkazuje na žádný element <mets:div>.", chyba_vygenerovan, false, get_misto_chyby(test_div_to_dmd.node_list.get(0)));
//                }       
//            }
//            else{
//                String ch = get_misto_chyby(test_div_to_dmd.node_list.get(0));
//                for(int i = 1; i < test_div_to_dmd.node_list.size(); i++){
//                    ch+= " " + get_misto_chyby(test_div_to_dmd.node_list.get(i));
//                }
//                return add_popisy("Elementy v <mets:dmdSec> neodkazují na žádný element <mets:div>.", chyba_vygenerovan, false, ch); 
//            }       
//        }        
        //KONEC TESTU DIV TO DMDSEC
        
        //TEST STRUKTURY PODLE METS DIV
        StructMap_Obj_return_bol_AL_node test_struktury = StructMap_Metods.compare_metsDiv_with_dmdSec_structure(
                                                                                                                 metsdiv_list,
                                                                                                                 metsdmdSec_list,
                                                                                                                 metsParser
                                                                                                                         .getDocument());
        if (!test_struktury.getBol()) {
            if (test_struktury.getList().size() == 1) {
                return nastavChybu("Element <mets:div> je špatně zatříděn.", getMistoChyby(test_struktury
                        .getList().get(0)));
            }
            else{
                String ch = getMistoChyby(test_struktury.getList().get(0));
                for (int i = 1; i < test_struktury.getList().size(); i++) {
                    ch += " " + getMistoChyby(test_struktury.getList().get(i));
                }
                return nastavChybu("Element <mets:div> a jeho rodičovský element <mets:div> odkazují na chybné elementy v <mets:dmdSec>.", ch);
            }
        }
        //KONEC TESTU STRUKTURY
        
        return true;
	}

}
