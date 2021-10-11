package com.wealth.stock.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.repository.StockTrackerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class TrackerServiceTest {
	
	@Mock
	StockTrackerRepository stockTrackerRepo;
	
	@InjectMocks
	TrackerService trackerService;

	@Test
	public void searchBySymbolTest() {
		
		List<StockTracker> stockTrackerList = new ArrayList<>();
		stockTrackerList.add(new StockTracker());
		when (stockTrackerRepo.findByStock(Mockito.anyString())).thenReturn(stockTrackerList);
		
		List<StockTracker> outputList = trackerService.searchBySymbol("ABC");
		assertEquals(stockTrackerList, outputList);
		
		
	}
	
	@Test
	public void addTrackerRecordTest() {
		
		StockTracker stockTracker = new StockTracker();
		when (stockTrackerRepo.save(Mockito.any())).thenReturn(stockTracker);
		
		StockTracker stockTrackerOutput = trackerService.addTrackerRecord(stockTracker);
		assertEquals(stockTracker, stockTrackerOutput);
		
	}
}
