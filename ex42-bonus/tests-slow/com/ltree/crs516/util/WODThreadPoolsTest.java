package com.ltree.crs516.util;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ltree.crs516.util.WODThreadPools;

public class WODThreadPoolsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(timeout=3_000_000_000L)
	public void testDelay() {
		long startTime = System.nanoTime();
		WODThreadPools.delay(1.5F);
		long endTime = System.nanoTime();
		long sleepDuration = endTime - startTime;
		assertThat(sleepDuration, greaterThan(1_000_000_000L));
	}

	@Test
	public void testGetDaemonThreadService() throws InterruptedException, ExecutionException {
		Future<String> future = WODThreadPools.getDaemonThreadService().submit(new TestCallable());
		String result = future.get();
		assertThat(result, equalTo("daemon"));
	}

	@Test
	public void testGetThreadService() throws InterruptedException, ExecutionException {
		Future<String> future = WODThreadPools.getThreadService().submit(new TestCallable());
		String result = future.get();
		assertThat(result, equalTo("non-daemon"));
	}

	private class TestCallable implements Callable<String>{

		@Override
		public String call() throws Exception {
			String result = "non-daemon";
			if(Thread.currentThread().isDaemon()){
				result = "daemon";
			}
			return result;
		}
	}
}
