package cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.NsCheckRuleBase;
import cz.zaf.sipvalidator.sip.SipInfo;

// Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd
// nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.
public class Pravidlo2 extends NsCheckRuleBase {

    static final public String KOD = "ns2";

    public Pravidlo2() {
        super(KOD,
                "Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.",
                "Popsáno je chybně umístění příslušných schémat XML.",
                "Bod 2.1. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        SipInfo file = this.ctx.getContext().getSip();
        boolean stav = false;
        String detailChyby = null;

        Node metsMets = this.ctx.getContext().getMetsParser().getMetsRootNode();
        if (metsMets == null) {
            detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
            file.set_xsi_schemaLocation("Nenalezen kořenový element.");
            throw new ZafException(BaseCode.CHYBA, detailChyby);
        } else {
            if (ValuesGetter.hasAttribut(metsMets, "xsi:schemaLocation")) 
            {
                String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xsi:schemaLocation");
            	file.set_xsi_schemaLocation(hodnota);
                if (hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd")
                    == false && 
                    hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd")
                    == false) 
                {
                	detailChyby = "Schéma v souboru SIP je špatně uvedeno, hodnota: " + hodnota;
                    file.set_xsi_schemaLocation(hodnota);
                    throw new ZafException(BaseCode.CHYBA, detailChyby);
                } 
            } else {
                detailChyby = "Kořenový element <mets:mets> neobsahuje atribut \"xsi:schemaLocation\".";
                throw new ZafException(BaseCode.CHYBA, detailChyby);
            }
        }
    }

}
