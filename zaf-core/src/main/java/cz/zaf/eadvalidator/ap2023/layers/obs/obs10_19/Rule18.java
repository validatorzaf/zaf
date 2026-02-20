package cz.zaf.eadvalidator.ap2023.layers.obs.obs10_19;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Maintenancestatus;

public class Rule18 extends EadRule {

    static final public String CODE = "obs18";
    static final public String RULE_TEXT = "Element <maintenancestatus> má atribut \"value\" o hodnotě \"derived\".";
    static final public String RULE_ERROR = "Element <maintenancestatus> nemá atribut \"value\" nebo tento atribut neobsahuje hodnotu \"derived\".";
    static final public String RULE_SOURCE = "Část 2.4 profilu EAD3 MV ČR";

    public Rule18() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Control control = ctx.getEad().getControl();
        Maintenancestatus maintenancestatus = control.getMaintenancestatus();
        if(maintenancestatus == null){
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenaleyen element maintenancestatus.", ctx.formatEadPosition(control));
        }
        String value = maintenancestatus.getValue();
        if (!StringUtils.equals("derived", value)) {
            throw new ZafException(BaseCode.CHYBNY_ATRIBUT, "Atribut value neobsahuje požadovanou hodnotu.", ctx.formatEadPosition(maintenancestatus));
        }
        
    }
}
