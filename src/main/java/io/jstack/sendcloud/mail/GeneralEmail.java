package io.jstack.sendcloud.mail;

public class GeneralEmail extends Email<GeneralEmail> {

    public GeneralEmail html(String html) {
        return addParameter("html", html);
    }

    @Override
    protected GeneralEmail getThis() {
        return this;
    }

}
