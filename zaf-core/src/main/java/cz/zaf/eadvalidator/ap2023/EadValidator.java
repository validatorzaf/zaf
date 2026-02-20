package cz.zaf.eadvalidator.ap2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.zaf.common.validation.BaseValidator;
import cz.zaf.common.validation.ValidationSubprofile;
import cz.zaf.common.validation.ValidationLayer;
import cz.zaf.common.validation.ValidatorListener;
import cz.zaf.eadvalidator.ap2023.layers.enc.EncodingValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.wf.WellFormedLayer;
import cz.zaf.eadvalidator.ap2023.profile.AP2023Profile;
import cz.zaf.eadvalidator.ap2023.layers.ns.NamespaceValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.val.SchemaValidationLayer;
import cz.zaf.eadvalidator.ap2023.layers.obs.ContentValidationLayer;

public class EadValidator implements ValidatorListener<EadValidationContext> {

    List<ValidationLayer<EadValidationContext>> validations;
    
    /**
     * List of excluded checks
     */
	private List<String> excludeChecks;
	
	final String innerFileName;

	private ValidationSubprofile validationProfile;

    public EadValidator(ValidationSubprofile profilValidace, List<String> excludeChecks, final String innerFileName) {
    	this.excludeChecks = excludeChecks;
    	this.innerFileName = innerFileName;
    	this.validationProfile = profilValidace;
        this.validations = prepareValidations(profilValidace);        
    }

    private List<ValidationLayer<EadValidationContext>> prepareValidations(ValidationSubprofile profilValidace) {
        List<ValidationLayer<EadValidationContext>> validations = new ArrayList<>(5);
        validations.add(new EncodingValidationLayer(innerFileName));
        validations.add(new WellFormedLayer(innerFileName));
        validations.add(new NamespaceValidationLayer(innerFileName));
        validations.add(new SchemaValidationLayer(innerFileName));
        validations.add(new ContentValidationLayer(profilValidace, innerFileName));
        return validations;
    }

    public void validate(EadLoader eadLoader) {
    	
    	// set default profix based on profile
    	String eadNsPrefix = null;
    	if(AP2023Profile.ARCH_DESC==validationProfile||
    			AP2023Profile.FINDING_AID==validationProfile) {
    		eadNsPrefix = "ead";
    	}

        EadValidationContext context = new EadValidationContext(eadLoader, excludeChecks, eadNsPrefix);

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
