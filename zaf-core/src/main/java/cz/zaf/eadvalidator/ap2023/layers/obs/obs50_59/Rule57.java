package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

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
import cz.zaf.schema.ead3.Unitdatestructured;

public class Rule57  extends EadRule {

    static final public String CODE = "obs57";
    static final public String RULE_TEXT = "Element <fromdate> má buď atribut \"standarddate\", nebo \"notbefore\" a současně element <todate> má buď atribut \"standarddate\", nebo \"notafter\". Dále hodnota použitého atributu odpovídá zápisu data a času dle ČSN ISO  8601 (xs:datetime) a formátu datace podle hodnoty atributu \"altrender\" použitého v rodičovském elementu <daterange>. Nakonec hodnota použitého atributu v elementu <todate> je větší než hodnota použitého atributu v elementu <fromdate>.";
    static final public String RULE_ERROR = "Element <fromdate> nemá atribut \"standarddate\" nebo \"notbefore\" anebo element <todate> nemá atribut \"standarddate\" nebo \"notafter\". Případně hodnota použitého atributu neobsahuje hodnotu v požadovaném formátu, nebo neodpovídá platnému časovému rozsahu.";
    static final public String RULE_SOURCE = "Část 5.8 profilu EAD3 MV ČR";
    
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
                var convFormats = new UnitdateFormatType[formats.length];
                for(int i = 0; i<formats.length; i++) {
                	try {
                		convFormats[i] = UnitdateFormatType.valueOf( formats[i] ); 
                	} catch (DateTimeParseException dpe) {
            			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
            					"Chybná hodnota atributu \"altrender\", hodnota: " + altrender + ".", 
            					ctx.formatEadPosition(unitDateStructured), dpe);                		
                	}
                }
                
                Fromdate fromdate = daterange.getFromdate();
                if(fromdate != null){
                	validateFromdate(fromdate, convFormats[0]);
                }
                Todate todate = daterange.getTodate();
                if(todate != null){
                	validateTodate(todate, convFormats[(convFormats.length>1)?1:0]);
                }
            }
        }
}

	private void validateTodate(Todate todate, UnitdateFormatType format) {
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
		
		LocalDateTime ldt;
		try {
			ldt = LocalDateTime.parse(srcdate);
		} catch(DateTimeParseException dpe) {			
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
						"Chybná hodnota atributu " + (estimate?"standarddate":"notafter") + ", hodnota: " + srcdate + ".", 
						ctx.formatEadPosition(todate), dpe);
		}
				
		// check its precision regarding defined format		
		if(!format.validateTo(ldt)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
					"Chybná hodnota atributu " + (estimate?"standarddate":"notafter") + ", hodnota: " + srcdate + " a uváděné přenosti: " + format + ".", 
					ctx.formatEadPosition(todate));
		}
	}

	private void validateFromdate(Fromdate fromdate, UnitdateFormatType format) {
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
		
		LocalDateTime ldt;
		try {
			ldt = LocalDateTime.parse(srcdate);
		} catch(DateTimeParseException dpe) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
					"Chybná hodnota atributu " + (estimate?"standarddate":"notbefore") + ", hodnota: " + srcdate + ".", 
					ctx.formatEadPosition(fromdate), dpe);
		}
		// check its precision regarding defined format		
		if(!format.validateFrom(ldt)) {
			throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, 
					"Chybná hodnota atributu " + (estimate?"standarddate":"notbefore") + ", hodnota: " + srcdate + " a uváděné přenosti: " + format + ".", 
					ctx.formatEadPosition(fromdate));
		}
	}
}
