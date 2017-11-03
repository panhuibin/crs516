package com.ltree.crs516.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread pool for threads used by WOD09. Two thread pools are
 * maintained with one giving normal threads running at 
 * normal priority and the other daemon threads at lower
 * priority.
 * 
 * @author crs516 development team.
 * 
 */
public class WODThreadPools {

	/**
	 * Factory that produces daemon threads.
	 */
    static ThreadFactory daemonThreadFactory = new ThreadFactory() {
    	volatile int counter =0;
		@Override
		public Thread newThread(Runnable r) {	
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("crs 516 daemon "+counter++);
            return t;
		}
	}; 

	/**
	 * Factory that produces non-daemon threads.
	 */
    static ThreadFactory nondaemonThreadFactory = new ThreadFactory() {
    	volatile int counter =0;
		@Override
		public Thread newThread(Runnable r) {	
            Thread t = new Thread(r);
            t.setName("crs 516 non-daemon "+counter++);
            return t;
		}
	}; 

    /**
     * ExecutorService to handle short jobs.
     * 
     */
    public static ExecutorService daemonThreadService = Executors
            .newCachedThreadPool(daemonThreadFactory);

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(WODThreadPools.class);


    /**
     * Mainly for running algorithms in the background at normal priority.
     * 
     */
    public static ExecutorService threadService = Executors
            .newCachedThreadPool(nondaemonThreadFactory);

    /**
     * Convenience method for threads to call if they want to sleep.
     * @param i a float, the number of seconds to sleep.
     */
    public static void delay(float i) {
        try {
            Thread.sleep((long) (1000 * i));
        } catch (InterruptedException e) {
            logger.debug("Sleep interrupted", e);
        }
    }

    /**
     * Allows client code to get an ExecutorService and tender jobs.
     * 
     * @return the ExecutorService.
     */
    public static synchronized ExecutorService getDaemonThreadService() {
        return daemonThreadService;
    }

    /**
     * Allows client code to get an ExecutorService and tender jobs.
     * 
     * @return the ExecutorService.
     */
    public static synchronized ExecutorService getThreadService() {
        return threadService;
    }
}
