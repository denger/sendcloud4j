package io.jstack.sendcloud4j.mail;

import io.jstack.sendcloud4j.SendCloud;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 邮件 API v2 实现。
 *
 * @author denger
 */
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

    public Result send(Email email) {
        try {
            String jsonResult = requestSend(getSendAPIURI(email),
                    email.getParameters());
            return new Result(jsonResult);
        } catch (IOException ioe) {
            logger.error("Request send mail error: {}", ioe);
            return new Result(Result.CODE.ERROR, ioe.getMessage());
        }
    }

    protected String getSendAPIURI(Email email) {
        return String.format("%s/apiv2/mail/%s", SendCloud.API_DOMAIN,
                (isTemplate(email) ? "sendtemplate" : "send"));
    }

    private boolean isTemplate(Email email) {
        return (email instanceof TemplateEmail);
    }

    private String requestSend(String uri, Map<String, String> params)
            throws IOException {
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
        form.add("apiUser", sendCloud.apiUser());
        form.add("apiKey", sendCloud.apiKey());

        return form;
    }

}
