package io.jstack.sendcloud.mail;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubStitutionVars {

    public static Sub sub() {
        return new Sub();
    }

    public static class Sub {

        private Map<String, List<String>> subs = new HashMap<String, List<String>>();

        public Sub set(String name, String value) {
            String subName = "%" + name + "%";
            List<String> values = subs.get(subName);
            if (values == null) {
                values = new ArrayList<String>();
            }
            values.add(value);

            subs.put(subName, values);
            return this;
        }

        public Map<String, List<String>> get() {
            return subs;
        }

    }
}
