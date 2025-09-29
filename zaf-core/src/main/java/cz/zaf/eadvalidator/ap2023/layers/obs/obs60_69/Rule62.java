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

        //pro elementy co mají atr lang
        Map<String, List<Scopecontent>> scopeContentWithLangMap;
        //pro elementy co nemaji element lang
        Map<String, List<Scopecontent>> scopeContentSimpleMap;
        scopeContentWithLangMap = new HashMap<>();
        scopeContentSimpleMap = new HashMap<>();

        for (Object child : childList) {
            if (child instanceof Scopecontent mainElement) {
                //zjistí jeslti má lang a podle toho ho přidá do jednoho ze seznamů
                addScopecontent(scopeContentWithLangMap, scopeContentSimpleMap, mainElement);

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
        if (!scopeContentSimpleMap.isEmpty() || !scopeContentWithLangMap.isEmpty()) {
            validateScopeContentEadCz(scopeContentWithLangMap, scopeContentSimpleMap);
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

    private void validateScopeContentEadCz(Map<String, List<Scopecontent>> scopeContentWithLangMap, Map<String, List<Scopecontent>> scopeContentWithoutLangMap) {
        //pokud neexistuje Scopecontent s atr lang = platí jedinečnost elementu
        //pokud existuje s lang muze byt bez lang jen jeden (předpoklad že to je cze) a lang by tak neměl mít cze
        int scopecontentWithoutLangCount = 0;
        if (!scopeContentWithoutLangMap.isEmpty()) {
            List<Scopecontent> scopecontentWithoutLangList = scopeContentWithoutLangMap.get("");
            scopecontentWithoutLangCount = scopecontentWithoutLangList.size();
            if (scopecontentWithoutLangCount > 1) {
                throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(scopecontentWithoutLangList));
            }
        }
        //když je jeden bez lang - nemůže být potom s lang s hodnotou cze
        if (scopecontentWithoutLangCount == 1 && !scopeContentWithLangMap.isEmpty()) {
            List<Scopecontent> scopeContentLangCzeList = scopeContentWithLangMap.get("cze");
            if (!scopeContentLangCzeList.isEmpty()) {
                throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element <ead:scopecontent>.", ctx.formatEadPosition(scopeContentLangCzeList));
            }
        }
        //ted už řeším jen s lang - nesmí být dva klíče stejné pokud nemají internal a external
        if (!scopeContentWithLangMap.isEmpty()) {
            for (Map.Entry<String, List<Scopecontent>> entry : scopeContentWithLangMap.entrySet()) {
                List<Scopecontent> withLangList = entry.getValue();
                int withLangListCount = withLangList.size();
                if (withLangListCount > 2) {
                    throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element <ead:scopecontent>.", ctx.formatEadPosition(withLangList));
                }
                if (withLangListCount == 2) {
                    Scopecontent scFirst = withLangList.get(0);
                    String audienceFirst = scFirst.getAudience();
                    Scopecontent scSecond = withLangList.get(1);
                    String audienceSecond = scSecond.getAudience();
                    if (audienceFirst == null || audienceSecond == null) {
                        throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element <ead:scopecontent>.", ctx.formatEadPosition(withLangList));
                    }
                    if ("internal".equals(audienceFirst) && "external".equals(audienceSecond) || "external".equals(audienceFirst) && "internal".equals(audienceSecond)) {
                        throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element <ead:scopecontent>.", ctx.formatEadPosition(withLangList));
                    }
                }
            }
        }
    }

    private void addScopecontent(Map<String, List<Scopecontent>> scopeContentWithLangMap, Map<String, List<Scopecontent>> scopeContentSimpleMap, Scopecontent sc) {
        String atrLang = sc.getLang();
        if (atrLang == null) {
            atrLang = "";
        }
        scopeContentWithLangMap.computeIfAbsent(atrLang, k -> new ArrayList<>()).add(sc);
    }

}
