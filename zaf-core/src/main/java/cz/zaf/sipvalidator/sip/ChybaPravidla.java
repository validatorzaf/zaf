package cz.zaf.sipvalidator.sip;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import cz.zaf.common.validation.Rule;
import cz.zaf.common.validation.RuleEvaluationContext;
import cz.zaf.sipvalidator.exceptions.codes.ErrorCode;
import cz.zaf.sipvalidator.nsesss2017.EntityId;

/**
 * Vysledek kontroly jednoho pravidla
 * 
 */
public class ChybaPravidla {
    /**
     * Identifikator kontroly
     * Pouziva se v report XML pro jeji oznaceni
     * 
     * Obvykly format Prefix<cislo>, napr. vir1
     */
    final String id;
    /**
     * Popisny text pravidla
     */
    final String textPravidla;

    /**
     * Technický popis chyby
     * 
     * Jedná se o popis konkrétní chyb,
     * může obsahovat kontextovou informaci apod.
     */
    final String vypisChyby;

    /**
     * Obecný popis chyby
     * 
     * Obvykle se jedná o připravený popis typu chyby
     */
    final String popisChybyObecny;

    /**
     * Identifikace místa vzniku chyby
     */
    final String mistoChyby;

    /**
     * Legislativni zdroj pro pravidlo
     */
    final String zdroj;

    /**
     * Kod chyby
     */
    final private ErrorCode errorCode;

    /**
     * Seznam chybných entit
     * 
     * Seznam může být null.
     */
    final private List<EntityId> entityIds;

    public ChybaPravidla(final Rule<? extends RuleEvaluationContext> rule,
                         final String vypisChyby,
                         final String mistoChyby,
                         final ErrorCode errorCode,
                         final List<EntityId> entityIds
                         ) {
                             this(rule.getCode(),
                                     rule.getDescription(),
                                     vypisChyby,
                                     rule.getGenericError(),
                                     mistoChyby,
                                     rule.getRuleSource(),
                                     errorCode,
                                     entityIds);
                         }

    public ChybaPravidla(
                         final String id,
                         final String textPravidla,
                         final String vypisChyby,
                         final String popisChybyObecny,
                         final String mistoChyby,
                         final String zdroj,
                         final ErrorCode errorCode,
                         final List<EntityId> entityIds) {
        Validate.notEmpty(id);
        Validate.notEmpty(textPravidla);

        this.id = id;
        this.textPravidla = textPravidla;
        this.vypisChyby = vypisChyby;
        this.popisChybyObecny = popisChybyObecny;
        this.mistoChyby = mistoChyby;
        this.zdroj = zdroj;
        this.errorCode = errorCode;
        if (CollectionUtils.isNotEmpty(entityIds)) {
            // Copy IDs
            this.entityIds = entityIds.stream().collect(Collectors.toList());
        } else {
            this.entityIds = null;
        }
    }

    public String getId(){
        return id;
    }

    public String getVypisChyby(){
        return vypisChyby;
    }
    
    public String getMistoChyby(){
        return mistoChyby;
    }
    
    public String getTextPravidla() {
        return textPravidla;
    }

    public String getZdroj() {
        return zdroj;
    }

    public String getPopisChybyObecny() {
        return popisChybyObecny;
    }

    public ErrorCode getKodChyby() {
        return errorCode;
    }

    public List<EntityId> getEntityIds() {
        return entityIds;
    }
}
