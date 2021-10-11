package com.wealth.stock.tracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wealth.stock.tracker.model.StockTrackerError;
import com.wealth.stock.tracker.model.StockTrackerInput;
import com.wealth.stock.tracker.model.StockTrackerUploadResponse;

/**
 * @author moove
 * 
 * Spring boot controller responsible for handling file upload, get data, add data for stock tracker application 
 */
@RestController
@RequestMapping("/tracker")
public class TrackerController {
	
	private static final Logger logger = LoggerFactory.getLogger(TrackerController.class);
	private static final Short uploadError = 800;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	FlatFileItemReader<StockTrackerInput> flatFileItemReader;
	
	/*
	 * Method to handle file upload for stock tracker
	 */
	@PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json", value = "/upload")
	public ResponseEntity<StockTrackerUploadResponse> processUploadedFiles(@RequestPart("file") MultipartFile multipartFile)  {
		
		ResponseEntity<StockTrackerUploadResponse> responseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		
		String fileName = multipartFile.getOriginalFilename();
		long fileSize = multipartFile.getSize();
		
		logger.info("Processing uploaded file of size " + fileName);
		logger.info("Processing uploaded file of size " + fileSize);
		
		//Construct api response object and header
		StockTrackerUploadResponse uploadResponse = new StockTrackerUploadResponse();
		uploadResponse.setFileName(fileName);
		uploadResponse.setFileSize(fileSize);
		
		try {
			
			//Passing the input file as stream for Spring batch reader 
			flatFileItemReader.setResource(new InputStreamResource(multipartFile.getInputStream()));
			
			//Trigger Spring batch job via launcher
			Map<String, JobParameter> maps = new HashMap<>();
			maps.put("time", new JobParameter(System.currentTimeMillis()));
			JobParameters parameters = new JobParameters(maps);
			JobExecution jobExecution = jobLauncher.run(job, parameters);  
			
			logger.info("Upload File job is running");
			
			//Setting the job status in api response
			uploadResponse.setFileUploadStatus(jobExecution.getStatus().toString());
			responseEntity = new ResponseEntity<>(uploadResponse, headers, HttpStatus.OK);

			logger.info("Upload File job is completed");
			
		} catch (Exception e) {
			
			//Construct error object for api response
			StockTrackerError error = new StockTrackerError();
			error.setErrorDescription("Exception occurred when executing the job");
			error.setErrorCode(uploadError);
			uploadResponse.setError(error);
			
			responseEntity = new ResponseEntity<>(uploadResponse, headers, HttpStatus.EXPECTATION_FAILED);
			
			logger.error("Error occurred in processing file" , e);
			
		}
		
		return responseEntity;
		
		
	}

}
