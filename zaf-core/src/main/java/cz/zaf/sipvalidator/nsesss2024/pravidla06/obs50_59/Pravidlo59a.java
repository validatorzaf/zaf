package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs50_59;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class Pravidlo59a extends K06PravidloBase {

    static final public String OBS59A = "obs59a";

    public Pravidlo59a() {
        super(OBS59A,
                "Každá entita dokument (<nsesss:Dokument>), nebo pokud existuje jakýkoli element <nsesss:Spis>, který obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:VyrizeniUzavreni> element <nsesss:Datum> s hodnotou větší než 31. 7. 2012, obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Evidence> element <nsesss:EvidencniCislo>.",
                "Chybí evidenční číslo spisu nebo dokumentu.",
                "Příloha č. 2 NSESSS, nsesss-common.xsd, ř. 411.");
    }

    //OBSAHOVÁ č.59a Každá entita dokument (<nsesss:Dokument>), nebo pokud existuje jakýkoli element <nsesss:Spis>, který obsahuje v hierarchii dětských elementů 
    //<nsesss:EvidencniUdaje>, <nsesss:VyrizeniUzavreni> element <nsesss:Datum> s hodnotou větší než 31. 7. 2012, 
    //obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Evidence> element <nsesss:EvidencniCislo>.
    @Override
    protected void kontrola() {
        List<Element> dokumenty = metsParser.getDokumenty();
        List<Element> spisy = metsParser.getSpisy();
        List<Element> dohromady = new ArrayList<>();
        dohromady.addAll(dokumenty);
        dohromady.addAll(spisy);
        if (!dohromady.isEmpty()) {
            for (Element entita : dohromady) {
                Element elDatum = getDatum(entita);
                if (elDatum != null) {
                    String datum = elDatum.getTextContent();
                    if (jeStarsiNez(elDatum, datum)) {
                        Element elEvidenciJednotka = ValuesGetter.getXChild(entita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.EVIDENCE, NsesssV4.EVIDENCNI_CISLO);
                        if (elEvidenciJednotka == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:EvidencniCislo>.", getMistoChyby(entita), getEntityId(entita));
                        }
                    }
                }
            }
        }
    }

    private Element getDatum(Element elZakladniEntita) {
        Element elDatum = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI, NsesssV4.DATUM);
        if (elDatum == null) {
            elDatum = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI_UZAVRENI, NsesssV4.DATUM);
        }
        if (elDatum == null) {
            elDatum = ValuesGetter.getXChild(elZakladniEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.UZAVRENI, NsesssV4.DATUM);
        }

        return elDatum;
    }

    private static boolean jeStarsiNez(Element elDatum, String dateStr) {
        boolean valid;
        try {
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            Date limit = DateUtils.parseDate("2012-07-31", "yyyy-MM-dd");
            valid = date.after(limit);
            return valid;

        } catch (ParseException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Datum vyřízení je v nesprávném formátu: " + dateStr + ".", elDatum);
        }
        return false;
    }

}
