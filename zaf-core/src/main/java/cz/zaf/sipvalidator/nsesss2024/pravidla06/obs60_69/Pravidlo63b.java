package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs60_69;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import static cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase.getMistoChyby;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

public class Pravidlo63b extends K06PravidloBase {

    static final public String OBS63B = "obs63b";

    public Pravidlo63b() {
        super(OBS63B,
                "Pokud je základní entitou spis (nsesss:Spis), potom v hierarchii dětských elementů nsesss:EvidencniUdaje, nsesss:VyrizeniUzavreni je hodnota roku v elementu nsesss:Datum nižší než aktuální rok.",
                "Chybí odůvodnění vyřízení jiným způsobem.",
                "§ 15 odst. 3 vyhlášky č. 259/2012 Sb.");
    }

    //OBSAHOVÁ č.63b Pokud je základní entitou spis (nsesss:Spis), potom v hierarchii dětských elementů nsesss:EvidencniUdaje, nsesss:VyrizeniUzavreni je hodnota 
    // roku v elementu nsesss:Datum nižší než aktuální rok.
    @Override
    protected void kontrola() {
        List<Element> zakladniEntity = metsParser.getZakladniEntity();
        int aktualniRok = this.kontrola.getLocalDate().getYear();
        if (!CollectionUtils.isEmpty(zakladniEntity)) {
            for (Element elZaklEntita : zakladniEntity) {
                String zaklEntName = elZaklEntita.getNodeName();
                if (zaklEntName.equals(NsesssV4.SPIS)) {
                    Element elDatum = ValuesGetter.getXChild(elZaklEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.VYRIZENI_UZAVRENI, NsesssV4.DATUM);
                    if (elDatum == null) {
                        nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Datum>.", getMistoChyby(elZaklEntita), getEntityId(elZaklEntita));
                    } else {
                        String datumStr = elDatum.getTextContent();
                        if (!jeNizsiNez(elDatum, datumStr, aktualniRok)) {
                            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:Datum> obsahuje datum vyšší než aktuální rok.", getMistoChyby(elDatum), getEntityId(elZaklEntita));
                        }
                    }
                }
            }
        }
    }

    private static boolean jeNizsiNez(Element elDatum, String dateStr, int aktualniRok) {
        boolean valid;
        try {
            Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd");
            Date limit = DateUtils.parseDate(aktualniRok + "-01-01", "yyyy-MM-dd");
            valid = date.before(limit);
            return valid;

        } catch (ParseException e) {
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Datum vyřízení je v nesprávném formátu: " + dateStr + ".", elDatum);
        }
        return false;
    }

}
