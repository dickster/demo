package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.Label2;

import java.util.List;

public class LabelConfig extends Config<Label2> {

    private List<String> data = Lists.newArrayList();
    private String /*name of spring bean*/ formatter;
    private boolean useParentModel;
    private boolean htmlStrings = false;

    public LabelConfig(String id) {
        super(id, WidgetTypeEnum.LABEL);
    }

    // new LabelConfig("hello", "name.first");
    public LabelConfig(String id, String property) {
        super(property, WidgetTypeEnum.LABEL);
        withId(id);
        useParentModel = true;  //otherwise, will assume property is a string resource key.
    }

    public LabelConfig withData(List<String> data) {
        this.data = data;
        return this;
    }

    public LabelConfig withData(String... data) {
        this.data = Lists.newArrayList(data);
        return this;
    }

    public LabelConfig(String name, boolean property) {
        super(name, WidgetTypeEnum.LABEL);
    }

    public LabelConfig withHtmlStrings() {
        this.htmlStrings = true;
        return this;
    }

    public boolean isHtmlStrings() {
        return htmlStrings;
    }

    @Override
    public Label2 create(String id) {
        return new Label2(id, this);
    }

    public String getFormatter() {
        return formatter;
    }

    public boolean isUseParentModel() {
        return useParentModel;
    }

    public List<String> getData() {
        return data;
    }

    public LabelConfig withFormatter(String formatter) {
        this.formatter = formatter;
        return this;
    }
}
