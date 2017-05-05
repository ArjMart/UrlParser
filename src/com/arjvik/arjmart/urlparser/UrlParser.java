package com.arjvik.arjmart.urlparser;


public class UrlParser {
	private String delimiter;
	private String template;
	private String[] brokenTemplate;
	
	public UrlParser(String template) {
		this();
		setTemplate(template);
	}
	
	public UrlParser() {
		setDelimiter("[/\\\\]");
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getTemplate() {
		return template;
	}
	
	public String[] getBrokenTemplate() {
		return brokenTemplate;
	}

	public void setTemplate(String template) {
		this.template = template;
		brokenTemplate = template.split(delimiter);
	}
	
	public UrlParametersMap parse(String URI) throws ParameterParseException{
		return parse(URI, new UrlParametersMap());
	}
	
	public UrlParametersMap parse(String URI, UrlParametersMap params) throws ParameterParseException {
		String[] brokenURI = URI.split(delimiter);
		for (int i = 0; i < brokenTemplate.length && i < brokenURI.length; i++) {
			if(brokenTemplate[i].matches("\\{.*\\}")){
				parseParameter(brokenTemplate[i],brokenURI[i],params);
			}
		}
		return params;
	}

	void parseParameter(String template, String value, UrlParametersMap params) throws ParameterParseException {
		String[] nameAndType = template.split(":");
		String type = nameAndType[0];
		type = type.substring(1); //crop out beginning "{" from "{TYPE:name}"
		String name = nameAndType[1];
		name = name.substring(0, name.length() - 1); //crop out end "}" from "{TYPE:name}"
		switch(type){
		case "STRING":
			addString(params, name, value);
			break;
		case "INT":
			addInt(params, name, value);
			break;
		case "BOOLEAN":
			addBoolean(params, name, value);
			break;
		default:
			throw new ParameterParseException("Invalid parser type");
		}
	}

	void addString(UrlParametersMap params, String name, String value) {
		params.addParameter(name, ParameterType.STRING, value);
	}
	
	void addInt(UrlParametersMap params, String name, String value) throws ParameterParseException{
		try{
			int intValue = Integer.parseInt(value);
			params.addParameter(name, ParameterType.INT, intValue);
		}catch(NumberFormatException e){
			throw new ParameterParseException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	void addBoolean(UrlParametersMap params, String name, String value) throws ParameterParseException {
		try{
			boolean intValue = parseBoolean(value);
			params.addParameter(name, ParameterType.BOOLEAN, intValue);
		}catch(NumberFormatException e){
			throw new ParameterParseException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	static boolean parseBoolean(String value) throws NumberFormatException {
		switch(value.toLowerCase()){
		case "true": return true;
		case "false": return false;
		case "1": return true;
		case "0": return false;
		case "yes": return true;
		case "no": return false;
		default:
			throw new NumberFormatException("Error converting \""+value+"\" to boolean");
		}
	}
	
	/*static String trimLastSlash(String s){
		return s.charAt(s.length() - 1)
	}*///TODO DO THIS!!!
	
}
