package io.jstack.sendcloud4j.mail;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件信息，抽象普通邮箱和模块邮件公共信息。
 *
 * @param <E>
 * @author denger
 */
public abstract class Email<E extends Email<E>> {

    /**
     * 普通发送邮件
     *
     * @return GeneralEmail
     */
    public static GeneralEmail general() {
        return new GeneralEmail();
    }

    /**
     * 根据模板名创建模块邮件。
     *
     * @param template name
     * @return TemplateEmail
     */
    public static TemplateEmail template(String template) {
        return new TemplateEmail(template);
    }

    protected Map<String, String> parameters;

    private boolean isRewrite = false;

    public Email() {
        parameters = new HashMap<String, String>();
    }

    /**
     * 发件人地址. 举例: support@test.com, Support<support@test.com>。<p>
     * 必须设置.
     *
     * @param from 发件人地址
     * @return E
     */
    public E from(String from) {
        return addParameter("from", from);
    }

    /**
     * 发件人名称. 显示如: Support<support@test.com>
     *
     * @param fromName 发件人名称
     * @return E
     */
    public E fromName(String fromName) {
        return addParameter("fromName", fromName);
    }

    /**
     * 标题. 不能为空
     *
     * @param subject 标题
     * @return E
     */
    public E subject(String subject) {
        return addParameter("subject", subject);
    }

    /**
     * 设置用户默认的回复邮件地址. 如果 replyTo 没有或者为空, 则默认的回复邮件地址为 from.
     *
     * @param replyTo 回复邮件地址
     * @return E
     */
    public E replyTo(String replyTo) {
        return addParameter("replyTo", replyTo);
    }

    /**
     * 收件人地址. 发送多个地址支持调用该方法多次或使用';'分隔, 如 denger@gmail.com;andy@gmail.com
     *
     * @param to 收件人地址
     * @return E
     */
    public E to(String to) {
        if (isNotBlank(to)) {
            to(to.split(";"));
        } else {
            addParameter("to", null);
        }
        return getThis();
    }

    /**
     * 收件人地址，批量设置。
     *
     * @param toAddrs 地址列表
     * @return E
     */
    public E to(String[] toAddrs) {
        return addParameters("to", toAddrs, ";");
    }

    protected String[] to() {
        return getParameters("to", ";");
    }

    /**
     * 本次发送所使用的标签ID. 此标签需要事先创建
     *
     * @param labelid 标签ID
     * @return E
     */
    public E labelId(int labelid) {
        return addParameter("labelId", labelid);
    }

    /**
     * 邮件头部信息. JSON 格式, 比如:{"header1": "value1", "header2": "value2"}
     *
     * @param headers 邮件头部信息
     * @return E
     */
    public E headers(String headers) {
        return addParameter("headers", headers);
    }

    /**
     * 不返回 emailId. 有多个收件人时, 会返回 emailId 的列表
     *
     * @return E
     */
    public E notRespEmailId() {
        return addParameter("respEmailId", false);
    }

    /**
     * 使用发送回执
     *
     * @return E
     */
    public E useNotification() {
        return addParameter("useNotification", true);
    }


    protected E addParameters(String name, String[] values, String sep) {
        StringBuffer bufValue = new StringBuffer();
        if (values != null && values.length > 0) {
            String origValue = getParameter(name);
            if (isNotBlank(origValue)) {
                bufValue.append(origValue);
            }
            for (String value : values) {
                if (!isNotBlank(value)) {
                    continue;
                }
                if (bufValue.length() > 0) bufValue.append(sep);
                bufValue.append(value);
            }
        }
        return addParameter(name, bufValue.toString());
    }

    protected E addParameter(String name, Object value) {
        String strValue = String.valueOf(value);
        if (value == null || !isNotBlank(strValue)) {
            parameters.remove(name);
        } else {
            parameters.put(name, String.valueOf(value));
        }
        return getThis();
    }

    protected String getParameter(String name) {
        return parameters.get(name);
    }

    protected String[] getParameters(String name, String sep) {
        String values = parameters.get(name);
        return isNotBlank(values) ? values.split(sep) : new String[0];
    }

    protected final Map<String, String> getParameters() {
        if (!isRewrite)
            rewriteParameters();
        isRewrite = true;

        return parameters;
    }

    private boolean isNotBlank(String value) {
        return value != null && value.trim().length() > 0;
    }

    protected void rewriteParameters() {
    }

    protected abstract E getThis();

}
