package com.sohu.sendcloud.client;


import com.sohu.sendcloud.client.mail.MailWebApi;

/**
 * Send Cloud API
 *
 * @author denger
 */
public class SendCloud {

    public static final String DOMAIN = "http://sendcloud.sohu.com";

    public static SendCloud createWebApi(String apiUser, String apiKey) {
        return new SendCloud(apiUser, apiKey);
    }

    private String apiUser;
    private String apiKey;

    public SendCloud(String apiUser, String apiKey) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
    }

    public MailWebApi mail() {
        return MailWebApi.create(apiKey, apiUser);
    }
}
