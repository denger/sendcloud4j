package io.jstack.sendcloud4j.mail;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemplateEmailTest {
    Logger logger = LoggerFactory.getLogger(TemplateEmailTest.class);

    private static final String NOT_USE_ADD_XSMTPAPI = "\"to\":[\"denger.it@gmail.com\"]";

    @Test
    public void testRewriteParametersWhenNotUseAddressList() {
        TemplateEmail templateEmail = new TemplateEmail("test_tmp")
                .to("denger.it@gmail.com")
                .substitutionVars(Substitution.sub().set("name", "Jack"));

        Map<String, String> params = templateEmail.getParameters();
        logger.info("Not use address list: {}", params);

        assertEquals(params.get("to"), null);
        assertTrue(params.get("xsmtpapi").contains(NOT_USE_ADD_XSMTPAPI));
    }

    @Test
    public void testRewriteParametersWhenUseAddressList() {
        TemplateEmail templateEmail = new TemplateEmail("test_tmp")
                .to("denger.it@gmail.com")
                .useAddressList()
                .substitutionVars(Substitution.sub().set("name", "Jack"));

        Map<String, String> params = templateEmail.getParameters();
        logger.info("used address list: {}", params);

        assertEquals(params.get("to"), "denger.it@gmail.com");
        assertEquals(params.get("xsmtpapi"), null);
    }

}
