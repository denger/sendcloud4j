package io.jstack.sendcloud4j.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.jstack.sendcloud.mail.Result;

import org.junit.Test;

public class TestResult {
	private static String SUCCESS_MSG = "{\"message\":\"success\"}";
	private static String BLANK_ERROR_MSG = "{\"message\":\"error\",\"errors\":[]}";
	private static String BAD_PASSWORD_ERROR_MSG = "{\"message\":\"error\",\"errors\":[\"Bad username / password!\"]}";
	private static String MOCK_MANY_ERRORS_MSG = "{\"message\":\"error\",\"errors\":[\"Bad username !\",\"Bad password !\"]}";

	@Test
	public void testFromSuccess() {
		Result result = new Result(SUCCESS_MSG);
		assertEquals(Result.CODE.SUCCESS, result.getMessage());
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testFromBlankError() {
		Result result = new Result(BLANK_ERROR_MSG);
		assertEquals(Result.CODE.ERROR, result.getMessage());
		assertFalse(result.isSuccess());
		assertEquals("",result.getError());
	}
	
	@Test
	public void testFromBadError() {
		Result result = new Result(BAD_PASSWORD_ERROR_MSG);
		assertEquals(Result.CODE.ERROR, result.getMessage());
		assertFalse(result.isSuccess());
		assertEquals("Bad username / password!",result.getError());
	}
	
	@Test
	public void testFromManyErrors() {
		Result result = new Result(MOCK_MANY_ERRORS_MSG);
		assertEquals("error", result.getMessage());
		assertFalse(result.isSuccess());
		assertEquals("Bad username !;Bad password !;",result.getError());
	}
}
