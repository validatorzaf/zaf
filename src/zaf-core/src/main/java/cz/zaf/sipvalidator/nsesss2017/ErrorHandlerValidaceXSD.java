/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import cz.zaf.sipvalidator.sip.PravidloKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

/**
 *
 * @author m000xz006159
 */
public class ErrorHandlerValidaceXSD implements  ErrorHandler{
    VysledekKontroly k;
    public boolean nalezenaChyba = false;

    public ErrorHandlerValidaceXSD(VysledekKontroly k) {
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

        PravidloKontroly p = new PravidloKontroly("val", false, null, textChyby, null, mistoChyby, null);
        k.add(p);
    }

    public boolean getNalezenaChyba() {
        return nalezenaChyba;
    }
    
    
}
