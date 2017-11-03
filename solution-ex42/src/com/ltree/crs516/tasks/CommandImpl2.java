package com.ltree.crs516.tasks;

import com.ltree.crs516.taskengine.Command;

 @SuppressWarnings("serial")
public class CommandImpl2 implements Command {

	@Override
	public void run() {
		System.out.println("Running command 2");	
	}

}
