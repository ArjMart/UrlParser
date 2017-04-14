package com.arjvik.arjmart.urlparser;

import java.util.HashMap;

public class UrlParametersMap {

	private HashMap<String,ParameterValue> map;
	
	UrlParametersMap() {
		map = new HashMap<>();
	}
	
	public void addParameter(String name, String type, String value){
		addParameter(name, ParameterType.valueOf(type),value);
	}
	
	public void addParameter(String name, ParameterType type, String value){
		map.put(name, new ParameterValue(type,value));
	}
	
	public boolean parameterExists(String name){
		return map.containsKey(name);
	}
	
	public String getString(String name) throws IncompatibleParameterTypeException{
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.STRING))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to String");
		return value.getValue();
	}
	
	public int getInt(String name) throws IncompatibleParameterTypeException{
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.INT))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Int");
		return Integer.parseInt(value.getValue());
	}
	
	public boolean getBoolean(String name) throws IncompatibleParameterTypeException{
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.BOOLEAN))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Boolean");
		return Boolean.parseBoolean(value.getValue());
	}
}

