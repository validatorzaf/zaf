package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs10_19;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.exceptions.codes.BaseCode;
import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

//OBSAHOVÁ č.10 Element <mets:mets> obsahuje právě jeden dětský element <mets:metsHdr>.",
public class Pravidlo10 extends K06PravidloBase {

	static final public String OBS10 = "obs10";

	public Pravidlo10() {
		super(OBS10,
				"Element <mets:mets> obsahuje dětský element <mets:metsHdr>.",
				"Chybí povinná část (záhlaví) struktury datového balíčku SIP.",
				"Bod 2.2. přílohy č. 3 NSESSS."
				);
	}

	@Override
    protected void kontrola() {
		Node metsMets = metsParser.getMetsRootNode();
        List<Node> metsHdrs = ValuesGetter.getChildList(metsMets, "mets:metsHdr");
        if (CollectionUtils.isEmpty(metsHdrs)) {
            nastavChybu(BaseCode.CHYBI_ELEMENT,
                        "Kořenový element <mets:mets> nemá žádný dětský element <mets:metsHdr>.", metsMets);
        }
        // Ze schematu musi byt maximalne jeden
        Validate.isTrue(metsHdrs.size() == 1);
	}

}
