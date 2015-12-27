package io.jstack.sendcloud4j.mail;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateEmail extends Email<TemplateEmail> {

    private boolean useAddressList = false;

    private Substitution.SubGroup subGroup;

    public TemplateEmail(String template) {
        addParameter("templateInvokeName", template);
    }

    public TemplateEmail substitutionVars(Substitution.Sub sub) {
        this.subGroup = Substitution.subGroup().add(sub);
        return getThis();
    }

    public TemplateEmail substitutionVars(Substitution.SubGroup subGroup) {
        this.subGroup = subGroup;
        return getThis();
    }

    public TemplateEmail useAddressList() {
        useAddressList = true;
        return addParameter("useAddressList", String.valueOf(useAddressList));
    }

    @Override
    protected TemplateEmail getThis() {
        return this;
    }

    @Override
    protected void rewriteParameters() {
        JSONObject ret = new JSONObject();
        if (subGroup == null) {
            subGroup = Substitution.subGroup();
        }
        ret.put("sub", subGroup.toSubsMap());
        List<String> toAddrs = new ArrayList<String>();

        if (!useAddressList) {
            toAddrs.addAll(Arrays.asList(to()));
            // 去除 to 参数
            to("");
            ret.put("to", toAddrs);
            addParameter("xsmtpapi", ret.toString());
        }
    }
}
