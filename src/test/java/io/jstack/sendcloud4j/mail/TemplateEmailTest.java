package io.jstack.sendcloud4j.mail;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TemplateEmailTest {
    Logger logger = LoggerFactory.getLogger(TemplateEmailTest.class);

    private static final String NOT_USE_ADD_XSMTPAPI = "{\"to\":[\"denger.it@gmail.com\"],\"sub\":{\"%name%\":[\"Jack\"]}}";

    @Test
    public void testRewriteParametersWhenNotUseAddressList() {
        TemplateEmail templateEmail = new TemplateEmail("test_tmp")
                .to("denger.it@gmail.com")
                .substitutionVars(Substitution.sub().set("name", "Jack"));

        Map<String, String> params = templateEmail.getParameters();
        assertEquals(params.get("to"), null);
        assertEquals(params.get("xsmtpapi"), NOT_USE_ADD_XSMTPAPI);

        logger.info("Not use address list: {}", params);
    }

    @Test
    public void testRewriteParametersWhenUseAddressList() {
        TemplateEmail templateEmail = new TemplateEmail("test_tmp")
                .to("denger.it@gmail.com")
                .useAddressList()
                .substitutionVars(Substitution.sub().set("name", "Jack"));

        Map<String, String> params = templateEmail.getParameters();
        assertEquals(params.get("to"), "denger.it@gmail.com");
        assertEquals(params.get("xsmtpapi"), null);

        logger.info("used address list: {}", params);
    }

}
