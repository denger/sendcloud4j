package io.jstack.sendcloud4j.mail;

import io.jstack.sendcloud4j.SendCloud;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MailWebApiTest {

    private String apiUser = "testApiUser";
    private String apiKey = "testApiKey";

    SendCloud sendCloud = SendCloud.createWebApi(apiUser, apiKey);

    @Test
    public void testGetSendTemplateAPIURI() {
        String uri = sendCloud.mail().getSendAPIURI(new TemplateEmail("tmmp"));
        assertEquals(uri, SendCloud.API_DOMAIN + "/apiv2/mail/sendtemplate");
    }

    @Test
    public void testGetSendAPIURI() {
        String uri = sendCloud.mail().getSendAPIURI(new GeneralEmail());
        assertEquals(uri, SendCloud.API_DOMAIN + "/apiv2/mail/send");
    }
}
