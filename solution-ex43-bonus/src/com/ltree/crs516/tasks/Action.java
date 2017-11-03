package com.ltree.crs516.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

 @Target(ElementType.METHOD)
 @Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	int value();
}
