package io.jstack.sendcloud4j.mail;


import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

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

    @Test
    public void testAddFileAttachments() {
        email.attachments(new File[]{new File("src/test/resouces/3byte_test.txt")});
        Map<String, byte[]> attachments = email.attachments();

        assertTrue(email.hasAttachment());
        assertEquals(attachments.size(), 1);
        assertTrue(attachments.containsKey("3byte_test.txt"));
        assertEquals(attachments.get("3byte_test.txt").length, 3);
    }

    @Test
    public void testAddBytesAttachments() {
        email.attachment(new byte[]{1,1}, "filename");
        Map<String, byte[]> attachments = email.attachments();

        assertTrue(email.hasAttachment());
        assertEquals(attachments.size(), 1);
        assertEquals(attachments.get("filename").length, 2);
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

