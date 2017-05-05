package com.arjvik.arjmart.urlparser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import static com.arjvik.arjmart.urlparser.UrlParser.parseBoolean;
import static com.arjvik.arjmart.urlparser.UrlParser.trimLastSlash;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
	public void testParse() throws ParameterParseException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap map = mock(UrlParametersMap.class);
		parser.setTemplate("/path/to/resource/{INT:IntParam}/{STRING:StringParam}/more/paths/{BOOLEAN:BooleanParam}");
		doNothing().when(parser).parseParameter(anyString(), anyString(), eq(map));
		doAnswer(returnsFirstArg()).when(parser).trimLastSlash(anyString());
		parser.parse("/path/to/resource/1/string/more/paths/true",map);
		ArgumentCaptor<String> template = ArgumentCaptor.forClass(String.class),
				value = ArgumentCaptor.forClass(String.class);
		verify(parser,atLeastOnce()).parseParameter(template.capture(), value.capture(), eq(map));
		List<String> templateParams = template.getAllValues(),
				valueParams = value.getAllValues();
		assertTrue("Template must contain all template values",templateParams.containsAll(
				Arrays.asList(new String[]{
						"{INT:IntParam}","{STRING:StringParam}","{BOOLEAN:BooleanParam}"
		})));
		assertTrue("Value must contain all values",valueParams.containsAll(
				Arrays.asList(new String[]{
						"1","string","true"
		})));
		assertTrue("Template and Value index must be the same for Int, String, and Boolean",
				templateParams.indexOf("{INT:IntParam}")==valueParams.indexOf("1") &&
				templateParams.indexOf("{STRING:StringParam}")==valueParams.indexOf("string") &&
				templateParams.indexOf("{BOOLEAN:BooleanParam}")==valueParams.indexOf("true")
		);
	}
	
	@Test
	public void testParseParameterOnString() throws ParameterParseException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap params = mock(UrlParametersMap.class);
		parser.parseParameter("{STRING:name}", "value", params);
		verify(parser).addString(params, "name", "value");
	}
	
	@Test
	public void testParseParameterOnInt() throws ParameterParseException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap params = mock(UrlParametersMap.class);
		parser.parseParameter("{INT:name}", "1", params);
		verify(parser).addInt(params, "name", "1");
	}
	
	@Test
	public void testParseParameterOnBoolean() throws ParameterParseException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap params = mock(UrlParametersMap.class);
		parser.parseParameter("{BOOLEAN:name}", "true", params);
		verify(parser).addBoolean(params, "name", "true");
	}
	
	@Test(expected=ParameterParseException.class)
	public void testParseParameterOnInvalidType() throws ParameterParseException {
		UrlParser parser = spy(new UrlParser());
		UrlParametersMap params = mock(UrlParametersMap.class);
		parser.parseParameter("{NOT-A-TYPE:name}", "true", params);
		fail("parseParameter should throw an error when passed a non-real type template");
	}
	
	@Test
	public void testAddString() {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addString(params, "name", "value");
		verify(params).addParameter("name", ParameterType.STRING, "value");
	}
	
	@Test
	public void testAddIntOnInt() throws ParameterParseException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addInt(params, "name", "1");
		verify(params).addParameter("name", ParameterType.INT, 1);
	}
	
	@Test(expected=ParameterParseException.class)
	public void testAddIntOnString() throws ParameterParseException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addInt(params, "name", "value");
		fail("addInt should throw an exception if passed a NAN");
	}
	
	@Test
	public void testAddBooleanOnBoolean() throws ParameterParseException {
		UrlParser parser = new UrlParser();
		UrlParametersMap params = spy(new UrlParametersMap());
		parser.addBoolean(params, "name", "true");
		verify(params).addParameter("name", ParameterType.BOOLEAN, true);
	}
	
	@Test(expected=ParameterParseException.class)
	public void testAddBooleanOnString() throws ParameterParseException {
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
	
	@Test()
	public void testTrimLastSlashWithSlash() {
		assertTrue("trimLastSlash should remove the last slash", trimLastSlash("/ends/with/slash/", "[/\\\\]").equals("/ends/with/slash"));
	}
	
	@Test()
	public void testTrimLastSlashWithoutSlash() {
		assertTrue("trimLastSlash should do nothing if no slash", trimLastSlash("/what/slash", "[/\\\\]").equals("/what/slash"));
	}

}
