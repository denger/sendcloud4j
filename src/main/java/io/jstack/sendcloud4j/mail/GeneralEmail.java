package io.jstack.sendcloud4j.mail;

public class GeneralEmail extends Email<GeneralEmail> {

    /**
     * 密送地址. 多个地址使用';'分隔
     *
     * @param bcc 密送地址
     * @return E
     */
    public GeneralEmail bcc(String bcc) {
        if (bcc != null && bcc.length() > 0) {
            return bcc(bcc.split(";"));
        } else {
            return addParameter("bcc", null);
        }
    }

    /**
     * 密送地址列表
     *
     * @param bccs 密送地址列表
     * @return E
     */
    public GeneralEmail bcc(String[] bccs) {
        return addParameters("bcc", bccs, ";");
    }

    /**
     * 抄送地址. 多个地址使用';'分隔
     *
     * @param cc 抄送地址
     * @return E
     */
    public GeneralEmail cc(String cc) {
        if (cc != null && cc.length() > 0) {
            cc(cc.split(";"));
        } else {
            return addParameter("cc", null);
        }
        return getThis();
    }

    /**
     * 抄送地址. 多个地址使用';'分隔
     *
     * @param ccs 抄送地址列表
     * @return E
     */
    public GeneralEmail cc(String[] ccs) {
        return addParameters("cc", ccs, ";");
    }

    /**
     * 邮件的内容. 邮件格式为 text/html
     *
     * @param html 邮件内容
     * @return E
     */
    public GeneralEmail html(String html) {
        return addParameter("html", html);
    }

    /**
     * 邮件的内容. 邮件格式为 text/plain
     *
     * @param plain 邮件内容
     * @return E
     */
    public GeneralEmail plain(String plain) {
        return addParameter("plain", plain);
    }


    @Override
    protected GeneralEmail getThis() {
        return this;
    }

}
