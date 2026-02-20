package cz.zaf.eadvalidator.ap2023.layers.obs.obs90_99;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Altformavail;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.C;
import cz.zaf.schema.ead3.Container;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Langmaterial;
import cz.zaf.schema.ead3.Language;
import cz.zaf.schema.ead3.Languageset;
import cz.zaf.schema.ead3.Origination;
import cz.zaf.schema.ead3.Relation;
import cz.zaf.schema.ead3.Relations;
import cz.zaf.schema.ead3.Unitdatestructured;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Rule98 extends EadRule {

    static final public String CODE = "obs98";
    static final public String RULE_TEXT = "Pokud element <unitdatestructured>, <origination> v elementu <did>, <language>, <container>, <altformavail> nebo <relation> má atribut \"altrender\", jeho hodnota je \"inherited\". V nadřazené jednotce popisu musí existovat element se shodnou strukturou jako má element s atributem \"altrender\", tj. se shodnými atributy včetně všech podřízených elementů, jejich atributů a hodnot. Nadřazený element nemusí mít uveden atribut \"altrender\".";
    static final public String RULE_ERROR = "Element <unitdatestructured>, <origination> v elementu <did>, <language>, <container>, <altformavail> nebo <relation> má atribut \"altrender\" neobsahující hodnotu \"inherited\". Nebo v nadřazené jednotce popisu neexistuje element se shodnou strukturou jako má element s atributem \"altrender\", tj. se shodnými atributy včetně všech podřízených elementů, jejich atributů a hodnot.";
    static final public String RULE_SOURCE = "Část 5.2 profilu EAD3 MV ČR";

    public Rule98() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Unitdatestructured> unitList = getUnitdatestructured(c);
            if (!CollectionUtils.isEmpty(unitList)) {
                for (Unitdatestructured unit : unitList) {
                    boolean hasAltender = hasAltender(unit);
                    if(hasAltender){
                        List<Unitdatestructured> parentUnitList = getUnitdatestructured(parent);
                        
                    }
                }
            }
        });
    }

    private boolean hasAltender(Object object) {
        if (object instanceof Unitdatestructured uni) {
            String altrender = uni.getAltrender();
            return checkAltrender(object, altrender);
        } else if (object instanceof Origination ori) {
            String altrender = ori.getAltrender();
            return checkAltrender(object, altrender);
        } else if (object instanceof Language lan) {
            String altrender = lan.getAltrender();
            return checkAltrender(object, altrender);
        } else if (object instanceof Container con) {
            String altrender = con.getAltrender();
            return checkAltrender(object, altrender);
        } else if (object instanceof Altformavail alt) {
            String altrender = alt.getAltrender();
            return checkAltrender(object, altrender);
        } else if (object instanceof Relation rel) {
            String altrender = rel.getAltrender();
            return checkAltrender(object, altrender);
        } else {
            return false;
        }
    }

    private boolean checkAltrender(Object element, String altrender) {
        if (altrender != null) {
            if (!StringUtils.equals("inherited", altrender)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut altrender obsahuje nepovolenou hodnotu: " + altrender + ".", ctx.formatEadPosition(element));
            }
            return true;
        }
        return false;
    }

    private List<Unitdatestructured> getUnitdatestructured(Object element) {
        List<Unitdatestructured> list = new ArrayList();
        Did did = null;
        if (element instanceof C c) {
            did = c.getDid();
        }
        if (element instanceof Archdesc archdesc) {
            did = archdesc.getDid();
        }
        if (did != null) {
            List<Object> mDid = did.getMDid();
            if (!CollectionUtils.isEmpty(mDid)) {
                for (Object md : mDid) {
                    if (md instanceof Unitdatestructured unitdatestructured) {
                        list.add(unitdatestructured);
                    }
                }
            }
        }
        return list;
    }

    private List<Origination> getOrigination(Object element) {
        List<Origination> list = new ArrayList();
        Did did = null;
        if (element instanceof C c) {
            did = c.getDid();
        }
        if (element instanceof Archdesc archdesc) {
            did = archdesc.getDid();
        }
        if (did != null) {
            List<Object> mDid = did.getMDid();
            if (!CollectionUtils.isEmpty(mDid)) {
                for (Object md : mDid) {
                    if (md instanceof Origination origination) {
                        list.add(origination);
                    }
                }
            }
        }
        return list;
    }

    private List<Language> getLanguage(Object element) {
        List<Language> list = new ArrayList();
        Did did = null;
        if (element instanceof C c) {
            did = c.getDid();
        }
        if (element instanceof Archdesc archdesc) {
            did = archdesc.getDid();
        }
        if (did != null) {
            List<Object> mDid = did.getMDid();
            if (!CollectionUtils.isEmpty(mDid)) {
                for (Object md : mDid) {
                    if (md instanceof Langmaterial langmaterial) {
                        List<Object> languageOrLanguageset = langmaterial.getLanguageOrLanguageset();
                        for (Object lOrl : languageOrLanguageset) {
                            if (lOrl instanceof Languageset languageset) {
                                List<Language> languageList = languageset.getLanguage();
                                list.addAll(languageList);
                                return list;
                            }
                            if (lOrl instanceof Language language) {
                                list.add(language);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private List<Container> getContainer(Object element) {
        List<Container> list = new ArrayList();
        Did did = null;
        if (element instanceof C c) {
            did = c.getDid();
        }
        if (element instanceof Archdesc archdesc) {
            did = archdesc.getDid();
        }
        if (did != null) {
            List<Object> mDid = did.getMDid();
            if (!CollectionUtils.isEmpty(mDid)) {
                for (Object md : mDid) {
                    if (md instanceof Container container) {
                        list.add(container);
                    }
                }
            }
        }
        return list;
    }

    private List<Altformavail> getAltformavail(Object element) {
        List<Altformavail> list = new ArrayList();
        List<Object> children = null;
        if (element instanceof C c) {
            children = c.getTheadAndC();
        }
        if (element instanceof Archdesc archdesc) {
            children = archdesc.getAccessrestrictOrAccrualsOrAcqinfo();
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (Object child : children) {
                if (child instanceof Altformavail altformavail) {
                    list.add(altformavail);
                }
            }
        }
        return list;
    }

    private List<Relation> getRelation(Object element) {
        List<Relation> list = new ArrayList();
        List<Object> children = null;
        if (element instanceof C c) {
            children = c.getTheadAndC();
        }
        if (element instanceof Archdesc archdesc) {
            children = archdesc.getAccessrestrictOrAccrualsOrAcqinfo();
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (Object child : children) {
                if (child instanceof Relations relations) {
                    List<Relation> relationList = relations.getRelation();
                    list.addAll(relationList);
                }
            }
        }
        return list;
    }
}
