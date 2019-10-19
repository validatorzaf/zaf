/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalid.validator;


/**
 *
 * @author m000xz006159
 */
public class ObjPravidlo {
    
    String typPravidla;
    String textPravidlaArchiv, textPravidlaClovek;
    String okNo;
    String poznamkaKPravidlu = "Žádná poznámka k pravidlu nebyla dosud vložena";
    int cisloPravidla;
    boolean jePravidloSplneno = false;
    

    public ObjPravidlo(String typPravidla, int cisloPravidla, String textPravidlaArchiv, String textPravidlaClovek, boolean jePravidloSplneno){
        this.typPravidla = typPravidla;
        this.cisloPravidla = cisloPravidla;
        this.textPravidlaArchiv = textPravidlaArchiv;
        this.textPravidlaClovek = textPravidlaClovek;
        this.jePravidloSplneno = jePravidloSplneno;
        okNo = setOkNo(jePravidloSplneno);
        
        
    }
    
    public String getPravidloProList(){
        String pravidlo = "";
//        int t = cisloPravidla;

        if(cisloPravidla == 41 || cisloPravidla == 42 || cisloPravidla == 43 || cisloPravidla == 32){
            if(cisloPravidla == 41 ) pravidlo = " " + okNo + " " + "54a" + ". " + typPravidla;
            if(cisloPravidla == 42 ) pravidlo = " " + okNo + " " + "61a" + ". " + typPravidla;
            if(cisloPravidla == 43 ) pravidlo = " " + okNo + " " + "59a" + ". " + typPravidla;
            if(cisloPravidla == 32) pravidlo = " " + okNo + " " + "93a" + ". " + typPravidla;
        }
        else{
            pravidlo = " " + okNo + " " + cisloPravidla + ". " + typPravidla;
        }
        return pravidlo;
    }
    
    private String setOkNo(boolean bol){
        String ok = "OK";
        if(bol) return ok;
        else return "NO";
        
    }
    
    public String getOkNo(){
        return okNo;
    }
    
    public String getTypPravidla(){
        return typPravidla;
    }
    
    public String getTextPravidlaArchiv(){
        return textPravidlaArchiv;
    }
    
    public String getTextPravidlaClovek(){
        return textPravidlaClovek;
    }
    
    public int getCisloPravidla(){
        return cisloPravidla;
    }
    
    public boolean getJePravidloSplneno(){
        return jePravidloSplneno;
    }
    
    public String getPoznamkaKPravidlu(){
        return poznamkaKPravidlu;
    }
    
    public void setPoznamkaKPravidlu(String poznamka){
        if(!poznamka.isEmpty()) poznamkaKPravidlu = poznamka;   
    }
    
    public void setJePravidloSplneno(boolean jePravidloSplneno){
        this.jePravidloSplneno = jePravidloSplneno;
    }
    
    
    @Override
    public String toString() {
        return getPravidloProList();
    }
    
    
}
