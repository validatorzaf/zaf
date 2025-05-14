package cz.zaf.validator.ws.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cz.zaf.api.rest.model.RequestProcessState;
import cz.zaf.api.rest.model.ValidationType;
import cz.zaf.schema.validace_v1.Validace;
import cz.zaf.validator.CmdParams;
import cz.zaf.validator.CmdValidator;
import cz.zaf.validator.profiles.ValidationProfile;
import jakarta.validation.Valid;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

@Service
public class ValidationService {

	private static final Logger log = LoggerFactory.getLogger(ValidationService.class);
	
	private static final String OUTPUT_FILE_NAME = "zaf-output.xml";
	
	private static final String INPUT_DIR_NAME = "input";
	
	@Value("${zaf.workdir.path:}")
	private String workingFolder;

	// TODO: initialize in PostConstruct to allow parametrization
	ExecutorService execService = Executors.newFixedThreadPool(2);
	
	Map<String, ValidationJob> jobsMap = new HashMap<>();
	
    static JAXBContext jaxbContext;
    {        
        try {
            jaxbContext = JAXBContext.newInstance(Validace.class);
        } catch (JAXBException e) {
            log.error("Failed to initialize JAXBContext", e);
            throw new IllegalStateException("Failed to initialize JAXBContext", e);
        }
    }

    class ValidationJob implements Runnable {
		private Path requestPath;
		
		/**
		 * Directory with input files
		 */
		private Path inputDirPath;

		enum JobStatus {
			PENDING,
			PROCESSING,
			DONE
		}
		
		// pending till executed
		private JobStatus jobStatus = JobStatus.PENDING;
		
		private Future<?> futureResult;

		private String originalFilename;

		private boolean batchMode;

		private ValidationProfile validationProfile;

		public ValidationJob(Path requestPath, String originalFilename,
				final boolean batchMode, 
				final ValidationProfile validationProfile) throws IOException {
			this.requestPath = requestPath;
			this.inputDirPath = requestPath.resolve(INPUT_DIR_NAME);
			this.batchMode = batchMode;
			this.validationProfile = validationProfile;
			Files.createDirectory(inputDirPath);
			if(originalFilename!=null) {
				this.originalFilename = originalFilename.trim();
				// remove path separators
				int lastPos = this.originalFilename.lastIndexOf('/');
				if(lastPos>=0) {
					this.originalFilename = this.originalFilename.substring(lastPos+1); 					
				}
				lastPos = this.originalFilename.lastIndexOf('\\');
				if(lastPos>=0) {
					this.originalFilename = this.originalFilename.substring(lastPos+1); 					
				}
			} 
			if(StringUtils.isEmpty(this.originalFilename)) {
				this.originalFilename = "data.bin";
			} 
		}

		@Override
		public void run() {
			jobStatus = JobStatus.PROCESSING;
			
			try {
				CmdParams cmdParams = new CmdParams();
				cmdParams.setInputPath(getDataPath().toString());
				cmdParams.setWorkDir(requestPath.toString());
				cmdParams.setOutputPath(getOutputPath().toString());
				cmdParams.setBatchMode(batchMode);
				cmdParams.setValidationProfile(validationProfile);

				CmdValidator cmdValidator = new CmdValidator(cmdParams);
				cmdValidator.validate();
			} catch (Exception e) {
				log.error("Failed to validate, path: {}", requestPath, e);
			}
			
			jobStatus = JobStatus.DONE;
		}

		private Path getOutputPath() {
			return requestPath.resolve(OUTPUT_FILE_NAME);
		}

		/**
		 * Return path to the data file
		 * @return
		 */
		public Path getDataPath() {
			return inputDirPath.resolve(originalFilename);
		}

		public void setResult(Future<?> futureResult) {
			this.futureResult = futureResult;
			
		}
	}

	public String validate(MultipartFile data, @Valid Boolean batchMode,
			@Valid ValidationType validationType, @Valid String requestId) {
		// Store file to the working folder
		if(workingFolder==null) {
			workingFolder = "";
		}
		
		String requestValidationId = UUID.randomUUID().toString();
		Path filePath = Paths.get(workingFolder).toAbsolutePath();
		Path requestPath = filePath.resolve(requestValidationId);
		try {
			Files.createDirectories(requestPath);
			log.debug("Storing file to the working folder: {}", requestPath);
			
			ValidationProfile validationProfile = null;
			if(validationType!=null) {
				switch(validationType) {
				case AP2023: 
					validationProfile = ValidationProfile.AP2023;
					break;
				case DAAIP2024: 
					validationProfile = ValidationProfile.DAAIP2024;
					break;
				case NSESSS2017:
					validationProfile = ValidationProfile.NSESSS2017;
					break;
				case NSESSS2024:
					validationProfile = ValidationProfile.NSESSS2023;
					break;
				}
			}
			
			ValidationJob job = new ValidationJob(requestPath, data.getOriginalFilename(),
					batchMode!=null && batchMode,
					validationProfile);
			// copy input stream to the file
			Files.copy(data.getInputStream(), job.getDataPath());
			
			synchronized(this) {
				jobsMap.put(requestValidationId, job);
			}

			job.setResult(execService.submit(job));
						
		} catch(Exception e) {
			throw new RuntimeException("Failed to store request, path: " + requestPath, e);
		}
		return requestValidationId;
	}

	public RequestProcessState getStatus(String validationRequestId) {
		synchronized(this) {
			ValidationJob vj = jobsMap.get(validationRequestId);
			if(vj!=null) {
				if(vj.jobStatus==ValidationJob.JobStatus.PENDING) {
					return RequestProcessState.PENDING;
				}
				if(!vj.futureResult.isDone()) {
					return RequestProcessState.PROCESSING;
				}
				// job is finished -> return result
				try {
					Object result = vj.futureResult.get();
					return RequestProcessState.FINISHED;
				} catch (Exception e) {
					return RequestProcessState.ERROR;
				}				
			}
		}
		return null;
	}
	
	synchronized private Path getResultPath(String validationRequestId) {
		ValidationJob vj = jobsMap.get(validationRequestId);
		if(vj==null) {
			throw new RuntimeException("Unknown request id: " + validationRequestId);
		}
		if(!vj.futureResult.isDone()) {
			throw new RuntimeException("Request is not finished, request id: " + validationRequestId);
		}
		return vj.getOutputPath();
	}

	public byte[] getResultAsBytes(String validationRequestId) {		
		// job is finished -> return result
		try {
			Path resultPath = getResultPath(validationRequestId);
			return Files.readAllBytes(resultPath);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read result, request id: " + validationRequestId,
					e);
		}
	}

	public Validace getResult(String validationRequestId) {
		Path resultPath = getResultPath(validationRequestId);
		
		// load xml using jaxbContext from resultPath
        try (InputStream is = Files.newInputStream(resultPath)) {
        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        	Object resultObj = unmarshaller.unmarshal(is);
        	return (Validace)resultObj;
        } catch (IOException e) {
			throw new RuntimeException("Failed to read result, request id: " + validationRequestId,
					e);
		} catch (JAXBException e) {
			throw new RuntimeException("Failed to read result, request id: " + validationRequestId,
					e);
		}
	}

}
