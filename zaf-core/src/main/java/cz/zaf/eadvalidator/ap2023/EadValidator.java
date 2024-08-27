package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.eadvalidator.ap2023.layers.enc.EncodingValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.wf.WellFormedLayer;
import cz.zaf.eadvalidator.ap2023.layers.ns.NamespaceValidationLayer;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;

public class EadValidator implements ValidatorListener<EadValidationContext> {

    List<ValidationLayer<EadValidationContext>> validations;

    public EadValidator(EadValidationProfile profilValidace, List<String> excludeChecks) {
        this.validations = prepareValidations(profilValidace);
    }

    private List<ValidationLayer<EadValidationContext>> prepareValidations(EadValidationProfile profilValidace) {
        List<ValidationLayer<EadValidationContext>> validations = new ArrayList<>(2);
        validations.add(new EncodingValidationLayer());
        validations.add(new WellFormedLayer());
        validations.add(new NamespaceValidationLayer());
        return validations;
    }

    public void validate(EadLoader eadLoader) {

        EadValidationContext context = new EadValidationContext(eadLoader);

        BaseValidator<EadValidationContext> validator = new BaseValidator<>(validations);
        validator.registerListener(this);
        validator.validate(context);
    }

    @Override
    public void layerValidationStarted(EadValidationContext context, ValidationLayer<EadValidationContext> layer) {
    	/*
        if (layer.getType() == ValidationLayers.NAMESPACE) {
        	log.debug("Parsing EAD, file: {}", context.getLoader().getFilePath());
            // load XML document if well-formatted
            Document doc = context.getLoader().load();
            if(doc==null) {
            	log.error("Parsing failed, file: {}", context.getLoader().getFilePath(), context.getLoader().getParserError());
            	
            	throw new ZafException(XmlCode.FAILED_TO_PARSE, "Nepodařilo se načíst soubor: " + context.getLoader().getFilePath(),
            			context.getLoader().getParserError());
            }
        }
        */
    }

    @Override
    public void layerValidationFinished(EadValidationContext context, ValidationLayer<EadValidationContext> layer) {
        if (layer.getType() == ValidationLayers.WELL_FORMED) {
        	// kontrola nacteni dokumentu
        	var document = context.getLoader().getDocument();
        	if(document==null) {
        		throw new NullPointerException("Document is null");
        	}
        	context.setRootNode(document.getDocumentElement());
        }
    }
}
