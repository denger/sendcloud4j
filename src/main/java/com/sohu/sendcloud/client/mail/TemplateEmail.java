package com.sohu.sendcloud.client.mail;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TemplateEmail extends Email<TemplateEmail> {

    private boolean useMailList = false;

    private SubStitutionVars.Sub subs;

    public TemplateEmail(String template) {
        addParameter("template_invoke_name", template);
    }

    public TemplateEmail substitutionVars(SubStitutionVars.Sub sub) {
        this.subs = sub;
        return getThis();
    }

    public TemplateEmail useMailList() {
        useMailList = true;
        return addParameter("use_maillist", String.valueOf(useMailList));
    }

    @Override
    protected TemplateEmail getThis() {
        return this;
    }

    @Override
    protected void rewriteParameters() {
        JSONObject ret = new JSONObject();
        if (subs == null) {
            subs = SubStitutionVars.sub();
        }
        ret.put("sub", subs.get());
        List<String> tos = new ArrayList<String>();

        // TODO 支持批量
        if (!useMailList) {
            tos.add(to());
            // 去除 to 参数
            to(null);
        }
        ret.put("to", tos);
        addParameter("substitution_vars", ret.toString());
    }
}
