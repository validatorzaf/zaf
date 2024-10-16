package cz.zaf.earkvalidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.InnerFileValidator;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.earkvalidator.AipValidationContext.InnerValidatorRequest;
import cz.zaf.earkvalidator.layers.dat.DataValidationLayer;
import cz.zaf.earkvalidator.layers.enc.EncodingValidationLayer;
import cz.zaf.earkvalidator.layers.wf.WellFormedLayer;
import cz.zaf.earkvalidator.layers.ns.NamespaceValidationLayer;
import cz.zaf.earkvalidator.layers.val.SchemaValidationLayer;
import cz.zaf.earkvalidator.layers.obs.ContentValidationLayer;
import cz.zaf.earkvalidator.layers.fls.FilesValidationLayer;
import cz.zaf.earkvalidator.profile.DAAIP2024Profile;

public class AipValidator implements ValidatorListener<AipValidationContext> {
	
	static private Logger log = LoggerFactory.getLogger(AipValidator.class);

	private List<String> excludeChecks;
	private List<ValidationLayer<AipValidationContext>> validations;

	public AipValidator(DAAIP2024Profile daaip2024Profile, List<String> excludeChecks) {
		this.excludeChecks = excludeChecks;

		validations = prepareValidations(daaip2024Profile);
	}

	private List<ValidationLayer<AipValidationContext>> prepareValidations(DAAIP2024Profile daaip2024Profile) {
		List<ValidationLayer<AipValidationContext>> validations = new ArrayList<>();
		validations.add(new DataValidationLayer(daaip2024Profile));
		validations.add(new EncodingValidationLayer());
		validations.add(new WellFormedLayer());
		validations.add(new NamespaceValidationLayer());
		validations.add(new SchemaValidationLayer());
		validations.add(new ContentValidationLayer());
		validations.add(new FilesValidationLayer());
		return validations; 
	}

	public void validate(AipLoader aipLoader) {

		AipValidationContext aipValidationContext = new AipValidationContext(aipLoader, excludeChecks);		
		
		BaseValidator<AipValidationContext> validator = new BaseValidator<>(validations);
		validator.registerListener(this);
		validator.validate(aipValidationContext);
		
		// run additional validators
		for(int i = 0; i < aipValidationContext.getInnerValidators().size(); i++) {
			InnerValidatorRequest request = aipValidationContext.getInnerValidators().get(i);
			InnerFileValidator<AipValidationContext> innerValidator = request.getInnerValidator();
			try {
				innerValidator.validate(aipValidationContext, request.getInnerFilePath(), aipValidationContext.getValidationResult());
			} catch(Exception e) {
	            StringBuilder sb = new StringBuilder();
	            sb.append("Uncatched exception");
	            if (innerValidator != null) {
	                sb.append(", aktivniKontrola: ").append(innerValidator);
	            }
	            log.error(sb.toString() + ", detail: " + e.toString(), e);
	            throw new IllegalStateException(sb.toString(), e);
			}
		}
	}

	@Override
	public void layerValidationStarted(AipValidationContext context, ValidationLayer<AipValidationContext> layer) {
        if(layer.getType()==ValidationLayers.NAMESPACE) {
        	// kontrola nacteni dokumentu
        	var document = context.getLoader().getMetsDocument();
        	if(document==null) {
        		throw new NullPointerException("Document is null");
        	}
        	context.setMetsRootElement(document.getDocumentElement());
        }
        if(layer.getType()==ValidationLayers.OBSAH) {
        	// overeni nahrani JAXB
        	if(context.getLoader().getMets()==null) {
        		try {
        			// Pokud by uzivatel preskocil pravidlo val.Rule1,
        			// tak je nutne manualni nacteni JAXB zde
        			context.getLoader().loadMetsJaxb();
        		} catch(Exception e) {
        			throw new NullPointerException("Failed to load JAXB object");
        		}
        	}
        	Objects.requireNonNull(context.getLoader().getMets());
        }
	}

	@Override
	public void layerValidationFinished(AipValidationContext context, ValidationLayer<AipValidationContext> layer) {
		// NOP		
	}

}
