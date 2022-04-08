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

Vývoj a další rozvoj Validátoru ZAF probíhá ve spolupráci [Hlavního města Prahy, Magistrátu hlavního města Prahy, 
odboru Archiv hlavního města Prahy](http://www.ahmp.cz/); [Univerzity Karlovy, Ústavu dějin a archivu Univerzity Karlovy](https://udauk.cuni.cz/ARCH-1.html) a [Lightcomp, v.o.s.](https://lightcomp.cz/)

Validátor ZAF je vyvíjen se snahou o zjištění shody a případných chyb validovaných balíčků SIP s NSESSS.
Použití Validátoru ZAF neprokazuje splnění požadavků na balíčky SIP ze strany archivů rozhodujících 
ve skartačním řízení. Výsledky validace Validátorem ZAF nemusí být shodné s výsledky zjištěnými 
validátory digitálních archivů.

### Dokumentace

Dokumentace k validátoru je součástí jednotlivých sestavení. Aktuální dokumentace je dostupná [zde](doc)

### Aktuální verze

Výdána verze 0.9.5

Jednotlivé verze jsou dostupné na [GitHub Releases](https://github.com/validatorzaf/zaf/releases) a 
v repozitáři pro Maven [https://www.lightcomp.cz/releases/repository/lc-public-release/](https://www.lightcomp.cz/releases/repository/lc-public-release/).


### Komunikace

Zjištěné chyby lze hlásit pomocí nástroje [Issues na GitHubu](https://github.com/validatorzaf/zaf/issues).

Pro jiné požadavky můžete zaslat email na adresu: mailto:petr.pytelka@lightcomp.cz


