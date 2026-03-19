package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Daterange;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Fromdate;
import cz.zaf.schema.ead3.Todate;
import cz.zaf.schema.ead3.Unitdatestructured;

public class Rule57  extends EadRule {

    static final public String CODE = "obs57";
    static final public String RULE_TEXT = "Každý element <unittitle>, který nemá atribut \"localtype\", obsažený v elementu <did> obsahuje prostou textovou hodnotu.";
    static final public String RULE_ERROR = "Některý element <unittile>, který nemá atribut \"localtype\", neobsahuje prostou textovou hodnotu.";
    static final public String RULE_SOURCE = "Část 5.6 a 5.7 profilu EAD3 MV ČR";
    
    public Rule57() {
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
            if (object instanceof Unitdatestructured unitDateStructured) {
                Daterange daterange = unitDateStructured.getDaterange();
                if(daterange == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element daterange.", ctx.formatEadPosition(unitDateStructured));
                }
                String altrender = daterange.getAltrender();
                if(StringUtils.isEmpty(altrender)){
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu altrender.", ctx.formatEadPosition(daterange));
                }
                var formats = altrender.split("-", -1);
                
                Fromdate fromdate = daterange.getFromdate();
                if(fromdate != null){
                	validateFromdate(fromdate, formats[0]);
                }
                Todate todate = daterange.getTodate();
                if(todate != null){
                	validateTodate(todate, formats[(formats.length>1)?1:0]);
                }
            }
        }
}

	private void validateTodate(Todate todate, String format) {
		String srcdate = todate.getStandarddate();
		boolean estimate = false;
		if(srcdate==null) {
			srcdate = todate.getNotafter();
			if(srcdate==null) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Není nastaven ani jeden z atributů standarddate, či notafter.", ctx.formatEadPosition(todate));
			}
			estimate = true;
			ctx.markValidatedAttribute(todate, "notafter");
		} else {
			ctx.markValidatedAttribute(todate, "standarddate");
		}
		
		// TODO: parse date/time and check its precision regarding defined format
	}

	private void validateFromdate(Fromdate fromdate, String format) {
		String srcdate = fromdate.getStandarddate();
		boolean estimate = false;
		if(srcdate==null) {
			srcdate = fromdate.getNotbefore();
			if(srcdate==null) {
				throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Není nastaven ani jeden z atributů standarddate, či notbefore.", ctx.formatEadPosition(fromdate));
			}
			ctx.markValidatedAttribute(fromdate, "notbefore");
			estimate = true;
		} else {
			ctx.markValidatedAttribute(fromdate, "standarddate");
		}
		
		// TODO: parse date/time and check its precision regarding defined format 
	}
}
