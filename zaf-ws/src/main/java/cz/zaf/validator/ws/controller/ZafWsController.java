package cz.zaf.validator.ws.controller;


import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cz.zaf.api.rest.controller.ValidationApi;
import cz.zaf.api.rest.model.DataPackageValidationResult;
import cz.zaf.api.rest.model.ReceivedValidationRequest;
import cz.zaf.api.rest.model.RelatedEntity;
import cz.zaf.api.rest.model.RelatedEntityType;
import cz.zaf.api.rest.model.RequestProcessState;
import cz.zaf.api.rest.model.RuleProfile;
import cz.zaf.api.rest.model.ValidationJsonResult;
import cz.zaf.api.rest.model.ValidationLevel;
import cz.zaf.api.rest.model.ValidationLevelStatus;
import cz.zaf.api.rest.model.ValidationRequestStatus;
import cz.zaf.api.rest.model.ValidationRule;
import cz.zaf.api.rest.model.ValidationType;
import cz.zaf.schema.validace_v1.TBalicek;
import cz.zaf.schema.validace_v1.TTypEntity;
import cz.zaf.schema.validace_v1.Validace;
import cz.zaf.schema.validation_v2.TPackage;
import cz.zaf.schema.validation_v2.Validation;
import cz.zaf.validator.ws.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
public class ZafWsController implements ValidationApi {
	
	private static final Logger log = LoggerFactory.getLogger(ZafWsController.class);
		
	@Autowired
	private ValidationService validationService;
		
	ObjectMapper objectMapper = new ObjectMapper();
	{
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.registerModule(new JavaTimeModule());
	}

    @Override
	public ResponseEntity validationsResult(String validationRequestId, 
			@NotNull String accept,
			@Size(min = 1, max = 36) String xCorrelationId) {
		RequestProcessState state = this.validationService.getStatus(validationRequestId);
		if(state == null) {
			return ResponseEntity.notFound().build(); 
		}
		
		String contentType = "application/xml";
		byte[] result = null;
		if("application/json".equals(accept)) {
			// try to create json result
			result = convertXmlToJson(validationRequestId);
			contentType = "application/json";
		} else {
			// by default read stored XML
			result = this.validationService.getResultAsBytes(validationRequestId);
		}
		// ValidationXmlResult vxr = new ValidationXmlResult(result);
		
		// by default return application/xml
		ResponseEntity re = ResponseEntity.ok()
				.header("Content-Type", contentType)
				.header("X-Correlation-Id", xCorrelationId)
				.body(result);
		return re;
	}

	private byte[] convertXmlToJson(String validationRequestId) {
		Validation validace = this.validationService.getResult(validationRequestId);
		
		ValidationJsonResult vjr = new ValidationJsonResult();
		vjr.setAppName(validace.getApplicationName());
		vjr.setAppVersion(validace.getApplicationVersion());
		ValidationType vt = ValidationType.fromValue(validace.getValidationType());

		vjr.setProfile(validace.getValidationProfile());
		vjr.setValidationType(vt);
		vjr.setValidationDate(
				validace.getValidationTimestamp()
					.toGregorianCalendar()
					.toZonedDateTime()
					.toOffsetDateTime());
		vjr.setRuleVersion(validace.getRulesVersion().intValue());
		vjr.setRequestId(validace.getValidationIdentifier());
		
		for(var balicek: validace.getPackage()) {
			convertBalicek(balicek, vjr);
		}
		
		try {
			String jsonString = objectMapper.writeValueAsString(vjr);
			
			byte[] byteArray = jsonString.getBytes("UTF-8");
			return byteArray;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert xml to json", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to convert xml to json", e);
		}		
	}

	private void convertBalicek(TPackage balicek, ValidationJsonResult vjr) {
		DataPackageValidationResult dpr = new DataPackageValidationResult();
		dpr.setId(balicek.getIdentifier());
		dpr.setName(balicek.getFileName());
		vjr.addDataPackagesItem(dpr);
		
		balicek.getCheck().forEach(kontrola -> {
			ValidationLevel vl = new ValidationLevel();
			vl.setName(kontrola.getName());
			vl.setFileName(kontrola.getEmbeddedFile());
			if(kontrola.getStatus() != null) {
				switch(kontrola.getStatus()) {
				case OK:
					vl.setStatus(ValidationLevelStatus.OK);
					break;
				case NOT_EXECUTED:
					vl.setStatus(ValidationLevelStatus.NOT_VALIDATED);
					break;
				case ERROR:
					vl.setStatus(ValidationLevelStatus.ERROR);
					break;
				}
			}
			dpr.addLevelsItem(vl);
			
			kontrola.getRule().forEach(pravidlo -> {
				ValidationRule vr = new ValidationRule();
				vr.setCode(pravidlo.getCode());
				vr.setDescription(pravidlo.getErrorDescription());
				vr.setDetail(pravidlo.getErrorReport());
				vr.setErrorCode(pravidlo.getErrorCode());
				vr.setLocation(pravidlo.getErrorLocation());
				vr.setRule(pravidlo.getText());
				vr.setSource(pravidlo.getSource());
				if(pravidlo.getEntity()!=null) {
					pravidlo.getEntity().getIdentifier().forEach(identEntity -> {
						RelatedEntity rei = new RelatedEntity();
						rei.setId(identEntity.getValue());
						rei.setSource(identEntity.getSource());
						if(identEntity.getType()!=null) {
							RelatedEntityType ret  = RelatedEntityType.fromValue(identEntity.getType());
							rei.setType(ret);
						}						
						
						vr.addRelatedEntitiesItem(rei);
					});
				}
				vl.addRulesItem(vr);
			});
		});		
	}

	@Override
	public ResponseEntity<ValidationRequestStatus> validationsStatus(String validationRequestId, String xCorrelationId) {
		RequestProcessState state = this.validationService.getStatus(validationRequestId);
		if(state == null) {
			return ResponseEntity.notFound().build(); 
		}
		return ResponseEntity.ok(new ValidationRequestStatus(state));
	}

	@Override
	public ResponseEntity<ReceivedValidationRequest> validationsValidate(MultipartFile data,
			@Size(min = 1, max = 36) String xCorrelationId, @Valid Boolean batchMode,
			@Valid ValidationType validationType,
			@Valid RuleProfile ruleProfile,
			@Valid String requestId) {

		ReceivedValidationRequest rvr = new ReceivedValidationRequest(); 
		rvr.setValidationRequestId(this.validationService.validate(data, batchMode, 
				validationType, requestId, ruleProfile!=null?ruleProfile.name():null)
				);
				
		return ResponseEntity.ok(rvr);
	}


}
