package com.arjvik.arjmart.urlparser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static com.arjvik.arjmart.urlparser.UrlParser.parseBoolean;

import java.util.Arrays;

import org.junit.Test;

public class UrlParserTest {

	@Test
	public void testSetGetTemplate() {
		UrlParser parser = new UrlParser();
		final String template = "/path/to/resource/{INT:IntParam}/{STRING:StringParam}";
		final String[] brokenTemplate = new String[]{"","path","to","resource","{INT:IntParam}","{STRING:StringParam}"};
		parser.setTemplate(template);
		assertTrue("getTemplate must return the template set by setTemplate", parser.getTemplate().equals(template));
		assertTrue("getBrokenTemplate must return the correct broken template", Arrays.deepEquals(parser.getBrokenTemplate(),brokenTemplate));
	}
	
	@Test
	public void testSetGetDelimeter() {
		UrlParser parser = new UrlParser();
		final String delimeter = "%";
		parser.setDelimiter(delimeter);
		assertTrue("getDelimeter must return the delimiter set by setDelimeter", parser.getDelimiter().equals(delimeter));
	}
	
	@Test
	public void testParseParameterOnString() throws ParameterFormatException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap params = mock(UrlParametersMap.class);
		parser.parseParameter("{STRING:name}", "value", params);
		verify(parser).addString(params, "name", "value");
	}
	
	
	
	@Test
	public void testAddString() {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addString(params, "name", "value");
		verify(params).addParameter("name", ParameterType.STRING, "value");
	}
	
	@Test
	public void testAddIntOnInt() throws ParameterFormatException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addInt(params, "name", "1");
		verify(params).addParameter("name", ParameterType.INT, 1);
	}
	
	@Test(expected=ParameterFormatException.class)
	public void testAddIntOnString() throws ParameterFormatException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addInt(params, "name", "value");
		fail("addInt should throw an exception if passed a NAN");
	}
	
	@Test
	public void testAddBooleanOnBoolean() throws ParameterFormatException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addBoolean(params, "name", "true");
		verify(params).addParameter("name", ParameterType.BOOLEAN, true);
	}
	
	@Test(expected=ParameterFormatException.class)
	public void testAddBooleanOnString() throws ParameterFormatException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addBoolean(params, "name", "value");
		fail("addBoolean should throw an exception if passed a non-boolean string");
	}

	@Test
	public void testParseBoolean() {
		assertTrue("parseBoolean should correctly parse booleans according to rules below",
				parseBoolean("true") &&
				!parseBoolean("false") &&
				parseBoolean("1") &&
				!parseBoolean("0") &&
				parseBoolean("yes") &&
				!parseBoolean("no")
		);
	}
	
	@Test(expected=NumberFormatException.class)
	public void testParseBooleanOnInvalid() {
		parseBoolean("not a boolean");
		fail("parseBoolean should throw an exception if invoked with a non-boolean value");
	}

}
