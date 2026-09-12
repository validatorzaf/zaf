# ZAF Validator

**English** | [Česky](README.cs.md)

[Homepage and documentation](https://validatorzaf.github.io/zaf) · [Source code](https://github.com/validatorzaf/zaf)

ZAF Validator is a Java library, command line tool and web service for validating
data packages and archival description produced in the Czech digital preservation
environment. It reads a package (optionally unpacking it from a ZIP archive),
runs a set of rules against it and writes the result into an XML report defined
by an XSD schema.

## What can be validated

| Validation type | Subject | Standard |
| --- | --- | --- |
| `NSESSS2017` | SIP data packages | [National Standard for Electronic Records Management Systems, 2017](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx) |
| `NSESSS2024` | SIP data packages | [National Standard for Electronic Records Management Systems, 2024](https://www.mvcr.cz/clanek/narodni-standard-pro-elektronicke-systemy-spisove-sluzby.aspx) |
| `AP2023` | Archival description and finding aids (EAD3) | [Czech EAD3 profile for finding aids](https://stands.nacr.cz/ead/) |
| `DAAIP2024` | AIP / DIP interchange packages (METS, PREMIS) | [Interchange format for digital archival records between Czech digital archives, v1.0](https://stands.nacr.cz/da-aip/) |

The validation type is detected automatically from the input by default and can
be forced with the `--type` switch. Each type offers several rule profiles
(`--profile`), for example metadata-only vs. complete SIP, archival description
vs. finding aid, or AIP vs. DIP.

Validation is organised into layers (data structure, character sets, XML
well-formedness, namespaces, XSD schema conformance, content rules, components).
If a layer reports an error, the following layers are not executed.

Malicious code scanning is performed externally; the application only records
the result passed to it via the `--threat` switch.

## Components

| Module | Description |
| --- | --- |
| `zaf-core` | Validation library and the `CmdValidator` entry point — the whole rule engine lives here |
| `zaf-cmd` | Packaging of the standalone command line application |
| `zaf-ws` | Internal service wrapping the validator in a REST API and an optional web UI; not part of the public distribution |
| `doc` | AsciiDoc documentation, including the full rule catalogue for every validation type |

## Requirements

* Java 21 or newer
* Maven 3.9+ (for building from sources)

Optional external tools:

* [veraPDF](https://verapdf.org/) — bundled, an external installation can be used via `-Dzaf.vera.path`
* [Siegfried](https://www.itforarchivists.com/siegfried/) — used for MIME type verification when `-Dzaf.siegfried.path` is set

## Command line usage

```
java -jar CmdValidator.jar [switches] <path>
```

Validate a single SIP and write the report to a file:

```
java -jar CmdValidator.jar -p SIP D34520.zip -o out.xml
```

Validate every package in a directory (batch mode):

```
java -jar CmdValidator.jar -b /data/packages
```

Selected switches:

| Switch | Meaning |
| --- | --- |
| `-t`, `--type=` | Validation type: `AUTO` (default), `NSESSS2017`, `NSESSS2024`, `AP2023`, `DAAIP2024` |
| `-p`, `--profile=` | Rule profile: `AUTO`, `SIP_METADATA`, `SIP_PREVIEW`, `SIP`, `AD`, `FA`, `AIP`, `DIP_METADATA`, `DIP_CONTENT`, `SIP_CHANGE` |
| `-b`, `--batch` | Batch mode, `path` is a directory of packages |
| `-w`, `--workdir=` | Working directory used for unpacking |
| `-k`, `--keep` | Keep unpacked files on disk |
| `-e`, `--exclude=` | Comma separated list of checks to skip |
| `-i`, `--id=` | Identifier of the validation run |
| `-T`, `--threat=` | Description of a threat detected by an external scanner |
| `-o`, `--output=` | Output file or directory |
| `-f`, `--format=` | Report format: `XML_V2` (default, `validation_v2.xsd`), `XML_V1` (`validace_v1.xsd`), `XML_OLD` (NSESSS only) |

Run the application without arguments to print the full help. Advanced behaviour
(veraPDF, Siegfried, ZIP encoding) is configured through `-D` system properties —
see the [documentation](https://validatorzaf.github.io/zaf).

## Use as a Java library

```xml
<dependency>
  <groupId>cz.zaf</groupId>
  <artifactId>zaf-core</artifactId>
  <version>${zafVersion}</version>
</dependency>
```

Released artifacts are available from:

```xml
<repositories>
  <repository>
    <id>lc-public-release</id>
    <url>https://pkgs.lightcomp.com/maven/public-release/</url>
  </repository>
</repositories>
```

## Building from sources

```
mvn clean install
```

## Scope and limitations

ZAF Validator is developed to detect conformance and possible errors of validated
packages against the relevant standards. Using ZAF Validator does not prove that
a SIP package meets the requirements of the archives deciding in the appraisal
and disposal procedure. Results produced by ZAF Validator need not match the
results of validators run by digital archives.

## License

[Apache License Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

The software bundles libraries and applications distributed under permissive or
weak copyleft licenses. The terms and disclaimers of the original authors apply
to those parts of the software; see the license terms of the individual
components.

## Development

ZAF Validator is developed in cooperation of
[the City of Prague, Prague City Hall, Prague City Archives](http://www.ahmp.cz/),
[Charles University, Institute of the History of Charles University and Archive of Charles University](https://udauk.cuni.cz/ARCH-1.html)
and [Lightcomp, v.o.s.](https://lightcomp.cz/)
