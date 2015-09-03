package forms;

import com.google.common.collect.Lists;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.validation.IValidator;

import javax.annotation.Nonnull;
import java.util.List;

public class WidgetConfig<T extends Component> {

    public static final MetaDataKey<String> NAME = new MetaDataKey<String>() {};

    private String property;
    private String name;
    private WidgetTypeEnum type;
    private String acordProperty;
    private String acordVersion;
    private List<String> mediatedAjaxEvents = Lists.newArrayList();
    private List<IValidator<?>> validations = Lists.newArrayList();
    private Boolean required;


    public WidgetConfig(@Nonnull String property, @Nonnull String label, @Nonnull WidgetTypeEnum type) {
        this.property = property;
        this.name = label;
        this.type = type;
    }

    public WidgetConfig(@Nonnull String property, @Nonnull WidgetTypeEnum type) {
        this.property = property;
        this.name = property; // use property as name by default.
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public WidgetTypeEnum getType() {
        return type;
    }

    public String getAcordProperty() {
        return acordProperty;
    }

    public String getProperty() {
        return property;
    }
    //other config stuff...visible, css class,

    public WidgetTypeEnum getWidgetType() {
        return type;
    }

    public String getAcordVersion() {
        return acordVersion;
    }

    public void setAcordVersion(String acordVersion) {
        this.acordVersion = acordVersion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WidgetConfig addAjaxEvent(String event) {
        mediatedAjaxEvents.add(event);
        return this;
    }

    public WidgetConfig addValidator(IValidator<?> validator) {
        validations.add(validator);
        return this;
    }
}
