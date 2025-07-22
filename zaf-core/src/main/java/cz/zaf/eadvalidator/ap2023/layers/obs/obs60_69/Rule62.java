package cz.zaf.eadvalidator.ap2023.layers.obs.obs60_69;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.P;
import cz.zaf.schema.ead3.Scopecontent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rule62 extends EadRule {

    static final public String CODE = "obs62";
    static final public String RULE_TEXT = "Každý element <ead:scopecontent> odpovídá pravidlům ead cz a obsahuje právě jeden neprázdný element <ead:p>.";
    static final public String RULE_ERROR = "Některý element <ead:scopecontent> neobsahuje právě jeden element <ead:p>. Případně je element <ead:p> prázdný.";
    static final public String RULE_SOURCE = "Část 5.13 profilu EAD3 MV ČR";

    public Rule62() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        //May occur within:archdesc, c, c01, c02, c03, c04, c05, c06, c07, c08, c09, c10, c11, c12, scopecontent

        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(archDescChildList);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            validate(cChildList);
        });
    }

    private void validate(List<Object> childList) {
//        List<Scopecontent> scopeContenetList = new ArrayList<>();
        Map<String, List<Scopecontent>> scopeContentMap;
        scopeContentMap = new HashMap<>();
        int scopeContentCount = 0;
        for (Object child : childList) {
            if (child instanceof Scopecontent mainElement) {
//                scopeContenetList.add(mainElement);
                addScopecontent(scopeContentMap, mainElement);
                scopeContentCount++;
                List<Object> cHistChilds = mainElement.getChronlistOrListOrTable();
                P p = null;
                for (Object cHistChild : cHistChilds) {
                    if (cHistChild instanceof P) {
                        if (p == null) {
                            p = validateP(cHistChild);
                        } else {
                            throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(p));
                        }
                    }
                }
                if (p == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element <ead:p>.", ctx.formatEadPosition(mainElement));
                }
            }
        }

        //validování elementu dle pravidel EADCZ
        if (!scopeContentMap.isEmpty()) {
            validateScopeContentMap(scopeContentMap, scopeContentCount);
        }

    }

    private P validateP(Object instanceOfP) {
        P p = (P) instanceOfP;
        // Kontrola obsahu p
        List<Serializable> pContentList = p.getContent();
        if (pContentList.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(p));
        }
        Serializable partContent = pContentList.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isEmpty(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(p));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(p));
        }
        return p;
    }

    private void validateScopeContentMap(Map<String, List<Scopecontent>> scopeContentMap, int scopeContentCount) {
        //pokud je pouze jeden - nesmí mít atribut "lang"
        if (scopeContentCount == 1) {
            String langAtr = scopeContentMap.keySet().iterator().next();
            if (!StringUtils.isEmpty(langAtr)) {
                Scopecontent sc = scopeContentMap.values().iterator().next().get(0);
                throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Element <ead:scopecontent> obsahuje nepovolený atribut lang.", ctx.formatEadPosition(sc));
            }
            return;
        }
        
        //počet elementů nesmí být lichý
        if (scopeContentCount % 2 != 0) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element <ead:scopecontent>.", ctx.formatEadPosition(scopeContentMap.values()));
        }

        //pokud je počet sudý, musí být vždy jeden s atributem lang s hodnotou jazyka (hodnotu jazyka by mělo hlídat schéma)
        //a druhý bez atributu lang
        //pokud má stejnou hodnotu v lang, tak musí mát atribut audience s hodnotami external a internal
        List<Scopecontent> scopeContentListNoAtrLang = scopeContentMap.getOrDefault("", List.of());
        int scopeContentListNoAtrLangSize = scopeContentListNoAtrLang.size();
        int scopeContentListWithUniqueAtrLangSize = 0;
        
        for (Map.Entry<String, List<Scopecontent>> entry : scopeContentMap.entrySet()) {
            String atrLang = entry.getKey();
            List<Scopecontent> scopecontentList = entry.getValue();
            //pokud je key prázdný = těch může být více než 2
            if (scopecontentList.size() > 2 && !atrLang.isEmpty()) {
                throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezeny nepovolené elementy <ead:scopecontent>.", ctx.formatEadPosition(scopecontentList));
            }
            //když jsou dva tak audience external internal
            if (scopecontentList.size() == 2 && !atrLang.isEmpty()) {
                Scopecontent sc1 = scopecontentList.get(0);
                Scopecontent sc2 = scopecontentList.get(1);
                String sc1Audience = sc1.getAudience();
                String sc2Audience = sc2.getAudience();
                Set<String> hodnoty = Set.of(sc1Audience, sc2Audience);
                boolean isCondition = hodnoty.contains("internal") && hodnoty.contains("external");
                if (!isCondition) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Nalezeny nepovolené hodnoty atributu audience elementu <ead:scopecontent>.", ctx.formatEadPosition(scopecontentList));
                }
            }
            if(scopecontentList.size() == 1 && !atrLang.isEmpty()){
                scopeContentListWithUniqueAtrLangSize++;
            }
        }
        //bez atr lang musí být stejně jako s unikatnim atr lang"
        if(scopeContentListNoAtrLangSize != scopeContentListWithUniqueAtrLangSize){
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezeny nepovolené elementy <ead:scopecontent>.", ctx.formatEadPosition(scopeContentListNoAtrLang));
        }

    }

    private void addScopecontent(Map<String, List<Scopecontent>> scopeContentMap, Scopecontent sc) {
        String atrLang = sc.getLang();
        if (atrLang == null) {
            atrLang = "";
        }
        scopeContentMap.computeIfAbsent(atrLang, k -> new ArrayList<>()).add(sc);

    }
}
