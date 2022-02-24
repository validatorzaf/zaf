package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.39 Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
public class Pravidlo39 extends K06PravidloBase {

    static final public String OBS39 = "obs39";

    public Pravidlo39() {
        super(OBS39,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap>, <mets:xmlData> právě jeden dětský element <tp:TransakcniLogObjektu>.",
                "Datový balíček SIP neobsahuje transakční protokol.",
                "Bod 2.12. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        List<Element> listElDigiProMd = metsParser.getNodes(MetsElements.DIGIPROV_MD);
        if (listElDigiProMd.isEmpty()) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen žádný element <mets:digiprovMD>.");
        }

        for (Element digiprovMD : listElDigiProMd) {
            Element mdWr = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if (mdWr == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }
            Element elXmlData = ValuesGetter.getXChild(mdWr, "mets:xmlData");
            if (elXmlData == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", mdWr);
            }

            List<Element> listElTrLogObj = ValuesGetter.getChildNodes(elXmlData, "tp:TransakcniLogObjektu");
            int size = listElTrLogObj.size();
            if (size == 0) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:xmlData> neobsahuje žádný dětský element <tp:TransakcniLogObjektu>.", elXmlData);
            }
            if (size > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:xmlData> obsahuje více dětských elementů <tp:TransakcniLogObjektu>.", elXmlData);
            }
        }
    }

}
