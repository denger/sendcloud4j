package io.jstack.sendcloud4j.mail;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

public class Result {
    private static Logger logger = LoggerFactory.getLogger(Result.class);

    private boolean success = false;
    private int statusCode;
    private String message;

    private String responseText;

    // fixme: 针对内部错误的处理方式待改进
    private static final int IO_EXCEPTION_CODE = 500;
    private static final int SOCKET_EXCEPTION_CODE = 501;
    private static final int CONNECT_EXCEPTION_CODE = 502;

    protected Result(String responseText) {
        this.responseText = responseText;
        parserResponseText(responseText);
    }

    protected static Result createExceptionResult(Email email, Throwable throwable) {
        logger.error("Send mail fail, request params: {}", email.getParameters(), throwable);
        return new Result(false, getIOErrorCode(throwable), throwable.getMessage());
    }

    private static int getIOErrorCode(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return SOCKET_EXCEPTION_CODE;
        } else if (throwable instanceof ConnectTimeoutException) {
            return CONNECT_EXCEPTION_CODE;
        }
        return IO_EXCEPTION_CODE;
    }

    protected Result(boolean success, int statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    protected void parserResponseText(String responseJson) {
        JSONObject jsonObject = new JSONObject(responseJson);
        if (jsonObject.has("result")) {
            this.success = jsonObject.getBoolean("result");
        }
        if (jsonObject.has("message")) {
            this.message = jsonObject.getString("message");
        }
        if (jsonObject.has("statusCode")) {
            this.statusCode = jsonObject.getInt("statusCode");
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.responseText;
    }
}
