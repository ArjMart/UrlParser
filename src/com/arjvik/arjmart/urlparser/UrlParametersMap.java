package com.arjvik.arjmart.urlparser;

import java.util.HashMap;
import java.util.Map;

/**
 * The UrlParametersMap class is responsible for returning the parameters passed to it. Its usage is as follows:
 * <br/> 
 * <pre>
 * UrlParametersMap params = parser.parse("/some/uri");
 * Boolean hasStringName = params.parameterExists("stringParamName");
 * String stringParameterName = params.getString("stringParamName");
 * </pre>
 * @author Arjun Vikram
 * @since 1.0.0
 */
public class UrlParametersMap {

	private Map<String,ParameterValue> map;
	
	/**
	 * Creates a new UrlParametersMap with a HashMap as its backend.
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */	
	public UrlParametersMap() {
		map = new HashMap<>();
	}
	
	/**
	 * Creates a new UrlParametersMap with the specified map as its backend.
	 * Used only for dependancy injection purposes.
	 * @param map A map to use as the backend for the UrlParametersMap
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public UrlParametersMap(Map<String,ParameterValue> map){
		this.map=map;
	}
	
	/**
	 * Adds a parameter to the UrlParametersMap.
	 * @param name The name of the parameter to be added
	 * @param type The type of the parameter to be added, must be one of {@link ParameterTypes}
	 * @param value The value of the parameter to be added
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
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

