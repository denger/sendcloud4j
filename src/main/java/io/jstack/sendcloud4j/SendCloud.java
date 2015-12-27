package io.jstack.sendcloud4j;


import io.jstack.sendcloud4j.mail.MailWebApi;

/**
 * Send Cloud API
 *
 * @author denger
 */
public class SendCloud {

    public static final String DOMAIN = "http://sendcloud.sohu.com";

    public static final String API_DOMAIN = "http://api.sendcloud.net";

    public static SendCloud createWebApi(String apiUser, String apiKey) {
        return new SendCloud(apiUser, apiKey);
    }

    private String apiUser;
    private String apiKey;

    private int connectTimeout = 500;
    private int socketTimeout = 1000;

    protected SendCloud(String apiUser, String apiKey) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
    }

    public void connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int connectTimeout() {
        return this.connectTimeout;
    }

    public void socketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int socketTimeout() {
        return this.socketTimeout;
    }

    public String apiUser() {
        return this.apiUser;
    }

    public String apiKey() {
        return this.apiKey;
    }

    public MailWebApi mail() {
        return MailWebApi.create(this);
    }
}
