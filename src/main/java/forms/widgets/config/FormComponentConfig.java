package forms.widgets.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import org.apache.wicket.Component;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class FormComponentConfig<T extends Component & HasConfig> extends Config<T> {

    private List<IValidator> validators = Lists.newArrayList();
    private boolean required;

    public FormComponentConfig(@Nonnull String property, @Nonnull String type, String pluginName) {
        super(property, type, pluginName);
    }

    public FormComponentConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString(), type.getPluginName());
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
                ", required=" + required +
                ", options=" + getOptions() +
                '}';
    }
}

