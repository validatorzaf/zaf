package cz.zaf.sipvalidator.nsesss2017.pravidla04.ns00_09;

import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.ZafException;
import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.pravidla04.NsCheckRuleBase;

// Soubor obsahuje právě jeden kořenový element <mets:mets>.
public class Pravidlo1 extends NsCheckRuleBase {

    static final public String KOD = "ns1";

    public Pravidlo1() {
        super(KOD,
        		"Datový balíček SIP obsahuje právě jeden kořenový element <mets:mets>.",
                "Datový balíček SIP neobsahuje kořenový element <mets:mets>.",
                "Bod 2.1. přílohy č. 3 NSESSS.");
    }

    @Override
    protected void kontrola() {
        Node metsMets = this.ctx.getContext().getMainDocument().getDocumentElement();
        boolean stav = false;
        String detailChyby = null;

        if (metsMets == null || !metsMets.getNodeName().equals("mets:mets")) {
            detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
            throw new ZafException(BaseCode.CHYBI_ELEMENT, detailChyby);
        }
    }

}
