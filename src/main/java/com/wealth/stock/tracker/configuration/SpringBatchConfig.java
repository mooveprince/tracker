package com.wealth.stock.tracker.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wealth.stock.tracker.entity.StockTracker;
import com.wealth.stock.tracker.model.StockTrackerInput;

/**
 * @author moove
 * 
 * Spring batch config which executes the job for the defined the step
 * Step include - Reading file, processing the file, writing the file to DB
 */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	ItemReader<StockTrackerInput> itemReader;
	
	@Autowired
	ItemProcessor<StockTrackerInput, StockTracker> itemProcessor;
	
	@Autowired
	ItemWriter<StockTracker> itemWriter;
	
	@Bean
	public Job job() {
		
		Step step = stepBuilderFactory.get("Stock-Tracker-File-Load")
					.<StockTrackerInput, StockTracker>chunk(100)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.build();
		
		return jobBuilderFactory.get("Stock-Tracker-Load")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}
	
	/*
	 * Flat file reader bean
	 */
	@Bean
	public FlatFileItemReader<StockTrackerInput> fileItemReader() {
		
		FlatFileItemReader<StockTrackerInput> flatFileItemReader = new FlatFileItemReader<>();
		
		//flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-File-Reader");
		flatFileItemReader.setLinesToSkip(1);
		
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
		
	}
	
	/*
	 *  Line Mapper bean to map the row with header
	 */
	@Bean
	public LineMapper<StockTrackerInput> lineMapper() {
		
		String[] header = {"quarter","stock","date","open","high","low","close","volume","percent_change_price","percent_change_volume_over_last_wk","previous_weeks_volume","next_weeks_open","next_weeks_close","percent_change_next_weeks_price","days_to_next_dividend","percent_return_next_dividend"};
		
		DefaultLineMapper<StockTrackerInput> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(header);
		
		BeanWrapperFieldSetMapper<StockTrackerInput> fieldMapper = new BeanWrapperFieldSetMapper<>();
		fieldMapper.setTargetType(StockTrackerInput.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldMapper);
		
		return defaultLineMapper;
	} 

}
