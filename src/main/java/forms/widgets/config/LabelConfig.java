package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.WfLabel;

import java.util.List;

public class LabelConfig extends Config<WfLabel> {

    private List<String> data = Lists.newArrayList();
    private String /*name of spring bean*/ formatter;
    private boolean useParentModel;
    private boolean htmlStrings;

    public LabelConfig(String id) {
        super(id, WidgetTypeEnum.LABEL);
        withTagName("label");
    }

    // new LabelConfig("hello", "name.first");
    public LabelConfig(String id, String property) {
        this(property);
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
    public WfLabel create(String id) {
        return new WfLabel(id, this);
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
