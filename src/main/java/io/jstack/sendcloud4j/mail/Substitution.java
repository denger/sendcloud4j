package io.jstack.sendcloud4j.mail;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块变量定义。
 *
 * @author denger
 */
public class Substitution {

    public static Sub sub() {
        return new Sub();
    }

    public static SubGroup subGroup() {
        return new SubGroup();
    }

    public static class Sub {

        private Map<String, String> sub = new HashMap<String, String>();

        public Sub set(String name, String value) {
            String subName = "%" + name + "%";
            sub.put(subName, value);
            return this;
        }

        public String get(String name) {
            return sub.get("%" + name + "%");
        }

        protected Map<String, String> get() {
            return sub;
        }

        private List<String> getList(String value) {
            List<String> vars = new ArrayList<String>(1);

            vars.add(value);
            return vars;
        }
    }

    public static class SubGroup {
        private Map<String, List<String>> subsMap = new HashMap<String, List<String>>();

        private List<Sub> subs = new ArrayList<Sub>();

        protected List<Sub> getSubs() {
            return subs;
        }

        public SubGroup add(Sub sub) {
            subs.add(sub);

            Map<String, String> vars = sub.get();
            for (Map.Entry<String, String> var : vars.entrySet()) {
                String subName = var.getKey();

                List<String> values = subsMap.get(subName);
                if (values == null) {
                    values = new ArrayList<String>();
                }
                values.add(var.getValue());

                subsMap.put(subName, values);
            }
            return this;
        }

        public Map<String, List<String>> toSubsMap() {
            return this.subsMap;
        }
    }
}
