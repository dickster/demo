package forms.spring;

public class StringLoader {

    public String get(String key) {
        // in the future, this will go to a localized database or whatever.
        // it will probably have to be prototype bean that has sessionBean injected into to access user & locale.
        if (key.startsWith("label.")) {
            return key.substring("label.".length());
        }
        return key;
    }
}
