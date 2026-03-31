package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs80_89;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import org.apache.commons.collections4.CollectionUtils;

public class Pravidlo86 extends K06PravidloBase {

    static final public String OBS86 = "obs86";

    public Pravidlo86() {
        super(OBS86,
                "Element <nsesss:MaterskeEntity> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> základní entity dokument (<nsesss:Dokument>).",
                "Uvedeno je chybně zatřídění dokumentu.",
                "Příloha č. 2 NSESSS, nsesss-common.xsd, ř. 1490.");
    }

    //OBSAHOVÁ č.86 Element <nsesss:MaterskeEntity> je uveden pouze v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> 
    //základní entity dokument (<nsesss:Dokument>).
    @Override
    protected void kontrola() {

        List<Element> listMatEntity = metsParser.getElements(NsesssV4.MATERSKE_ENTITY);
        if (!CollectionUtils.isEmpty(listMatEntity)) {
            for (Element elMatEntity : listMatEntity) {
                Element elDokument = ValuesGetter.getXParent(elMatEntity, NsesssV4.TRIDENI, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.DOKUMENT);
                if (elDokument == null) {
                    nastavChybu(BaseCode.CHYBI_ELEMENT, "Element <nsesss:MaterskeEntity> je chybně zatříděn.", getMistoChyby(elMatEntity));
                } else {
                    if (!jeZakladniEntitou(elDokument)) {
                        nastavChybu(BaseCode.CHYBNY_ELEMENT, "Element <nsesss:MaterskeEntity> je obsažen v elementu <nsess:Dokument> jenž není základní entitou. " + getJmenoIdentifikator(elDokument),
                                getMistoChyby(elDokument), kontrola.getEntityId(elDokument));
                    }
                }
            }
        }
    }

    private boolean jeZakladniEntitou(Element elDokument) {
        return ValuesGetter.isXParent(elDokument, "mets:xmlData");
    }

}
