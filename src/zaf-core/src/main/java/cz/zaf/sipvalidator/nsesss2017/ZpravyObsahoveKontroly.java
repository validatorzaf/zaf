/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

/**
 *
 * @author m000xz006159
 */
public class ZpravyObsahoveKontroly {
    static String[] list_text_obsahova = {
    "Pravidlo neexistuje.",
    //1. - 4.
            "",
            "",
            "",
            "",
    // 5.-9. volné indexy
            "",
            "",
            "",
            "",
            "",
    //10. - 20.
    "Element <mets:mets> obsahuje dětský element <mets:metsHdr>.",
    "Element <mets:mets> obsahuje právě jeden dětský element <mets:dmdSec>.",
    "Element <mets:mets> obsahuje alespoň jeden element <mets:amdSec>.",
    "Element <mets:mets> obsahuje právě jeden dětský element <mets:structMap>.",
    "Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
    "Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
    "Element <mets:metsHdr> obsahuje právě jeden element <mets:agent> s atributem TYPE s hodnotou ORGANIZATION.",
    "Element <mets:metsHdr> obsahuje alespoň jeden element <mets:agent> s atributem TYPE s hodnotou INDIVIDUAL.",
    "Každý element <mets:agent> obsahuje atribut ROLE s hodnotou CREATOR.",
    "Každý element <mets:agent> obsahuje atribut ID.",
    "Každý element <mets:agent> obsahuje právě jeden dětský element <mets:name> s neprázdnou hodnotou.",
    //21. volný index
    "volný index",
    };
    
    public static String get_text_Obsahova(int index){
        if(index >= list_text_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_text_obsahova[index];
    }
    
    static String[] list_zdroje_obsahova = {
    "0 volný index",
    //1. - 4.
            "",
            "",
            "",
            "",
    // 5.-9. volné indexy
            "",
            "",
            "",
            "",
            "",
    //10. - 20.
    "Bod 2.2. přílohy č. 3 NSESSS.",
    "Bod 2.6. přílohy č. 3 NSESSS.",
    "Bod 2.9. přílohy č. 3 NSESSS.",
    "Bod 2.2.17. přílohy č. 3 NSESSS.",
    "Bod 2.2. přílohy č. 3 NSESSS.",
    "Bod 2.2. přílohy č. 3 NSESSS.",
    "Bod 2.3. přílohy č. 3 NSESSS.",
    "Bod 2.3. přílohy č. 3 NSESSS.",
    "Bod 2.3. přílohy č. 3 NSESSS.",
    "Bod 2.3. přílohy č. 3 NSESSS.",
    "Bod 2.4. přílohy č. 3 NSESSS.",
    //21. volný index
    "volný index",
    };
    
    public static String get_zdroje_Obsahova(int index){
        if(index >= list_zdroje_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_zdroje_obsahova[index];
    }
    
    static String[] list_popis_obsahova = {
    "0 volný index",
    //1. - 4.
            "",
            "",
            "",
            "",
    // 5.-9. volné indexy
            "",
            "",
            "",
            "",
            "",
    //10. - 20.
    "Chybí povinná část (záhlaví) struktury datového balíčku SIP.",
    "Chybí povinná (popisná) část struktury datového balíčku SIP.",
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    "Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
    "Chybí datum poslední úpravy datového balíčku SIP.",
    "Chybí datum vytvoření datového balíčku SIP.",
    "Uveden je chybně popis původce.",
    "Uveden je chybně popis původce.",
    "Uveden je chybně popis původce.",
    "Uveden je chybně popis původce.",
    "Chybí informace o původci.",
    //21. volný index
    "volný index",
    
    };
    
    public static String get_popis_Obsahova(int index){
        if(index >= list_popis_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_popis_obsahova[index];
    }
    
}
