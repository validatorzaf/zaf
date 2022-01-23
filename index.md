## Validátor ZAF

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

### Dokumentace

Dokumentace k validátoru je součástí jednotlivých sestavení. Aktuální dokumentace je dostupná [zde](doc)

### Aktuální verze

Výdána verze 0.9.3, dostupná na (GitHub Releases)[https://github.com/validatorzaf/zaf/releases] a 
v repozitáři pro Maven (https://www.lightcomp.cz/releases/repository/lc-public-release/)[https://www.lightcomp.cz/releases/repository/lc-public-release/].
