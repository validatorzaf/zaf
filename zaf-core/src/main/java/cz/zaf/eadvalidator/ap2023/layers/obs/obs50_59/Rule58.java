package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.cz.UnitdateFormatType;
import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Daterange;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Fromdate;
import cz.zaf.schema.ead3.Todate;
import cz.zaf.schema.ead3.Unitdate;
import cz.zaf.schema.ead3.Unitdatestructured;

public class Rule58  extends EadRule {

    static final public String CODE = "obs58";
    static final public String RULE_TEXT = "Element <unitdate> obsahuje uvedenou neprázdnou textovou podobu datace.";
    static final public String RULE_ERROR = "Element <unitdate> neobsahuje přímo uvedenou dataci.";
    static final public String RULE_SOURCE = "Část 5.8.2 profilu EAD3 MV ČR";
    
    public Rule58() {
    	super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }
    
	@Override
	protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        
        Did didA = archDesc.getDid();
        validate(didA);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            validate(didC);
        });		
	}

    private void validate(Did did) {
        List<Object> mDid = did.getMDid();
        for (Object object : mDid) {
            if (object instanceof Unitdate unitDate) {
            	List<Serializable> content = unitDate.getContent();
                if(CollectionUtils.isEmpty(content)) {
                    throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unitdate je prázdný.", ctx.formatEadPosition(unitDate));
                }
                if(content.size()>1) {
                	throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unitdate obsahuje více vnořených elementů, měl by obsahovat přímo text.", ctx.formatEadPosition(unitDate));
                }
                var cnt = content.get(0);
                if(cnt instanceof String) {
                	// ok - nop
                } else {
                	throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element unitdate obsahuje element, měl by obsahovat přímo text. Hodnota: " + cnt, ctx.formatEadPosition(unitDate));
                }
                
                ctx.markValidatedElement(unitDate);
                ctx.markValidatedContent(object);
            }
        }
    }
}
