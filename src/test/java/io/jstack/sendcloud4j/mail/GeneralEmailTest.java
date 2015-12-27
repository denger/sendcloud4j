package io.jstack.sendcloud4j.mail;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeneralEmailTest {

    @Test
    public void testAddBccParameter() {
        GeneralEmail generalEmail = new GeneralEmail();

        generalEmail.bcc("jstack@jstack.io");
        generalEmail.bcc("denger@jstack.io;tc@jstack.io");

        assertEquals(generalEmail.getParameter("bcc"), "jstack@jstack.io;denger@jstack.io;tc@jstack.io");
    }

    @Test
    public void testAddCcParameter() {
        GeneralEmail generalEmail = new GeneralEmail();

        generalEmail.cc("jstack@jstack.io");
        generalEmail.cc("denger@jstack.io;tc@jstack.io");

        assertEquals(generalEmail.getParameter("cc"), "jstack@jstack.io;denger@jstack.io;tc@jstack.io");
    }

}
