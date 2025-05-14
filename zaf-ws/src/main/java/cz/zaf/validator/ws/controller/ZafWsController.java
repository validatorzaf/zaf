package cz.zaf.validator.ws.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import cz.zaf.api.rest.controller.ValidationApi;
import cz.zaf.api.rest.model.NotFound;
import cz.zaf.api.rest.model.ReceivedValidationRequest;
import cz.zaf.api.rest.model.RequestProcessState;
import cz.zaf.api.rest.model.ValidationRequestStatus;
import cz.zaf.api.rest.model.ValidationType;
import cz.zaf.api.rest.model.ValidationXmlResult;
import cz.zaf.validator.ws.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
public class ZafWsController implements ValidationApi {
	
	private static final Logger log = LoggerFactory.getLogger(ZafWsController.class);
		
	@Autowired
	private ValidationService validationService;

	@Override
	public ResponseEntity validationsResult(String validationRequestId, 
			@NotNull String accept,
			@Size(min = 1, max = 36) String xCorrelationId) {
		RequestProcessState state = this.validationService.getStatus(validationRequestId);
		if(state == null) {
			return ResponseEntity.notFound().build(); 
		}
		
		byte[] result = this.validationService.getResult(validationRequestId);
		ValidationXmlResult vxr = new ValidationXmlResult(result);
		
		ResponseEntity re = ResponseEntity.ok()
				.header("Content-Type", accept)
				.header("X-Correlation-Id", xCorrelationId)
				.body(result);
		return re;
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
			@Valid ValidationType validationType, @Valid String requestId) {

		ReceivedValidationRequest rvr = new ReceivedValidationRequest(); 
		rvr.setValidationRequestId(this.validationService.validate(data, batchMode, validationType, requestId));
				
		return ResponseEntity.ok(rvr);
	}


}
