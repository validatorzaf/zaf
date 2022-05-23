/*
 * To change this license header, cho'ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.sip.ChybaPravidla;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;

/**
 * Kontrola jmennych prostoru
 * 
 */
public class K04_JmennychProstoruXML
        extends KontrolaBase {

    static final public String NAME = "jmenných prostorů";

    static final public String NS1 = "ns1";
    static final public String NS2 = "ns2";

    static String[] list_text_jmProstory = {
            //1 text ns1
            "Soubor obsahuje právě jeden kořenový element <mets:mets>.",
            //2 text ns2
            "Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd."
    };

    public K04_JmennychProstoruXML() {
    }

    // Soubor obsahuje právě jeden kořenový element <mets:mets>.
    private void jeJedenMets() {
        Node metsMets = ctx.getMainDocument().getDocumentElement();
        boolean stav = false;
        String detailChyby = null;

        if (metsMets == null || !metsMets.getNodeName().equals("mets:mets")) {
            detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
        } else {
            stav = true;
            detailChyby = "Datový balíček SIP obsahuje právě jeden kořenový element <mets:mets>.";
        }

        if (!stav) {
            String obecnyPopisChyby = "Chybí kořenový element datového balíčku SIP.";
            ChybaPravidla p = new ChybaPravidla(NS1,
                    list_text_jmProstory[0],
                    detailChyby,
                    obecnyPopisChyby, null,
                    "Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
                    BaseCode.CHYBA,
                    null);
            vysledekKontroly.add(p);
        }
    }

    // Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd
    // nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.
    private void atributyMets() {

        SipInfo file = ctx.getSip();
        boolean stav = false;
        String detailChyby = null;

        Node metsMets = ctx.getMetsParser().getMetsRootNode();
        if (metsMets == null) {
            detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
            file.set_xsi_schemaLocation("Nenalezen kořenový element.");
        } else {
            if (ValuesGetter.hasAttribut(metsMets, "xsi:schemaLocation")) {
                String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xsi:schemaLocation");
                if (hodnota.equals(
                                   "http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd")
                        ||
                        hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd")) {
                    stav = true;
                    detailChyby = "atribut xsi:schemaLocation obsahuje očekávanou hodnotu: " + hodnota + ".";
                    file.set_xsi_schemaLocation(hodnota);
                } else {
                    detailChyby = "Schéma v souboru SIP je špatně uvedeno, hodnota: " + hodnota;
                    file.set_xsi_schemaLocation(hodnota);
                }
            } else {
                detailChyby = "Kořenový element <mets:mets> neobsahuje atribut \"xsi:schemaLocation\".";
            }
        }

        if (!stav) {
            String obecnyPopisChyby = "Popsáno je chybně umístění příslušných schémat XML.";
            ChybaPravidla p = new ChybaPravidla(NS2,
                    list_text_jmProstory[1],
                    detailChyby,
                    obecnyPopisChyby, null,
                    "Bod 2.1. přílohy č. 3 NSESSS.", // zdroj
                    BaseCode.CHYBA,
                    null);
            vysledekKontroly.add(p);
        }

    }

    @Override
    public void provedKontrolu() {

        jeJedenMets();
        atributyMets();
    }

    @Override
    public String getNazev() {
        return NAME;
    }

    @Override
    TypUrovenKontroly getUrovenKontroly() {
        return TypUrovenKontroly.JMENNE_PROSTORY_XML;
    }
}
