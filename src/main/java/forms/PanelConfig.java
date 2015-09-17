package forms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class PanelConfig {

    private String title;
    private String name;
    private List<WidgetConfig> widgetConfigs = Lists.newArrayList();

    public List<WidgetConfig> getWidgetConfigs() {
        return ImmutableList.copyOf(widgetConfigs);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
