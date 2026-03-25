package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

// OBSAHOVÁ č.98a Pokud jakýkoli element <nsesss:Komponenta> obsahuje atribut forma_uchovani s hodnotou digitalizát, 
// potom rodičovská entita dokument obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> 
// element <nsesss:AnalogovyDokument> s hodnotou ano.
public class Pravidlo98a extends K06PravidloBase {

    public static final String OBS98A = "obs98a";

    public Pravidlo98a() {
        super(OBS98A,
                "Pokud jakýkoli element <nsesss:Komponenta> obsahuje atribut forma_uchovani s hodnotou digitalizát, potom rodičovská entita dokument obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano.",
                "Uveden je chybně digitalizát dokumentu.",
                "Příloha č. 2 NSESSS, nsesss-common.xsd, ř. 114; Informační list NA, čá. 5/2019, č. 15/2019.");
    }

    @Override
    protected void kontrola() {
        List<Element> listKomponent = metsParser.getElements(NsesssV4.KOMPONENTA);
        for (Element elKomponenta : listKomponent) {
            String atrFormaUchovani = elKomponenta.getAttribute("forma_uchovani");
            if (StringUtils.equals(atrFormaUchovani, "digitalizát")) {
                Element elDokument = ValuesGetter.getXParent(elKomponenta, NsesssV4.KOMPONENTY, NsesssV4.DOKUMENT);
                Element elAnalogovyDokument = ValuesGetter.getXChild(elDokument, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.MANIPULACE, NsesssV4.ANALOGOVY_DOKUMENT);
                if (elAnalogovyDokument == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:AnalogovyDokument>.", getMistoChyby(elDokument), kontrola.getEntityId(elDokument));
                }
                String strAnDok = elAnalogovyDokument.getTextContent();
                if (!StringUtils.equals(strAnDok, "ano")) {
                    nastavChybu(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element <nsesss:AnalogovyDokument> neobsahuje hodnotu ano, ale hodnotu:" + strAnDok + ".", getMistoChyby(elDokument), kontrola.getEntityId(elDokument));
                }
            }
        }
    }

}
