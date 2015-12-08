package forms.validation;

import static ch.lambdaj.Lambda.on;

public class ValidationFields<T> {

    private Class<T> clazz;
    private T proxy;

    public ValidationFields(Class<T> clazz) {
        this.clazz = clazz;
        this.proxy = on(clazz);
    }

    protected T getObj() {
        return proxy;
    }


}

