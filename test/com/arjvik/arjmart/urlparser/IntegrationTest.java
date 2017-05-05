package com.arjvik.arjmart.urlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegrationTest {

	@Test
	public void test() throws ParameterParseException, IncompatibleParameterTypeException, ParameterNotProvidedException {
		UrlParser parser = new UrlParser();
		parser.setTemplate("/path/to/resource/{INT:IntParam}/{STRING:StringParam}/more/paths/{BOOLEAN:BooleanParam}/{INT:NotProvided}");
		UrlParametersMap parameters = parser.parse("/path/to/resource/1/string/more/paths/true/");
		assertTrue(parameters.parameterExists("StringParam"));
		assertTrue(parameters.getInt("IntParam")==1);
		assertTrue(parameters.getBoolean("BooleanParam")==true);
		assertTrue(parameters.getString("StringParam").equals("string"));
		try{
			parameters.getInt("NotProvided");
			fail();
		}catch(ParameterNotProvidedException ignored){;}
		try{
			parameters.getInt("BooleanParam");
		}catch(IncompatibleParameterTypeException ignored){;}
	}
}
