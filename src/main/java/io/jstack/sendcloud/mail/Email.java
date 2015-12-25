package io.jstack.sendcloud.mail;

import java.util.HashMap;
import java.util.Map;

public abstract class Email<E extends Email<E>> {

    public static GeneralEmail general() {
        return new GeneralEmail();
    }

    public static TemplateEmail template(String template) {
        return new TemplateEmail(template);
    }

    protected Map<String, String> parameters;

    private boolean isRewrite = false;

    public Email() {
        parameters = new HashMap<String, String>();
    }

    public E from(String from) {
        return addParameter("from", from);
    }

    public E fromName(String fromName) {
        return addParameter("fromname", fromName);
    }

    public E subject(String subject) {
        return addParameter("subject", subject);
    }

    public E replyTo(String replyTo) {
        return addParameter("replyto", replyTo);
    }

    public E bcc(String bcc) {
        return addParameter("bcc", bcc);
    }

    public E cc(String cc) {
        return addParameter("cc", cc);
    }

    public E to(String to) {
        return addParameter("to", to);
    }

    protected String to() {
        return getParameter("to");
    }

    public E label(String label) {
        return addParameter("label", label);
    }

    public E headers(String headers) {
        return addParameter("headers", headers);
    }

    protected E addParameter(String name, String value) {
        if (value == null || value.trim().length() <= 0) {
            parameters.remove(name);
        } else {
            parameters.put(name, value);
        }
        return getThis();
    }

    protected String getParameter(String name) {
        return parameters.get(name);
    }

    protected final Map<String, String> getParameters() {
        if (!isRewrite)
            rewriteParameters();
        isRewrite = true;

        return parameters;
    }

    protected void rewriteParameters() {
    }

    protected abstract E getThis();

}
