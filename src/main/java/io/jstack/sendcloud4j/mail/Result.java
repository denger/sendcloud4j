package io.jstack.sendcloud4j.mail;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Result {
    private static Logger logger = LoggerFactory.getLogger(Result.class);

    private boolean success = false;
    private int statusCode;
    private String message;

    private String responseText;


    protected Result(String responseText) {
        this.responseText = responseText;
        parserResponseText(responseText);
    }

    protected static Result createExceptionResult(Email email, Throwable throwable) {
        logger.error("Send mail fail, request params: {}", email.getParameters(), throwable);
        return new Result(false, 500, throwable.getMessage());
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
