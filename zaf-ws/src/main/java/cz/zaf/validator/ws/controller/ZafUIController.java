package cz.zaf.validator.ws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import cz.zaf.api.rest.model.RequestProcessState;
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
	public String showUploadForm() {
		if(uiEnabled) {
			return "index";
		} else {
            // This ensures nothing is served for the root path.
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZAF UI is disabled.");
            // Alternatively, you could return a specific "service unavailable" page:
            // return "service-unavailable"; // (You'd need templates/service-unavailable.html)
		}
	}


	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
		if(!uiEnabled) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ZAF UI is disabled.");
		}
		try {
			// Convert file to byte array
			byte[] fileBytes = file.getBytes();
			
			String valRequestId = validationService.validate(file, false, null, UUID.randomUUID().toString());
			int counter = 0;
			do {
				RequestProcessState rps = validationService.getStatus(valRequestId);
				if(rps==RequestProcessState.ERROR) {
					model.addAttribute("message", "File upload failed!");
					return "upload";					
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
			model.addAttribute("druhValidace", result.getDruhValidace());
			model.addAttribute("profilPravidel", result.getProfilPravidel());
			model.addAttribute("verzePravidel", result.getVerzePravidel().intValue());
			model.addAttribute("dataPackages", result.getBalicek());
			// seznam chyb
			// List<TPravidlo> chyby = new ArrayList<>();
			
			
			// model.
			return "result";
		} catch (Exception e) {
			model.addAttribute("message", "File upload failed!");
			return "upload";
		}
	}
}
