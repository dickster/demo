package forms;

import com.google.common.collect.Lists;

import java.util.List;

public class PanelConfig implements HasWidgetConfigs {

    private String title;
    private List<WidgetConfig<?>> widgetConfigs = Lists.newArrayList();

    @Override
    public List<WidgetConfig<?>> getWidgetConfigs() {
        return widgetConfigs;
    }


}
