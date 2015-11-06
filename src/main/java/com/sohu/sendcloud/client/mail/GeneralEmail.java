package com.sohu.sendcloud.client.mail;


public class GeneralEmail extends Email<GeneralEmail> {


    public GeneralEmail html(String html) {
        return addParameter("html", html);
    }

    @Override
    protected GeneralEmail getThis() {
        return this;
    }

}
