package com.arjvik.arjmart.urlparser;

/**
 * The UrlParser class is responsible for parsing URIs, and returning their parameters.
 * It aims to abstract the parsing of parameters in a RESTful API and instead allow
 * the developer to focus on the back-end of the RESTful API. Its usage is as follows:
 * <br>
 * Setup:
 * <pre>
 * UrlParser parser = new UrlParser();
 * Parser.setDelimeter("[/\\\\]"); // or any other delimiter
 * Parser.setTemplate("/path/to/resource/{STRING:StringParam}"); // path to resource
 * </pre>
 * Parsing:
 * <pre>
 * UrlParametersMap params = parser.parse(request.getURI()); // or request path
 * Boolean hasStringParam = params.parameterExists("StringParam");
 * String stringParam = params.getString("StringParam");
 * </pre>
 * See the respective methods for more info on formatting codes
 * 
 * @author Arjun Vikram
 * @since 1.0.0
 * @see UrlParametersMap
 */
public class UrlParser {
	private String delimiter;
	private String template;
	private String[] brokenTemplate;
	
	/**
	 * Creates a new UrlParser with the given template. Identical to:
	 * <pre>
	 * UrlParser parser = new UrlParser();
	 * parser.setTemplate(template);
	 * </pre> 
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param template the template passed to setDelimeter
	 * @see #setTemplate(String)
	 */
	public UrlParser(String template) {
		this();
		setTemplate(template);
	}
	
	/**
	 * Creates a new UrlParser with default delimiter of {@code [/\\\\]} and no default template
	 * 
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @see #setDelimiter(String)
	 */
	public UrlParser() {
		setDelimiter("[/\\\\]");
	}
	
	/**
	 * Returns the delimiter used by the UrlParser
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @return the delimiter used by the UrlParser
	 * @see #setDelimiter(String)
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Sets the delimiter used by the UrlParser.
	 * Must be in standard regex form (e.g. {@code "[/\\\\]"}).
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param delimiter the delimiter to be used for the UrlParser
	 * @see #getDelimiter()
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Returns the template used by the UrlParser
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @return the template used by the UrlParser
	 * @see #setTemplate(String)
	 */
	public String getTemplate() {
		return template;
	}
	
	/**
	 * Returns the broken form of the template used by the UrlParser
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @return the broken form of the template used by the UrlParser
	 * @see #setTemplate(String)
	 */
	public String[] getBrokenTemplate() {
		return brokenTemplate;
	}

	/**
	 * Sets the template used by the UrlParser.
	 * The template is the base URI with placeholders in the parameters
	 * For example: {@code /path/to/resource/{STRING:StringParam}/{INT:IntParam}/more/paths/{BOOLEAN:BooleanParam}.
	 * Please do not close the path with a / (or other delimiter). The parser strips out these characters during parsing
	 * Placeholder values are as follows: {@code{TYPE:name}}.
	 * The type can be any of the types listed in {@link ParameterType}, namely STRING, INT, BOOLEAN. 
	 * The name can be any alphanumeric value.
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param template the template to be used for the UrlParser
	 * @see #getTemplate()
	 */
	public void setTemplate(String template) {
		this.template = template;
		brokenTemplate = template.split(delimiter);
	}
	
	/**
	 * Parses the provided URI and returns a {@link UrlParametersMap}.
	 * This map contains all the provided parameters that are found in the URI.
	 * If the template is longer than the URI, the parameters are marked as not being provided (excluded from the {@link UrlParametersMap})
	 * If the URI is longer than the template, any extra values are ignored.
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param URI the URI to parse.
	 * @return the {@link UrlParametersMap} that contains the provided parameters
	 * @throws ParameterParseException if parameters aren't formatted according to template
	 */
	public UrlParametersMap parse(String URI) throws ParameterParseException{
		return parse(URI, new UrlParametersMap());
	}
	
