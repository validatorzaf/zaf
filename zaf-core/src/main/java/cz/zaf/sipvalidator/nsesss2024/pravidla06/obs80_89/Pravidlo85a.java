package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

public class Pravidlo85a extends K06PravidloBase {

    static final public String OBS85A = "obs85a";

    public Pravidlo85a() {
        super(OBS85A,
                "Pokud jakýkoli element <nsesss:Dil> nebo <nsesss:Spis> obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:Umisteni> s neprázdnou hodnotou.",
                "Chybí fyzické umístění dokumentů v analogově podobě.",
                "§ 12 odst. 6 písm. i) vyhlášky č. 259/2012 Sb.; požadavek 3.3.20, 5.2.6, a 7.3.4 NSESSS.");
    }

    //OBSAHOVÁ č.85a Pokud jakýkoli element <nsesss:Dil> nebo <nsesss:Spis> obsahuje v hierarchii dětských elementů 
    // <nsesss:EvidencniUdaje>, <nsesss:Manipulace> element <nsesss:AnalogovyDokument> s hodnotou ano, 
    // potom element <nsesss:Manipulace> obsahuje dětský element <nsesss:Umisteni> s neprázdnou hodnotou.
    
    @Override
    public void kontrola() {
        List<Element> listEntit = metsParser.getEntity(NsesssV4.SPIS, NsesssV4.DIL);
        for (Element elEntita : listEntit) {
            Element elManipulace = ValuesGetter.getXChild(elEntita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.MANIPULACE);
            if (elManipulace != null) {
                Element elAnalogovyDokument = ValuesGetter.getXChild(elManipulace, NsesssV4.ANALOGOVY_DOKUMENT);
                if (elAnalogovyDokument != null) {
                    String strAnalDok = elAnalogovyDokument.getTextContent();
                    if (StringUtils.equals(strAnalDok, "ano")) {
                        Element elUmisteni = ValuesGetter.getXChild(elManipulace, NsesssV4.UMISTENI);
                        if (elUmisteni == null) {
                            nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:Umisteni>.", getMistoChyby(elManipulace), getEntityId(elEntita));
                        }
                        String strUmisteni = elUmisteni.getTextContent();
                        if (StringUtils.isBlank(strUmisteni)) {
                            nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:Umisteni> není vyplněn.", getMistoChyby(elUmisteni), getEntityId(elEntita));
                        }
                    }
                }
            }
        }
    }
}
