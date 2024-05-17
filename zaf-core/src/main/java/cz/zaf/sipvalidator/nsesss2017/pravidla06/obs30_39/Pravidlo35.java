package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

/**
 * OBSAHOVÁ č.35 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů &lt;mets:mdWrap&gt; atribut OTHERMDTYPE s hodnotou TP.
 *
 */
public class Pravidlo35 extends K06PravidloBase {

    static final public String OBS35 = "obs35";

    public Pravidlo35() {
        super(OBS35,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut OTHERMDTYPE s hodnotou TP.",
                "Uveden je chybně popis schématu XML.",
                "Bod 2.11. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElDigiProMd = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if (listElDigiProMd.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:digiprovMD>.");
        }
        for (Element digiprovMD : listElDigiProMd) {
            Element mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if (mdWrap == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }

            String hodnotaAtrOtherMdType = mdWrap.getAttribute("OTHERMDTYPE");
            if (StringUtils.isBlank(hodnotaAtrOtherMdType)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:mdWrap> neobsahuje atribut OTHERMDTYPE.", mdWrap);
            }

            if (!"TP".equals(hodnotaAtrOtherMdType)) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut OTHERMDTYPE neobsahuje hodnotu TP.", getMistoChyby(mdWrap));
            }
        }
    }

}