	/**
	 * Allows you to add parameters to a custom UrlParametersMap or a subclass
	 * @see #parse(String)
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param URI the URI to parse
	 * @param params the {@link UrlParametersMap} to add parameters to
	 * @return the {@link UrlParametersMap} that contains the provided parameters
	 * @throws ParameterParseException if parameters aren't formatted according to template
	 */
	public UrlParametersMap parse(String URI, UrlParametersMap params) throws ParameterParseException {
		String cleanedURI = trimLastSlash(URI);
		String[] brokenURI = cleanedURI.split(delimiter);
		for (int i = 0; i < brokenTemplate.length && i < brokenURI.length; i++) {
			if(brokenTemplate[i].matches("\\{.*\\}")){
				parseParameter(brokenTemplate[i],brokenURI[i],params);
			}
		}
		return params;
	}

	/**
	 * Parses individual parameters, sorting them according to their type
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param template the template to be used for parsing
	 * @param value the value of the parameter
	 * @param params the {@link UrlParametersMap} to add the parameters to
	 * @throws ParameterParseException if parameters aren't formatted according to template
	 */
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

	/**
	 * Parses a string, adding it to the provided {@link UrlParametersMap}
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @param params the {@link UrlParametersMap} to add the parameter to
	 */
	void addString(UrlParametersMap params, String name, String value) {
		params.addParameter(name, ParameterType.STRING, value);
	}
	
	/**
	 * Parses an integer, adding it to the provided {@link UrlParametersMap}
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @param params the {@link UrlParametersMap} to add the parameter to
	 * @throws ParameterParseException if parameters aren't formatted according to template
	 */
	void addInt(UrlParametersMap params, String name, String value) throws ParameterParseException{
		try{
			int intValue = Integer.parseInt(value);
			params.addParameter(name, ParameterType.INT, intValue);
		}catch(NumberFormatException e){
			throw new ParameterParseException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	/**
	 * Parses a boolean, adding it to the provided {@link UrlParametersMap}
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @param params the {@link UrlParametersMap} to add the parameter to
	 * @throws ParameterParseException if parameters aren't formatted according to template
	 */
	void addBoolean(UrlParametersMap params, String name, String value) throws ParameterParseException {
		try{
			boolean intValue = parseBoolean(value);
			params.addParameter(name, ParameterType.BOOLEAN, intValue);
		}catch(NumberFormatException e){
			throw new ParameterParseException("String \""+value+"\" could not be parsed as integer",e,value,ParameterType.INT);
		}
	}

	/**
	 * Converts multiple formats of booleans from their string representation to a boolean format
	 * <table summary="">
	 * 	<tr>
	 *      <th>String</th>
	 *      <th>Boolean</th>
	 * 	</tr>
	 *      <td>true</td>
	 *      <td>{@code true}</td>
	 * 	</tr>
	 *      <td>false</td>
	 *      <td>{@code false}</td>
	 * 	</tr>
	 *      <td>1</td>
	 *      <td>{@code true}</td>
	 * 	</tr>
	 *      <td>0</td>
	 *      <td>{@code false}</td>
	 * 	</tr>
	 *      <td>yes</td>
	 *      <td>{@code true}</td>
	 * 	</tr>
	 *      <td>no</td>
	 *      <td>{@code false}</td>
	 * 	</tr>
	 * </table>
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param name the name of the parameter
	 * @param value the value of the parameter
	 * @param params the {@link UrlParametersMap} to add the parameter to
	 * @return the boolean value of the passed value
	 * @throws NumberFormatException if value is not one of the possible values listed above
	 */
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
	
	
	/**
	 * Removes the last delimiter (slash by default) in the URI passed to it.
	 * If the passed string ends with the delimiter, it is removed.
	 * Otherwise, it is returned as is
	 * @see #trimLastSlash(String, String)
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param s the URI to trim
	 * @return the URI without its final delimiter
	 */
	String trimLastSlash(String s){
		return trimLastSlash(s,delimiter);
	}
	
	/**
	 * Removes the last delimiter (slash by default) in the URI passed to it.
	 * If the passed string ends with the delimiter, it is removed.
	 * Otherwise, it is returned as is
	 * @author Arjun Vikram
	 * @since 1.0.0
	 * @param s the URI to trim
	 * @param delimiter the delimiter to remove from the end of the string
	 * @return the URI without its final delimiter
	 */
	static String trimLastSlash(String s, String delimiter){
		return Character.valueOf(s.charAt(s.length() - 1)).toString().matches(delimiter) ?
				s.substring(0, s.length() - 1) :
				s;
	}
	
}
