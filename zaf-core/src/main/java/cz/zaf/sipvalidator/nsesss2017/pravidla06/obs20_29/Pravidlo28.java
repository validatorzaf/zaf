package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Element;

// OBSAHOVÁ č.28 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s
// atributem pevny s hodnotou ano,
// potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů
// <mets:mdWrap>, <mets:xmlData> právě jeden dětský element
// <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.
public class Pravidlo28 extends K06PravidloBase {

    static final public String OBS28 = "obs28";

    public Pravidlo28() {
        super(OBS28,
                "Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <nsesss:Dil>, <nsesss:Dokument> nebo <nsesss:Spis>.",
                "Datový balíček SIP neobsahuje díl, spis ani dokument.",
                "Bod 2.7. přílohy č. 3 NSESSS; příloha č. 2 NSESSS, ř. 20.");
    }

    @Override
    protected void kontrola() {
        List<Element> pevneKrizoveOdkazy = metsParser.getKrizoveOdkazyPevnyAno();

        if (!pevneKrizoveOdkazy.isEmpty()) {
            return;
        }

        List<Element> zaklEntityChybne = metsParser.getZakladniEntityChybne();
        if (CollectionUtils.isNotEmpty(zaklEntityChybne)) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Element <mets:xmlData> obsahuje nepovolené dětské elementy.", zaklEntityChybne);
        }

        List<Element> zaklEntity = metsParser.getZakladniEntity();
        if (CollectionUtils.isEmpty(zaklEntity)) {
            Element xmlData = metsParser.getMetsXmlData();
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <mets:xmlData> neobsahuje žádné dětské elementy.", xmlData);
        }
        if (zaklEntity.size() > 1) {
            nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                    "Element <mets:xmlData> obsahuje více dětských elementů.", zaklEntity);
        }
    }

}
