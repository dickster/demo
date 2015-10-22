package forms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

public class GroupConfig implements Config {

    private String title;
    private String name;

    // contains widgets or other groups...?
    private Boolean renderBodyOnly = false;

    private List<Config> configs = Lists.newArrayList();

    public GroupConfig() {
    }

    public List<Config> getConfigs() {
        return ImmutableList.copyOf(configs);
    }

    protected List<WidgetConfig> getWidgetConfigs() {
        List<WidgetConfig> result = Lists.newArrayList();
        for (Config config:configs) {
            if (config instanceof WidgetConfig) {
                result.add((WidgetConfig)config);
            }
            else if (config instanceof GroupConfig) {
                result.addAll(((GroupConfig)config).getWidgetConfigs());
            }
        }
        return result;
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

    @Override
    public final WidgetTypeEnum getType() {
        return WidgetTypeEnum.GROUP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRenderBodyOnly() {
        return renderBodyOnly;
    }

    public void setRenderBodyOnly(Boolean renderBodyOnly) {
        this.renderBodyOnly = renderBodyOnly;
    }


}
