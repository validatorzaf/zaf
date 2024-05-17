package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06_Obsahova;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.Arrays;

public class Pravidlo95 extends K06PravidloBase {

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
    protected void kontrola() {
        List<Element> vecneSkupiny = metsParser.getNodes(NsesssV3.VECNA_SKUPINA);
        if (CollectionUtils.isEmpty(vecneSkupiny)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:VecnaSkupina>.");
        }
        List<Element> soucasti = metsParser.getNodes(NsesssV3.SOUCAST);
        List<Element> typoveSpisy = metsParser.getNodes(NsesssV3.TYPOVY_SPIS);

        Iterable<Element> multiCol = IterableUtils.chainedIterable(vecneSkupiny, soucasti, typoveSpisy);
        for (Element entita : multiCol) {
            Element pu_entita = ValuesGetter.getXChild(entita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                    NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);

            if (pu_entita == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(entita),
                        entita, kontrola.getEntityId(entita));
            }

            Element je_entita = ValuesGetter.getSourozencePrvnihoSeJmenem(pu_entita, NsesssV3.JEDNODUCHY_SPISOVY_ZNAK);
            if (je_entita == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(entita),
                        entita, kontrola.getEntityId(entita));
            }

            Element pu_rodic = ValuesGetter.getXChild(entita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                    NsesssV3.MATERSKA_ENTITA, NsesssV3.VECNA_SKUPINA,
                    NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                    NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
            if (pu_rodic == null) {
                pu_rodic = ValuesGetter.getXChild(entita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                        NsesssV3.MATERSKA_ENTITA, NsesssV3.TYPOVY_SPIS,
                        NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.TRIDENI, NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
            }
            if (pu_rodic == null) {
                pu_rodic = ValuesGetter.getXChild(entita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                        NsesssV3.MATERSKA_ENTITA, NsesssV3.SOUCAST, NsesssV3.EVIDENCNI_UDAJE,
                        NsesssV3.TRIDENI, NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
            }
            if (pu_rodic == null) {
                pu_rodic = ValuesGetter.getXChild(entita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                        NsesssV3.SPISOVY_PLAN);
            }
            if (pu_rodic != null) {
                if (pu_rodic.getNodeName().equals(NsesssV3.SPISOVY_PLAN)) {
                    if (K06_Obsahova.spisZnakObsahujeOddelovac(pu_entita.getTextContent())) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Spisový znak nejvyšší věcné skupiny v sobě nesmí obsahovat oddělovač. "
                                + getJmenoIdentifikator(entita), entita, kontrola.getEntityId(entita));
                    }
                } else {
                    String entita_jsz = je_entita.getTextContent();
                    String entita_pus = pu_entita.getTextContent();
                    String rodic_pus = pu_rodic.getTextContent();
                    Element elElntitaPuRodic = kontrola.getEntity(pu_rodic);
                    List<Element> listChybnychEntit = Arrays.asList(elElntitaPuRodic, entita);
                    boolean zacina = entita_pus.startsWith(rodic_pus);
                    if (!zacina) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz
                                + ". Plně urč. spis. znak entity: " + entita_pus
                                + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". "
                                + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic),
                                getMistoChyby(je_entita) + " " + getMistoChyby(pu_entita) + " "
                                + getMistoChyby(pu_rodic),
                                kontrola.getEntityId(listChybnychEntit));
                    }
                    boolean konci = entita_pus.endsWith(entita_jsz);
                    if (!konci) {
                        nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. Jedn. spis. znak entity: " + entita_jsz
                                + ". Plně urč. spis. znak entity: " + entita_pus
                                + ". Plně urč. spis. znak rodičovské entity: " + rodic_pus + ". "
                                + getJmenoIdentifikator(entita) + " " + getJmenoIdentifikator(pu_rodic),
                                getMistoChyby(je_entita) + " " + getMistoChyby(pu_entita) + " "
                                + getMistoChyby(pu_rodic),
                                kontrola.getEntityId(listChybnychEntit));
                    }
                }
            }
        }
    }

}
