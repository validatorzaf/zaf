package cz.zaf.eadvalidator.ap2023.layers.obs.obs30_39;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Agent;
import cz.zaf.schema.ead3.Agenttype;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Eventdatetime;
import cz.zaf.schema.ead3.Eventtype;
import cz.zaf.schema.ead3.Maintenanceevent;
import cz.zaf.schema.ead3.Maintenancehistory;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Rule31 extends EadRule {

    static final public String CODE = "obs31";
    static final public String RULE_TEXT = "Existuje právě jeden element <ead:maintenanceevent>, který obsahuje povinné části dle specifikace. Kontroluje se existence podřízených elementů: eventtype, eventdatetime, agenttype a agent a jejich správné uvedení.";
    static final public String RULE_ERROR = "Neexistuje element <ead:maintenanceevent>, který neobsahuje všechny povinné části dle specifikace.";
    static final public String RULE_SOURCE = "Část 2.7 profilu EAD3 MV ČR";

    public Rule31() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Control control = ctx.getEad().getControl();
        Maintenancehistory maintenancehistory = control.getMaintenancehistory();
        if (maintenancehistory == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen element maintenancehistory.", ctx.formatEadPosition(control));
        }
        List<Maintenanceevent> maintenanceeventList = maintenancehistory.getMaintenanceevent();
        if (maintenanceeventList.isEmpty()) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element maintenanceevent.", ctx.formatEadPosition(maintenancehistory));
        }
        Maintenanceevent found = null;
        for (Maintenanceevent maintenanceevent : maintenanceeventList) {
            //Eventtype 31 - povinný ze schématu
            Eventtype eventtype = maintenanceevent.getEventtype();
            if (!"created".equals(eventtype.getValue())) {
                throw new ZafException(BaseCode.DUPLICITA, "Nalezen duplicitní element maintenanceevent.", ctx.formatEadPosition(maintenanceevent));
            }

            //Eventdatetime 32
            Eventdatetime eventdatetime = maintenanceevent.getEventdatetime();
            String standarddatetime = eventdatetime.getStandarddatetime();
            String content = eventdatetime.getContent();
            if (StringUtils.isBlank(standarddatetime)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Element eventdatetime nemá vyplněn atribut eventdatetime.", ctx.formatEadPosition(maintenanceevent));
            }
            boolean isDateValid = isIso8601(standarddatetime);
            if (!isDateValid) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut eventdatetime neobsahuje hodnotu v očekávaném formátu.", ctx.formatEadPosition(maintenanceevent));
            }

            if (!StringUtils.equals(content, standarddatetime)) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Odlišná hodnota atributu a elementu eventdatetime.", ctx.formatEadPosition(eventdatetime));
            }

            //33 Agenttype
            Agenttype agenttype = maintenanceevent.getAgenttype();
            if (!"machine".equals(agenttype.getValue())) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ATRIBUTU, "Atribut agenttype:value neobsahuje očekávanou hodnotu machine.", ctx.formatEadPosition(agenttype));
            }

            //34 Agent
            Agent agent = maintenanceevent.getAgent();
            if (StringUtils.isBlank(agent.getContent())) {
                throw new ZafException(BaseCode.CHYBNA_HODNOTA_ELEMENTU, "Element agent není vyplněn.", ctx.formatEadPosition(agent));
            }

            found = maintenanceevent;
        }
        if (found == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Nenalezen požadovaný element maintenanceevent.", ctx.formatEadPosition(maintenancehistory));
        }

    }

    private boolean isIso8601(String standarddatetime) {
        try {
            OffsetDateTime.parse(standarddatetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
