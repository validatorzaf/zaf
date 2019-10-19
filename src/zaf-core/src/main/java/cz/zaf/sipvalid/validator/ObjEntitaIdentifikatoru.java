/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;

import org.w3c.dom.Node;

/**
 *
 * @author m000xz006159
 */
public class ObjEntitaIdentifikatoru {

    Node entita;
    String ident = "i", zdroj = "z";
    
    public ObjEntitaIdentifikatoru(Node nsesss_identifikator, String ident, String zdroj) {
        entita = find_entita(nsesss_identifikator);
        this.ident = ident;
        this.zdroj = zdroj;
    }
    
    private Node find_entita(Node nsesss_identifikator){
        Node r1 = nsesss_identifikator.getParentNode();
        if(r1.getNodeName().equals("nsesss:BezpecnostniKategorie") ||r1.getNodeName().equals("nsesss:OdkazVyrizovanyDokument") || r1.getNodeName().equals("nsesss:SkartacniRezim") || r1.getNodeName().equals("nsesss:TypDokumentu") || r1.getNodeName().equals("nsesss:SpisovyPlan")){
            return r1;
        }
        else{
            // nsesss:Soucast nsesss:TypovySpis nsesss:VecnaSkupina nsesss:Komponenta + dok spis dil
            Node eu = ValuesGetter.getXParent(nsesss_identifikator, "nsesss:Identifikace", "nsesss:EvidencniUdaje");
            Node r2 = eu.getParentNode();
            return r2;
        }
    }
    
    
    
    public Node get_entita(){
        return entita;
    }
    
    public String get_identifikator(){
        return ident;
    }
    
    public String get_zdroj(){
        return zdroj;
    }
    
    
}
