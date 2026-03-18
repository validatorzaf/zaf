package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.Ap2023Constants;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Corpname;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Famname;
import cz.zaf.schema.ead3.Name;
import cz.zaf.schema.ead3.Origination;
import cz.zaf.schema.ead3.Part;
import cz.zaf.schema.ead3.Persname;
import cz.zaf.schema.ead3.Ref;
import cz.zaf.schema.ead3.Source;
import jakarta.xml.bind.JAXBElement;
import java.io.Serializable;
import java.util.List;

public class Rule99 extends EadRule {

    static final public String CODE = "obs99";
    static final public String RULE_TEXT = "Každý element origination obsahuje právě jeden podřízený element a to jeden z následujících elementů: - persname - famname - corpname - name přičemž tento element má atribut \"localtype\" o hodnotě \"ORIGINATOR\" a obsahuje právě jeden element <part>, který obsahuje právě jeden element <ref>. Element <ref> má atribut \"target\" o hodnotě, která odkazuje na element <control>/<sources>/<source>.";
    static final public String RULE_ERROR = "Některý element <origination> obsahuje nesprávný element. Případně vnořený element nemá atribut \"localtype\" nebo tento atribut neobsahuje hodnotu \"ORIGINATOR\", nemá správný obsah.";
    static final public String RULE_SOURCE = "Část 8.1 profilu EAD3 MV ČR";

    public Rule99() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        Did didA = archDesc.getDid();
        validateDid(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validateDid(didC);
        });
    }

    private void validateDid(Did did) {
        List<Object> didChildren = did.getMDid();
        for (Object didChild : didChildren) {
            if (didChild instanceof Origination origination) {
                List<Object> originationChildList = origination.getCorpnameOrFamnameOrName();

                // Must contain exactly one child element
                if (originationChildList.size() != 1) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                            "Element origination musí obsahovat právě jeden podřízený element, nalezeno: " + originationChildList.size() + ".",
                            ctx.formatEadPosition(origination));
                }

                Object originationChild = originationChildList.get(0);
                if (originationChild instanceof Persname persname) {
                    validate(persname, persname.getLocaltype(), persname.getPart());
                } else if (originationChild instanceof Corpname corpname) {
                    validate(corpname, corpname.getLocaltype(), corpname.getPart());
                } else if (originationChild instanceof Famname famname) {
                    validate(famname, famname.getLocaltype(), famname.getPart());
                } else if (originationChild instanceof Name name) {
                    validate(name, name.getLocaltype(), name.getPart());
                } else {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT,
                            "Nenalezen očekávaný element (persname, famname, corpname nebo name).",
                            ctx.formatEadPosition(origination));
                }
            }
        }
    }

    private void validate(Object element, String localType, List<Part> partList) {
        if (!Ap2023Constants.LOCALTYPE_ORIGINATOR.equals(localType)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                    "Atribut localtype nemá očekávanou hodnotu.",
                    ctx.formatEadPosition(element));
        }

        // Must contain exactly one part
        if (partList.size() != 1) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU,
                    "Element musí obsahovat právě jeden element part, nalezeno: " + partList.size() + ".",
                    ctx.formatEadPosition(element));
        }

        validatePart(partList.get(0));
    }

    private void validatePart(Part part) {
        Ref foundRef = null;
        List<Serializable> content = part.getContent();
        for (Serializable item : content) {
            if (item instanceof JAXBElement) {
                JAXBElement<?> inner = (JAXBElement<?>) item;
                Object value = inner.getValue();
                if (value instanceof Ref ref) {
                    if (foundRef != null) {
                        throw new ZafException(BaseCode.DUPLICITA,
                                "Nalezen duplicitní element ref.",
                                ctx.formatEadPosition(ref));
                    }
                    foundRef = ref;
                    Object target = ref.getTarget();
                    if (!(target instanceof Source)) {
                        throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU,
                                "Nenalezena očekávaná hodnota atributu target.",
                                ctx.formatEadPosition(ref));
                    }
                }
            }
        }
        if (foundRef == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT,
                    "Nenalezen element ref.",
                    ctx.formatEadPosition(part));
        }
    }

}
