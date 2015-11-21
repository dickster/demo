package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import forms.WidgetTypeEnum;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public abstract class WidgetConfig extends AbstractConfig {

    private List<IValidator> validators = Lists.newArrayList();
    private List<String> mediatedAjaxEvents = Lists.newArrayList();
    private boolean required;
    private Map options = Maps.newHashMap();  // a place to store custom options.

    public WidgetConfig(@Nonnull String property, @Nonnull String type) {
        super(property, type);
        withCss("form-control");
    }

    public WidgetConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString());
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
                ", options=" + options +
                '}';
    }
}

