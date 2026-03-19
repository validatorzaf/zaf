package cz.zaf.eadvalidator.ap2023.layers.obs.obs40_49;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Accruals;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Did;
import cz.zaf.schema.ead3.Didnote;
import cz.zaf.schema.ead3.Scopecontent;
import cz.zaf.schema.ead3.Unittitle;
import cz.zaf.schemas.ead.EadNS;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

public class Rule42 extends EadRule {

    static final public String CODE = "obs42";
    static final public String RULE_TEXT = "Elementy <unittitle>, <scopecontent>, <accruals> nebo <didnote> se mohou vyskytovat vícekrát v rámci rodičovského elementu, pokud se liší hodnotou atributu \"lang\", existencí atributu \"audience\" nebo hodnotou atributu \"audience\", případně mají nastavenu hodnotu atributu localtype v souladu s ostatními pravidly.";
    static final public String RULE_ERROR = "Dvojice elementů  <unittitle>, <scopecontent>, <relation>, <accruals> nebo <didnote> na stejné úrovni má špatně uvedené nebo vyplněné atributy \"audience\".";
    static final public String RULE_SOURCE = "Část 3.6 profilu EAD3 MV ČR";

    static List<ComparedObect> siblingsList;

    private static class ComparedObect {

        String name, audience, lang, localtype;
        // Link to source object to display potential conflict
        Object srcObject;
        
        public ComparedObect(@Nonnull final String name, final String audience, final String lang, final Object srcObject) {
        	this(name, audience, lang, null, srcObject);
        }

        public ComparedObect(@Nonnull final String name, final String audience, final String lang, final String localtype, final Object srcObject) {
            if (name == null) {
                throw new IllegalStateException("missing object name");
            }
            this.name = name;

            if (audience == null) {
                this.audience = EadNS.AUDIENCE_EXTERNAL;
            } else {
            	this.audience = audience;
            }

            if (lang == null) {
                this.lang = "cze";
            } else {
            	this.lang = lang;
            }

            this.srcObject = srcObject;
            this.localtype = localtype;
        }

        public String getAudience() {
            return audience;
        }

        public String getLang() {
            return lang;
        }

        public String getName() {
            return name;
        }

        public Object getSrcObject() {
            return srcObject;
        }
        
        @Override
        public boolean equals(Object otherObj) {
        	if(otherObj==null) {
        		return false;
        	}
        	if (otherObj instanceof ComparedObect co) {
				return Objects.equals(name, co.name) &&
					Objects.equals(audience, co.audience) &&
					Objects.equals(lang, co.lang) &&
					Objects.equals(localtype, co.localtype);				
			} else {
				return false;
			}
        }
        
        @Override
        public int hashCode() {
        	return name.hashCode();
        }
    }

    public Rule42() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();

        Did didA = archDesc.getDid();
        
        siblingsList = new ArrayList<>();
        getFromDid(didA);

        List<Object> listA = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        getFromMain(listA);

        compare();

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            Did didC = c.getDid();
            siblingsList = new ArrayList<>();
            getFromDid(didC);

            List<Object> listC = c.getMDescBase();
            getFromMain(listC);
            
            compare();
        });
    }

    private void getFromDid(Did did) {
        List<Object> mDid = did.getMDid();
        for (Object object : mDid) {
            if (object instanceof Unittitle unittitle) {
                String audience = unittitle.getAudience();
                String lang = unittitle.getLang();
                String localtype = null;
                if("FORMAL_TITLE".equals(unittitle.getLocaltype())) {
                	localtype = unittitle.getLocaltype();
                }
                siblingsList.add(new ComparedObect("unittitle", audience, lang, localtype, unittitle));
                if(audience!=null) {
                	ctx.markValidatedAttributeOnly(unittitle, "audience");
                }
            } else
            if (object instanceof Didnote didnote) {
                String audience = didnote.getAudience();
                String lang = didnote.getLang();
                siblingsList.add(new ComparedObect("didnote", audience, lang, didnote));
                if(audience!=null) {
                	ctx.markValidatedAttributeOnly(didnote, "audience");
                }
            }
        }
    }

    private void getFromMain(List<Object> list) {
        for (Object object : list) {
            if (object instanceof Scopecontent scopecontent) {
                String audience = scopecontent.getAudience();
                String lang = scopecontent.getLang();
                siblingsList.add(new ComparedObect("scopecontent", audience, lang, scopecontent));
            }
            if (object instanceof Accruals accruals) {
                String audience = accruals.getAudience();
                String lang = accruals.getLang();
                siblingsList.add(new ComparedObect("accruals", audience, lang, accruals));
            }
        }
    }

    private void compare() {
    	if(CollectionUtils.isEmpty(siblingsList)) {
    		return;
    	}
        Set<ComparedObect> seen = new HashSet<>();

        for (ComparedObect co : siblingsList) {
            if (!seen.add(co)) {
                throw new ZafException(BaseCode.DUPLICITA, "Opakovaný výskyt elementu.", ctx.formatEadPosition(co.getSrcObject()));
            }
        }
    }

}
