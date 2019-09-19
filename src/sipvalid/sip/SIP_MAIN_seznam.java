/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipvalid.sip;

/**
 *
 * @author m000xz006159
 */
public class SIP_MAIN_seznam {
    static String[] list_text_obsahova = {
    "Pravidlo neexistuje.",
    //1. - 4.
    "Element <mets:mets> obsahuje atribut OBJID s neprázdnou hodnotou.",
    "Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro provedení skartačního řízení nebo Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
    "Element <mets:mets> obsahuje atribut LABEL s hodnotou Datový balíček pro předávání dokumentů a jejich metadat do archivu.",
    "Element <mets:mets> obsahuje atribut xmlns:xsi s hodnotou http://www.w3.org/2001/XMLSchema-instance.",
    // 5.-9. volné indexy
    "volný index",
    "volný index",
    "volný index",
    "volný index",
    "Element <mets:mets> obsahuje atribut xmlns:xlink s hodnotou http://www.w3.org/1999/xlink.",
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
    //33. - 40.
    "Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 1.0.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
    "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <mets:mets> právě jeden dětský element <mets:fileSec>.",
    //41. (54a.)
    "Pokud existuje jakýkoli element <nsesss:KrizovyOdkaz> a obsahuje atribut pevny s hodnotou ano, potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec), přičemž v případě multiplicitního výskytu stejné entity typu součást, typový spis, věcná skupina nebo objektu spisový plán v sekci dmdSec je uvedená entita/objekt uvedena v sekci structMap právě jednou (atribut DMDID obsahuje ID libovolného výskytu příslušné entity/objektu).",
    //42. (61a.)
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:VlastniDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:VytvoreneMnozstvi> s neprázdnou hodnotou.",
    //43. (94a.)
    "Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem.",
    //44. - 50.
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut DMDID s hodnotou uvedenou v atributu ID jakéhokoli elementu <nsesss:Komponenta>, přičemž právě jedna hodnota atributu DMDID odpovídá právě jedné hodnotě atributu ID.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut MIMETYPE s hodnotou identifikace souborového formátu příslušné komponenty.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUMTYPE hodnotu SHA-256 nebo SHA-512.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CHECKSUM s hodnotou kontrolního součtu příslušné komponenty podle kryptografického algoritmu uvedeného v atributu CHECKSUMTYPE.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut SIZE s hodnotou velikosti příslušné komponenty v bytech.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje atribut CREATED.",
    "Pokud existuje jakýkoli element <mets:file>, každý obsahuje právě jeden dětský element <mets:FLocat>.",
    //51. - 60.
    "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:type s hodnotou simple.",
    "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut xlink:href s hodnotou, která odpovídá relativní cestě odkazu jakékoli komponenty uložené ve složce komponenty, přičemž právě jedna hodnota atributu xlink:href odpovídá relativní cestě odkazu právě jedné komponenty.",
    "Pokud existuje jakýkoli element <mets:FLocat>, každý obsahuje atribut LOCTYPE s hodnotou URL.",
    "Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom každý element <mets:div> obsahuje dětský element podle struktury entit/objektů (od spisového plánu po komponentu) v sekci dmdSec s atributem TYPE s hodnotou příslušné entity/objektu a s atributem DMDID s hodnotou příslušné entity/objektu v atributu ID a s atributem ADMID s hodnotou, která odpovídá hodnotě atributu ID příslušné entity/objektu v sekci amdSec (entita/objekt v hierarchii dětských elementů <mets:digiprovMD>, <mets:mdWrap>, <mets:xmlData>, <tp:TransakcniLogObjektu>, <tp:TransLogInfo>, <tp:Objekt>, <tp:Identifikator>, <tns:HodnotaID> a <tns:ZdrojID> odpovídá v hodnotách hodnotám elementu <nsesss:Identifikator> a jeho atributu zdroj příslušné entity/objektu v sekci dmdSec).",
    "Pokud existuje jakýkoli element <mets:div> s atributem TYPE s hodnotou komponenta, každý obsahuje právě jeden element <mets:fptr>.",
    "Pokud existuje jakýkoli element <mets:fptr>, každý obsahuje atribut FILEID s hodnotou, která odpovídá hodnotě atributu ID elementu <mets:file> příslušné komponenty. Příslušnost vyjadřuje stejná hodnota atributu DMDID rodičovského elementu <mets:div> a elementu <mets:file>.",
    "Jakýkoli element <nsesss:Identifikator> obsahuje neprázdnou hodnotu.",
    "Jakýkoli element <nsesss:Identifikator> obsahuje atribut zdroj s neprázdnou hodnotu.",
    "Žádná entita (od spisového plánu po dokument) nebo objekt <nsesss:Komponenta>, <nsesss:BezpecnostniKategorie>, <nsesss:SkartacniRezim> nebo <nsesss:TypDokumentu> neobsahuje stejné hodnoty elementu <nsesss:Identifikator> a jeho atributu zdroj a současně odlišné hodnoty v ostatních elementech, jako má jiná entita nebo objekt uvedeného typu, kromě atributu ID uvedené entity.",
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ne, obsahuje element <nsesss:Dokument> dětský element <nsesss:Komponenty>.",
    //61. - 70.
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:EvidencniUdaje> obsahuje v dětském elementu <nsesss:Puvod> element <nsesss:DorucenyDokument>, potom je v posledním uvedeném elementu uveden dětský element <nsesss:DoruceneMnozstvi> s neprázdnou hodnotou.",
    "Pokud existuje jakýkoli element <nsesss:Jazyk>, každý obsahuje pouze hodnoty uvedené v číselníku ISO 639-2:1998 uvedeném na URL: http://www.loc.gov/standards/iso639-2/ISO-639-2_utf-8.txt.",
    "Pokud jakýkoli element <nsesss:Vyrizeni> nebo element <nsesss:VyrizeniUzavreni> obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:Oduvodneni> s neprázdnou hodnotou.",
    "Pokud je základní entitou dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je součtem hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim>.",
    "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je rovna vyšší hodnotě, přičemž jednou hodnotou je součet hodnoty elementu <nsesss:RokSpousteciUdalosti>, 1 a hodnoty elementu <nsesss:SkartacniLhuta> uvedeného v rodičovském elementu <nsesss:SkartacniRezim> a druhou hodnotou nejvyšší hodnota součtu hodnoty elementu <nsesss:DatumDoruceni> nebo <nsesss:DatumVytvoreni> (v závislosti na tom, zda jde o doručený nebo vlastní dokument), 1 a hodnoty elementu <nsesss:SkartacniLhuta> jakékoli dětské entity dokument (nsesss:Dokument>).",
    "Pokud je základní entitou díl (<nsesss:Dil>), spis (<nsesss:Spis> nebo dokument (<nsesss:Dokument>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> obsahuje element <nsesss:RokSkartacniOperace> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), potom v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> obsahuje element <nsesss:SkartacniZnak> hodnotu, která je rovna nejvyššímu skartačnímu znaku dětské entity dokument (<nsesss:Dokument>), přičemž priorita skartačních znaků od nejvyšší po nejnižší je v pořadí A, V, S.",
    "Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.",
    "Pokud je základní entitou dokument (<nsesss:Dokument>), potom její element <nsesss:EvidencniUdaje> obsahuje dětský element <nsesss:Vyrizeni>.",
    "Jakýkoli element <nsesss:Identifikator> není opakovatelný, pokud se nenachází v hierarchii elementů <nsesss:Komponenta>, <nsesss:EvidencniUdaje> a <nsesss:Identifikace>.",
    //71. - 80.
    "Pokud existuje jakýkoli element <nsesss:DatumOtevreni>, obsahuje stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:DatumUzavreni>, pokud poslední uvedený element existuje uvnitř rodičovského elementu <nsesss:Manipulace>.",
    "Pokud existuje jakýkoli element <nsesss:CasPouziti>, každý obsahuje atribut datum.",
    "Pokud existuje jakýkoli element <nsesss:CasOvereni>, každý obsahuje atribut datum.",
    "Pokud existuje jakýkoli element <nsesss:PosuzovanyOkamzik>, každý obsahuje atribut datum.",
    "Pokud existuje jakýkoli element <nsesss:Platnost>, v každém obsahuje jeho dětský element <nsesss:PlatnostOd> stejnou nebo menší hodnotu, než je hodnota elementu <nsesss:PlatnostDo>.",
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom rodičovské entity obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> se stejnou hodnotou.",
    "Pokud základní entita obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom obsahuje v hierarchii dětských elementů <nsesss:Vyrazovani> a <nsesss:SkartacniRizeni> element <nsesss:Mnozstvi> s neprázdnou hodnotou.",
    "Element <nsesss:SkartacniRizeni> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> základní entity.",
    "V elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:RokSkartacniOperace> uvnitř rodičovského elementu <nsesss:DataceVyrazeni> stejné entity.",
    "V jakémkoli elementu <nsesss:SkartacniRizeni> obsahuje element <nsesss:Datum> hodnotu, která je menší nebo rovna aktuálnímu roku.",
    //81. - 90.
    "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:DatumDo>, potom je jeho hodnota větší než <nsesss:DatumOd>.",
    "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:MesicDo>, potom je jeho hodnota větší než <nsesss:MesicOd>.",
    "Pokud je v jakémkoli elementu <nsesss:UrceneCasoveObdobi> uveden dětský element <nsesss:RokDo>, potom je jeho hodnota větší než <nsesss:RokOd>.",
    "Pokud existuje jakýkoli element <nsesss:Vyrizeni> a obsahuje element <nsesss:Zpusob> s hodnotou jiný způsob, potom je na stejné úrovni posledního uvedeného elementu uveden dětský element <nsesss:ObsahVyrizeni> s neprázdnou hodnotou.",
    "Pokud jakýkoli element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:UkladaciJednotka> s neprázdnou hodnotou.",
    "Pokud je základní entitou dokument (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:MaterskeEntity>.",
    "Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> je uveden i element <nsesss:DatumOdeslani>.",
    "Pokud element <nsesss:Dokument> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano a současně element <nsesss:Vyrizeni> obsahuje dětský element <nsesss:DatumOdeslani>, pak element <nsesss:Vyrizeni> obsahuje element <nsesss:OdeslaneMnozstvi> s neprázdnou hodnotou.",
    "Pokud je základní entitou dokument (<nsesss:Dokument>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Vyrizeni>.",
    "Pokud je základní entitou spis (<nsesss:Spis>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:VyrizeniUzavreni>.",
    //91. - 98.
    "Pokud je základní entitou díl (<nsesss:Dil>), potom obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:DataceVyrazeni> element <nsesss:RokSpousteciUdalosti> hodnotu, v níž je uvedený rok větší nebo roven hodnotě uvedené v elementu <nsesss:Datum> v hierarchii elementů <nsesss:EvidencniUdaje> a <nsesss:Uzavreni>.",
    "Pokud existuje jakýkoli element <nsesss:Identifikator> s atributem zdroj s hodnotou IČ nebo IČO, hodnota obsahuje číslo o osmi číslicích, přičemž vážený součet prvních sedmi číslic má po dělení jedenácti zbytek, který po odečtení od 11 a následném vydělení 10 má zbytek roven poslední číslici.",
    "Jakýkoli element <nsesss:Nazev> obsahuje neprázdnou hodnotu.",
    //94
    "Každá entita vyjma jakéhokoli spisového plánu (<nsesss:SpisovyPlan>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou, jejíž poslední část je stejná jako hodnota elementu <nsesss:JednoduchySpisovyZnak>.",
    //95
    "Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou obsahující hodnotu elementu <nsesss:PlneUrcenySpisovyZnak> rodičovské entity, oddělovač a hodnotu elementu <nsesss:JednoduchySpisovyZnak> výchozí entity.",
    
    "Každá základní entita a každá entita typový spis (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).",
    "Pokud existuje více než jedna základní entita, všechny obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
    "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli dětské entity dokument (<nsesss:Dokument>).",
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
    "Bod 2.1. přílohy č. 3 NSESSS.",
    "Bod 2.1. přílohy č. 3 NSESSS.",
    "Bod 2.1. přílohy č. 3 NSESSS.",
    "Bod 2.1. přílohy č. 3 NSESSS.",
    // 5.-9. volné indexy
    "volný index",
    "volný index",
    "volný index",
    "volný index",
    "Bod 2.1. přílohy č. 3 NSESSS.",
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
    //33. - 40.
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.11. přílohy č. 3 NSESSS.",
    "Bod 2.12. přílohy č. 3 NSESSS.",
    "Bod 2.12. přílohy č. 3 NSESSS.",
    "Bod 2.13. přílohy č. 3 NSESSS.",
    //41. (54a.)
    "Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3).",
    //42. (61a.)
    "Příloha č. 2 NSESSS, ř. 1208.",
    //43. (94a.)
    "Požadavek 3.1.30 NSESSS.",
    //44. - 50.
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.15. přílohy č. 3 NSESSS.",
    "Bod 2.16. přílohy č. 3 NSESSS.",
    //51. - 60.
    "Bod 2.16. přílohy č. 3 NSESSS.",
    "Bod 2.16. přílohy č. 3 NSESSS.",
    "Bod 2.16. přílohy č. 3 NSESSS.",
    "Bod 2.17 a 2.18. přílohy č. 3 NSESSS; Informační list NA, roč. 2018, čá. 2, příloha k č. 20/2018 (20.3).",
    "Bod 2.19. přílohy č. 3 NSESSS.",
    "Bod 2.19. přílohy č. 3 NSESSS.",
    "Příloha č. 2 NSESSS, ř. 123.",
    "Příloha č. 2 NSESSS, ř. 288.",
    "Příloha č. 2 NSESSS, ř. 123.",
    "Příloha č. 2 NSESSS, ř. 45.",
    //61. - 70.
    "Příloha č. 2 NSESSS, ř. 365.",
    "Příloha č. 2 NSESSS, ř. 132.",
    "Příloha č. 2 NSESSS, ř. 1270.",
    "§ 15 odst. 4 vyhlášky č. 259/2012 Sb.",
    "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.",
    "§ 20 odst. 1 vyhlášky č. 259/2012 Sb.",
    "§ 15 odst. 5 vyhlášky č. 259/2012 Sb.",
    "§ 15 odst. 2 vyhlášky č. 259/2012 Sb.; příloha č. 2 NSESSS, ř. 1250.",
    "Příloha č. 2 NSESSS, ř. 421.",
    "Příloha č. 2 NSESSS, ř. 497.",
    //71. nevyplněno
    "",
    //72. - 74.
    "§ 4 odst. 7 písm. b) vyhlášky č. 259/2012 Sb.",
    "§ 4 odst. 7 písm. d) vyhlášky č. 259/2012 Sb.",
    "§ 4 odst. 7 písm. d) vyhlášky č. 259/2012 Sb.",
    //75. nevypněno
    "",
    //76. - 78.
    "Příloha č. 2 NSESSS, ř. 616.",
    "Příloha č. 2 NSESSS, ř. 1006.",
    "Příloha č. 2 NSESSS, ř. 1228.",
    //79. nevyplněno
    "",
    //80.
    "§ 20 odst. 1 vyhlášky č. 259/2012 Sb.",
    //81. - 83. nevyplněno
    "",
    "",
    "",
    //84. - 88.
    "Příloha č. 2 NSESSS, ř. 1265.",
    "Příloha č. 2 NSESSS, ř. 1352.",
    "Příloha č. 2 NSESSS, ř. 1397.",
    "Příloha č. 2 NSESSS, ř. 1471 a 1481.",
    "Příloha č. 2 NSESSS, ř. 1476.",
    //89. - 92. nevyplněno
    "",
    "",
    "",
    "",
    //93. - 98.
    "Příloha č. 2 NSESSS, ř. 167.",
    "Požadavek 3.1.30 NSESSS.",
    "Požadavek 3.1.30 NSESSS.",
    "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.",
    "Požadavek 3.1.34 NSESSS.",
    "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.",
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
    "Chybí identifikátor datového balíčku SIP.",
    "Uveden je chybně popis datového balíčku SIP.",
    "Uveden je chybně popis datového balíčku SIP.",
    "Uvedena je chybně adresa jmenného prostoru schématu XML.",
    // 5.-9. volné indexy
    "volný index",
    "volný index",
    "volný index",
    "volný index",
    "Uvedena je chybně adresa jmenného prostoru schématu XML.",
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
    //33. - 40.
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Uveden je chybně popis schématu XML.",
    "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
    "Datový balíček SIP neobsahuje transakční protokol.",
    "Chybí připojení komponent (počítačových souborů).",
    //41. (54a.)
    "Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol nebo je struktura uvedena chybně s ohledem na existenci pevného křížového odkazu.",
    //42. (61a.)
    "Chybí množství vlastního dokumentu v analogové podobě.",
    //43. (94a.)
    "Chybně jsou uvedeny spisové znaky.",
    //44. - 50.
    "Chybí provázání komponenty (počítačového souboru) s popisnou částí.",
    "Komponenta (počítačový soubor) má uvedený chybný datový formát.",
    "Chybí popis pro ověření celistvosti komponenty (počítačového souboru) nebo je chybně uveden.",
    "Celistvost komponenty (počítačového souboru) je narušena nebo chybí možnost jejího ověření.",
    "Chybí velikost komponenty (počítačového souboru) nebo je uvedena chybně.",
    "Chybí datum vytvoření komponenty (počítačového souboru).",
    "Chybí připojení komponenty (počítačového souboru) do datového balíčku SIP nebo je provedeno chybně.",
    //51. - 60.
    "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
    "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
    "Uveden je chybně popis odkazu na komponentu (počítačový soubor).",
    "Chybí spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument nebo komponenta ve strukturální mapě a jejich provázání na transakční protokol.",
    "Chybí povinná část (strukturální mapa) struktury datového balíčku SIP.",
    "Není v souladu provázání komponent (počítačových souborů) mezi částí počítačových souborů a strukturální mapou.",
    "Není uveden identifikátor.",
    "Není uveden zdroj identifikátoru.",
    "Uveden je vícekrát stejný spisový plán, věcná skupina, typový spis, součást, díl, spis, dokument, komponenta, bezpečnostní kategorie, skartační režim nebo typ dokumentu nebo je vícekrát použit stejný identifikátor.",
    "Chybí popis komponenty (počítačového souboru) dokumentu v digitální podobě.",
    //61. - 70.
    "Chybí doručené množství dokumentu v analogové podobě.",    
    "Uveden je chybně jazyk dokumentu.",
    "Chybí odůvodnění vyřízení jiným způsobem.",
    "Uveden je chybně rok skartační operace u dokumentu (počítá se jako rok spouštěcí události + 1 + skartační lhůta).",
    "Uveden je chybně rok skartační operace u dílu nebo spisu (počítá se jak podle roku spouštěcí události + 1 + skartační lhůta, tak podle roku skartační operace u dokumentů - záleží na tom, co je vyšší).",
    "U dílu, spisu nebo dokumentu nelze provést skartační řízení, protože ještě nenadešel uváděný rok skartační operace.",
    "Uveden je chybně skartační znak u dílu nebo spisu (stanovuje se podle nejvyššího skartačního znaku dokumentu).",
    "Chybí skartační režim věcné skupiny.",
    "Chybí vyřízení dokumentu.",
    "Uveden je chybně identifikátor věcné skupiny, typového spisu, součásti, dílu, spisu nebo dokumentu.",
    //71. - 80.
    "Není v souladu datum otevření a datum uzavření spisového plánu, věcné skupiny, typového spisu, součásti, dílu nebo spisu.",
    "Chybí strojový zápis času opatření komponenty (počítačového souboru) elektronickým podpisem, elektronickou značkou nebo časovým razítkem.",
    "Chybí strojový zápis času ověření elektronického podpisu, elektronické značky nebo časového razítka.",
    "Chybí strojový zápis času, k němuž je vztaženo posuzování platnosti elektronického podpisu, elektronické značky nebo časového razítka.",
    "Není v souladu rozsah platnosti certifikátu elektronického podpisu, elektronické značky nebo časového razítka.",
    "Uvedeno je chybně u věcné skupiny, typového spisu, součásti, dílu nebo spisu, že neobsahují dokumenty v analogové podobě.",
    "Chybí množství dílu, spisu nebo dokumentu v analogové podobě.",
    "Chybí informace o skartačním řízení.",
    "Není v souladu datum skartačního řízení a roku skartační operace.",
    "Uvedeno je chybně datum skartačního řízení (uváděný rok ještě nenadešel).",
    //81. - 90.
    "Není v souladu rozsah určeného časového období.",
    "Není v souladu rozsah určeného časového období.",
    "Není v souladu rozsah určeného časového období.",
    "Chybí obsah vyřízení jiným způsobem.",
    "Chybí ukládací jednotka dokumentu v analogově podobě.",
    "Chybí zatřídění dokumentu.",
    "Chybí příjemce nebo datum odeslání dokumentu.",
    "Chybí množství odeslaného dokumentu v analogové podobě.",
    "Není v souladu rok spouštěcí události a datum vyřízení u dokumentu.",
    "Není v souladu rok spouštěcí události a datum vyřízení nebo datum uzavření u spisu.",
    //91. - 98.
    "Není v souladu rok spouštěcí události a datum uzavření u dílu.",
    "Chybně je uvedeno IČO subjektu.",
    "Není uveden název.",
    "Chybně jsou uvedeny spisové znaky.",
    "Chybně jsou uvedeny spisové znaky.",
    "Chybně jsou uvedeny spisové znaky.",
    "Chybně jsou uvedeny spisové znaky.",
    "Chybně jsou uvedeny spisové znaky.",
    };
    
