package cz.zaf.eadvalidator.ap2023.layers.obs.obs20_29;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Agencycode;
import org.apache.commons.lang3.StringUtils;
import cz.zaf.schemas.ead.EadNS;

public class Rule21 extends EadRule {

    static final public String CODE = "obs21";
    static final public String RULE_TEXT = "Element <agencycode> má atribut \"localtype\" o hodnotě \"CZ_MVCR_INSTITUTION_ID\" a obsahuje neprázdnou hodnotu, která odpovídá konstrukci tvorby čísla archivu, tj. 9 číslic.";
    static final public String RULE_ERROR = "Element <agencycode> nemá atribut \"localtype\" nebo tento atribut obsahuje nepovolenou hodnotu. Případně element obsahuje hodnotu, která neodpovídá konstrukci tvorby čísla archivu, tj. 9 číslic.";
    static final public String RULE_SOURCE = "Část 2.5 profilu EAD3 MV ČR";

    public Rule21() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Agencycode agencycode = ctx.getEad().getControl().getMaintenanceagency().getAgencycode();
        String localType = agencycode.getLocaltype();

        if (StringUtils.isEmpty(localType)) {
            throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut localtype.", ctx.formatEadPosition(agencycode));
        }

        if (!EadNS.LOCALTYPE_INSTITUTION_ID.equals(localType)) {
            throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut localtype má chybnou hodnotu: " + localType, ctx.formatEadPosition(agencycode));
        }

        String content = agencycode.getContent();
        if ((!StringUtils.isNumeric(content)) || (content.length() != 9)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element agencycode obsahuje nesprávnou hodnotu: " + content + ".", ctx.formatEadPosition(agencycode));
        }
    }
}
