package io.jstack.sendcloud4j.mail;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmailTest {

    private Email email = new MockTestEmail();

    @Test
    public void testAddAndGetParameter() {
        String from = "jack@jstack.io";
        email.addParameter("from", from);

        assertEquals(email.getParameter("from"), from);
        assertEquals(email.getParameters().size(), 1);
        assertEquals(email.getParameters().get("from"), from);
    }

    @Test
    public void testMultipleRecipients() {
        email.to("denger@jstack.io");
        email.to("jack@jstack.io");
        email.to("andy@jstack.io");
        email.to("support@jstack.io");
        email.to("rak@jstack.io;hello@jstack.io");

        assertEquals(email.to().length, 6);
    }

    @Test
    public void testRemoveParameterIfSetValueEmptyOrNull() {
        email.from("denger@stack.io");
        email.from(null);
        assertNull(email.getParameter("from"));

        email.to("denger@jstack.io");
        email.to("");
        assertEquals(email.to().length, 0);
    }

    @Test
    public void testRewriteParameterWhenGetParameters() {
        email.addParameter("test", "hello");
        assertEquals(email.getParameters().get("test"), "hello world");
    }

    class MockTestEmail extends Email<MockTestEmail> {

        @Override
        protected MockTestEmail getThis() {
            return this;
        }

        @Override
        protected void rewriteParameters() {
            if (getParameter("test") != null) {
                addParameter("test", getParameter("test") + " world");
            }
        }
    }
}