    public static String get_popis_Obsahova(int index){
        if(index >= list_popis_obsahova.length || index < 0){
            return "chybný index";
        }
        return list_popis_obsahova[index];
    }
    
    
    static String[] list_dat_struktura = {
        //0 prazdná hodnota při true
        "",
        //1 data1
        "Datový balíček SIP je soubor v datovém formátu ZIP (jeho MIME Content-type je detekován jako application/zip) nebo složka.",
        //2 data2
        "Pokud je datový balíček SIP komprimován do souboru v datovém formátu ZIP, po rozbalení obsahuje právě jednu složku. Ta má stejný název jako je název souboru v datovém formátu ZIP.",
        //3 data3
        "Složka obsahuje právě jeden soubor pojmenovaný mets.xml nebo právě jeden soubor pojmenovaný mets.xml a složku pojmenovanou komponenty.",
        //4
        "Datový balíček SIP je chybně strukturován.", // data1
        //5
        "Uvedeno je chybně označení datového balíčku SIP.", //data2
        //6 zdroj d1
        "Požadavek 11.2.9 a 11.2.1 NSESSS.",    
        //7 zdroj d2
        "Požadavek 11.2.9 NSESSS.",    
        //8 zdroj d3
        "Požadavek 11.2.2, 11.2.3 a 11.2.8 NSESSS.",    
        //9 data 3
        "Uvedena jsou chybně metadata a komponenty (počítačové soubory) v datovém balíčku SIP."    
    };
    
    static String[] list_text_jmProstory = {
        "",
        //1 text ns1
        "Soubor obsahuje právě jeden kořenový element <mets:mets>.",
        //2 text ns2
        "Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd."
    };
    public static String get_text_JmProstory(int index){
        if(index >= list_text_jmProstory.length || index < 0){
            return "chybný index";
        }
        return list_text_jmProstory[index];
    }
    
    public static String get_DatovaStruktura(int index){
        if(index >= list_dat_struktura.length || index < 0){
            return "chybný index";
        }
        return list_dat_struktura[index];
    }
}
