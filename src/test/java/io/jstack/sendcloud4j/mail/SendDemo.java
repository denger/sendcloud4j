package io.jstack.sendcloud4j.mail;


import io.jstack.sendcloud4j.SendCloud;

import java.io.File;

public class SendDemo {

    public static void main(String[] args) {
        send();
        sendTemplate();
        sendAttachmentTemplate();
    }

    public static void sendAttachmentTemplate(){
        SendCloud sendCloud = SendCloud.createWebApi("rmbpay", "opNAlJON0NMozWJl");

        Email email = Email.template("order_user")
                .from("support@rmbpay.io")
                .substitutionVars(Substitution.sub()
                        .set("product", "附件测试")
                        .set("name", "denger"))
                .attachment(new File("/Users/denger/Workspaces/Works/rmbpayWorks/sendcloud4j/settings.gradle"))
                .attachment(new File("/Users/denger/Workspaces/Works/rmbpayWorks/sendcloud4j/settings.gradle"), "hello")
                .to("denger.it@gmail.com");

        Result result = sendCloud.mail().send(email);

        System.out.println(result.isSuccess() + ":" + result.getMessage());
        System.out.println(result);
    }


    public static void sendTemplate(){
        SendCloud sendCloud = SendCloud.createWebApi("rmbpay", "opNAlJON0NMozWJl");

        Email email = Email.template("order_user")
                .from("support@rmbpay.io")
                .substitutionVars(Substitution.sub()
                        .set("product", "iPhone 6S")
                        .set("name", "denger"))
                .to("denger.it@gmail.com");

        Result result = sendCloud.mail().send(email);

        System.out.println(result.isSuccess() + ":" + result.getMessage());
        System.out.println(result);
    }

    public static void send(){
        SendCloud sendCloud = SendCloud.createWebApi("rmbpay", "opNAlJON0NMozWJl");

        Email email = Email.general()
                .from("denger@jstack.io")
                .fromName("Denger Tung")
                .plain("<b>Hello World!</b>") // or .plain()
                .subject("1024")
                .to("denger.it@gmail.com");

        Result result = sendCloud.mail().send(email);

        System.out.println(result.isSuccess() + ":" + result.getMessage());
        System.out.println(result);

    }
}
