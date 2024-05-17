package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.37 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
public class Pravidlo37 extends K06PravidloBase {

    static final public String OBS37 = "obs37";

    public Pravidlo37() {
        super(OBS37,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MIMETYPE s hodnotou text/xml.",
                "Uveden je chybně popis schématu XML.",
                "Bod 2.11. přílohy č. 3 NSESSS.");
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
            String hodnotaElMimeType = mdWrap.getAttribute("MIMETYPE");
            if (StringUtils.isBlank(hodnotaElMimeType)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:mdWrap> neobsahuje atribut MIMETYPE.", mdWrap);
            }
            if (!"text/xml".equals(hodnotaElMimeType)) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut MIMETYPE neobsahuje hodnotu text/xml.", mdWrap);
            }
        }
    }

}
