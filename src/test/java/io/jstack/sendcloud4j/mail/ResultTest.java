package io.jstack.sendcloud4j.mail;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {
    private static String SUCCESS_MSG = "{\"result\":true,\"statusCode\":200,\"message\":\"请求成功\",\"info\":{\"emailIdList\":[\"xxxxx@s\"]}}";

    private static String BLANK_ERROR_MSG = "{\"result\":false,\"statusCode\":40863,\"message\":\"to中有不存在的地址列表. \",\"info\":{\"emailIdList\":[\"xxxxx@s\"]}}";

    private static String BLANK_MSG = "{}";

    @Test
    public void testFromSuccess() {
        Result result = new Result(SUCCESS_MSG);

        assertTrue(result.isSuccess());
        assertEquals(result.getStatusCode(), 200);
        assertEquals(result.getMessage(), "请求成功");
    }

    @Test
    public void testFromFail() {
        Result result = new Result(BLANK_ERROR_MSG);
        assertFalse(result.isSuccess());
        assertEquals(result.getStatusCode(), 40863);
        assertEquals(result.getMessage(), "to中有不存在的地址列表. ");
    }

    @Test
    public void testFromException() {
        Result result = Result.createExceptionResult(new GeneralEmail(), new NullPointerException("Nullexception"));

        assertFalse(result.isSuccess());
        assertEquals(result.getStatusCode(), 500);
        assertEquals(result.getMessage(), "Nullexception");
    }

    @Test
    public void testFromBlankJson() {
        Result result = new Result(BLANK_MSG);
        assertFalse(result.isSuccess());
        assertEquals(result.getStatusCode(), 0);
        assertEquals(result.getMessage(), null);
    }

}
