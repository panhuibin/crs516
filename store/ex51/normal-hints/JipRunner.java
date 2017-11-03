package com.ltree.crs516.taskengine;

import static com.ltree.crs516.data.DataConstants.DATA_DIR;
import static com.ltree.crs516.taskengine.DistanceCalculator.dataFile;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltree.crs516.taskengine.JipRunner;
import com.mentorgen.tools.profile.runtime.Profile;

public final class JipRunner {
		private boolean firstRun = true;
		private final Logger logger = LoggerFactory.getLogger(JipRunner.class);

		public void profile() {
			DistanceCalculator calculator = new DistanceCalculator();
			calculator.setFile(new File(DATA_DIR+dataFile[0]));
			
			if (firstRun) {// Warm up the JVM on first run.
				firstRun = false;
				for (int i = 0; i < 5; i++) {
					logger.info("Warm up run {}", i);
					calculator.action();
				}
			}
			
			//This is where the profiling starts.
			Profile.clear(); //Initializes the profiler.
			Profile.start();
			
			//This is where you choose the action method to profile We are using action().			
			calculator.action();
			Profile.stop();
			Profile.shutdown();
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JipRunner().profile();
	}
}
