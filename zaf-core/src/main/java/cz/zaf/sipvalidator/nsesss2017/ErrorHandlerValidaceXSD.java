/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import cz.zaf.common.exceptions.codes.XmlCode;
import cz.zaf.common.result.RuleValidationError;
import cz.zaf.common.result.ValidationLayerResult;

/**
 * Error handler pro validaci schematu
 * 
 */
public class ErrorHandlerValidaceXSD implements ErrorHandler {
    ValidationLayerResult k;
    public boolean nalezenaChyba = false;

    public ErrorHandlerValidaceXSD(ValidationLayerResult k) {
        this.k = k;
    }

    @Override
    public void warning(SAXParseException saxpe) throws SAXException {
        setUpError(saxpe, 0);
    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        setUpError(saxpe, 1);
    }

    @Override
    public void fatalError(SAXParseException saxpe) throws SAXException {
        setUpError(saxpe, 2);
    }
    
    private void setUpError(SAXParseException e, int priorita){
        // Vypisuje se jen prvni chyba
        if(nalezenaChyba) {
            return;
        }
        nalezenaChyba = true;
        String textPriorita = "";
        switch(priorita){
            case(0):
                textPriorita = "VAROVÁNÍ: ";
                break;
            case(1):
                textPriorita = "CHYBA: ";
                break; 
            case(2):
                textPriorita = "FATÁLNÍ CHYBA: ";
                break;    
        }
        String mistoChyby = "Řádek: " + e.getLineNumber() + ". Sloupec: " + e.getColumnNumber() + ".";
        String textChyby = textPriorita;          
            
        String celaChybovaHlaska = e.getMessage();
           
        boolean vypsano = false;

        if(celaChybovaHlaska.endsWith("is expected."))
        {
            int indexPosledniDvojtecky = celaChybovaHlaska.lastIndexOf(":") + 1;                
            String textZaPosledniDvojteckou = celaChybovaHlaska.substring(indexPosledniDvojtecky);
            int indexPosledniSlozeneZavorky = textZaPosledniDvojteckou.indexOf("}");
            String nazevChybejicihoTagu = textZaPosledniDvojteckou.substring(0, indexPosledniSlozeneZavorky);

            textChyby += " CHYBÍ ELEMENT "+nazevChybejicihoTagu + ".";
//            mistoChyby += " Element " + nazevChybejicihoTagu + "."; 
            vypsano = true;
        }
        
        if(celaChybovaHlaska.contains(": Attribute '"))
        {
            int indexPrvnihoApostrofu = celaChybovaHlaska.indexOf("'")+ 1;
            String textZaPrvnimApostrofem = celaChybovaHlaska.substring(indexPrvnihoApostrofu) ;
            int indexUzavirajicihoApostrofu = textZaPrvnimApostrofem.indexOf("'");
            String jmenoAtributu = textZaPrvnimApostrofem.substring(0, indexUzavirajicihoApostrofu);
            
            textChyby += " CHYBÍ ATRIBUT: "+jmenoAtributu + ".";                   
            
            int delkaCeleHlasky = celaChybovaHlaska.length();
            String celyTextBezPoslednich2Znaku = celaChybovaHlaska.substring(0, delkaCeleHlasky -2);
            int indexPoslednihoApostrofu = celyTextBezPoslednich2Znaku.lastIndexOf("'") +1;
            String jmenoTagu = celyTextBezPoslednich2Znaku.substring(indexPoslednihoApostrofu);
            
            textChyby += " U TAGU: "+jmenoTagu+".";
//            mistoChyby += " Element " + jmenoTagu + ".";
            
            vypsano = true;
        }
        
        if(!vypsano){
            textChyby += " " +celaChybovaHlaska;
        }

        RuleValidationError p = new RuleValidationError(K05_ProtiSchematu.VAL1,
                K05_ProtiSchematu.VAL1_TEXT,
                textChyby,
                K05_ProtiSchematu.VAL1_POPIS_CHYBY,
                mistoChyby,
                K05_ProtiSchematu.VAL1_ZDROJ,
                XmlCode.NEODPOVIDA_SCHEMATU,
                null);
        k.add(p);
    }

    public boolean getNalezenaChyba() {
        return nalezenaChyba;
    }
    
    
}
