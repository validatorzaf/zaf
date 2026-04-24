package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs20_29;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import java.text.ParseException;
import java.util.Date;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.w3c.dom.Element;

// OBSAHOVÁ č.28 Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, 
// potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <nsesss:Dil> nebo <nsesss:Spis>. 
// Anebo právě jeden dětský element <nsesss:Dokument>, pokud byl vyřízen do 31. 12. 2026 včetně.
public class Pravidlo28 extends K06PravidloBase {

    static final public String OBS28 = "obs28";

    public Pravidlo28() {
        super(OBS28,
                "Pokud neexistuje žádný element <nsesss:KrizovyOdkaz> s atributem pevny s hodnotou ano, potom element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <nsesss:Dil> nebo <nsesss:Spis>. Anebo právě jeden dětský element <nsesss:Dokument>, pokud byl vyřízen do 31. 12. 2026 včetně.",
                "Datový balíček SIP neobsahuje díl, spis ani dokument.",
                "§ 12 odst. 1 vyhlášky č. 259/2012 Sb.; bod 1.8 přílohy č. 2 NSESSS; příloha č. 2 NSESSS, nsesss.xsd, ř. 14.");
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

        Element elZakladniEntita = zaklEntity.get(0);
        if (elZakladniEntita.getNodeName().equals(NsesssV4.DOKUMENT)) {
            Element elDatum = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI, NsesssV4.DATUM);
            if (elDatum == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Nenalezen element <" + NsesssV4.DATUM + "> pro vyřízení.",
                        getMistoChyby(elZakladniEntita), getEntityId(elZakladniEntita));
            } else {
                String datumVyrizeni = elDatum.getTextContent();
                jePredVcetne(elDatum, datumVyrizeni);
            }
        }

    }

    private static void jePredVcetne(Element elDatum, String dateStr) {
        try {
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            Date limit = DateUtils.parseDate("2026-12-31", "yyyy-MM-dd");
            boolean valid = !date.after(limit);
            if (!valid) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                        "Datum vyřízení Dokumentu je po 31. 12. 2026. Datum vyřízení: " + dateStr + ".", elDatum);
            }

        } catch (ParseException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Datum vyřízení je v nesprávném formátu: " + dateStr + ".", elDatum);
        }
    }
}
