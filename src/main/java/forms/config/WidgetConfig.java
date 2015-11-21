package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class WidgetConfig extends AbstractConfig {

    private /*not sent with json*/List<IValidator> validators = Lists.newArrayList();
    private List<String> mediatedAjaxEvents = Lists.newArrayList();
    private boolean required;

    public WidgetConfig(@Nonnull String property, @Nonnull String type, String pluginName) {
        super(property, type, pluginName);
        withCss("form-control");
    }

    public WidgetConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
    }

    public WidgetConfig addAjaxEvent(String event) {
        mediatedAjaxEvents.add(event);
        return this;
    }

    public WidgetConfig addValidator(IValidator<?> validator) {
        validators.add(validator);
        return this;
    }

    public List<IValidator> getValidators() {
        return ImmutableList.copyOf(validators);
    }

    public boolean isRequired() {
        return required;
    }

    public WidgetConfig required(Boolean required) {
        this.required = required;
        return this;
    }

    public WidgetConfig required() {
        return required(true);
    }

    @Override
    public String toString() {
        return "WidgetConfig{" +
                "validators=" + validators +
                ", mediatedAjaxEvents=" + mediatedAjaxEvents +
                ", required=" + required +
                ", options=" + getOptions() +
                '}';
    }
}

