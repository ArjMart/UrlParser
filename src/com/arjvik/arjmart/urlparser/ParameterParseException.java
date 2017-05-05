package com.arjvik.arjmart.urlparser;

public class ParameterParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ParameterType targetType;
	private String value;

	public ParameterParseException() {
		// TODO Auto-generated constructor stub
	}

	public ParameterParseException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParameterParseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ParameterParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ParameterParseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
	public ParameterParseException(String value, ParameterType targetType) {
		this.value=value;
		this.targetType=targetType;
	}

	public ParameterParseException(String arg0, String value, ParameterType targetType) {
		super(arg0);
		this.value=value;
		this.targetType=targetType;
	}

	public ParameterParseException(Throwable arg0, String value, ParameterType targetType) {
		super(arg0);
		this.value=value;
		this.targetType=targetType;
	}

	public ParameterParseException(String arg0, Throwable arg1, String value, ParameterType targetType) {
		super(arg0, arg1);
		this.value=value;
		this.targetType=targetType;
	}

	public ParameterParseException(String arg0, Throwable arg1, boolean arg2, boolean arg3, String value, ParameterType targetType) {
		super(arg0, arg1, arg2, arg3);
		this.value=value;
		this.targetType=targetType;
	}

	public ParameterType getTargetType() {
		return targetType;
	}

	public String getValue() {
		return value;
	}

}
