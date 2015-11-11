package io.jstack.sendcloud.mail;

import io.jstack.sendcloud.SendCloud;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class MailWebApi {

    private SendCloud sendCloud;

    private static Charset UTF_8 = Charset.forName("UTF-8");

    private static Logger logger = LoggerFactory.getLogger(MailWebApi.class);

    public static MailWebApi create(SendCloud sendCloud) {
        return new MailWebApi(sendCloud);
    }

    private MailWebApi(SendCloud sendCloud) {
        this.sendCloud = sendCloud;
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
                .connectTimeout(sendCloud.connectTimeout())
                .socketTimeout(sendCloud.socketTimeout())
                .execute()
                .returnContent().asString(UTF_8);
    }

    private Form convertFrom(Map<String, String> parameters) {
        Form form = Form.form();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            form.add(param.getKey(), param.getValue());
        }
        form.add("api_user", sendCloud.apiUser());
        form.add("api_key", sendCloud.apiKey());

        return form;
    }

}
