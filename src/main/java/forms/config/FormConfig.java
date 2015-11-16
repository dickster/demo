package forms.config;

import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig {

    private IFormValidator validator;

    public FormConfig(String name) {
        super(name);
        withRenderBodyOnly(false);
        withCss("form");
    }

    public IFormValidator getValidator() {
        return validator;
    }

    public FormConfig<T> withValidator(IFormValidator validator) {
        this.validator = validator;
        return this;
    }

}
