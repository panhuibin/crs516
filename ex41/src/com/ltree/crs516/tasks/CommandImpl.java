package com.ltree.crs516.tasks;

import com.ltree.crs516.taskengine.Command;

public class CommandImpl implements Command{

    @Override
    public void run() {
        System.out.println("run method called ");
    }
}
