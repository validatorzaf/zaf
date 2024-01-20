package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs30_39;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import java.util.List;

import org.w3c.dom.Element;

import cz.zaf.sipvalidator.mets.MetsElements;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla06.K06PravidloBase;

/**
 * OBSAHOVÁ č.38 Každý element &lt;mets:digiprovMD&gt; obsahuje v hierarchii
 * dětských elementů &lt;mets:mdWrap&gt; právě jeden dětský element
 * &lt;mets:xmlData&gt;.
 *
 *
 */
public class Pravidlo38 extends K06PravidloBase {

    static final public String OBS38 = "obs38";

    public Pravidlo38() {
        super(OBS38,
                "Každý element <mets:digiprovMD> obsahuje v hierarchii dětských elementů <mets:mdWrap> právě jeden dětský element <mets:xmlData>.",
                "Chybí povinná část (transakční protokol) struktury datového balíčku SIP.",
                "Bod 2.12. přílohy č. 3 NSESSS."
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
            Element mdWprap = ValuesGetter.getXChild(digiprovMD, "mets:mdWrap");
            if (mdWprap == null) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:digiprovMD> neobsahuje dětský element <mets:mdWrap>.", digiprovMD);
            }

            List<Element> listElXmlData = ValuesGetter.getChildNodes(mdWprap, "mets:xmlData");
            int size = listElXmlData.size();

            if (size == 0) {
                nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Element <mets:mdWrap> neobsahuje žádný dětský element <mets:xmlData>.", mdWprap);
            }
            if (size > 1) {
                nastavChybu(BaseCode.NEPOVOLENY_ELEMENT,
                        "Element <mets:mdWrap> obsahuje více dětských elementů <mets:xmlData>.", mdWprap);
            }
        }
    }

}
