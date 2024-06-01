package cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

// Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd
// nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.
public class Pravidlo2 extends PravidloBase {

    static final public String KOD = "ns2";

    public Pravidlo2() {
        super(KOD,
                "Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.",
                "Popsáno je chybně umístění příslušných schémat XML.",
                "Bod 2.1. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        String detailChyby = null;

        Node metsMets = this.ctx.getContext().getMetsParser().getMetsRootNode();
        if (metsMets == null) {
            detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
            throw new ZafException(BaseCode.CHYBA, detailChyby);
        } else {
            if (ValuesGetter.hasAttribut(metsMets, "xsi:schemaLocation")) 
            {
                String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xsi:schemaLocation");
                if (!hodnota.equals(
                                    "http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd")
                        &&
                        !hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd")) 
                {
                	detailChyby = "Schéma v souboru SIP je špatně uvedeno, hodnota: " + hodnota;
                    throw new ZafException(BaseCode.CHYBA, detailChyby);
                } 
            } else {
                detailChyby = "Kořenový element <mets:mets> neobsahuje atribut \"xsi:schemaLocation\".";
                throw new ZafException(BaseCode.CHYBA, detailChyby);
            }
        }
    }

}
