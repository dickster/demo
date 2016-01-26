package forms.spring;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class StringLoader {
    //just a temp stash for strings... this really should be in db somewhere.
    static final Map<String, String> STRINGS =
            new ImmutableMap.Builder<String, String>()
                    .put("label.reject", "we currently do not do business in your country.")
                    .put("label.thanks", "thank you for your payment of...")
                    .put("label.paymentMethod", "payment method")
                    .put("label.bogus", "blah")
                    .put("label.names", "my name is {1}, {0}.")
                    .put("label.age", "age")
                    .put("label.middle", "middle")
                    .put("label.address", "address")
                    .put("label.country", "country")
                    .put("label.first", "first")
                    .put("label.last", "last")
                    .put("label.occupation", "occupation")
                    .put("label.vehicleType","vehicle type")
                    .put("label.one", "MasterCard!")
                    .put("label.two", "VISA")
                    .put("label.three", "Seriously? you have Diner's Club?")
                    .put("label.ccNumber", "credit card #")
                    .put("label.ccExpiry", "expiry date")
                    .put("label.ccSecurity", "security code")
                    .put("label.paymentFrequency", "paymentFrequency")
                    .put("label.hello", "hello")
                    .put("label.world", "world")
                    .put("button.next", "next")
                    .put("button.cancel", "cancel")
                    .put("button.finished", "finished")
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
