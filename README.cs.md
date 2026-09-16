# Validátor ZAF

[English](README.md) | **Česky**

[Domovská stránka a dokumentace](https://validatorzaf.github.io/zaf) · [Zdrojové kódy](https://github.com/validatorzaf/zaf)

Validátor ZAF je knihovna pro jazyk Java, řádková aplikace a webová služba pro
kontrolu datových balíčků a archivního popisu v prostředí české digitální
archivace. Aplikace provede načtení balíčku (příp. rozbalení ze souboru ZIP),
vyhodnotí sadu pravidel a uloží výsledek kontroly do souboru XML vytvořeného
podle schématu XSD.

## Co lze kontrolovat

| Typ validace | Předmět kontroly | Standard |
| --- | --- | --- |
| `NSESSS2017` | datové balíčky SIP | [národní standard pro elektronické systémy spisové služby z r. 2017](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx) |
| `NSESSS2024` | datové balíčky SIP | [národní standard pro elektronické systémy spisové služby z r. 2024](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx) |
| `AP2023` | archivní popis a archivní pomůcky (EAD3) | [profil standardu EAD3 pro archivní pomůcky v České republice](https://stands.nacr.cz/ead/) |
| `DAAIP2024` | výměnné balíčky AIP / DIP (METS, PREMIS) | [standard výměnného formátu digitálních archiválií mezi digitálními archivy v ČR, verze 1.0](https://stands.nacr.cz/da-aip/) |

Typ validace je ve výchozím nastavení rozpoznán automaticky ze vstupních dat,
lze ho vynutit přepínačem `--type`. Každý typ nabízí několik profilů pravidel
(`--profile`), např. jen metadata vs. úplný SIP, archivní popis vs. archivní
pomůcka nebo AIP vs. DIP.

Kontrola je rozdělena do vrstev (datová struktura, znakové sady, správnost XML,
jmenné prostory, soulad se schématem XSD, kontrola obsahu, komponenty). Pokud je
v rámci jedné vrstvy nalezena chyba, další vrstvy se již neprovádí.

Kontrola škodlivého kódu probíhá externě, aplikace pouze zaznamená její výsledek
předaný přepínačem `--threat`.

## Součásti projektu

| Modul | Popis |
| --- | --- |
| `zaf-core` | validační knihovna a vstupní bod `CmdValidator` – obsahuje celý výkonný kód pravidel |
| `zaf-cmd` | sestavení samostatné řádkové aplikace |
| `zaf-ws` | interní služba zpřístupňující validátor přes REST API a volitelné webové rozhraní; není součástí veřejné distribuce |
| `doc` | dokumentace ve formátu AsciiDoc včetně úplného katalogu pravidel pro všechny typy validace |

## Požadavky

* Java 21 nebo novější
* Maven 3.9+ (pro sestavení ze zdrojových kódů)

Volitelné externí nástroje:

* [veraPDF](https://verapdf.org/) – vestavěný, externí instalaci lze použít přes `-Dzaf.vera.path`
* [Siegfried](https://www.itforarchivists.com/siegfried/) – využit pro ověření MIME typu, pokud je nastaveno `-Dzaf.siegfried.path`

## Použití z příkazové řádky

```
java -jar CmdValidator.jar [přepínače] <path>
```

Kontrola jednotlivého SIPu se zápisem výstupu do protokolu:

```
java -jar CmdValidator.jar -p SIP D34520.zip -o out.xml
```

Kontrola všech balíčků v adresáři (dávkový režim):

```
java -jar CmdValidator.jar -b C:\temp\SIP1580724295329
```

Vybrané přepínače:

| Přepínač | Význam |
| --- | --- |
| `-t`, `--type=` | typ validace: `AUTO` (výchozí), `NSESSS2017`, `NSESSS2024`, `AP2023`, `DAAIP2024` |
| `-p`, `--profile=` | profil pravidel: `AUTO`, `SIP_METADATA`, `SIP_PREVIEW`, `SIP`, `AD`, `FA`, `AIP`, `DIP_METADATA`, `DIP_CONTENT`, `SIP_CHANGE` |
| `-b`, `--batch` | dávkový režim, `path` je adresář s balíčky |
| `-w`, `--workdir=` | pracovní adresář pro rozbalení |
| `-k`, `--keep` | zachování rozbalených souborů na disku |
| `-e`, `--exclude=` | seznam kontrol oddělených čárkou, které se nemají provádět |
| `-i`, `--id=` | identifikátor prováděné kontroly |
| `-T`, `--threat=` | popis hrozby nalezené externím nástrojem |
| `-o`, `--output=` | jméno souboru nebo adresáře pro uložení výsledků |
| `-f`, `--format=` | formát protokolu: `XML_V2` (výchozí, `validation_v2.xsd`), `XML_V1` (`validace_v1.xsd`), `XML_OLD` (pouze NSESSS) |

Spuštění bez parametrů vypíše úplnou nápovědu. Pokročilé chování (veraPDF,
Siegfried, kódování ZIP) se řídí systémovými parametry `-D`, viz
[dokumentace](https://validatorzaf.github.io/zaf).

## Použití jako knihovny pro Javu

```xml
<dependency>
  <groupId>cz.zaf</groupId>
  <artifactId>zaf-core</artifactId>
  <version>${zafVersion}</version>
</dependency>
```

Připravené balíčky jsou dostupné v repozitáři:

```xml
<repositories>
  <repository>
    <id>lc-public-release</id>
    <url>https://pkgs.lightcomp.com/maven/public-release/</url>
  </repository>
</repositories>
```

## Sestavení ze zdrojových kódů

```
mvn clean install
```

## Rozsah a omezení

Validátor ZAF je vyvíjen se snahou o zjištění shody a případných chyb
validovaných balíčků s příslušnými standardy. Použití Validátoru ZAF neprokazuje
splnění požadavků na balíčky SIP ze strany archivů rozhodujících ve skartačním
řízení. Výsledky validace Validátorem ZAF nemusí být shodné s výsledky zjištěnými
validátory digitálních archivů.

## Licenční podmínky

[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Součástí software jsou programové knihovny a aplikace šířené pod permisivní nebo
slabě copyleftovou licencí. V těchto částech softwaru platí příslušné podmínky a
vyloučení odpovědnosti původního autora viz licenční ustanovení jednotlivých
částí softwaru.

## Vývoj

Vývoj a další rozvoj Validátoru ZAF probíhá ve spolupráci
[Hlavního města Prahy, Magistrátu hlavního města Prahy, odboru Archiv hlavního města Prahy](http://www.ahmp.cz/),
[Univerzity Karlovy, Ústavu dějin a archivu Univerzity Karlovy](https://udauk.cuni.cz/ARCH-1.html)
a [Lightcomp, v.o.s.](https://lightcomp.cz/)
