package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBaseOld;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

public class Pravidlo95 extends K06PravidloBaseOld {

    static final public String OBS95 = "obs95";

    public Pravidlo95() {
        super(OBS95,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>), která se nachází v rodičovské entitě věcná skupina (<nsesss:VecnaSkupina>) nebo typový spis (<nsesss:TypovySpis>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> element <nsesss:PlneUrcenySpisovyZnak> s hodnotou obsahující hodnotu elementu <nsesss:PlneUrcenySpisovyZnak> rodičovské entity, oddělovač a hodnotu elementu <nsesss:JednoduchySpisovyZnak> výchozí entity.",
                "Chybně jsou uvedeny spisové znaky.",
                "Požadavek 3.1.30 NSESSS.");
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
        List<Node> vecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
        if (CollectionUtils.isEmpty(vecneSkupiny) ) {
            return nastavChybu("Nenalezen element <nsesss:VecnaSkupina>.");
        }
        List<Node> soucasti = metsParser.getNodes(NsessV3.SOUCAST);
        List<Node> typoveSpisy = metsParser.getNodes(NsessV3.TYPOVY_SPIS);

        Iterable<Node> multiCol = IterableUtils.chainedIterable(vecneSkupiny, soucasti, typoveSpisy);
        for(Node entita: multiCol) {
            Node pu_entita = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                    "nsesss:PlneUrcenySpisovyZnak");
            if (pu_entita == null)
                return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(entita),
                                   entita);
            Node je_entita = ValuesGetter.getSourozencePrvnihoSeJmenem(pu_entita, "nsesss:JednoduchySpisovyZnak");
            if (je_entita == null)
                return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(entita),
                                   entita);
            Node pu_rodic = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                   "nsesss:MaterskaEntita", "nsesss:VecnaSkupina",
                                                   NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                   "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:MaterskaEntita", "nsesss:TypovySpis",
                                                  NsessV3.EVIDENCNI_UDAJE,
                                                  "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:MaterskaEntita", "nsesss:Soucast", NsessV3.EVIDENCNI_UDAJE,
                                                  "nsesss:Trideni", "nsesss:PlneUrcenySpisovyZnak");
            if (pu_rodic == null)
                pu_rodic = ValuesGetter.getXChild(entita, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:SpisovyPlan");
            if (pu_rodic != null) {
                if (pu_rodic.getNodeName().equals("nsesss:SpisovyPlan")) {
                    if (K06_Obsahova.spisZnakObsahujeOddelovac(pu_entita.getTextContent())) {
                        return nastavChybu("Spisový znak nejvyšší věcné skupiny v sobě nesmí obsahovat oddělovač. "
                                + getJmenoIdentifikator(entita), entita);
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
