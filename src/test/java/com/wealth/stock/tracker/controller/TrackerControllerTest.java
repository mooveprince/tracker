package com.wealth.stock.tracker.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.model.StockTrackerInput;
import com.wealth.stock.tracker.service.TrackerService;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(controllers=TrackerController.class)
public class TrackerControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TrackerControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	TrackerService trackerService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	JobLauncher jobLauncher;
	
	@MockBean
	Job job;
	
	@MockBean
	FlatFileItemReader<StockTrackerInput> flatFileItemReader;
	
	static final String searchEndpoint = "/tracker/search";
	static final String addEndpoint = "/tracker/add";
	
	@Test
	public void searchByTickerTest () throws Exception {
		
		logger.info("Entering searchByTickerTest");
		
		List<StockTracker> stockTrackerList = new ArrayList<>();
		stockTrackerList.add(new StockTracker());
		when(trackerService.searchBySymbol(Mockito.any())).thenReturn(stockTrackerList);
		
		mockMvc.perform(get(searchEndpoint).param("symbol", "")
				.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
		        .andExpect(status().isExpectationFailed());
		
		mockMvc.perform(get(searchEndpoint).param("symbol", "ABC")
				.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
		        .andExpect(status().isOk());
		
		when(trackerService.searchBySymbol(Mockito.any())).thenThrow(new RuntimeException());
		mockMvc.perform(get(searchEndpoint).param("symbol", "XYZ")
				.contentType(MediaType.APPLICATION_JSON)
				).andDo(print())
		        .andExpect(status().isExpectationFailed());
		
	}
	
	
	@Test
	public void addTrackerTest() throws JsonProcessingException, Exception {
		logger.info("Entering addTrackerTest");
		
		StockTracker stockTrackerOutput = new StockTracker();
		when(trackerService.addTrackerRecord(Mockito.any())).thenReturn(stockTrackerOutput);
		
		StockTracker stockTrackerInput = new StockTracker();
		mockMvc.perform(post(addEndpoint)
				.content(objectMapper.writeValueAsString(stockTrackerInput))
			    .contentType("application/json")
			    ).andDo(print())
			    .andExpect(status().isExpectationFailed());
		
		stockTrackerInput.setQuarter((byte)1);
		stockTrackerInput.setStock("ABC");
		stockTrackerInput.setDate(Date.valueOf("2011-03-31"));
		
		mockMvc.perform(post(addEndpoint)
				.content(objectMapper.writeValueAsString(stockTrackerInput))
			    .contentType("application/json")
			    ).andDo(print())
			    .andExpect(status().isOk());
		
		when(trackerService.addTrackerRecord(Mockito.any())).thenReturn(null);
		mockMvc.perform(post(addEndpoint)
				.content(objectMapper.writeValueAsString(stockTrackerInput))
			    .contentType("application/json")
			    ).andDo(print())
			    .andExpect(status().isExpectationFailed());
		
		
		when(trackerService.addTrackerRecord(Mockito.any())).thenThrow(new RuntimeException());
		mockMvc.perform(post(addEndpoint)
				.content(objectMapper.writeValueAsString(stockTrackerInput))
			    .contentType("application/json")
			    ).andDo(print())
			    .andExpect(status().isExpectationFailed());
		
		
	}
	
	

}
