package com.wealth.stock.tracker.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wealth.stock.tracker.model.StockTrackerInput;
import com.wealth.stock.tracker.model.StockTrackerUploadResponse;

@RestController
@RequestMapping("/tracker")
public class TrackerController {
	
	private static final Logger logger = LoggerFactory.getLogger(TrackerController.class);
	
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	FlatFileItemReader<StockTrackerInput> flatFileItemReader;
	
	@GetMapping(value = "/load")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		
		JobParameters parameters = new JobParameters(maps);
		
		JobExecution jobExecution = jobLauncher.run(job, parameters);  
		
		logger.info("Job is running");
		
		return jobExecution.getStatus();
	}
	
	@PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json", value = "/upload")
	public ResponseEntity<StockTrackerUploadResponse> processUploadedFiles(@RequestPart("file") MultipartFile multipartFile) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		ResponseEntity<StockTrackerUploadResponse> responseEntity = null;
		
		String fileName = multipartFile.getName();
		long fileSize = multipartFile.getSize();
		
		logger.info("Processing uploaded file of size " + fileName);
		logger.info("Processing uploaded file of size " + fileSize);
		
		flatFileItemReader.setResource(new InputStreamResource(multipartFile.getInputStream()));
		
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		
		JobParameters parameters = new JobParameters(maps);
		
		JobExecution jobExecution = jobLauncher.run(job, parameters);  
		
		logger.info("Upload File job is running");
		
		StockTrackerUploadResponse uploadResponse = new StockTrackerUploadResponse();
		uploadResponse.setFileName(fileName);
		uploadResponse.setFileSize(fileSize);
		uploadResponse.setFileUploadStatus(jobExecution.getStatus().toString());
		
		HttpHeaders headers = new HttpHeaders();
		responseEntity = new ResponseEntity<>(uploadResponse, headers, HttpStatus.OK);
		
		return responseEntity;
		
	}

}
