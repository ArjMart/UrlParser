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

	public void setTemplate(String template) {
		this.template = template;
		brokenTemplate = template.split(delimiter);
	}
	
	public UrlParametersMap parse(String URI) throws ParameterFormatException {
		String[] brokenURI = URI.split(delimiter);
		UrlParametersMap params = new UrlParametersMap();
		for (int i = 0; i < brokenTemplate.length; i++) {
			if(brokenTemplate[i].matches("\\{\\{.*\\}\\}")){
				String[] nameAndType = brokenTemplate[i].split(":");
				String name = nameAndType[1];
				String type = nameAndType[0];
				String value = brokenURI[i];
				//params.addParameter(name, ParameterType.valueOf(type) , value);
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
				}
			}
		}
		return params;
	}

	private void addString(UrlParametersMap params, String name, String value) {
		params.addParameter(name, ParameterType.STRING, value);
	}
	
	private void addInt(UrlParametersMap params, String name, String value) throws ParameterFormatException{
		try{
			int intValue = Integer.parseInt(value);
			params.addParameter(name, ParameterType.INT, intValue);
		}catch(NumberFormatException e){
			throw new ParameterFormatException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	private void addBoolean(UrlParametersMap params, String name, String value) throws ParameterFormatException {
		try{
			boolean intValue = parseBoolean(value);
			params.addParameter(name, ParameterType.BOOLEAN, intValue);
		}catch(NumberFormatException e){
			throw new ParameterFormatException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	private boolean parseBoolean(String value) throws NumberFormatException {
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
	
}
