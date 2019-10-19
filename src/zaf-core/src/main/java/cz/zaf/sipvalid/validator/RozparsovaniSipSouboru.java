/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;

/**
 *
 * @author m000xz006159
 */
public class RozparsovaniSipSouboru {
    
    File sip_to_file;
//    public static org.w3c.dom.Document parsedDOM_SipSoubor;
    public static Document parsedSAX_Sipsoubor;
    public static Node metsMets,metsDmdSec, metsMdWrap, xmlData, metsHdr;
    public static ArrayList<Node> zakladniEntity, identifikatory, nazvy, dokumenty, manipulace, urceneCasoveObdobi, plneurcenySpisovyZnak; 
    public RozparsovaniSipSouboru(SIP_MAIN file) throws ParserConfigurationException, SAXException {
        
        sip_to_file = new File(SIP_MAIN_helper.getCesta_mets(file));
        parsedSAX_Sipsoubor = null;
        metsMets = null;
        metsDmdSec = null;
        metsMdWrap = null;
        xmlData = null;
        metsHdr = null;
        zakladniEntity = null;
        identifikatory = null;
        nazvy = null;
        manipulace = null;
        urceneCasoveObdobi = null;
        plneurcenySpisovyZnak = null;
        
        try {
            InputStream is = new FileInputStream(sip_to_file);
            parsedSAX_Sipsoubor = PositionalXMLReader.readXML(is);
        }
        catch(SAXException e){
            System.out.println("Problém se SAX " + e);
        }
        catch(IOException e){
            parsedSAX_Sipsoubor = null;
        }

        if(parsedSAX_Sipsoubor != null){
            metsMets = parsedSAX_Sipsoubor.getDocumentElement();
            if(!metsMets.getNodeName().equals("mets:mets")) metsMets = null;
            if(metsMets != null){
                if(ValuesGetter.hasAttribut(metsMets, "OBJID")){
                    // nastaveni id SIP
                    file.setID(ValuesGetter.getValueOfAttribut(metsMets, "OBJID"));
                }
            }
            // konec
            metsDmdSec = ValuesGetter.getXChild(metsMets, "mets:dmdSec");
            metsMdWrap = ValuesGetter.getXChild(metsMets, "mets:dmdSec", "mets:mdWrap");
            xmlData = ValuesGetter.getXChild(metsMets, "mets:dmdSec", "mets:mdWrap", "mets:xmlData");    
            zakladniEntity = ValuesGetter.getChildList(xmlData, "nsesss:Dokument", "nsesss:Spis", "nsesss:Dil");
            
            //velké seznamy u velkých souborů
            identifikatory = ValuesGetter.getAllAnywhereArrayList("nsesss:Identifikator", parsedSAX_Sipsoubor);
            nazvy = ValuesGetter.getAllAnywhereArrayList("nsesss:Nazev", parsedSAX_Sipsoubor);
            dokumenty = ValuesGetter.getAllAnywhereArrayList("nsesss:Dokument", parsedSAX_Sipsoubor);
            manipulace = ValuesGetter.getAllAnywhereArrayList("nsesss:Manipulace", parsedSAX_Sipsoubor);
            urceneCasoveObdobi = ValuesGetter.getAllAnywhereArrayList("nsesss:UrceneCasoveObdobi", parsedSAX_Sipsoubor);
            plneurcenySpisovyZnak = ValuesGetter.getAllAnywhereArrayList("nsesss:PlneUrcenySpisovyZnak", parsedSAX_Sipsoubor);
            
            if(zakladniEntity != null && zakladniEntity.isEmpty()) zakladniEntity = null; // kvůli pravidlům
            if(identifikatory != null && identifikatory.isEmpty()) identifikatory = null;
            if(nazvy != null && nazvy.isEmpty()) nazvy = null;
            if(dokumenty != null && dokumenty.isEmpty()) dokumenty = null;
            if(manipulace != null && manipulace.isEmpty()) manipulace = null;
            if(urceneCasoveObdobi != null && urceneCasoveObdobi.isEmpty()) urceneCasoveObdobi = null;
            if(plneurcenySpisovyZnak != null && plneurcenySpisovyZnak.isEmpty()) plneurcenySpisovyZnak = null;
            
            metsHdr = ValuesGetter.getXChild(metsMets, "mets:metsHdr");
            if(zakladniEntity != null){
                // 0 dok, 1 spi 2 dil
                SIP_MAIN_helper.set_SIP_type(zakladniEntity.get(0).getNodeName(), file);
                Node skZnakMaterskeEntity = ValuesGetter.getXChild(zakladniEntity.get(0), "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniZnak");
                if(skZnakMaterskeEntity != null) file.setSKznak(skZnakMaterskeEntity.getTextContent());
                Node skLhutaMaterskeEntity = ValuesGetter.getXChild(zakladniEntity.get(0), "nsesss:EvidencniUdaje", "nsesss:Vyrazovani", "nsesss:SkartacniRezim", "nsesss:SkartacniLhuta");
                if(skLhutaMaterskeEntity != null) file.setSKLhuta(skLhutaMaterskeEntity.getTextContent());
            }  
        }
    }
    
    // přehodnotit kvuli xtemu poctu zakladnich entit
//    private void nastavPocetDokumentu(SIP_Arc_Package file){
//        int pocet;
//        Node dokumenty = ValuesGetter.getFirstAnywhere("nsesss:Dokumenty", parsedDOM_SipSoubor);
//        if(dokumenty == null) file.setPocetDokumentuVevnitrSipu(0);
//        else{
//            pocet = ValuesGetter.pocetDeti(dokumenty, "nsesss:Dokument");
//            file.setPocetDokumentuVevnitrSipu(pocet);
//        }
//    }
    
    
    
}
