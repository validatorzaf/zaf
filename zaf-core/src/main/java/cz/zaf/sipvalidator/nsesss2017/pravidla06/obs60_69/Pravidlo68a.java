package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import java.util.ArrayList;

// Každá základní entita díl (<nsesss:Dil>), spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>) obsahuje 
// v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> element <nsesss:SkartacniZnak> s hodnotou, 
// která je rovna hodnotě stejného elementu rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).
public class Pravidlo68a extends K06PravidloBase {

    static final public String OBS68A = "obs68a";

    public Pravidlo68a() {
        super(OBS68A,
                "Každá základní entita díl (<nsesss:Dil>), spis (<nsesss:Spis>) nebo dokument (<nsesss:Dokument>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> element <nsesss:SkartacniZnak> s hodnotou, která je rovna hodnotě stejného elementu rodičovské entity věcná skupina (<nsesss:VecnaSkupina>) nebo součást (<nsesss:Soucast>).",
                "Skartační znak dílu, spisu nebo dokumentu není v souladu s věcnou skupinou nebo součástí, do které je základní entita zatříděn.",
                "Požadavek 6.1.6. a 6.1.16. NSESSS.");
    }

    @Override
    protected void kontrola() {

        List<Element> zakladniEntitity = metsParser.getZakladniEntity();
        for (Element elZakladnientita : zakladniEntitity) {
            String zakladniEntititaSkartacniZnak = getSkartacniZnak(elZakladnientita);

            Element elNadrazenaEntita = getElNadrazenaEntita(elZakladnientita);
            String nadrazenaEntitaSkartacniZnak = getSkartacniZnak(elNadrazenaEntita);

            if (!zakladniEntititaSkartacniZnak.equals(nadrazenaEntitaSkartacniZnak)) {
                List<Element> chybne = new ArrayList<>();
                chybne.add(elZakladnientita);
                chybne.add(elNadrazenaEntita);
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Skartační znaky entit nejsou stejné.", chybne, kontrola.getEntityId(chybne));
            }
        }
    }

    private String getSkartacniZnak(Element elEntita) {
        Element elSkartacniZnak = ValuesGetter.getXChild(elEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI, NsessV3.SKARTACNI_REZIM, NsessV3.SKARTACNI_ZNAK);
        if (elSkartacniZnak == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniZnak> základní entity.", elEntita, kontrola.getEntityId(elEntita));
        }
        return elSkartacniZnak.getTextContent();
    }

    private Element getElNadrazenaEntita(Element elZakladniEntita) {
        Element elMaterskaEntita = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI, NsessV3.MATERSKA_ENTITA);
        if (elMaterskaEntita == null) {
            elMaterskaEntita = ValuesGetter.getXChild(elZakladniEntita, NsessV3.EVIDENCNI_UDAJE, NsessV3.TRIDENI, NsessV3.MATERSKE_ENTITY);
        }
        if (elMaterskaEntita == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena nadřazená entita základní entity.", elZakladniEntita, kontrola.getEntityId(elZakladniEntita));
        }
        Element elNadrazenaEntita = ValuesGetter.getChildNodes(elMaterskaEntita).get(0);
        return elNadrazenaEntita;
    }
}
