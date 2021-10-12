package com.wealth.stock.tracker.batch;

import static org.hamcrest.CoreMatchers.is;

import java.util.Collection;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.wealth.stock.tracker.model.StockTrackerInput;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
public class StockTrackerSpringBatchTest {
	
	private static final String TEST_INPUT = "src/test/resources/test-input.csv";
	
	@Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
  
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    
    @Autowired
	FlatFileItemReader<StockTrackerInput> flatFileItemReader;
  
    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }
    
    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("time", System.currentTimeMillis()+"");
        return paramsBuilder.toJobParameters();
   }
    
   @Test
   public void endToEndJobTesting () throws Exception {
	   
	    flatFileItemReader.setResource(new FileSystemResource(TEST_INPUT));
	   
	    JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
	    JobInstance actualJobInstance = jobExecution.getJobInstance();
	    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
	    
	    MatcherAssert.assertThat(actualJobInstance.getJobName(), is("Stock-Tracker-Load"));
	    MatcherAssert.assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
	   
   }
   
   
   @Test
   public void stepTesting() {
	   
	   flatFileItemReader.setResource(new FileSystemResource(TEST_INPUT));
	   
	   JobExecution jobExecution = jobLauncherTestUtils.launchStep(
			      "Stock-Tracker-File-Load", defaultJobParameters()); 
	   Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
	   ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
	   
	   MatcherAssert.assertThat(actualStepExecutions.size(), is(1));
	   MatcherAssert.assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));

   }

}
