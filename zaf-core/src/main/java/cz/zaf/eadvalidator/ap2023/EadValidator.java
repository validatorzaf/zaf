package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.eadvalidator.ap2023.layers.enc.EncodingValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.wf.WellFormedLayer;
import cz.zaf.eadvalidator.ap2023.layers.ns.NamespaceValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.val.SchemaValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.obs.ContentValidationLayer;
import cz.zaf.eadvalidator.ap2023.profile.EadValidationProfile;

public class EadValidator implements ValidatorListener<EadValidationContext> {

    List<ValidationLayer<EadValidationContext>> validations;

    public EadValidator(EadValidationProfile profilValidace, List<String> excludeChecks) {
    	// TODO: process exclude checks
        this.validations = prepareValidations(profilValidace);
    }

    private List<ValidationLayer<EadValidationContext>> prepareValidations(EadValidationProfile profilValidace) {
        List<ValidationLayer<EadValidationContext>> validations = new ArrayList<>(2);
        validations.add(new EncodingValidationLayer());
        validations.add(new WellFormedLayer());
        validations.add(new NamespaceValidationLayer());
        validations.add(new SchemaValidationLayer());
        validations.add(new ContentValidationLayer(profilValidace));
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
        if(layer.getType()==ValidationLayers.NAMESPACE) {
        	// kontrola nacteni dokumentu
        	var document = context.getLoader().getDocument();
        	if(document==null) {
        		throw new NullPointerException("Document is null");
        	}
        	context.setRootNode(document.getDocumentElement());
        }
        if(layer.getType()==ValidationLayers.OBSAH) {
        	// overeni nahrani JAXB
        	if(context.getLoader().getEad()==null) {
        		try {
        			// Pokud by uzivatel preskocil pravidlo val.Rule1,
        			// tak je nutne manualni nacteni JAXB zde
        			context.getLoader().loadJaxb();
        		} catch(Exception e) {
        			throw new NullPointerException("Failed to load JAXB object");
        		}
        	}
        	Objects.requireNonNull(context.getLoader().getEad());
        }
    }

    @Override
    public void layerValidationFinished(EadValidationContext context, ValidationLayer<EadValidationContext> layer) {
    }
}
