package com.ltree.crs516.tasks;

import java.util.ArrayList;
import java.util.List;

import com.ltree.crs516.taskengine.Command;

/**
 * This is a Command that in turn holds other commands. Its run()
 * method calls the run() methods of the contained commands. 
 * @author Crs 516 Development Team
 *
 */
 @SuppressWarnings("serial")
public class CompositeCommandImpl implements Command {

	private List<Command> commandList = new ArrayList<>();
	
	@Override
	public void run() {
		for (Command command : commandList) {
			command.run();
		}
	}

	public void addCommand(Command command){
		commandList.add(command);
	}
}
 
