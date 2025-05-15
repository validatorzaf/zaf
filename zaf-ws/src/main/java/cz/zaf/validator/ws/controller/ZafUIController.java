package cz.zaf.validator.ws.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import cz.zaf.api.rest.model.RequestProcessState;
import cz.zaf.schema.validace_v1.Validace;
import cz.zaf.validator.ws.service.ValidationService;

@Controller
public class ZafUIController {

	@Autowired
	private ValidationService validationService;

	@GetMapping("/upload")
	public String showUploadForm() {
		return "upload";
	}


	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
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
