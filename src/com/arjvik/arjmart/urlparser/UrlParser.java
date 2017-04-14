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
	
	public UrlParametersMap parse(String URI) {
		String[] brokenURI = URI.split(delimiter);
		UrlParametersMap params = new UrlParametersMap();
		for (int i = 0; i < brokenTemplate.length; i++) {
			if(brokenTemplate[i].matches("\\{\\{.*\\}\\}")){
				String[] nameAndType = brokenTemplate[i].split(":");
				String name = nameAndType[1];
				String type = nameAndType[0];
				String value = brokenURI[i];
				params.addParameter(name, ParameterType.valueOf(type) , value);
			}
		}
		return params;
	}

}
