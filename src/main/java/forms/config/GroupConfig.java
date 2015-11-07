package forms.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;

import javax.annotation.Nonnull;
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

    public GroupConfig withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public final WidgetTypeEnum getType() {
        return WidgetTypeEnum.GROUP;
    }

    public GroupConfig withName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRenderBodyOnly() {
        return renderBodyOnly;
    }

    public GroupConfig withRenderBodyOnly(Boolean renderBodyOnly) {
        this.renderBodyOnly = renderBodyOnly;
        return this;
    }

    public GroupConfig withConfigs(Config... configs) {
        return withConfigs(Lists.newArrayList(configs));
    }

    public GroupConfig withConfigs(@Nonnull List<Config> configs) {
        this.configs = configs;
        return this;
    }

    public GroupConfig withConfig(Config config) {
        configs.add(config);
        return this;
    }

}
