package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;

import java.io.File;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes the action method of DistanceCalculator and returns
 * the timing to JMeter. 
 * @author crs516 development team.
 *
 */
//AbstractJavaSamplerClient is a JMeter interface. JMeter expects us to write an 
//implementation of AbstractJavaSamplerClient. What we have here is a Strategy 
//Pattern. The handler method where we code the strategy (logic for JMeter to 
//execute) is the runTest(JavaSamplerContext ctx) method. It returns a 
//SampleResult object which will have the data JMeter reports.
public final class JMeterClient extends AbstractJavaSamplerClient {

	private final Logger logger = LoggerFactory.getLogger(JMeterClient.class);
	private boolean firstRun = true; //Determines if we should warm up JVM.

	@Override
	public SampleResult runTest(JavaSamplerContext ctx) {
		SampleResult sampleResult = new SampleResult();
		DistanceCalculator calculator = new DistanceCalculator();
		
		//You edit the line below to change the dataFile that is used. On more
		//powerful hardware use a larger datafile to get meaningful times.
		calculator.setFile(new File(DATA_DIR+DistanceCalculator.dataFile[1]));
		
		if (firstRun) {
			firstRun = false;
			// Warm up the JVM.
			for (int i = 0; i < 5; i++) {
				logger.info("Warm up run {}", i);
				
				//You edit the line below and another one further down to change the 
				//method used.			
				calculator.action();
			}
		}

		//This is where the timing starts.
		sampleResult.sampleStart();
		
		//You also edit the line below to change the method used.		
$		calculator.action();
@		calculator.action4();
		
		//This is where the timing ends.
		sampleResult.sampleEnd();
		
		
		logger.info("Maximum Distance is {}", calculator.getMaximumDistance());
		sampleResult.setSuccessful(true);
	    sampleResult.setSampleLabel("Dist. Calc.");
		return sampleResult;//Reports back to JMeter.
	}
	
}
