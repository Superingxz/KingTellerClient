package com.kingteller.framework.db.table;

import com.kingteller.framework.utils.FieldUtils;

public class KeyValue {
	private String key;
	private Object value;
	
	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	
	public KeyValue() {}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		if(value instanceof java.util.Date || value instanceof java.sql.Date){
			return FieldUtils.SDF.format(value);
		}
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
