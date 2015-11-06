package com.sohu.sendcloud.client.mail;

import com.sohu.sendcloud.client.SendCloud;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class MailWebApi {

    private String apiKey;
    private String apiUser;

    private static Charset UTF_8 = Charset.forName("UTF-8");

    private static Logger logger = LoggerFactory.getLogger(MailWebApi.class);

    public static MailWebApi create(String apiKey, String apiUser) {
        return new MailWebApi(apiKey, apiUser);
    }

    private MailWebApi(String apiKey, String apiUser) {
        this.apiKey = apiKey;
        this.apiUser = apiUser;
    }

    public String send(Email email) {
        try {
            return requestSend(getSendAPIURI(email), email.getParameters());
        } catch (IOException ioe) {
            logger.error("Request send mail error: {}", ioe);
            return ioe.getMessage();
        }
    }

    private String getSendAPIURI(Email email) {
        return String.format("%s/webapi/mail.%s.json", SendCloud.DOMAIN, (isTemplate(email) ? "send_template" : "send"));
    }

    private boolean isTemplate(Email email) {
        return (email instanceof TemplateEmail);
    }

    private String requestSend(String uri, Map<String, String> params) throws IOException {
        return Request.Post(uri)
                .bodyForm(convertFrom(params).build(), UTF_8)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute()
                .returnContent().asString(UTF_8);
    }

    private Form convertFrom(Map<String, String> parameters) {
        Form form = Form.form();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            form.add(param.getKey(), param.getValue());
        }
        form.add("api_user", apiUser);
        form.add("api_key", apiKey);

        return form;
    }

}
