package forms.spring;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.wicket.Session;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StringLoader {

    //just a temp stash for strings... this will be in db somewhere.
    static final Map<String, String> ENGLISH =
            new ImmutableMap.Builder<String, String>()
                    .put("label.reject", "we currently do not do business in your country.")
                    .put("label.thanks", "thank you for your payment of...")
                    .put("label.paymentMethod", "payment method")
                    .put("label.email", "email")
                    .put("label.phone", "phone")
                    .put("label.bogus", "blah")
                    .put("label.names", "my name is {1}, {0}.")
                    .put("label.year", "year")
                    .put("label.model", "model")
                    .put("label.age", "age")
                    .put("label.vin", "vin")
                    .put("label.color", "color")
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

    private Map<String, String> FRENCH = Maps.newHashMap();

    public StringLoader() {
        int i = 0;
        List<String> prefixes = Lists.newArrayList("Le ","La ", "L'", "Une ", "C'est une ", "Par ");
        for (String key:ENGLISH.keySet()) {
            String englishValue = ENGLISH.get(key);
            FRENCH.put(key, prefixes.get(i)+englishValue);
            i = (i+1)%prefixes.size();
        }
    }

    public String get(String key) {
        Session session = Session.get();
        String language = (String) session.getAttribute("language");
        String result = (language != null && "french".equalsIgnoreCase(language.toString()))
                ? FRENCH.get(key)
                : ENGLISH.get(key);
        return result==null ? unresolved(key) : result;
    }

    private String unresolved(String key) {
        return "["+key+"]";
    }
}
