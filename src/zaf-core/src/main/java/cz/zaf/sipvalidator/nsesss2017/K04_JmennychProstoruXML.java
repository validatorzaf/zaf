/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidator.nsesss2017;

import static cz.zaf.sipvalidator.nsesss2017.RozparsovaniSipSouboru.metsMets;
import static cz.zaf.sipvalidator.nsesss2017.RozparsovaniSipSouboru.parsedSAX_Sipsoubor;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.zaf.sipvalidator.sip.KontrolaContext;
import cz.zaf.sipvalidator.sip.SIP_MAIN_helper;
import cz.zaf.sipvalidator.sip.SIP_MAIN_kontrola_pravidlo;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.TypUrovenKontroly;
import cz.zaf.sipvalidator.sip.UrovenKontroly;
import cz.zaf.sipvalidator.sip.VysledekKontroly;

/**
 *
 * @author standa
 */
public class K04_JmennychProstoruXML
	implements UrovenKontroly
{
	
	static final public String NAME = "kontrola jmenných prostorů"; 
    
    public K04_JmennychProstoruXML() {
    }
    
    // Soubor obsahuje právě jeden kořenový element <mets:mets>.
    private void jeJedenMets(VysledekKontroly k){
        Node metsMets = parsedSAX_Sipsoubor.getDocumentElement();
        if(metsMets == null || !metsMets.getNodeName().equals("mets:mets")){
            SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "ns1", false, "Datový balíček SIP neobsahuje kořenový element <mets:mets>.", 0, "");
            k.add(p);
        }
        else{
            NodeList list = ValuesGetter.getAllAnywhere("mets:mets", parsedSAX_Sipsoubor);
            if(list.getLength() > 1){
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "ns1", false, "Datový balíček sip obsahuje více elementů <mets:mets>.", 0, "");
                k.add(p);
            }
            else{
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "ns1", true, "Datový balíček SIP obsahuje právě jeden kořenový element <mets:mets>.", 0, "");
                k.add(p);
            }
        }
    }
    
    // Element <mets:mets> obsahuje atribut xsi:schemaLocation s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd
    // nebo s hodnotou http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd.
    private void atributyMets(VysledekKontroly k, SipInfo file){
        if(metsMets == null){
            SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(1, "ns2", false, "Datový balíček SIP neobsahuje kořenový element <mets:mets>.", 0, "");
            file.set_xsi_schemaLocation("Nenalezen kořenový element.");
            k.add(p);
        }
        else{
            if(ValuesGetter.hasAttribut(metsMets, "xsi:schemaLocation")){
               String hodnota = ValuesGetter.getValueOfAttribut(metsMets, "xsi:schemaLocation");
               if(hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 TransakcniProtokolNavrh_verze1.7.xsd") || 
            		   hodnota.equals("http://www.loc.gov/METS/ http://www.loc.gov/standards/mets/mets.xsd http://www.mvcr.cz/nsesss/v3 http://www.mvcr.cz/nsesss/v3/nsesss.xsd http://nsess.public.cz/erms_trans/v_01_01 http://www.mvcr.cz/nsesss/v3/nsesss-TrP.xsd")){
                    SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(1, "ns2", true, "atribut xsi:schemaLocation obsahuje očekávanou hodnotu: " + hodnota + ".", 0, "");
                    file.set_xsi_schemaLocation(hodnota);
                    k.add(p);
               } 
               else{
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(1, "ns2", false, "Schéma v souboru SIP je špatně uvedeno.", 0, "");
                file.set_xsi_schemaLocation(hodnota);
                k.add(p);
               }
            }
            else{
                SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(1, "ns2", false, "Kořenový element <mets:mets> neobsahuje atribut \"xsi:schemaLocation\".", 0, "");
                k.add(p); 
            }
        }
    }

	@Override
	public void provedKontrolu(KontrolaContext ctx) throws Exception {
		boolean isFailed = ctx.isFailed();
		
        VysledekKontroly k = new VysledekKontroly(
        		TypUrovenKontroly.JMENNE_PROSTORY_XML,
        		NAME);
        ctx.pridejKontrolu(k);
        if(isFailed) {
        	return;
        }

        SipInfo file = ctx.getSip();        
		if (parsedSAX_Sipsoubor != null) {
			jeJedenMets(k);
			atributyMets(k, file);
		} else {
			String s;
			if (SIP_MAIN_helper.ma_metsxml(file)) {
				s = "Soubor je chybný.";
			} else {
				s = "SIP balíček neobsahoval kontrolovatelný soubor.";
			}

			SIP_MAIN_kontrola_pravidlo p = new SIP_MAIN_kontrola_pravidlo(0, "ns1", false, s, 0, "");
			SIP_MAIN_kontrola_pravidlo p2 = new SIP_MAIN_kontrola_pravidlo(1, "ns2", false, s, 0, "");
			k.add(p);
			k.add(p2);
		}
	}

	@Override
	public String getNazev() {
		return NAME;
	}
    
}
