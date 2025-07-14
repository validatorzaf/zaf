package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.Arrays;

// OBSAHOVÁ č.98
// Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>),
// obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>,
// <nsesss:Trideni>
// elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se
// stejnými hodnotami,
// jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>,
// <nsesss:Trideni> elementy
// <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli
// dětské entity dokument
// (<nsesss:Dokument>).",
public class Pravidlo98 extends K06PravidloBase {

    public static final String OBS98 = "obs98";

    public Pravidlo98() {
        super(OBS98,
                "Pokud je základní entitou díl (<nsesss:Dil>) nebo spis (<nsesss:Spis>), obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami, jaké obsahují v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> jakékoli dětské entity dokument (<nsesss:Dokument>).",
                "Chybně jsou uvedeny spisové znaky.",
                "§ 14 odst. 4 vyhlášky č. 259/2012 Sb.");
    }

    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }
        List<Element> dokumenty = predpokladDokumenty();
        if (dokumenty == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element <nsesss:Dokument>.");
        }

        for (int i = 0; i < zakladniEntity.size(); i++) {
            Element zakladnientita = zakladniEntity.get(i);
            if (zakladnientita.getNodeName().equals(NsesssV3.DIL)
                    || zakladnientita.getNodeName().equals(NsesssV3.SPIS)) {
                kontrolaEntity(zakladnientita, dokumenty);
            }
        }
    }

    private void kontrolaEntity(Element zakladniEntita, List<Element> dokumenty) {
        Element n_zakl_jsz = ValuesGetter.getXChild(zakladniEntita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                NsesssV3.JEDNODUCHY_SPISOVY_ZNAK);
        if (n_zakl_jsz == null) {
            String detailChyby = "Nenalezen element <nsesss:JednoduchySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladniEntita);
            nastavChybu(BaseCode.CHYBI_ELEMENT, detailChyby, getMistoChyby(zakladniEntita),
                    kontrola.getEntityId(zakladniEntita));
        }
        Element n_zakl_pusz = ValuesGetter.getXChild(zakladniEntita, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
        if (n_zakl_pusz == null) {
            String detailChyby = "Nenalezen element <nsesss:PlneUrcenySpisovyZnak> základní entity. "
                    + kontrola.getJmenoIdentifikator(zakladniEntita);
            nastavChybu(BaseCode.CHYBI_ELEMENT, detailChyby, getMistoChyby(zakladniEntita),
                    kontrola.getEntityId(zakladniEntita));
        }
        String jednoduchySpZnZaklEnt = n_zakl_jsz.getTextContent();
        String plneUrcenySpZnZaklEnt = n_zakl_pusz.getTextContent();

        String jednoduchy, plneUrceny;

        for (int j = 0; j < dokumenty.size(); j++) {
            Element dokument = dokumenty.get(j);
            Element n_j = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                    NsesssV3.JEDNODUCHY_SPISOVY_ZNAK);
            if (n_j == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument),
                        getMistoChyby(dokument), kontrola.getEntityId(dokument));
            }
            jednoduchy = n_j.getTextContent();
            Element n_p = ValuesGetter.getXChild(dokument, NsesssV3.EVIDENCNI_UDAJE, NsesssV3.TRIDENI,
                    NsesssV3.PLNE_URCENY_SPISOVY_ZNAK);
            if (n_p == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. "
                        + kontrola.getJmenoIdentifikator(dokument), getMistoChyby(dokument),
                        kontrola.getEntityId(dokument));
            }

            plneUrceny = n_p.getTextContent();

            boolean b = jednoduchySpZnZaklEnt.equals(jednoduchy) && plneUrcenySpZnZaklEnt.equals(plneUrceny);
            if (!b) {
                List<Element> zakladniEntitaPlusDokument = Arrays.asList(zakladniEntita, dokument);
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Nesplněna podmínka pravidla. " + kontrola.getJmenoIdentifikator(zakladniEntita)
                        + " " + kontrola.getJmenoIdentifikator(dokument),
                        getMistoChyby(n_zakl_jsz) + " " + getMistoChyby(n_zakl_pusz)
                        + " " + getMistoChyby(n_j) + " " + getMistoChyby(n_p),
                        kontrola.getEntityId(zakladniEntitaPlusDokument));
            }
        }
    }

}
