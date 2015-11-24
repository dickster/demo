package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class FormComponentConfig extends Config {

    @DontSendInJson
    private List<IValidator> validators = Lists.newArrayList();

    @DontSendInJson  // don't include in gson - not required by .js side.
    private List<String> mediatedAjaxEvents = Lists.newArrayList();

    @DontSendInJson
    private boolean required;

    public FormComponentConfig(@Nonnull String property, @Nonnull String type, String pluginName) {
        super(property, type, pluginName);
        withCss("form-control");
    }

    public FormComponentConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
    }

    public FormComponentConfig addAjaxEvent(String event) {
        mediatedAjaxEvents.add(event);
        return this;
    }

    public FormComponentConfig addValidator(IValidator<?> validator) {
        validators.add(validator);
        return this;
    }

    public List<IValidator> getValidators() {
        return ImmutableList.copyOf(validators);
    }

    public boolean isRequired() {
        return required;
    }

    public FormComponentConfig required(Boolean required) {
        this.required = required;
        return this;
    }

    public FormComponentConfig required() {
        return required(true);
    }

    @Override
    public String toString() {
        return "FormComponentConfig{" +
                "validators=" + validators +
                ", mediatedAjaxEvents=" + mediatedAjaxEvents +
                ", required=" + required +
                ", options=" + getOptions() +
                '}';
    }
}

