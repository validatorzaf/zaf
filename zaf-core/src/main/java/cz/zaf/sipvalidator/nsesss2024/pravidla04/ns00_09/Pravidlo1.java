package cz.zaf.sipvalidator.nsesss2024.pravidla04.ns00_09;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.PravidloBase;

// Soubor obsahuje právě jeden kořenový element <mets:mets>.
public class Pravidlo1 extends PravidloBase {

    static final public String KOD = "ns1";

    public Pravidlo1() {
        super(KOD,
        		"Soubor obsahuje právě jeden kořenový element <mets:mets>.",
                "Chybí kořenový element datového balíčku SIP.",
                "Bod 1.1 přílohy č. 2 NSESSS.");
    }

    @Override
    protected void kontrola() {
        Node metsMets = this.ctx.getContext().getMainDocument().getDocumentElement();

        if (metsMets == null || !metsMets.getNodeName().equals("mets:mets")) {
        	String detailChyby = "Datový balíček SIP neobsahuje kořenový element <mets:mets>.";
            throw new ZafException(BaseCode.CHYBI_ELEMENT, detailChyby);
        }
    }

}
