package com.wealth.stock.tracker.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wealth.stock.tracker.constant.ErrorEnum;
import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.model.AddInputRequest;
import com.wealth.stock.tracker.model.SearchInputRequest;
import com.wealth.stock.tracker.model.StockTrackerAddResponse;
import com.wealth.stock.tracker.model.StockTrackerError;
import com.wealth.stock.tracker.model.StockTrackerInput;
import com.wealth.stock.tracker.model.StockTrackerSearchResponse;
import com.wealth.stock.tracker.model.StockTrackerUploadResponse;
import com.wealth.stock.tracker.service.TrackerService;
import com.wealth.stock.tracker.util.TrackerUtil;

/**
 * @author moove
 * 
 * Spring boot controller responsible for handling file upload, get data, add data for stock tracker application 
 */
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
	
	@Autowired
	TrackerService trackerService;
	
	/*
	 * Method to handle file upload for stock tracker
	 */
	@PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json", value = "/upload")
	public ResponseEntity<StockTrackerUploadResponse> processUploadedFiles(@RequestPart("file") MultipartFile multipartFile)  {
		
		//Response entity object declaration
		ResponseEntity<StockTrackerUploadResponse> responseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		
		String fileName = multipartFile.getOriginalFilename();
		long fileSize = multipartFile.getSize();
		
		logger.info("Processing uploaded file of size " + fileName);
		logger.info("Processing uploaded file of size " + fileSize);
		
		//Construct upload api response object and header
		StockTrackerUploadResponse uploadResponse = new StockTrackerUploadResponse();
		uploadResponse.setFileName(fileName);
		uploadResponse.setFileSize(fileSize);
		StockTrackerError error = null;
		
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
			error = new StockTrackerError();
			error.setErrorCode(ErrorEnum.JOB_FAILURE.getKey());
			error.setErrorDescription(ErrorEnum.JOB_FAILURE.getValue());
			uploadResponse.setError(error);
			
			responseEntity = new ResponseEntity<>(uploadResponse, headers, HttpStatus.EXPECTATION_FAILED);
			
			logger.error("Error occurred in processing file" , e);
			
		}
		
		return responseEntity;
		
		
	}
	
	/*
	 * Method to get/search the detail of the stock by its ticker
	 */
	@GetMapping(produces = "application/json", value = "/search")
	public ResponseEntity<StockTrackerSearchResponse> searchByTicker(@RequestParam String symbol) {
		
		
		//Response entity object declaration
		ResponseEntity<StockTrackerSearchResponse> responseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		
		//Construct generic input request so user know what's the request they sent - In this case, just the ticker
		SearchInputRequest inputRequest = new SearchInputRequest();
		inputRequest.setSymbol(symbol);
		
		//Construct search api response object
		StockTrackerSearchResponse stockTrackerSearchResponse = new StockTrackerSearchResponse();
		stockTrackerSearchResponse.setInputRequest(inputRequest);
		StockTrackerError error = null;
		
		if (TrackerUtil.isStringNullOrEmpty(symbol)) {
			error = new StockTrackerError();
			error.setErrorCode(ErrorEnum.EMPTY_SYMBOL.getKey());
			error.setErrorDescription(ErrorEnum.EMPTY_SYMBOL.getValue());
			stockTrackerSearchResponse.setStockTrackerError(error);
			
			responseEntity = new ResponseEntity<>(stockTrackerSearchResponse, headers, HttpStatus.EXPECTATION_FAILED);
			logger.error("Empty string has been passed for stock ticker");
		} else {
			
			List<StockTracker> stockTrackerList = trackerService.searchBySymbol(symbol);
			stockTrackerSearchResponse.setStockTracker(stockTrackerList);
			
			responseEntity = new ResponseEntity<>(stockTrackerSearchResponse, headers, HttpStatus.OK);
		}
		
		
		return responseEntity;
		
	}
	
	/*
	 * Method to add a tracker record to stock tracker
	 */
	@PostMapping(produces = "application/json", value = "/add")
	public ResponseEntity<StockTrackerAddResponse> addTracker(@RequestBody StockTracker stockTracker) {
		
		//Response entity object declaration
		ResponseEntity<StockTrackerAddResponse> responseEntity = null;
		HttpHeaders headers = new HttpHeaders();
		
		//Construct input request so user know what they sent
		AddInputRequest inputRequest = new AddInputRequest();
		inputRequest.setStockTracker(stockTracker);
		
		//Construct add api response object
		StockTrackerAddResponse stockTrackerAddResponse = new StockTrackerAddResponse();
		stockTrackerAddResponse.setInputRequest(inputRequest);
		StockTrackerError error = null;
		
		//Three items are mandatory - quarter, ticker and date
		byte quarter = stockTracker.getQuarter();
		Date date = stockTracker.getDate();
		String stock = stockTracker.getStock();
		
		if ( quarter <= 0 || TrackerUtil.isStringNullOrEmpty(stock) || date == null) {
			error = new StockTrackerError();
			error.setErrorCode(ErrorEnum.INVALID_INPUT.getKey());
			error.setErrorDescription(ErrorEnum.INVALID_INPUT.getValue());
			stockTrackerAddResponse.setStockTrackerError(error);
			
			responseEntity = new ResponseEntity<>(stockTrackerAddResponse, headers, HttpStatus.EXPECTATION_FAILED);
			
			logger.error("Invlaid input sent for add the tracker record");
			
		} else {
			trackerService.addTrackerRecord(stockTracker);
			stockTrackerAddResponse.setAddStockTrackerStatus("SUCCESSFULLY ADDED");
			responseEntity = new ResponseEntity<>(stockTrackerAddResponse, headers, HttpStatus.OK);
			
		}
		
		
		return responseEntity;
		
	}

}
