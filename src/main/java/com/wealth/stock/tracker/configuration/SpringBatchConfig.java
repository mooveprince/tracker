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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.wealth.stock.tracker.model.StockTracker;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, 
			StepBuilderFactory stepBuilderFactory,
			ItemReader<StockTracker> itemReader,
			ItemProcessor<StockTracker, StockTracker> itemProcessor,
			ItemWriter<StockTracker> itemWriter) {
		
		Step step = stepBuilderFactory.get("Stock-Tracker-File-Load")
					.<StockTracker, StockTracker>chunk(100)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.build();
		
		return jobBuilderFactory.get("Stock-Tracker-Load")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
	}
	
	@Bean
	public FlatFileItemReader<StockTracker> fileItemReader(@Value("${input}") Resource resource) {
		
		FlatFileItemReader<StockTracker> flatFileItemReader = new FlatFileItemReader<>();
		
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-File-Reader");
		flatFileItemReader.setLinesToSkip(1);
		
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
		
	}
	
	
	@Bean
	public LineMapper<StockTracker> lineMapper() {
		
		String[] header = {"quarter","stock","date","open","high","low","close","volume","percent_change_price","percent_change_volume_over_last_wk","previous_weeks_volume","next_weeks_open","next_weeks_close","percent_change_next_weeks_price","days_to_next_dividend","percent_return_next_dividend"};
		
		DefaultLineMapper<StockTracker> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(header);
		
		BeanWrapperFieldSetMapper<StockTracker> fieldMapper = new BeanWrapperFieldSetMapper<>();
		fieldMapper.setTargetType(StockTracker.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldMapper);
		
		return defaultLineMapper;
	}

}
