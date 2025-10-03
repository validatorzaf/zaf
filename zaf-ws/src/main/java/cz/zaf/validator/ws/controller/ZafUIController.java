package cz.zaf.validator.ws.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import cz.zaf.api.rest.model.RequestProcessState;
import cz.zaf.api.rest.model.ValidationType;
import cz.zaf.common.ZafInfo;
import cz.zaf.schema.validace_v1.Validace;
import cz.zaf.validator.ws.service.ValidationService;

/**
 * Kontroller pro jednoduché webové rozhraní UI
 * 
 * Webové rozhraní se aktivuje v konfiguraci pomocí volby "zaf.ui.enabled"
 */
@Controller
public class ZafUIController {
	
	 @Value("${zaf.ui.enabled:false}") // Default to false if property is missing
	 private boolean uiEnabled;

	@Autowired
	private ValidationService validationService;

	@GetMapping("/")
	public String showUploadForm(Model model) {
		if(uiEnabled) {
			model.addAttribute("appVersion", ZafInfo.getAppVersion());
			return "index";
		} else {
            // This ensures nothing is served for the root path.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZAF UI is disabled.");
            // Alternatively, you could return a specific "service unavailable" page:
            // return "service-unavailable"; // (You'd need templates/service-unavailable.html)
		}
	}


	@PostMapping(path = "/upload", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE //"multipart/form-data"
			)
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "validationType", required = false) String paramValidationType,
			@RequestParam(value = "batch", required = false) Boolean batchMode,
			@RequestParam(value = "validationProfile", required = false) String validationProfile,
			Model model) {
		if(!uiEnabled) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZAF UI is disabled.");
		}
		model.addAttribute("appVersion", ZafInfo.getAppVersion());
		try {
			// Convert file to byte array
			byte[] fileBytes = file.getBytes();
			
			boolean batch = (batchMode!=null && batchMode);
			ValidationType validationType = null;
			if(paramValidationType!=null&&!"AUTO".equals(paramValidationType)) {
				validationType = ValidationType.valueOf(paramValidationType);
			}
			
			
			String valRequestId = validationService.validate(file, batch, validationType, UUID.randomUUID().toString(),
					validationProfile);
			int counter = 0;
			do {
				RequestProcessState rps = validationService.getStatus(valRequestId);
				if(rps==RequestProcessState.ERROR) {
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Validation error.");					
				} else 
				if(rps==RequestProcessState.FINISHED) {
					break;
				}
				Thread.sleep(1000);
				counter++;
			} while(counter<1000);
			
			// read result
			Validace result = validationService.getResult(valRequestId);

			// Read back final response
			model.addAttribute("requestId", valRequestId);			
			model.addAttribute("validationType", result.getTypValidace());
			model.addAttribute("validationProfile", result.getProfilValidace());			
			model.addAttribute("verzePravidel", result.getVerzePravidel().intValue());
			model.addAttribute("dataPackages", result.getBalicek());
			// seznam chyb
			// List<TPravidlo> chyby = new ArrayList<>();
			
			
			// model.
			return "result";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "index";
		}
	}
	
	@GetMapping("/download/{requestId}")
	public ResponseEntity<ByteArrayResource> downloadResult(@PathVariable("requestId") String requestId) {
	    if (!uiEnabled) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZAF UI is disabled.");
	    }
	    try {
	        // Let validationService give you the serialized result
	        // (adjust if your service returns a File, String, XML etc.)
	        byte[] fileBytes = validationService.getResultAsBytes(requestId);

	        if (fileBytes == null) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found for requestId: " + requestId);
	        }

	        ByteArrayResource resource = new ByteArrayResource(fileBytes);

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=\"validation-" + requestId + ".xml\"")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .contentLength(fileBytes.length)
	                .body(resource);

	    } catch (Exception e) {
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving result.", e);
	    }
	}	
}
