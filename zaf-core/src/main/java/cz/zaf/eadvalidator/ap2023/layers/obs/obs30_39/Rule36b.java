package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Archdesc;
import cz.zaf.schema.ead3.Chronitem;
import cz.zaf.schema.ead3.Chronlist;
import cz.zaf.schema.ead3.Daterange;
import cz.zaf.schema.ead3.Event;
import cz.zaf.schema.ead3.Fileplan;
import cz.zaf.schema.ead3.Head;
import cz.zaf.schema.ead3.P;
import java.io.Serializable;
import java.util.List;

public class Rule36b extends EadRule {

    static final public String CODE = "obs36b";
    static final public String RULE_TEXT = "Element <fileplan> má neprázdný atribut \"encodinganalog\". Obsahuje element <head> a může obsahovat jeden element <p>. Dále obsahuje jeden element <chronlist>, který obsahuje jeden element <chronitem>, který obsahuje element <daterange> a jeden element <event> o hodnotě \"Platnost spisového plánu\".";
    static final public String RULE_ERROR = "Elementu <fileplan> chybí atribut \"encodinganalog\" nebo je tento prázdný. Nebo neobsahuje element <head> či obsahuje více jak jeden element <p>. Nebo chybí element <chronlist> či je uveden víckrát, nebo v něm nejsou správně zanořeny nebo nejsou vůbec zanořeny elementy <head>, <p>, <chronitem>, <event> a <daterange>.";
    static final public String RULE_SOURCE = "Část 3.1.3 profilu EAD3 MV ČR";

    public Rule36b() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Archdesc archDesc = ctx.getEad().getArchdesc();
        List<Object> archDescChildList = archDesc.getAccessrestrictOrAccrualsOrAcqinfo();
        validate(archDescChildList);

        ctx.getEadLevelIterator().iterate((c, parent) -> {
            List<Object> cChildList = c.getMDescBase();
            validate(cChildList);
        });

    }

    private void validate(List<Object> childList) {
        for (Object obj : childList) {
            if (obj instanceof Fileplan fileplan) {
                String encodinganalog = fileplan.getEncodinganalog();
                if (StringUtils.isBlank(encodinganalog)) {
                    throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Chybí nebo je prázdný atribut encodinganalog.", ctx.formatEadPosition(fileplan));
                }
                ctx.markValidatedAttribute(fileplan, "encodinganalog");
                
                // check id
                if(fileplan.getId()!=null) {
                	ctx.markValidatedAttribute(fileplan, "id");
                }
                
                Head head = fileplan.getHead();
                if (head == null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Chybí element head.", ctx.formatEadPosition(fileplan));
                }
                ctx.markValidatedElement(head);
                List<Object> chronlistOrListOrTable = fileplan.getChronlistOrListOrTable();
                P oneP = null;
                Chronlist oneChronlist = null;
                for (Object child : chronlistOrListOrTable) {
                    if (child instanceof P p) {
                        if (oneP != null) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element p.", ctx.formatEadPosition(fileplan));
                        }
                        oneP = p;
                    }
                    if (child instanceof Chronlist chronlist) {
                        if (oneChronlist != null) {
                            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Nalezen nepovolený element chronlist.", ctx.formatEadPosition(fileplan));
                        }
                        oneChronlist = chronlist;
                        validateChronlist(chronlist);
                    }
                }

                if(oneChronlist==null) {
                    throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element chronlist.", ctx.formatEadPosition(fileplan));
                }

            }
        }
    }

    private void validateChronlist(Chronlist chronlist) {
        List<Chronitem> chronitemList = chronlist.getChronitem();
        if (chronitemList.isEmpty()) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen žádný element chronitem.", ctx.formatEadPosition(chronlist));
        }
        if (chronitemList.size() != 1) {
            throw new ZafException(BaseCode.CHYBNY_ELEMENT, "Nalezen nepovolený element chronitem.", ctx.formatEadPosition(chronlist));
        }
        ctx.markValidatedElement(chronlist);
        Chronitem chronitem = chronitemList.get(0);
        ctx.markValidatedElement(chronitem);
        Daterange daterange = chronitem.getDaterange();
        if (daterange == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element daterange.", ctx.formatEadPosition(chronitem));
        }                
        ctx.markValidatedElement(daterange);
        var fromdate = daterange.getFromdate();
        if(fromdate!=null) {
        	if(fromdate.getStandarddate()==null) {
        		throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Není nastaven atribut standarddate", ctx.formatEadPosition(fromdate));
        	}
        	ctx.markValidatedAttribute(fromdate, "standarddate");
        	// TODO: check format of fromdate.getStandarddate()
        }
        var todate = daterange.getTodate();
        if(todate!=null) {
        	if(todate.getStandarddate()==null) {
        		throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Není nastaven atribut standarddate", ctx.formatEadPosition(todate));
        	}
            ctx.markValidatedAttribute(todate, "standarddate");
            // TODO: check format of todate.getStandarddate() 
        }
        if(fromdate==null && todate==null) {
        	throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element fromdate ani todate.", ctx.formatEadPosition(daterange));
        }
        
        Event event = chronitem.getEvent();
        if (event == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element event.", ctx.formatEadPosition(chronitem));
        }
        ctx.markValidatedElement(event);
        ctx.markValidatedContent(event);
        validateContent(event);
    }

    private void validateContent(Event event) {
        List<Serializable> content = event.getContent();
        if (content.size() != 1) {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybí hodnota v elementu.", ctx.formatEadPosition(event));
        }
        Serializable partContent = content.get(0);
        if (partContent instanceof String str) {
            if (!StringUtils.equals("Platnost spisového plánu", str)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element neobsahuje požadovanou hodnotu.", ctx.formatEadPosition(event));
            }
        } else {
            throw new ZafException(BaseCode.CHYBI_HODNOTA_ELEMENTU, "Chybný typ hodnoty v elementu.", ctx.formatEadPosition(event));
        }
    }

}
