package io.jstack.sendcloud4j.mail;

import io.jstack.sendcloud4j.SendCloud;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

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

    public static final ContentType TEXT_PLAIN = ContentType.create("text/plain", UTF_8);

    public static MailWebApi create(SendCloud sendCloud) {
        return new MailWebApi(sendCloud);
    }

    private MailWebApi(SendCloud sendCloud) {
        this.sendCloud = sendCloud;
    }

    public Result send(Email email) {
        try {
            String jsonResult = requestSend(getSendAPIURI(email), email);
            return new Result(jsonResult);
        } catch (IOException ioe) {
            return Result.createExceptionResult(email, ioe);
        }
    }

    protected String getSendAPIURI(Email email) {
        return String.format("%s/apiv2/mail/%s", SendCloud.API_DOMAIN,
                (isTemplate(email) ? "sendtemplate" : "send"));
    }

    private boolean isTemplate(Email email) {
        return (email instanceof TemplateEmail);
    }

    private String requestSend(String uri, Email email) throws IOException {
        Request request = Request.Post(uri)
                .connectTimeout(sendCloud.connectTimeout())
                .socketTimeout(sendCloud.socketTimeout());

        if (email.hasAttachment()) {
            request.body(getMultipartEmailHttpEntity(email));
        } else {
            request.bodyForm(convertFrom(email.getParameters()).build(), UTF_8);
        }
        return request.execute().returnContent().asString(UTF_8);
    }

    private HttpEntity getMultipartEmailHttpEntity(Email email) {
        MultipartEntityBuilder entityBuilder = createEntityBuilder();

        Map<String, byte[]> attachments = email.attachments();
        for (Map.Entry<String, byte[]> attachment : attachments.entrySet()) {
            entityBuilder.addBinaryBody(email.attachmentsKey(), attachment.getValue(),
                    ContentType.MULTIPART_FORM_DATA, attachment.getKey());
        }
        addParametersToTextBody(entityBuilder, email.getParameters());

        return entityBuilder.build();
    }

    public MultipartEntityBuilder createEntityBuilder() {
        return MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(UTF_8);
    }

    private void addParametersToTextBody(MultipartEntityBuilder entityBuilder, Map<String, String> parameters) {
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            entityBuilder.addTextBody(param.getKey(), param.getValue(), TEXT_PLAIN);
        }
        entityBuilder.addTextBody("apiUser", sendCloud.apiUser(), TEXT_PLAIN);
        entityBuilder.addTextBody("apiKey", sendCloud.apiKey(), TEXT_PLAIN);
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
