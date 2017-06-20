package com.kingteller.framework.annotation.sqlite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author 王定波
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) 
public @interface Property {
	 public String column() default "";
	 public String defaultValue() default "";
}
