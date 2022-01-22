package cz.zaf.sipvalidator.nsesss2017.gendoc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.zaf.sipvalidator.nsesss2017.ObsahovePravidlo;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;

public class GenerateDoc {

    ObsahovePravidlo[] devel = ZakladniProfilValidace.DEVEL.createObsahovaPravidla();
    ObsahovePravidlo[] prejimka = ZakladniProfilValidace.PREJIMKA.createObsahovaPravidla();
    ObsahovePravidlo[] skartaceMetadata = ZakladniProfilValidace.SKARTACE_METADATA.createObsahovaPravidla();
    ObsahovePravidlo[] skartaceUplny = ZakladniProfilValidace.SKARTACE_UPLNY.createObsahovaPravidla();

    Map<String, ObsahovePravidlo> prejimkaMap = Stream.of(prejimka).collect(Collectors.toMap(
                                                                                             ObsahovePravidlo::getKodPravidla,
                                                                                             Function.identity()));
    Map<String, ObsahovePravidlo> skartaceMetadataMap = Stream.of(skartaceMetadata).collect(Collectors.toMap(
                                                                                                             ObsahovePravidlo::getKodPravidla,
                                                                                                             Function.identity()));
    Map<String, ObsahovePravidlo> skartaceUplnyMap = Stream.of(skartaceUplny).collect(Collectors.toMap(
                                                                                                       ObsahovePravidlo::getKodPravidla,
                                                                                                       Function.identity()));
    List<String> pravidla = new ArrayList<>();
    Map<String, ObsahovePravidlo> pravidlaMap = new HashMap<>();

    public GenerateDoc() {
        pridejPravidla(devel);
        pridejPravidla(prejimka);
        pridejPravidla(skartaceMetadata);
        pridejPravidla(skartaceUplny);
    }

    private void pridejPravidla(ObsahovePravidlo[] pravidlaIn) {
        for (ObsahovePravidlo prav : pravidlaIn) {
            if (pravidlaMap.putIfAbsent(prav.getKodPravidla(), prav) == null) {
                pravidla.add(prav.getKodPravidla());
            }
        }
    }

    private void write(PrintStream out) {
        for (String pravKod : pravidla) {
            ObsahovePravidlo pravidlo = pravidlaMap.get(pravKod);
            write(out, pravidlo);
        }

    }

    private void write(PrintStream out, ObsahovePravidlo pravidlo) {

        String kod = pravidlo.getKodPravidla();
        // String zjištění, kdy je pravidlo aktivní
        List<ProfilValidace> aktivni = new ArrayList<>();
        if (skartaceMetadataMap.containsKey(kod)) {
            aktivni.add(ZakladniProfilValidace.SKARTACE_METADATA);
        }
        if (skartaceUplnyMap.containsKey(kod)) {
            aktivni.add(ZakladniProfilValidace.SKARTACE_UPLNY);
        }
        if (prejimkaMap.containsKey(kod)) {
            aktivni.add(ZakladniProfilValidace.PREJIMKA);
        }

        String aktivniText;
        if (aktivni.size() > 0) {
            List<String> nazvy = aktivni.stream().map(p -> p.getNazev()).collect(Collectors.toList());
            aktivniText = String.join(", ", nazvy);
        } else {
            aktivniText = "neaktivní";
        }

        String textPravidla = pravidlo.getTtextPravidla();
        textPravidla = textPravidla.replace("\r", "");
        textPravidla = textPravidla.replace("\n- ", "\n* ");

        out.println("[[" + kod + "]]");
        out.println("== " + kod);
        out.println();
        out.println("[horizontal]");
        out.println();
        out.println("Pravidlo:: " + textPravidla);
        out.println("Kód:: " + kod);
        out.println("Zdroj:: " + pravidlo.getZdroj());
        out.println("Popis chyby:: " + pravidlo.getObecnyPopisChyby());
        out.println("Aktivní:: " + aktivniText);
        out.println();
    }

    public static void main(String[] args) {
        GenerateDoc gd = new GenerateDoc();
        gd.write(System.out);
    }
}
