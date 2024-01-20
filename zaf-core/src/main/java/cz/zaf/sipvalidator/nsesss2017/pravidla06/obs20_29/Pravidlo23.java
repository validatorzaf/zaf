package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs20_29;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

import org.w3c.dom.Element;

//OBSAHOVÁ č.23 Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
public class Pravidlo23 extends K06PravidloBase {

    static final public String OBS23 = "obs23";

    public Pravidlo23() {
        super(OBS23,
                "Element <mets:dmdSec> obsahuje v hierarchii dětských elementů <mets:mdWrap> atribut MDTYPEVERSION s hodnotou 3.0.",
                "Uveden je chybně popis schématu XML.", // Uveden je chybně popis původce.
                "Bod 2.7. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Element metsMdWrap = metsParser.getMetsMdWrap();
        if (metsMdWrap == null) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element <mets:mdWrap>.");
        }
        if (!ValuesGetter.hasAttribut(metsMdWrap, "MDTYPEVERSION")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:mdWrap> neobsahuje atribut MDTYPEVERSION.", metsMdWrap);
        }
        String hodnotaAtr = ValuesGetter.getValueOfAttribut(metsMdWrap, "MDTYPEVERSION");
        if (StringUtils.isBlank(hodnotaAtr)) {
            nastavChybu(BaseCode.CHYBI_HODNOTA_ATRIBUTU,
                    "Atribut MDTYPEVERSION elementu <mets:mdWrap> má prázdnou hodnotu.", metsMdWrap);
        }
        
        if (!"3.0".equals(hodnotaAtr)) {    
            nastavChybu(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut MDTYPEVERSION elementu <mets:mdWrap> neobsahuje hodnotu 3.0.", getMistoChyby(metsMdWrap));
        }
    }

}
