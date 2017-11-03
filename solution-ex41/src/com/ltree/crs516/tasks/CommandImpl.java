package com.ltree.crs516.tasks;

import java.util.logging.Logger;

import com.ltree.crs516.taskengine.Command;

 @SuppressWarnings("serial")
public final class CommandImpl implements Command {

	/**
	 * Will be called by task engine
	 */
	@Override
	public void run() {
		Logger.getGlobal().info("run method called ");
	}
}
