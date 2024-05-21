package cz.zaf.sipvalidator.nsesss2017.gendoc;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;

public class GenerateDoc {

    Rule<? extends RuleEvaluationContext>[] devel = ZakladniProfilValidace.DEVEL.createObsahovaPravidla();
    Rule<? extends RuleEvaluationContext>[] prejimka = ZakladniProfilValidace.PREJIMKA.createObsahovaPravidla();
    Rule<? extends RuleEvaluationContext>[] skartaceMetadata = ZakladniProfilValidace.SKARTACE_METADATA
            .createObsahovaPravidla();
    Rule<? extends RuleEvaluationContext>[] skartaceUplny = ZakladniProfilValidace.SKARTACE_UPLNY
            .createObsahovaPravidla();

    Map<String, Rule<? extends RuleEvaluationContext>> prejimkaMap = Stream.of(prejimka)
            .collect(Collectors.toMap(Rule::getCode, Function.identity()));
    Map<String, Rule<? extends RuleEvaluationContext>> skartaceMetadataMap = Stream.of(skartaceMetadata)
            .collect(Collectors.toMap(Rule::getCode, Function.identity()));
    Map<String, Rule<? extends RuleEvaluationContext>> skartaceUplnyMap = Stream.of(skartaceUplny)
            .collect(Collectors.toMap(Rule::getCode, Function.identity()));
    List<String> pravidla = new ArrayList<>();
    Map<String, Rule<? extends RuleEvaluationContext>> pravidlaMap = new HashMap<>();

    public GenerateDoc() {
        pridejPravidla(devel);
        pridejPravidla(prejimka);
        pridejPravidla(skartaceMetadata);
        pridejPravidla(skartaceUplny);
    }

    private void pridejPravidla(Rule<? extends RuleEvaluationContext>[] pravidlaIn) {
        for (Rule<? extends RuleEvaluationContext> prav : pravidlaIn) {
            if (pravidlaMap.putIfAbsent(prav.getCode(), prav) == null) {
                pravidla.add(prav.getCode());
            }
        }
    }

    private void write(PrintStream out) {
        for (String pravKod : pravidla) {
            Rule<? extends RuleEvaluationContext> pravidlo = pravidlaMap.get(pravKod);
            write(out, pravidlo);
        }

    }

    private void write(PrintStream out, Rule<? extends RuleEvaluationContext> pravidlo) {

        String kod = pravidlo.getCode();
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

        String textPravidla = pravidlo.getDescription();
        textPravidla = textPravidla.replace("\r", "");
        textPravidla = textPravidla.replace("\n- ", "\n* ");

        out.println("[[" + kod + "]]");
        out.println("== " + kod);
        out.println();
        out.println("[horizontal]");
        out.println();
        out.println("Pravidlo:: " + textPravidla);
        out.println("Kód:: " + kod);
        out.println("Zdroj:: " + pravidlo.getRuleSource());
        out.println("Popis chyby:: " + pravidlo.getGenericError());
        out.println("Aktivní:: " + aktivniText);
        out.println();
    }

    public static void main(String[] args) {
        GenerateDoc gd = new GenerateDoc();
        gd.write(System.out);
    }
}
