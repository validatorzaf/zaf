package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.15 Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
public class Pravidlo15 extends K06PravidloBase {

    static final public String OBS15 = "obs15";

    public Pravidlo15() {
        super(OBS15,
                "Element <mets:metsHdr> obsahuje atribut CREATEDATE.",
                "Chybí datum vytvoření datového balíčku SIP.",
                "Bod 2.2. přílohy č. 3 NSESSS."
        );
    }

    @Override
    protected void kontrola() {
        Node metsHdr = metsParser.getMetsHdr();

        if (!ValuesGetter.hasAttribut(metsHdr, "CREATEDATE")) {
            nastavChybu(BaseCode.CHYBI_ATRIBUT,
                    "Element <mets:metsHdr> nemá atribut CREATEDATE.", metsHdr);
        }
    }

}
