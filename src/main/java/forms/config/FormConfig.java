package forms.config;

import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig {

    private IFormValidator validator;
    //optional .js var used to layout form.
    private String layoutVar;

    public FormConfig() {
        super("a");
        withRenderBodyOnly(false);
        withCss("form");
        withLayoutVar("layout_"+getName());
    }

    public IFormValidator getValidator() {
        return validator;
    }

    public FormConfig<T> withValidator(IFormValidator validator) {
        this.validator = validator;
        return this;
    }

    public FormConfig withLayoutVar(String layoutVar) {
        this.layoutVar = layoutVar;
        return this;
    }

    public String getLayoutVar() {
        return layoutVar;
    }
}
