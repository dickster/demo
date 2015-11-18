package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import forms.WidgetTypeEnum;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class WidgetConfig implements Config {

    private String property;
    private String name;
    private String type;
    private List<IValidator> validators = Lists.newArrayList();
    private String css = "form-control";
    private Map options = Maps.newHashMap();  // a place to store custom options.
    private List<String> mediatedAjaxEvents = Lists.newArrayList();
    private Boolean required;

    public WidgetConfig(@Nonnull String property, @Nonnull String type) {
        this.property = property;
        this.name = property; // use property as name by default.
        this.type = type;
    }

    public WidgetConfig(@Nonnull String property, WidgetTypeEnum type) {
        this(property, type.toString());
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getWidgetType() {
        return type;
    }

    public WidgetConfig name(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
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

    public Boolean getRequired() {
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
                "property='" + property + '\'' +
                ", type='" + type + '\'' +
                ", css='" + css + '\'' +
                '}';
    }

    public String getCss() {
        return css;
    }

    public WidgetConfig withCss(String css) {
        this.css = css;
        return this;
    }

}

