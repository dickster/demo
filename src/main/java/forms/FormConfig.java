package forms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import demo.forms.WidgetConfig;

import java.util.List;

public class FormConfig {

    private String title;
    private String name;
    private String version;
    private List<WidgetConfig<?>> widgetConfigs = Lists.newArrayList();

    public List<WidgetConfig<?>> getWidgetConfigs() {
        return ImmutableList.copyOf(widgetConfigs);
    }

}
