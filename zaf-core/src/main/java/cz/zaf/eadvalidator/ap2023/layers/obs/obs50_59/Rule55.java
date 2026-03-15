package cz.zaf.eadvalidator.ap2023.layers.obs.obs50_59;

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
import java.io.Serializable;
import java.util.List;

public class Rule55 extends EadRule {

    static final public String CODE = "obs55";
    static final public String RULE_TEXT = "Každý element <daterange>, který je obsažen v hierarchii elementů <did><unitdatestructured>, má atribut \"altrender\" o hodnotě, která odpovídá způsobu konstrukce formátu datace (tj. obsahuje pouze C/Y/YM/D/DT/-, přičemž znak \"-\" nesmí být na začátku nebo na konci řetězce), a obsahuje právě jeden neprázdný element <fromdate> a právě jeden neprázdný element <todate>.";
    static final public String RULE_ERROR = "Některý element <daterange> nemá atribut \"altrender\" nebo tento atribut obsahuje nepovolenou hodnotu. Případně element <daterange> neobsahuje právě jen element <fromdate> a/nebo právě jeden <todate>. Případně je element <fromdate> a/nebo <todate> prázdný.";
    static final public String RULE_SOURCE = "Část 5.8 profilu EAD3 MV ČR";
    
    public static enum UnitdateFormatType {
		C("C"), Y("Y"), YM("YM"), D("D"), DT("DT");
		
		private final String value;
		
		UnitdateFormatType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

    public Rule55() {
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
                String altrender = daterange.getAltrender();
                if(StringUtils.isEmpty(altrender)){
                    throw new ZafException(BaseCode.CHYBI_HODNOTA_ATRIBUTU, "Chybí hodnota atributu altrender.", ctx.formatEadPosition(daterange));
                }
                validateAltrender(altrender, daterange);
                Fromdate fromdate = daterange.getFromdate();
                if(fromdate == null){
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element fromdate.", ctx.formatEadPosition(daterange));
                }
                Todate todate = daterange.getTodate();
                if(todate == null){
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element todate.", ctx.formatEadPosition(daterange));
                }
                validateDate(fromdate.getContent(), fromdate);
                validateDate(todate.getContent(), todate);
            }
        }
    }

    private void validateAltrender(String altrender, Daterange daterange) {
    	var formats = altrender.split("-");
    	if(formats.length > 2) {
    		throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut altrender obsahuje nepovolenou hodnotu: " + altrender + ". Atribut altrender může obsahovat nejvýše jeden znak '-'.", ctx.formatEadPosition(daterange));
    	}
    	for(String format: formats) {
    		boolean validFormat = false;
			for(UnitdateFormatType type: UnitdateFormatType.values()) {
				if(type.getValue().equals(format)) {
					validFormat = true;
					break;
				}
			}
			if(!validFormat) {
				throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut altrender obsahuje nepovolenou hodnotu: " + altrender + ". Neznámý formát datace: " + format, ctx.formatEadPosition(daterange));
			}
    	}
	}

	private void validateDate(List<Serializable> content, Object object) {
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybná hodnota v elementu.", ctx.formatEadPosition(object));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (StringUtils.isBlank(str)) {
                throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Prázdná hodnota elementu.", ctx.formatEadPosition(object));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(object));
        }
    }
}
