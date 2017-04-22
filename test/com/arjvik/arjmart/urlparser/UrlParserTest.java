package com.arjvik.arjmart.urlparser;

import static org.junit.Assert.*;

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
	public void testParse() {
		fail("Not yet implemented"); // TODO
	}

}
