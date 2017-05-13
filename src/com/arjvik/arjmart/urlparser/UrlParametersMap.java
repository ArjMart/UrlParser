package com.arjvik.arjmart.urlparser;

import java.util.HashMap;
import java.util.Map;

/**
 * The UrlParametersMap class is responsible for returning the parameters passed to it. Its usage is as follows:
 * <br> 
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
		this(new HashMap<>());
	}
	
	/**
	 * Creates a new UrlParametersMap with the specified map as its backend.
	 * Used only for dependency injection purposes.
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param map A map to use as the backend for the UrlParametersMap
	 */
	public UrlParametersMap(Map<String,ParameterValue> map){
		this.map=map;
	}
	
	/**
	 * Adds a parameter to the UrlParametersMap. This is a convenience method, and should not be used.
	 * @deprecated
	 * @param name The name of the parameter to be added
	 * @param type The type of the parameter to be added, must be one of {@link ParameterType}
	 * @param value The value of the parameter to be added
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @see #addParameter(String, ParameterType, Object)
	 */
	@Deprecated
	public void addParameter(String name, String type, Object value){
		addParameter(name, ParameterType.valueOf(type),value);
	}
	
	/**
	 * Adds a parameter to the UrlParametersMap.
	 * @param name The name of the parameter to be added
	 * @param type The type of the parameter to be added, must be one of {@link ParameterType}
	 * @param value The value of the parameter to be added
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public void addParameter(String name, ParameterType type, Object value){
		map.put(name, new ParameterValue(type,value));
	}
	
	/**
	 * Checks whether a parameter of the given name is present in the UrlParametersMap.
	 * This is true if the parameter was provided. It can be false in either of two ways:
	 * <ul>
	 * <li>The parameter of the given name was not provided in the template
	 * <li>The parameter of the given name was not provided in the request URI
	 * </ul>
	 * @param name The name of the parameter
	 * @return true if parameter is present in this UrlParametersMap
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public boolean parameterExists(String name){
		return map.containsKey(name);
	}
	
	/**
	 * Returns the string value of the parameter of given name.
	 * @param name The name of the parameter
	 * @return the value of the parameter
	 * @throws IncompatibleParameterTypeException if parameter is not of type string
	 * @throws ParameterNotProvidedException if parameter was not provided
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public String getString(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.STRING))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to String");
		return (String) value.getValue();
	}
	
	/**
	 * Returns the integer value of the parameter of given name.
	 * @param name The name of the parameter
	 * @return the value of the parameter
	 * @throws IncompatibleParameterTypeException if parameter is not of type integer
	 * @throws ParameterNotProvidedException if parameter was not provided
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public int getInt(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.INT))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Int");
		return (Integer) value.getValue();
	}
	
	/**
	 * Returns the boolean value of the parameter of given name.
	 * @param name The name of the parameter
	 * @return the value of the parameter
	 * @throws IncompatibleParameterTypeException if parameter is not of type boolean
	 * @throws ParameterNotProvidedException if parameter was not provided
	 * @author Arjun Vikram
	 * @since 1.0.0
	 */
	public boolean getBoolean(String name) throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		if(!map.containsKey(name))
			throw new ParameterNotProvidedException("Parameter "+name+" was not provided");
		ParameterValue value = map.get(name);
		if(!value.getType().equals(ParameterType.BOOLEAN))
			throw new IncompatibleParameterTypeException("Parameter "+value.getType().toString()+" can not be cast to Boolean");
		return (Boolean) value.getValue();
	}
}

