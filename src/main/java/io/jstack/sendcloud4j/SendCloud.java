package io.jstack.sendcloud4j;


import io.jstack.sendcloud4j.mail.MailWebApi;
import org.apache.http.HttpHost;

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

    protected SendCloud(String apiUser, String apiKey) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
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

