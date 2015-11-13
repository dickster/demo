package forms.config;

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
    private String css = "col_md_6";
    private Map options = Maps.newHashMap();  // a place to store custom options.

    public String getType() {
        return type;
    }

    private String acordProperty;
    private String acordVersion;
    private List<String> mediatedAjaxEvents = Lists.newArrayList();
    private List<IValidator<?>> validations = Lists.newArrayList();
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

    public String getAcordProperty() {
        return acordProperty;
    }

    public String getProperty() {
        return property;
    }
    //other config stuff...visible, css class,

    public String getWidgetType() {
        return type;
    }

    public String getAcordVersion() {
        return acordVersion;
    }

    public WidgetConfig acordVersion(String acordVersion) {
        this.acordVersion = acordVersion;
        return this;
    }

    public WidgetConfig name(String name) {
        this.name = name;
        return this;
    }

    public WidgetConfig addAjaxEvent(String event) {
        mediatedAjaxEvents.add(event);
        return this;
    }

    public WidgetConfig addValidator(IValidator<?> validator) {
        validations.add(validator);
        return this;
    }

    public List<IValidator<?>> getValidations() {
        return validations;
    }

    public Boolean getRequired() {
        return required;
    }

    public WidgetConfig required(Boolean required) {
        this.required = required;
        return this;
    }

    @Override
    public String toString() {
        return "WidgetConfig{" +
                "property='" + property + '\'' +
                ", type='" + type + '\'' +
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

