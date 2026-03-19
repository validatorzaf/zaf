package cz.zaf.eadvalidator.ap2023.layers.obs.obs100_109;

import org.apache.commons.lang3.StringUtils;

import cz.zaf.common.exceptions.ZafException;
import cz.zaf.common.exceptions.codes.BaseCode;
import cz.zaf.eadvalidator.ap2023.EadRule;
import cz.zaf.schema.ead3.Control;
import cz.zaf.schema.ead3.Objectxmlwrap;
import cz.zaf.schema.ead3.Source;
import cz.zaf.schema.ead3.Sources;
import java.util.List;

public class Rule106 extends EadRule {

    static final public String CODE = "obs106";
    static final public String RULE_TEXT = "Element <sources> obsahuje alespoň jeden element <source>. Každý element <source> má neprázdný atribut \"id\" a obsahuje právě jeden element <objectxmlwrap>. Element <source> neobsahuje element <sourceentry> ani <descriptivenote>.";
    static final public String RULE_ERROR = "Element <sources> neobsahuje žádný element <source> nebo element <source> nemá neprázdný atribut \"id\" a/nebo neobsahuje právě jeden element <objectxmlwrap>. Případně element <source> obsahuje neočekávaný element <sourceentry> nebo <descriptivenote>.";
    static final public String RULE_SOURCE = "Část 8.3 profilu EAD3 MV ČR";

    public Rule106() {
        super(CODE, RULE_TEXT, RULE_ERROR, RULE_SOURCE);
    }

    @Override
    protected void evalImpl() {
        Control control = ctx.getEad().getControl();
        Sources sources = control.getSources();
        if (sources == null) {
            return;
        }

        List<Source> sourceList = sources.getSource();
        if (sourceList.isEmpty()) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element sources neobsahuje žádný element source.", ctx.formatEadPosition(sources));
        }

        ctx.markValidatedElement(sources);
        for (Source source : sourceList) {
            validateSource(source);
        }        
    }

    private void validateSource(Source source) {
        String id = source.getId();
        if (StringUtils.isBlank(id)) {
            throw new ZafException(BaseCode.CHYBI_ATRIBUT, "Element source nemá vyplněný atribut id.", ctx.formatEadPosition(source));
        }

        if (!source.getSourceentry().isEmpty()) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Element source obsahuje nepovolený element sourceentry.", ctx.formatEadPosition(source));
        }

        if (source.getDescriptivenote() != null) {
            throw new ZafException(BaseCode.NEPOVOLENY_ELEMENT, "Element source obsahuje nepovolený element descriptivenote.", ctx.formatEadPosition(source));
        }

        Objectxmlwrap objectxmlwrap = source.getObjectxmlwrap();
        if (objectxmlwrap == null) {
            throw new ZafException(BaseCode.CHYBI_ELEMENT, "Element source neobsahuje element objectxmlwrap.", ctx.formatEadPosition(source));
        }

        ctx.markValidatedAttribute(source, "id");
        ctx.markValidatedElement(objectxmlwrap);
        ctx.markValidatedTree(objectxmlwrap);
    }
}
