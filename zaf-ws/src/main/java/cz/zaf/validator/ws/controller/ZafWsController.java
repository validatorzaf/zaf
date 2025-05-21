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
import cz.zaf.api.rest.model.ValidationJsonResult;
import cz.zaf.api.rest.model.ValidationLevel;
import cz.zaf.api.rest.model.ValidationLevelStatus;
import cz.zaf.api.rest.model.ValidationRequestStatus;
import cz.zaf.api.rest.model.ValidationRule;
import cz.zaf.api.rest.model.ValidationType;
import cz.zaf.schema.validace_v1.TBalicek;
import cz.zaf.schema.validace_v1.TTypEntity;
import cz.zaf.schema.validace_v1.Validace;
import cz.zaf.validator.ws.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Controller
public class ZafWsController implements ValidationApi {
	
	private static final Logger log = LoggerFactory.getLogger(ZafWsController.class);
		
	@Autowired
	private ValidationService validationService;
	
	static private Map<TTypEntity, RelatedEntityType> entityTypeMap = new HashMap<>();
	static {
		entityTypeMap.put(TTypEntity.ARCHIVNÍ_SOUBOR, RelatedEntityType.ARCHIVNI_SOUBOR);
		entityTypeMap.put(TTypEntity.DOKUMENT, RelatedEntityType.DOKUMENT);
		entityTypeMap.put(TTypEntity.DÍL, RelatedEntityType.DIL);
		entityTypeMap.put(TTypEntity.JEDNOTLIVOST, RelatedEntityType.JEDNOTLIVOST);		
		entityTypeMap.put(TTypEntity.SLOŽKA, RelatedEntityType.SLOZKA);
		entityTypeMap.put(TTypEntity.SOUČÁST, RelatedEntityType.SOUCAST);
		entityTypeMap.put(TTypEntity.SPIS, RelatedEntityType.SPIS);
		entityTypeMap.put(TTypEntity.SPISOVÝ_PLÁN, RelatedEntityType.SPISOVY_PLAN);
		entityTypeMap.put(TTypEntity.SÉRIE, RelatedEntityType.SERIE);
		entityTypeMap.put(TTypEntity.VĚCNÁ_SKUPINA, RelatedEntityType.VECNA_SKUPINA);
		entityTypeMap.put(TTypEntity.ČÁST_JEDNOTLIVOSTI, RelatedEntityType.CAST_JEDNOTLIVOSTI);		
	}
	
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
		Validace validace = this.validationService.getResult(validationRequestId);
		
		ValidationJsonResult vjr = new ValidationJsonResult();
		vjr.setAppName(validace.getNazevAplikace());
		vjr.setAppVersion(validace.getVerzeAplikace());
		ValidationType vt = null;
		switch(validace.getDruhValidace()) {
		case "archivní popis":
			vt = ValidationType.AP2023;
			break;
		default:
			throw new RuntimeException("Unknown validation type: " + validace.getDruhValidace());
		}
		vjr.setProfile(validace.getProfilPravidel());
		vjr.setValidationType(vt);
		vjr.setValidationDate(
				validace.getDatumValidace()
					.toGregorianCalendar()
					.toZonedDateTime()
					.toOffsetDateTime());
		vjr.setRuleVersion(validace.getVerzePravidel().intValue());
		vjr.setRequestId(validace.getIdentifikatorValidace());
		
		for(TBalicek balicek: validace.getBalicek()) {
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

	private void convertBalicek(TBalicek balicek, ValidationJsonResult vjr) {
		DataPackageValidationResult dpr = new DataPackageValidationResult();
		dpr.setId(balicek.getIdentifikator());
		dpr.setName(balicek.getNazevSouboru());
		vjr.addDataPackagesItem(dpr);
		
		balicek.getKontrola().forEach(kontrola -> {
			ValidationLevel vl = new ValidationLevel();
			vl.setName(kontrola.getNazev());
			vl.setFileName(kontrola.getVnitrniSoubor());
			if(kontrola.getStav() != null) {
				switch(kontrola.getStav()) {
				case OK:
					vl.setStatus(ValidationLevelStatus.OK);
					break;
				case NESPUSTENA:
					vl.setStatus(ValidationLevelStatus.NOT_VALIDATED);
					break;
				case CHYBA:
					vl.setStatus(ValidationLevelStatus.ERROR);
					break;
				}
			}
			dpr.addLevelsItem(vl);
			
			kontrola.getPravidlo().forEach(pravidlo -> {
				ValidationRule vr = new ValidationRule();
				vr.setCode(pravidlo.getKod());
				vr.setDescription(pravidlo.getPopisChyby());
				vr.setDetail(pravidlo.getVypisChyby());
				vr.setErrorCode(pravidlo.getKodChyby());
				vr.setLocation(pravidlo.getMistoChyby());
				vr.setRule(pravidlo.getZneni());
				vr.setSource(pravidlo.getZdroj());
				if(pravidlo.getEntity()!=null) {
					pravidlo.getEntity().getIdentifikator().forEach(identEntity -> {
						RelatedEntity rei = new RelatedEntity();
						rei.setId(identEntity.getValue());
						rei.setSource(identEntity.getZdroj());
						if(identEntity.getTyp()!=null) {
							RelatedEntityType ret  = entityTypeMap.get(identEntity.getTyp());
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
			@Valid ValidationType validationType, @Valid String requestId) {

		ReceivedValidationRequest rvr = new ReceivedValidationRequest(); 
		rvr.setValidationRequestId(this.validationService.validate(data, batchMode, 
				validationType, requestId, null)
				);
				
		return ResponseEntity.ok(rvr);
	}


}
