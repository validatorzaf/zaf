package cz.zaf.sipvalidator.nsesss2024.pravidla06.obs10_19;

import org.w3c.dom.Node;

import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2024.ValuesGetter;
import cz.zaf.sipvalidator.nsesss2024.pravidla06.K06PravidloBase;

//OBSAHOVÁ č.14 Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
public class Pravidlo14 extends K06PravidloBase {

    static final public String OBS14 = "obs14";

    public Pravidlo14() {
        super(OBS14,
                "Element <mets:metsHdr> obsahuje atribut LASTMODDATE.",
                "Chybí datum poslední úpravy datového balíčku SIP.",
                "Bod 1.2 přílohy č. 2 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsHdr = metsParser.getMetsHdr();

        if (!ValuesGetter.hasAttribut(metsHdr, "LASTMODDATE")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:metsHdr> nemá atribut LASTMODDATE.", metsHdr);
        }

    }
}
