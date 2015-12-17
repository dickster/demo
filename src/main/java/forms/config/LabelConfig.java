package forms.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.Label2;
import org.apache.commons.lang3.StringUtils;

import java.text.Format;
import java.util.List;

public class LabelConfig extends Config<Label2> {

    private List<String> properties = Lists.newArrayList();
    private String text = null;  // this should be localized!
    private boolean htmlStrings = false;
    private @DontSendInJson Format format;
    private String icon;

    public LabelConfig(String name) {
        super(name, WidgetTypeEnum.LABEL);
        text(name);
    }

    public LabelConfig(String name, boolean property) {
        super(name, WidgetTypeEnum.LABEL);
    }

    public LabelConfig(String name, Format format, String... properties) {
        super(name, WidgetTypeEnum.LABEL);
        this.format = format;
        this.properties = Lists.newArrayList(properties);
    }

    public LabelConfig withHtmlStrings() {
        this.htmlStrings = true;
        return this;
    }

    public boolean isHtmlStrings() {
        return htmlStrings;
    }

    public LabelConfig withIcon(String icon) {
        Preconditions.checkArgument(StringUtils.isNotBlank(icon));
        this.icon = icon.startsWith("fa ") ? icon : "fa " + icon;
        withHtmlStrings();
        return this;
    }

    public LabelConfig text(String value) {
        this.text = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public Format getFormat() {
        return format;
    }

    @Override
    public Label2 create(String id) {
        return new Label2(id, this);
    }

    public String getIcon() {
        return icon;
    }
}
