package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import java.util.Arrays;

// OBSAHOVÁ č.96 Každá základní entita a každá entita typový spis
// (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů
// <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými
// hodnotami, jaké obsahují v hierarchii dětských elementů
// <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské
// entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást
// (<nsesss:Soucast>).",
public class Pravidlo96 extends K06PravidloBase {

    static final public String OBS96 = "obs96";

    public Pravidlo96() {
        super(OBS96,
                "Každá základní entita a každá entita typový spis (<nsesss:TypovySpis>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).",
                "Chybně jsou uvedeny spisové znaky.",
                "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        for (Element zakladniEntita : zakladniEntity) {
            Element n_zakl_jsz = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                    NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
            Element n_zakl_pusz = ValuesGetter.getXChild(zakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                    NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
            if (n_zakl_jsz == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladniEntita),
                        zakladniEntita, kontrola.getEntityId(zakladniEntita));
            }
            if (n_zakl_pusz == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                        + getJmenoIdentifikator(zakladniEntita),
                        zakladniEntita, kontrola.getEntityId(zakladniEntita));
            }

            if (zakladniEntita.getNodeName().equals(NsessV3.DIL)) {
                kontrolaDilu(zakladniEntita, n_zakl_jsz, n_zakl_pusz);
            } else {
                String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
                String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

                String jednoduchy, plneUrceny;
                List<Element> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
                List<Element> vecneSkupiny = ValuesGetter.getAllChildNodes(zakladniEntita, vsechnyVecneSkupiny);
                if (CollectionUtils.isEmpty(vecneSkupiny)) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:VecnaSkupina> základní entity.",
                            zakladniEntita, kontrola.getEntityId(zakladniEntita));
                }
                Element prvniVecnaSkupina = vecneSkupiny.get(0);
                Element n_j = ValuesGetter.getXChild(prvniVecnaSkupina, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                        NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
                Element n_p = ValuesGetter.getXChild(prvniVecnaSkupina, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                        NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
                if (n_j == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(zakladniEntita),
                            prvniVecnaSkupina, kontrola.getEntityId(zakladniEntita));
                }
                if (n_p == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(zakladniEntita),
                            prvniVecnaSkupina, kontrola.getEntityId(zakladniEntita));
                }
                jednoduchy = n_j.getTextContent();
                plneUrceny = n_p.getTextContent();
                boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
                if (!b) {
                    List<Element> zakladniEntitaPlusPrvniVecnaSkupina = Arrays.asList(zakladniEntita, prvniVecnaSkupina);
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + getJmenoIdentifikator(zakladniEntita),
                            getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz) + " "
                            + getMistoChyby(n_j) + " " + getMistoChyby(n_p),
                            kontrola.getEntityId(zakladniEntitaPlusPrvniVecnaSkupina));
                }
            }
        }
    }

    private void kontrolaDilu(Element dil,
            Element n_zakl_jsz, Element n_zakl_pusz) {
        String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
        String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

        Element elSoucast = ValuesGetter.getXChild(dil, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                NsessV3.MATERSKA_ENTITA, NsessV3.SOUCAST);
        if (elSoucast == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Soucast>. " + getJmenoIdentifikator(dil),
                    dil, kontrola.getEntityId(dil));
        }
        Element trideniSoucasti = ValuesGetter.getXChild(elSoucast, NsessV3.EVIDENCNI_UDAJE,
                NsessV3.TRIDENI);
        if (trideniSoucasti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Trideni>. " + getJmenoIdentifikator(elSoucast),
                    elSoucast, kontrola.getEntityId(elSoucast));
        }

        Element elJednSpisZnakSoucasti = ValuesGetter.getXChild(trideniSoucasti, NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
        if (elJednSpisZnakSoucasti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(elSoucast),
                    elSoucast, kontrola.getEntityId(elSoucast));
        }
        Element elPlneUrcSpisZnakSoucasti = ValuesGetter.getXChild(trideniSoucasti, NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (elPlneUrcSpisZnakSoucasti == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(elSoucast),
                    elSoucast, kontrola.getEntityId(elSoucast));
        }

        Element elTypovySpis = ValuesGetter.getXChild(trideniSoucasti, NsessV3.MATERSKA_ENTITA, NsessV3.TYPOVY_SPIS);
        if (elTypovySpis == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:TypovySpis>. " + getJmenoIdentifikator(dil),
                    dil, kontrola.getEntityId(dil));
        }
        Element elJednSpisZnakTypovehoSpisu = ValuesGetter.getXChild(elTypovySpis, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
        if (elJednSpisZnakTypovehoSpisu == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(elTypovySpis),
                    elTypovySpis, kontrola.getEntityId(elTypovySpis));
        }
        Element elPlneUrcSpisZnakTypovehoSpisu = ValuesGetter.getXChild(elTypovySpis, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI,
                NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (elPlneUrcSpisZnakTypovehoSpisu == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(elTypovySpis),
                    elTypovySpis, kontrola.getEntityId(elTypovySpis));
        }

        String jednoduchy, plneUrceny;
        jednoduchy = elJednSpisZnakSoucasti.getTextContent();
        plneUrceny = elPlneUrcSpisZnakSoucasti.getTextContent();

        String tyt_p = elPlneUrcSpisZnakTypovehoSpisu.getTextContent();
        String typ_j = elJednSpisZnakTypovehoSpisu.getTextContent();

        boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
        if (!b) {
            List<Element> dilPlusSoucast = Arrays.asList(dil, elSoucast);
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + getJmenoIdentifikator(dil) + " "
                    + getJmenoIdentifikator(elJednSpisZnakSoucasti),
                    getMistoChyby(n_zakl_jsz) + " "
                    + getMistoChyby(n_zakl_pusz) + " " + getMistoChyby(elJednSpisZnakSoucasti) + " "
                    + getMistoChyby(elPlneUrcSpisZnakSoucasti),
                    kontrola.getEntityId(dilPlusSoucast));
        }

        List<Element> vsechnyVecneSkupiny = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
        List<Element> vecneSkupiny = ValuesGetter.getAllChildNodes(dil, vsechnyVecneSkupiny);
        if (vecneSkupiny == null || vecneSkupiny.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:VecnaSkupina> základní entity. "
                    + getJmenoIdentifikator(dil), dil,
                    kontrola.getEntityId(dil));
        }
        Element elVecnaSkupinaDilu = vecneSkupiny.get(vecneSkupiny.size() - 1);
        Element elJednSpisZnakVecneSkupiny = ValuesGetter.getXChild(elVecnaSkupinaDilu, NsessV3.EVIDENCNI_UDAJE,
                NsessV3.TRIDENI, NsessV3.JEDNODUCHY_SPISOVY_ZNAK);
        Element elPlneUrcSpisZnakVecneSkupiny = ValuesGetter.getXChild(elVecnaSkupinaDilu, NsessV3.EVIDENCNI_UDAJE,
                NsessV3.TRIDENI, NsessV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (elJednSpisZnakVecneSkupiny == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(elVecnaSkupinaDilu),
                    elVecnaSkupinaDilu, kontrola.getEntityId(elVecnaSkupinaDilu));
        }
        if (elPlneUrcSpisZnakVecneSkupiny == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(elVecnaSkupinaDilu),
                    elVecnaSkupinaDilu, kontrola.getEntityId(elVecnaSkupinaDilu));
        }
        String jednoduchy_vs = elJednSpisZnakVecneSkupiny.getTextContent();
        String plneUrceny_vs = elPlneUrcSpisZnakVecneSkupiny.getTextContent();
        boolean b1 = jednoduchy_vs.equals(typ_j) && plneUrceny_vs.equals(tyt_p);
        if (!b1) {
            List<Element> dilTypovySpisVecnaSkupina = Arrays.asList(dil, elVecnaSkupinaDilu, elTypovySpis);
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + getJmenoIdentifikator(dil) + " " + getJmenoIdentifikator(elTypovySpis)
                    + " " + getJmenoIdentifikator(elVecnaSkupinaDilu),
                    getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz) + " "
                    + getMistoChyby(elJednSpisZnakTypovehoSpisu) + " " + getMistoChyby(elPlneUrcSpisZnakTypovehoSpisu),
                    kontrola.getEntityId(dilTypovySpisVecnaSkupina));
        }
    }

}
