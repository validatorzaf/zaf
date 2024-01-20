package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.33 Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
public class Pravidlo33 extends K06PravidloBase {

    static final public String OBS33 = "obs33";

    public Pravidlo33() {
        super(OBS33,
                "Každý element <mets:digiprovMD> obsahuje právě jeden dětský element <mets:mdWrap>.",
                "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
                "Bod 2.11. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        List<Element> listElDigiproMD = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if (listElDigiproMD.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:digiprovMD>.");
        }
        for (Element digiprovMD : listElDigiproMD) {
            List<Element> listElMdWrap = ValuesGetter.getChildNodes(digiprovMD, "mets:mdWrap");
            int size = listElMdWrap.size();
            if (size == 0) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }
            if (size > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:digiprovMD> obsahuje více dětských elementů <mets:mdWrap>.", digiprovMD);
            }
        }
    }

}
