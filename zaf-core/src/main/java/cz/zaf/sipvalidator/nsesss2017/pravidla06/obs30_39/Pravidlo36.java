package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ č.36 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů &lt;mets:mdWrap&gt; atribut MDTYPE s hodnotou OTHER.
 *
 */
public class Pravidlo36 extends K06PravidloBase {

    static final public String OBS36 = "obs36";

    public Pravidlo36() {
        super(OBS36,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPE s hodnotou OTHER.",
                "Uveden je chybně popis schématu XML.",
                "Bod 2.11. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElDigiProMD = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if (listElDigiProMD.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:digiprovMD>.");
        }
        for (Element digiprovMD : listElDigiProMD) {
            Element mdWrap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if (mdWrap == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }

            String hodnotaElMdType = mdWrap.getAttribute("MDTYPE");
            if (StringUtils.isBlank(hodnotaElMdType)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:mdWrap> neobsahuje atribut MDTYPE.", mdWrap);
            }
            if (!"OTHER".equals(hodnotaElMdType)) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut MDTYPE neobsahuje hodnotu OTHER.", mdWrap);
            }
        }
    }

}
