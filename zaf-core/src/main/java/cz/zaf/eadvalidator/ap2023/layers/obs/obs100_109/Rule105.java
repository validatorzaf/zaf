package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Descriptivenote;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Ptr;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import cz.zaf.schema.ead3.Source;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rule105 extends EadRule {

    static final public String CODE = "obs105";
    static final public String RULE_TEXT = "Element <ead:descriptivenote> obsahuje právě jeden element <ead:p>, který obsahuje právě jeden element <ead:ptr>. Ten má atribut \"target\" o hodnotě, která odkazuje na element <control>/<sources>/<source>.";
    static final public String RULE_ERROR = "Element <ead:descriptivenote> neobsahuje právě jeden element <ead:p> nebo element <ead:p> neobsahuje právě jeden element <ead:ptr>. Případně element <ead:ptr> nemá atribut \"target\" nebo tento atribut neobsahuje požadovanou hodnotu.";
    static final public String RULE_SOURCE = "Část 8.2.1 a 8.2.2 profilu EAD3 MV ČR";
    private final Map<String, Source> mapSource = new HashMap();

    public Rule105() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> accessrestrictOrAccrualsOrAcqinfo = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        for (Object archObject : accessrestrictOrAccrualsOrAcqinfo) {
            if (archObject instanceof Relations relations) {
                validate(relations);
            }
        }

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> mDescBase = c.getMDescBase();
            for (Object mDescBaseObject : mDescBase) {
                if (mDescBaseObject instanceof Relations relations) {
                    validate(relations);
                }
            }
        });
    }

    private void validate(Relations relations) {
        List<Relation> listRelation = relations.getRelation();
        for (Relation relation : listRelation) {
            Descriptivenote descriptivenote = relation.getDescriptivenote();
            if (descriptivenote != null) {

                List<P> pList = descriptivenote.getP();

                if (pList.isEmpty()) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element p.", ctx.formatEadPosition(descriptivenote));
                }
                if (pList.size() != 1) {
                    throw new ZafException(BaseCode.DUPLICITA, "Nalezen nepovolený element p.", ctx.formatEadPosition(descriptivenote));
                }
                P p = pList.get(0);
                //ptr nejde zavolat
                validatePart(p);
            }
        }
    }

    private void validatePart(P p) {
        Ptr found = null;
        List<Serializable> content = p.getContent();
        for (Serializable item : content) {
            if (item instanceof JAXBElement) {
                JAXBElement<?> inner = (JAXBElement<?>) item;
                Object value = inner.getValue();
                if (value instanceof Ptr ptr) {
                    if (found != null) {
                        throw new ZafException(BaseCode.DUPLICITA, "Nalezen duplicitní element ptr.", ctx.formatEadPosition(ptr));
                    }
                    found = ptr;

                    //objekt je přímo ten source (element) - ověřit že je to source
                    Object target = ptr.getTarget();
                    if (!(target instanceof Source source)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nenalezena očkávaná hodnota atributu target.", ctx.formatEadPosition(ptr));
                    }
                }
            }
        }
    }

}
