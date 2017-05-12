package com.arjvik.arjmart.urlparser;

import java.util.HashMap;
import java.util.Map;

/**
 * The UrlParametersMap class is responsible for returning the parameters passed to it. Its usage is as follows:
 * <pre>
 * UrlParametersMap params = parser.parse("/some/uri");
 * Boolean hasStringName = params.parameterExists("stringParamName");
 * String stringParameterName = params.getString("stringParamName");
 * </pre>
 */
public class UrlParametersMap {

	private Map<String,ParameterValue> map;
	
	public UrlParametersMap() {
		map = new HashMap<>();
	}
	
	public UrlParametersMap(Map<String,ParameterValue> map){
		this.map=map;
	}
	
	public void addParameter(String name, String type, Object value){
		addParameter(name, ParameterType.valueOf(type),value);
	}
	
	public void addParameter(String name, ParameterType type, Object value){
		map.put(name, new ParameterValue(type,value));
	}
	
	public boolean parameterExists(String name){
		return map.containsKey(name);
	}
	
	public String getString(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.STRING))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to String");
		return (String) value.getValue();
	}
	
	public int getInt(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.INT))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Int");
		return (Integer) value.getValue();
	}
	
	public boolean getBoolean(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.BOOLEAN))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Boolean");
		return (Boolean) value.getValue();
	}
}

