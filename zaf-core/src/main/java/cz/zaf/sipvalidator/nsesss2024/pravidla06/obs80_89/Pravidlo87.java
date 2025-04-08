package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.NsesssV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

public class Pravidlo87 extends K06PravidloBase {

    static final public String OBS87 = "obs87";

    public Pravidlo87() {
        super(OBS87,
                "Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> je uveden i element <nsesss:DatumOdeslani>.",
                "Chybí příjemce nebo datum odeslání dokumentu.",
                "Příloha č. 2 NSESSS, ř. 1471 a 1481.");
    }

    //OBSAHOVÁ č.87 Pokud existuje element <nsesss:Vyrizeni> a obsahuje dětský element 
    // <nsesss:DatumOdeslani>, pak současně obsahuje i element <nsesss:Prijemce>. 
    // Pravidlo se uplatňuje i obráceně - v případě uvedení elementu <nsesss:Prijemce> 
    // je uveden i element <nsesss:DatumOdeslani>.",
    @Override
    protected void kontrola() {
        List<Element> vyrizeni = metsParser.getNodes(NsesssV3.VYRIZENI);
        for (Element elVyrizeni : vyrizeni) {
            Element datumOdeslani = ValuesGetter.getXChild(elVyrizeni, NsesssV3.DATUM_ODESLANI);
            Element prijemce = ValuesGetter.getXChild(elVyrizeni, NsesssV3.PRIJEMCE);
            Element entita = kontrola.getEntity(elVyrizeni);
            if (datumOdeslani != null && prijemce == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Prijemce>. " + getJmenoIdentifikator(elVyrizeni),
                        elVyrizeni, kontrola.getEntityId(entita));
            }
            if (prijemce != null && datumOdeslani == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:DatumOdeslani>. " + getJmenoIdentifikator(elVyrizeni),
                        elVyrizeni, kontrola.getEntityId(entita));
            }
        }
    }
}
