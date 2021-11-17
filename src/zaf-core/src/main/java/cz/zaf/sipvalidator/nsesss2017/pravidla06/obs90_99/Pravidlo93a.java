package cz.zaf.sipvalidator.nsesss2017.pravidla06.obs90_99;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.w3c.dom.Node;

import cz.zaf.sipvalidator.nsesss2017.K06PravidloBase;
import cz.zaf.sipvalidator.nsesss2017.NsessV3;
import cz.zaf.sipvalidator.nsesss2017.ValuesGetter;

/**
 * OBSAHOVÁ 93a. Každá entita věcná skupina (<nsesss:VecnaSkupina>),
 * jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>),
 * obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>,
 * <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a
 * <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.
 * 
 */
public class Pravidlo93a  extends K06PravidloBase {

    static final public String OBS93A = "obs93a";

    public Pravidlo93a() {
        super(OBS93A,
                "Každá entita věcná skupina (<nsesss:VecnaSkupina>), jejíž rodičovská entita je spisový plán (<nsesss:SpisovyPlan>), obsahuje v hierarchii dětských elementů <nsesss:EvidencniUdaje>, <nsesss:Trideni> elementy <nsesss:JednoduchySpisovyZnak> a <nsesss:PlneUrcenySpisovyZnak> se stejnými hodnotami.",
                "Chybně jsou uvedeny spisové znaky.", 
                "Požadavek 3.1.30 NSESSS.");
    }

	@Override
	protected boolean kontrolaPravidla() {
        List<Node> vsList = metsParser.getNodes(NsessV3.VECNA_SKUPINA);
        if(CollectionUtils.isEmpty(vsList)) {
            return nastavChybu("Věcná skupina neexistuje", metsParser.getDocument());
        }
        for(Node vs: vsList) {
            Node spl = ValuesGetter.getXChild(vs, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni", "nsesss:SpisovyPlan");
            if(spl != null){
                Node jsz = ValuesGetter.getXChild(vs, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                  "nsesss:JednoduchySpisovyZnak");
                if(jsz == null) {
                	return nastavChybu("Nenalezen element <nsesss:JednoduchySpisovyZnak>. " + getJmenoIdentifikator(vs), vs);
                }
                Node pusz = ValuesGetter.getXChild(vs, NsessV3.EVIDENCNI_UDAJE, "nsesss:Trideni",
                                                   "nsesss:PlneUrcenySpisovyZnak");
                if(pusz == null) {
                	return nastavChybu("Nenalezen element <nsesss:PlneUrcenySpisovyZnak>. " + getJmenoIdentifikator(vs), vs);
                }
                if(!jsz.getTextContent().equals(pusz.getTextContent())){
                    return nastavChybu("Elementy neobsahují stejné hodnoty. " + getJmenoIdentifikator(vs), getMistoChyby(jsz) + " " + getMistoChyby(pusz));
                }
            }
        }
        return true;
	}

}
