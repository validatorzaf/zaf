package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

/**
 * OBSAHOVÁ č.34 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů &lt;mets:mdWrap&gt; atribut MDTYPEVERSION s hodnotou 1.0.
 *
 */
public class Pravidlo34 extends K06PravidloBase {
    
    static final public String OBS34 = "obs34";
    
    public Pravidlo34() {
        super(OBS34,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 1.0.",
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
            String hodnotaAtrMdtypeversion = ValuesGetter.getValueOfAttribut(mdWrap, "MDTYPEVERSION");
            if (StringUtils.isBlank(hodnotaAtrMdtypeversion)) {
                nastavChybu(BaseCode.CHYBI_ATRIBUT,
                        "Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", mdWrap);
            }
            
            if (!("1.0").equals(hodnotaAtrMdtypeversion)) {
                nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                        "Atribut MDTYPEVERSION neobsahuje hodnotu 1.0.", mdWrap);
            }
        }
    }
    
}
