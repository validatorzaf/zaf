package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs90_99;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.NsesssV4;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;
import org.apache.commons.lang3.StringUtils;

/**
 * OBSAHOVÁ 93a. Každá entita obsahuje element <JednoduchySpisovyZnak> s
 * neprázdnou hodnotou.
 *
 */
public class Pravidlo93a extends K06PravidloBase {

    static final public String OBS93A = "obs93a";

    public Pravidlo93a() {
        super(OBS93A,
                "Každá entita obsahuje element <JednoduchySpisovyZnak> s neprázdnou hodnotou.",
                "Není uveden jednoduchý spisový znak.",
                "Příloha č. 2 NSESSS, nsesss-common.xsd, ř. 146.");
    }

    @Override
    protected void kontrola() {
        List<Element> listEntit = metsParser.getEntity(NsesssV4.DIL, NsesssV4.SPIS, NsesssV4.DOKUMENT, NsesssV4.VECNA_SKUPINA, NsesssV4.SOUCAST, NsesssV4.TYPOVY_SPIS);
        for (Element elentita : listEntit) {
            Element elJsZ = ValuesGetter.getXChild(elentita, NsesssV4.EVIDENCNI_UDAJE, NsesssV4.TRIDENI, NsesssV4.JEDNODUCHY_SPISOVY_ZNAK);
            if (elJsZ == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT, "Nenalezen element <nsesss:JednoduchySpisovyZnak>.", getMistoChyby(elentita), getEntityId(elentita));
            }
            String jsz = elJsZ.getTextContent();
            if (StringUtils.isBlank(jsz)) {
                nastavChybu(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Element <nsesss:JednoduchySpisovyZnak> je prázdný.", getMistoChyby(elentita), getEntityId(elentita));
            }
        }

    }

}
