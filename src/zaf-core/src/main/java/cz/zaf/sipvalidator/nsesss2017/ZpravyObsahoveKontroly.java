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
    //22. - 31.
    "Element <mets:dmdSec> obsahuje právě jeden dětský element <mets:mdWrap>.",
    "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
    "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou NSESSS.",
    "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    "Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.",
    "Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom pro každý jeho výskyt obsahuje element <mets:dmdSec> v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> dětský element <nsesss:Dil> nebo <nsesss:Dokument> nebo <nsesss:Spis> se stejnou hodnotou v dětském elementu <nsesss:Identifikator> a v jeho atributu zdroj.",
    "Každý element <mets:amdSec> obsahuje atribut ID.",
    "Každý element <mets:amdSec> obsahuje právě jeden dětský element <mets:digiprovMD>.",
    //32. 93a
    "Každá entita věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
    //33. - 38.
    "Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 1.0.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
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
    //22. - 31.
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS.",
    "Bod 2.7. přílohy č. 3 NSESSS; příloha č. 2 NSESSS, ř. 20.",
    "Bod 2.7. přílohy č. 3 NSESSS; příloha č. 2 NSESSS, ř. 20.",
    "Bod 2.9. přílohy č. 3 NSESSS.",
    "Bod 2.10. přílohy č. 3 NSESSS.",
    //32. 93a
    "Požadavek 3.1.30 NSESSS.",
    //33. - 38.
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.12. přílohy č. 3 NSESSS.",
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
    //22. - 31.
    "Chybí povinná (popisná) část struktury datového balíčku SIP.",
    "Uveden je chybně popis schématu XML.", // Uveden je chybně popis původce.
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Chybí povinná (popisná) část struktury datového balíčku SIP.",
    "Datový balíček SIP neobsahuje díl, spis ani dokument.",
    "Chybí popis dílu, spisu nebo dokumentu, který je v datovém balíčku SIP připojen pevným křížovým odkazem.",
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    //32. 93a
    "Chybně jsou uvedeny spisové znaky.",
    //33. - 38.
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    };
    
    public static String get_popis_Obsahova(int index){
        if(index >= list_popis_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_popis_obsahova[index];
    }
    
}
