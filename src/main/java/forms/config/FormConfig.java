package forms.config;

import com.google.common.collect.Maps;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

import java.util.Map;

public class FormConfig<T> extends GroupConfig {

    private IFormValidator validator;
    private Map<String, String> typeToPlugin = Maps.newHashMap();

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
