package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.ArrayList;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo95 extends K06PravidloBase {

    public Pravidlo95(K06_Obsahova kontrola) {
        super(kontrola, K06_Obsahova.OBS95,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou obsahující hodnotu elementu <nsesss:PlneUrcenySpisovyZnak> rodičovské entity, oddělovač a hodnotu elementu <nsesss:JednoduchySpisovyZnak> výchozí entity.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.30 NSESSS.");
        // TODO Auto-generated constructor stub
    }

    //OBSAHOVÁ č.95 
    // Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), 
    // která obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s 
    //hodnotou obsahující oddělovač tvořený mezerou, pomlčkou, spojovníkem, lomítkem nebo tečkou, který není posledním znakem, 
    // se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), 
    // přičemž hodnota jejího elementu <nsesss:PlneUrcenySpisovyZnak> v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> se rovná hodnotě elementu 
    // <nsesss:PlneUrcenySpisovyZnak> výchozí entity před posledním oddělovačem.",
    @Override
    protected boolean kontrolaPravidla() {
        ArrayList<Node> vecneSkupiny = ValuesGetter.getAllAnywhereArrayList("nsesss:VecnaSkupina", metsParser
                .getDocument());
        ArrayList<Node> soucasti = ValuesGetter.getAllAnywhereArrayList("nsesss:Soucast", metsParser.getDocument());
        ArrayList<Node> typoveSpisy = ValuesGetter.getAllAnywhereArrayList("nsesss:TypovySpis", metsParser
                .getDocument());
        ArrayList<Node> list = new ArrayList<>();
        if (vecneSkupiny == null)
            return nastavChybu("Nenalezen element <nsesss:VecnaSkupina>.", K06_Obsahova.MISTO_CHYBY_NEUPRESNENO);
        list.addAll(vecneSkupiny);
        if (soucasti != null)
            list.addAll(soucasti);
        if (typoveSpisy != null)
            list.addAll(typoveSpisy);

        int velikostListu = list.size();
        for (int i = 0; i < velikostListu; i++) {
            Node entita = list.get(i);
            Node pu_entita = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                    "nsesss:PlneUrcenySpisovyZnak");
            if (pu_entita == null)
                return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(entita),
                                   getMistoChyby(entita));
            Node je_entita = ValuesGetter.getSourozencePrvnihoSeJmenem(pu_entita, "nsesss:JednoduchySpisovyZnak");
            if (je_entita == null)
                return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(entita),
                                   getMistoChyby(entita));
            Node pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                   "nsesss:MaterskaEntita", "nsesss:VecnaSkupina",
                                                   "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                   "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                  "nsesss:MaterskaEntita", "nsesss:TypovySpis", "nsesss:EvidencniUdaje",
                                                  "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                  "nsesss:MaterskaEntita", "nsesss:Soucast", "nsesss:EvidencniUdaje",
                                                  "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, "nsesss:EvidencniUdaje", "nsesss:Trideni",
                                                  "nsesss:SpisovyPlan");
            if (pu_rodic != null) {
                if (pu_rodic.getNodeName().equals("nsesss:SpisovyPlan")) {
                    if (K06_Obsahova.spisZnakObsahujeOddelovac(pu_entita.getTextContent())) {
                        return nastavChybu("Spisový znak nejvyšší věcné skupiny v sobě nesmí obsahovat oddělovač. "
                                + getJmenoIdentifikator(entita), getMistoChyby(entita));
                    }
                } else {
                    String entita_jsz = je_entita.getTextContent();
                    String entita_pus = pu_entita.getTextContent();
                    String rodic_pus = pu_rodic.getTextContent();
                    boolean zacina = entita_pus.startsWith(rodic_pus);
                    if (!zacina) {
                        return nastavChybu("Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz
                                + ". Plně urč. spis. znak entity: " + entita_pus
                                + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". "
                                + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic),
                                          getMistoChyby(je_entita) + " " + getMistoChyby(pu_entita) + " "
                                                  + getMistoChyby(pu_rodic));
                    }
                    boolean konci = entita_pus.endsWith(entita_jsz);
                    if (!konci) {
                        return nastavChybu("Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz
                                + ". Plně urč. spis. znak entity: " + entita_pus
                                + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". "
                                + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic),
                                          getMistoChyby(je_entita) + " " + getMistoChyby(pu_entita) + " "
                                                  + getMistoChyby(pu_rodic));
                    }
                }
            }
        }

        return true;
    }

}
