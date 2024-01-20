package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs60_69;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo68 extends K06PravidloBase {

    static final public String OBS68 = "obs68";

    public Pravidlo68() {
        super(OBS68,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.",
                "Chybí skartační režim věcné skupiny.",
                "§ 15 odst. 2 vyhlášky č. 259/2012 Sb.; příloha č. 2 NSESSS, ř. 1250.");
    }

    //OBSAHOVÁ č.68 Každá entita věcná skupina (<nsesss:VecnaSkupina>), která je rodičovskou entitou spisu (<nsesss:Spis>) nebo dokumentu (<nsesss:Dokument>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani> element <nsesss:SkartacniRezim>.
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = predpokladZakladniEntity();
        if (zakladniEntity == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena žádná základní entita.");
        }

        for (Element zakladnientita : zakladniEntity) {
            String nazevZakladniEntity = zakladnientita.getNodeName();
            Element vecnaskupina = null;
            if (nazevZakladniEntity.equals(NsessV3.SPIS) || nazevZakladniEntity.equals(NsessV3.DOKUMENT)) {
                if (nazevZakladniEntity.equals(NsessV3.DOKUMENT)) {
                    vecnaskupina = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE,
                            NsessV3.TRIDENI, NsessV3.MATERSKE_ENTITY,
                            NsessV3.VECNA_SKUPINA);
                } else {
                    vecnaskupina = ValuesGetter.getXChild(zakladnientita, NsessV3.EVIDENCNI_UDAJE,
                            NsessV3.TRIDENI, NsessV3.MATERSKA_ENTITA,
                            NsessV3.VECNA_SKUPINA);
                }

                if (vecnaskupina == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezena rodičovská entita věcná skupina základní entity. "
                            + getJmenoIdentifikator(zakladnientita), zakladnientita, kontrola.getEntityId(zakladnientita));
                }
                Element sr = ValuesGetter.getXChild(vecnaskupina, NsessV3.EVIDENCNI_UDAJE, NsessV3.VYRAZOVANI,
                        NsessV3.SKARTACNI_REZIM);
                if (sr == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniRezim>. " + getJmenoIdentifikator(vecnaskupina),
                            vecnaskupina, kontrola.getEntityId(zakladnientita));
                }
            }
        }
    }
}
