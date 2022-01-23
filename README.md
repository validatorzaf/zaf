# Validátor ZAF
[Domovská stránka a dokumentace](https://validatorzaf.github.io/zaf)

## Popis

Validátor ZAF je knihovna pro kontrolu datových balíčků SIP podle 
[národního standardu pro elektronické systémy spisové služby z r.
2017](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx)
na základě sestavených pravidel.
Aplikace provádí načtení balíčků SIP (příp. rozbalení ze souborů ZIP),
kontrolu a uložení výsledků kontroly do souboru XML vytvořeného podle schématu
XSD.
Kontrola škodlivého kódu probíhá externě a aplikace pouze načítá výsledky této
kontroly podle použitých přepínačů. Pokud je v rámci jednoho z typů kontrol
nalezena chyba, další typy kontrol se neprovádí.

## Licenční podmínky

[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Součástí software jsou programové knihovny a aplikace šířené pod permisivní nebo slabě copyleftovou
licencí. V těchto částech softwaru platí příslušné podmínky a vyloučení odpovědnosti původního
autora viz licenční ustanovení jednotlivých částí softwaru.
