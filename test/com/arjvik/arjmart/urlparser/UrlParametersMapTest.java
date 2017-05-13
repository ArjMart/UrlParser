package com.arjvik.arjmart.urlparser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class UrlParametersMapTest {

	@Test
	public void testAddParameter() {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String,ParameterValue>) mock(HashMap.class);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.addParameter("ParameterName", ParameterType.STRING, "value");
		verify(map).put("ParameterName", new ParameterValue(ParameterType.STRING,"value"));
	}

	@Test
	public void testParameterExistsForRealParam() {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String,ParameterValue>) mock(HashMap.class);
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertTrue("ParameterMap.parameterExists should return true for items that exist",parameters.parameterExists("ParameterName"));
		verify(map).containsKey("ParameterName");
	}
	
	@Test
	public void testParameterExistsForNonexistingParam() {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String,ParameterValue>) mock(HashMap.class);
		when(map.containsKey("DoesntExist")).thenReturn(false);
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertFalse("ParameterMap.parameterExists should return false for items that don't exist",parameters.parameterExists("DoesntExist"));
		verify(map).containsKey("DoesntExist");
	}

	@Test
	public void testGetStringOnString() throws ParameterNotProvidedException {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertEquals("ParameterMap.getString should return the right string",parameters.getString("ParameterName"),"value");
		verify(map).get("ParameterName");
	}
	
	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetStringOnInt() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.INT,1));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getString("ParameterName");
		fail("ParameterMap.getString should throw an error if wrong type");
	}
	
	@Test(expected=ParameterNotProvidedException.class)
	public void testGetStringOnNotProvided() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("DoesntExist")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("DoesntExist")).thenReturn(false);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getString("DoesntExist");
		fail("ParameterMap.getString should throw an error if parameter not provided");
	}
	
	@Test
	public void testGetIntOnInt() throws ParameterNotProvidedException {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.INT,1));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertEquals("ParameterMap.getInt should return the right int",parameters.getInt("ParameterName"),1);
		verify(map).get("ParameterName");
	}
	
	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetIntOnString() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getInt("ParameterName");
		fail("ParameterMap.getInt should throw an error if wrong type");
	}
	
	@Test(expected=ParameterNotProvidedException.class)
	public void testGetIntOnNotProvided() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("DoesntExist")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("DoesntExist")).thenReturn(false);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getInt("DoesntExist");
		fail("ParameterMap.getInt should throw an error if parameter not provided");
	}

	@Test
	public void testGetBooleanOnBooolean() throws ParameterNotProvidedException {
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.BOOLEAN,true));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		assertEquals("ParameterMap.getBoolean should return the right boolean",parameters.getBoolean("ParameterName"),true);
		verify(map).get("ParameterName");
	}

	@Test(expected=IncompatibleParameterTypeException.class)
	public void testGetBooleanOnString() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("ParameterName")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("ParameterName")).thenReturn(true);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getBoolean("ParameterName");
		fail("ParameterMap.getBoolean should throw an error if wrong type");
	}
	

	@Test(expected=ParameterNotProvidedException.class)
	public void testGetBooleanOnNotProvided() throws IncompatibleParameterTypeException, ParameterNotProvidedException{
		@SuppressWarnings("unchecked")
		Map<String,ParameterValue> map = (Map<String, ParameterValue>) mock(HashMap.class);
		when(map.get("DoesntExist")).thenReturn(new ParameterValue(ParameterType.STRING,"value"));
		when(map.containsKey("DoesntExist")).thenReturn(false);
		UrlParametersMap parameters = new UrlParametersMap(map);
		parameters.getBoolean("DoesntExist");
		fail("ParameterMap.getBoolean should throw an error if parameter not provided");
	}
}