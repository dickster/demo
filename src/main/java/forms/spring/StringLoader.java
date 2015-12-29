package forms.spring;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class StringLoader {
    //just a temp stash for strings... this really should be in db somewhere.
    static final Map<String, String> STRINGS =
            new ImmutableMap.Builder<String, String>()
                    .put("label.bogus", "blah")
                    .put("label.names", "my name is {1}, {0}.")
                    .put("label.age", "age")
                    .put("label.middle", "middle")
                    .put("label.first", "first")
                    .put("label.last", "last")
                    .put("label.occupation", "occupation")
                    .put("button.next", "next")
                    .put("button.ok", "ok")
                    .put("button.refresh", "refresh")
                    .put("button.submit", "submit")
            .build();

    public String get(String key) {
        // it will probably have to be prototype bean that has sessionBean injected into to access user & locale.
        String value = STRINGS.get(key);
        return value==null ? unresolved(key) : value;
    }

    private String unresolved(String key) {
        return "["+key+"]";
    }
}
