# Validátor ZAF
verze XXXXXX

## Popis

Validátor ZAF je knihovna pro kontrolu datových balíčků SIP podle 
[národního standardu pro elektronické systémy spisové služby z r.
2017](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx)
na základě sestavených
[pravidel](http://digitalniarchiv.ahmp.cz/kontrolasip/pravidla/kontrolasip-pravidla-XXXXXX.ods).
Aplikace provádí načtení balíčků SIP (příp. rozbalení ze souborů ZIP),
kontrolu a uložení výsledků kontroly do souboru XML vytvořeného podle [schématu
XSD](http://digitalniarchiv.ahmp.cz/schema/kontrolasip/vX/kontrolasip.xsd).
Kontrola škodlivého kódu probíhá externě a aplikace pouze načítá výsledky této
kontroly podle použitých přepínačů. Pokud je v rámci jednoho z typů kontrol
nalezena chyba, další typy kontrol se neprovádí.

## Instalace

XXXXXX

### Požadavky

* OS Microsoft Windows, GNU/Linux, OS X nebo Oracle Solaris
* Java 1.8 a vyšší

## Použití

Validátor ZAF lze volat prostřednictvím příkazového řádku:
java -jar CmdValidator [přepínače] [<path>]

path - cesta k SIPu, v případě dávkového režimu ke složce se SIPy

### Přepínače:

-b|--batch Dávkový režim, vstupem je adresář obsahující SIPy
-w|--workdir= Umístění pracovního adresáře, zde budou SIPy rozbaleny
-d|--druh= Druh kontroly (1 - výchozí):

> 1 = pro provedení skartačního řízení (jen metadata bez přiložených komponent)
> 2 = pro provedení skartačního řízení (s přiloženými komponentami)
> 3 = pro předávání dokumentů a jejich metadat do archivu

-e|--exclude= Seznam kontrol oddělených čárkou, které se nemají provádět
-i|--id= Identifikátor prováděné kontroly
-z|--hrozba= Podrobnosti v případě nalezení hrozby (pro předání z antivirového programu)
-o|--output= Jméno souboru nebo adresáře pro uložení výsledků

## Podpora

Aplikace zaznamenává a vypisuje čas spuštění a čas dokončení úlohy do konzole.

Pokud aplikace předčasně padá s upozorněním "Java heap space", je nutné zvýšit množství alokované
paměti pro Javu. Provádí se přidáním parametrů -Xms a -Xmx s číslem udávajícím velikost paměti
a s jednotkou.

Kontakt: [XXXX@XXXX](XXXX@XXXX)

## Autoři

Stanislav Bečvář
Hlavní město Praha
Magistrát hlavního města Prahy
Odbor „Archiv hlavního města Prahy“

Petr Pytelka
LightComp v.o.s.

Karel Žáček
LightComp v.o.s.

## Licenční podmínky

[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)  

Součástí software jsou programové knihovny a aplikace šířené pod permisivní nebo slabě copyleftovou
licencí. V těchto částech softwaru platí příslušné podmínky a vyloučení odpovědnosti původního
autora viz licenční ustanovení jednotlivých částí softwaru.
