package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

//
// OBSAHOVÁ č.66 Každá entita Díl (<nsesss:Dokument>), Spis (<nsesss:Dokument>) a Dokument (<nsesss:Dokument>) obsahuje v hierarchii dětských elementů 
// <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> element <nsesss:SkartacniZnak> s hodnotou A nebo S, 
// pokud byla daná entita  vyřízena/uzavřena po 31. 12. 2026.
//
public class Pravidlo66 extends K06PravidloBase {

    static final public String OBS66 = "obs66";

    public Pravidlo66() {
        super(OBS66,
                "Každá entita Díl (<nsesss:Dokument>), Spis (<nsesss:Dokument>) a Dokument (<nsesss:Dokument>) obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Vyrazovani>, <nsesss:SkartacniRezim> element <nsesss:SkartacniZnak> s hodnotou A nebo S, pokud byla daná entita  vyřízena/uzavřena po 31. 12. 2026.",
                "Uveden je chybně skartační znak \"V\".",
                "§ 15 odst. 2 vyhlášky č. 259/2012 Sb.; požadavek 6.1.5 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> listVsech = metsParser.getEntity(NsesssV4.DIL, NsesssV4.SPIS, NsesssV4.DOKUMENT);
        for (Element entita : listVsech) {
            String skartacniZank = getSkartacniZnak(entita);
            if (!(skartacniZank.equals("A") || skartacniZank.equals("S"))) {
                Element elDatum = getDatumVyrizeniUzavreni(entita);
                if (elDatum != null) {
                    String datumVyrizeniUzavreni = elDatum.getTextContent();
                    if ((jeVyrizenaPo(elDatum, datumVyrizeniUzavreni) && skartacniZank.equals("V"))) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:SkartacniZnak> obsahuje nepovolenou hodnotu: " + skartacniZank + ".", getMistoChyby(entita), kontrola.getEntityId(entita));
                    }
                } else {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:SkartacniZnak> obsahuje nepovolenou hodnotu: " + skartacniZank + ".", getMistoChyby(entita), kontrola.getEntityId(entita));
                }
            }
        }
    }

    private String getSkartacniZnak(Element entita) {
        Element elSkartacniZnak = ValuesGetter.getXChild(entita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRAZOVANI, NsesssV4.SKARTACNI_REZIM, NsesssV4.SKARTACNI_ZNAK);
        if (elSkartacniZnak == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:SkartacniZnak>.", getMistoChyby(entita), kontrola.getEntityId(entita));
        }
        return elSkartacniZnak.getTextContent();
    }

    private Element getDatumVyrizeniUzavreni(Element elEntita) {
        Element elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI, NsesssV4.DATUM);
        if (elDatum == null) {
            elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI_UZAVRENI, NsesssV4.DATUM);
        }
        if (elDatum == null) {
            elDatum = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.UZAVRENI, NsesssV4.DATUM);
        }
        if (elDatum == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>.", getMistoChyby(elEntita), kontrola.getEntityId(elEntita));
        }

        return elDatum;
    }

    private static boolean jeVyrizenaPo(Element elDatum, String dateStr) {
        boolean valid = false;
        try {
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            Date limit = DateUtils.parseDate("2026-12-31", "yyyy-MM-dd");
            valid = date.after(limit);

        } catch (ParseException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Datum vyřízení je v nesprávném formátu: " + dateStr + ".", elDatum);
        }
        return valid;
    }
}
